export function renderCodePanel(container, lines) {
  container.innerHTML = "";
  const fragment = document.createDocumentFragment();

  lines.forEach((line, index) => {
    const row = document.createElement("div");
    row.className = "code-row";
    row.dataset.line = String(index + 1);
    row.innerHTML = `<span class="line-number">${index + 1}</span><code>${escapeHtml(line)}</code>`;
    fragment.appendChild(row);
  });

  container.appendChild(fragment);
}

export function highlightCodeLine(container, lineNumber) {
  const rows = container.querySelectorAll(".code-row");
  rows.forEach(row => row.classList.remove("active-line"));

  const active = container.querySelector(`.code-row[data-line='${lineNumber}']`);
  if (active) {
    active.classList.add("active-line");
    active.scrollIntoView({ block: "nearest" });
  }
}

function escapeHtml(value) {
  return value
    .replaceAll("&", "&amp;")
    .replaceAll("<", "&lt;")
    .replaceAll(">", "&gt;");
}
