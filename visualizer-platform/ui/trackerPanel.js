export function renderTracker(container, tracker = {}) {
  container.innerHTML = "";

  const entries = Object.entries(tracker);
  if (!entries.length) {
    return;
  }

  // Use compact-tracker grid for responsive inline cards
  entries.forEach(([key, value]) => {
    const item = document.createElement("div");
    item.className = "tracker-item";
    item.innerHTML = `
      <div class="tracker-key">${escapeHtml(key)}</div>
      <div class="tracker-value">${escapeHtml(String(value))}</div>
    `;
    container.appendChild(item);
  });
}

function escapeHtml(value) {
  return value
    .replaceAll("&", "&amp;")
    .replaceAll("<", "&lt;")
    .replaceAll(">", "&gt;");
}
