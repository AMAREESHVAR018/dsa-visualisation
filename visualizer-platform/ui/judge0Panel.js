const LANGUAGE_OPTIONS = [
  { id: 62, label: "Java (OpenJDK 13)" },
  { id: 71, label: "Python 3" },
  { id: 54, label: "C++ (GCC 9.2)" }
];

const SAMPLE_CODE = {
  62: `public class Main {\n  public static void main(String[] args) {\n    System.out.println("Hello from Judge0 Java");\n  }\n}`,
  71: "print('Hello from Judge0 Python')",
  54: "#include <bits/stdc++.h>\nusing namespace std;\nint main(){ cout << \"Hello from Judge0 C++\\n\"; }"
};

export function renderJudge0Panel(container, options = {}) {
  const endpoint = options.endpoint || "/api/judge0/execute";
  const state = {
    languageId: 62,
    sourceCode: SAMPLE_CODE[62],
    stdin: ""
  };

  container.innerHTML = `
    <p class="judge-note">Secure mode: this UI only calls your backend proxy endpoint ${endpoint}. API keys stay server-side.</p>
    <label class="input-group">
      <span>Language</span>
      <select id="judgeLanguage"></select>
    </label>
    <label class="input-group">
      <span>Source Code</span>
      <textarea id="judgeCode" rows="8"></textarea>
    </label>
    <label class="input-group">
      <span>Stdin</span>
      <textarea id="judgeStdin" rows="3" placeholder="Optional input..."></textarea>
    </label>
    <div class="input-actions">
      <button type="button" id="judgeRunButton" class="btn-solid">Run via Judge0</button>
      <button type="button" id="judgeSampleButton" class="btn-soft">Load Sample</button>
    </div>
    <pre id="judgeOutput" class="judge-output">Output will appear here.</pre>
  `;

  const languageSelect = container.querySelector("#judgeLanguage");
  const codeInput = container.querySelector("#judgeCode");
  const stdinInput = container.querySelector("#judgeStdin");
  const runButton = container.querySelector("#judgeRunButton");
  const sampleButton = container.querySelector("#judgeSampleButton");
  const output = container.querySelector("#judgeOutput");

  LANGUAGE_OPTIONS.forEach(option => {
    const element = document.createElement("option");
    element.value = String(option.id);
    element.textContent = option.label;
    languageSelect.appendChild(element);
  });

  languageSelect.value = String(state.languageId);
  codeInput.value = state.sourceCode;

  languageSelect.addEventListener("change", () => {
    state.languageId = Number(languageSelect.value);
  });

  codeInput.addEventListener("input", () => {
    state.sourceCode = codeInput.value;
  });

  stdinInput.addEventListener("input", () => {
    state.stdin = stdinInput.value;
  });

  sampleButton.addEventListener("click", () => {
    codeInput.value = SAMPLE_CODE[state.languageId] || SAMPLE_CODE[62];
    state.sourceCode = codeInput.value;
  });

  runButton.addEventListener("click", async () => {
    output.textContent = "Running...";
    runButton.disabled = true;

    try {
      const response = await fetch(endpoint, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          language_id: state.languageId,
          source_code: state.sourceCode,
          stdin: state.stdin
        })
      });

      if (!response.ok) {
        const fallback = await response.text();
        throw new Error(`Proxy error (${response.status}): ${fallback || "No response body"}`);
      }

      const data = await response.json();
      const formatted = [
        `Status: ${data.status?.description || "Unknown"}`,
        data.stdout ? `STDOUT:\n${data.stdout}` : "",
        data.stderr ? `STDERR:\n${data.stderr}` : "",
        data.compile_output ? `COMPILE OUTPUT:\n${data.compile_output}` : "",
        data.time ? `Time: ${data.time}s` : "",
        data.memory ? `Memory: ${data.memory} KB` : ""
      ].filter(Boolean).join("\n\n");

      output.textContent = formatted || "Execution finished with no output.";
    } catch (error) {
      output.textContent = [
        "Judge0 proxy call failed.",
        String(error.message || error),
        "Tip: run the secure proxy server and verify endpoint path."
      ].join("\n");
    } finally {
      runButton.disabled = false;
    }
  });
}
