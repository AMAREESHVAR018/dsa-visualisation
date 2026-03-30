# Judge0 Secure Proxy + Local Dev Server

Use this setup so your Judge0 API key never appears in browser code, while frontend and proxy run from the same local server.

## 1) Set environment variables

PowerShell:

```powershell
$env:JUDGE0_RAPID_KEY = "<your_rapidapi_key>"
$env:JUDGE0_RAPID_HOST = "judge0-ce.p.rapidapi.com"
$env:PORT = "8787"
$env:JUDGE0_PROXY_ORIGIN = "*"
```

## 2) Start unified local dev server (recommended)

```powershell
node D:/java/DSA_Handbook/visualizer-platform/server/devServer.mjs
```

This serves:

- `GET /algorithm-visualizer.html` (and all static files)
- `POST /api/judge0/execute` (proxy to Judge0)

Open:

`http://localhost:8787/algorithm-visualizer.html`

## 3) Start proxy-only server (optional)

```powershell
node D:/java/DSA_Handbook/visualizer-platform/server/judge0Proxy.mjs
```

## 4) Call from frontend

Frontend sends requests only to your proxy endpoint:

`POST /api/judge0/execute`

Body:

```json
{
  "language_id": 62,
  "source_code": "public class Main { public static void main(String[] args){ System.out.println(\"Hi\"); }}",
  "stdin": ""
}
```

The proxy forwards to Judge0 and returns result JSON.

## Security notes

- Never put `JUDGE0_RAPID_KEY` in client JavaScript.
- Restrict `JUDGE0_PROXY_ORIGIN` in production.
- Add auth/rate-limiting before exposing publicly.
