import { createServer } from "node:http";

const PORT = Number(process.env.PORT || 8787);
const RAPID_HOST = process.env.JUDGE0_RAPID_HOST || "judge0-ce.p.rapidapi.com";
const RAPID_KEY = process.env.JUDGE0_RAPID_KEY || "";

if (!RAPID_KEY) {
  console.warn("JUDGE0_RAPID_KEY is missing. Set it before running proxy.");
}

const CORS_ORIGIN = process.env.JUDGE0_PROXY_ORIGIN || "*";

const server = createServer(async (req, res) => {
  if (req.method === "OPTIONS") {
    writeCors(res);
    res.writeHead(204);
    res.end();
    return;
  }

  if (req.method === "POST" && req.url === "/api/judge0/execute") {
    try {
      const body = await readJson(req);
      const payload = {
        language_id: body.language_id,
        source_code: body.source_code,
        stdin: body.stdin || ""
      };

      const upstream = await fetch(`https://${RAPID_HOST}/submissions?base64_encoded=false&wait=true`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "X-RapidAPI-Key": RAPID_KEY,
          "X-RapidAPI-Host": RAPID_HOST
        },
        body: JSON.stringify(payload)
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
    return;
  }

  writeCors(res);
  res.writeHead(404, { "Content-Type": "application/json" });
  res.end(JSON.stringify({ error: "Not found" }));
});

server.listen(PORT, () => {
  console.log(`Judge0 proxy listening at http://localhost:${PORT}`);
  console.log("POST /api/judge0/execute");
});

function writeCors(res) {
  res.setHeader("Access-Control-Allow-Origin", CORS_ORIGIN);
  res.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
  res.setHeader("Access-Control-Allow-Headers", "Content-Type");
}

function readJson(req) {
  return new Promise((resolve, reject) => {
    let data = "";
    req.on("data", chunk => {
      data += chunk;
      if (data.length > 2_000_000) {
        reject(new Error("Payload too large"));
      }
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
