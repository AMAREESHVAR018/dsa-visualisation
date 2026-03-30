export function parseNumberList(text, fallback = []) {
  if (!text || !text.trim()) return [...fallback];
  const values = text
    .split(",")
    .map(token => Number(token.trim()))
    .filter(value => Number.isFinite(value));
  return values.length ? values : [...fallback];
}

export function parseEdges(text, fallback = []) {
  const rows = (text || "").split(/\r?\n/).map(row => row.trim()).filter(Boolean);
  const edges = [];

  for (const row of rows) {
    const parts = row.split(/[\s,]+/).map(Number);
    if (parts.length < 3 || parts.some(value => !Number.isFinite(value))) continue;
    edges.push({ u: parts[0], v: parts[1], w: parts[2] });
  }

  return edges.length ? edges : [...fallback];
}

export function buildCircleNodes(count, width = 760, height = 340) {
  const nodes = [];
  const radius = Math.min(width, height) * 0.36;
  const cx = width / 2;
  const cy = height / 2;

  for (let i = 0; i < count; i += 1) {
    const angle = (Math.PI * 2 * i) / Math.max(1, count) - Math.PI / 2;
    nodes.push({
      id: i,
      x: Math.round(cx + radius * Math.cos(angle)),
      y: Math.round(cy + radius * Math.sin(angle))
    });
  }

  return nodes;
}

export function cloneArray(array) {
  return Array.isArray(array) ? [...array] : [];
}

export function boolArray(size, initial = false) {
  return Array.from({ length: size }, () => initial);
}
