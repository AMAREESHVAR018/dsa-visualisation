import { createReadStream, existsSync, statSync } from "node:fs";
import { createServer } from "node:http";
import { dirname, extname, join, normalize } from "node:path";
import { fileURLToPath } from "node:url";

const __filename = fileURLToPath(import.meta.url);
const __dirname = dirname(__filename);
const projectRoot = normalize(join(__dirname, "..", ".."));

const PORT = Number(process.env.PORT || 8787);
const RAPID_HOST = process.env.JUDGE0_RAPID_HOST || "judge0-ce.p.rapidapi.com";
const RAPID_KEY = process.env.JUDGE0_RAPID_KEY || "";
const CORS_ORIGIN = process.env.JUDGE0_PROXY_ORIGIN || "*";

if (!RAPID_KEY) {
  console.warn("JUDGE0_RAPID_KEY is missing. Judge0 endpoint will fail until key is set.");
}

const mimeByExt = {
  ".html": "text/html; charset=utf-8",
  ".css": "text/css; charset=utf-8",
  ".js": "application/javascript; charset=utf-8",
  ".mjs": "application/javascript; charset=utf-8",
  ".json": "application/json; charset=utf-8",
  ".svg": "image/svg+xml",
  ".png": "image/png",
  ".jpg": "image/jpeg",
  ".jpeg": "image/jpeg",
  ".gif": "image/gif",
  ".webp": "image/webp"
};

const server = createServer(async (req, res) => {
  if (req.method === "OPTIONS") {
    writeCors(res);
    res.writeHead(204);
    res.end();
    return;
  }

  if (req.method === "POST" && req.url === "/api/judge0/execute") {
    await handleJudge0Proxy(req, res);
    return;
  }

  if (req.method !== "GET") {
    writeCors(res);
    res.writeHead(405, { "Content-Type": "application/json" });
    res.end(JSON.stringify({ error: "Method not allowed" }));
    return;
  }

  serveStatic(req, res);
});

server.listen(PORT, () => {
  console.log(`Dev server running at http://localhost:${PORT}`);
  console.log(`Open http://localhost:${PORT}/algorithm-visualizer.html`);
});

async function handleJudge0Proxy(req, res) {
  try {
    const body = await readJson(req);

    const upstream = await fetch(`https://${RAPID_HOST}/submissions?base64_encoded=false&wait=true`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "X-RapidAPI-Key": RAPID_KEY,
        "X-RapidAPI-Host": RAPID_HOST
      },
      body: JSON.stringify({
        language_id: body.language_id,
        source_code: body.source_code,
        stdin: body.stdin || ""
      })
    });

    const text = await upstream.text();
    writeCors(res);
    res.writeHead(upstream.status, { "Content-Type": "application/json" });
    res.end(text);
  } catch (error) {
    writeCors(res);
    res.writeHead(500, { "Content-Type": "application/json" });
    res.end(JSON.stringify({ error: String(error.message || error) }));
  }
}

function serveStatic(req, res) {
  writeCors(res);

  const rawUrl = req.url?.split("?")[0] || "/";
  const requested = rawUrl === "/" ? "/algorithm-visualizer.html" : rawUrl;
  const safePath = normalize(requested).replace(/^([.][.][/\\])+/, "");
  const fullPath = normalize(join(projectRoot, safePath));

  if (!fullPath.startsWith(projectRoot)) {
    res.writeHead(403, { "Content-Type": "application/json" });
    res.end(JSON.stringify({ error: "Forbidden" }));
    return;
  }

  let targetPath = fullPath;
  if (!existsSync(targetPath)) {
    res.writeHead(404, { "Content-Type": "application/json" });
    res.end(JSON.stringify({ error: "Not found" }));
    return;
  }

  const stats = statSync(targetPath);
  if (stats.isDirectory()) {
    targetPath = join(targetPath, "index.html");
    if (!existsSync(targetPath)) {
      res.writeHead(404, { "Content-Type": "application/json" });
      res.end(JSON.stringify({ error: "Not found" }));
      return;
    }
  }

  const mime = mimeByExt[extname(targetPath).toLowerCase()] || "application/octet-stream";
  res.writeHead(200, { "Content-Type": mime });
  createReadStream(targetPath).pipe(res);
}

function writeCors(res) {
  res.setHeader("Access-Control-Allow-Origin", CORS_ORIGIN);
  res.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
  res.setHeader("Access-Control-Allow-Headers", "Content-Type");
}

function readJson(req) {
  return new Promise((resolve, reject) => {
    let data = "";
    req.on("data", chunk => {
      data += chunk;
      if (data.length > 2_000_000) reject(new Error("Payload too large"));
    });
    req.on("end", () => {
      try {
        resolve(JSON.parse(data || "{}"));
      } catch {
        reject(new Error("Invalid JSON payload"));
      }
    });
    req.on("error", reject);
  });
}
