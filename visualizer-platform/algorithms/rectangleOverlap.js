export const rectangleOverlapAlgorithm = {
  key: "RectangleOverlap",
  label: "Rectangle Overlap",
  category: "array",
  description: "Check if two rectangles overlap by comparing coordinates.",
  codeLines: ["boolean overlap(Rectangle a, Rectangle b) {", "  return a.x < b.x + b.width && a.x + a.width > b.x", "      && a.y < b.y + b.height && a.y + a.height > b.y;", "}"],
  inputSchema: [{ id: "rect1", label: "Rect1 (x,y,w,h)", type: "array", defaultValue: [0, 0, 5, 5] }, { id: "rect2", label: "Rect2 (x,y,w,h)", type: "array", defaultValue: [3, 3, 4, 4] }],
  createDefaultInput() { return { rect1: [0, 0, 5, 5], rect2: [3, 3, 4, 4] }; },
  buildTimeline({ rect1 = [], rect2 = [] }) {
    const r1 = Array.isArray(rect1) ? rect1 : [0, 0, 5, 5];
    const r2 = Array.isArray(rect2) ? rect2 : [3, 3, 4, 4];
    const steps = [];
    const [x1, y1, w1, h1] = r1;
    const [x2, y2, w2, h2] = r2;
    steps.push({ description: `Check overlap: R1(${x1},${y1},${w1}x${h1}) vs R2(${x2},${y2},${w2}x${h2})`, why: "Compare boundaries", stateSnapshot: { mode: "array", values: [x1, y1, w1, h1, x2, y2, w2, h2] }, highlightedElements: { compare: [0, 1, 2, 3, 4, 5, 6, 7] }, codeLine: 1, tracker: { rect1: r1, rect2: r2 }, passCount: 0 });
    const overlaps = x1 < x2 + w2 && x1 + w1 > x2 && y1 < y2 + h2 && y1 + h1 > y2;
    steps.push({ description: overlaps ? "✓ Rectangles overlap" : "✗ No overlap", why: overlaps ? "All conditions satisfied" : "Boundaries don't overlap", stateSnapshot: { mode: "array", values: overlaps ? [1, 1, 1, 1, 1, 1, 1, 1] : [0, 0, 0, 0, 0, 0, 0, 0] }, highlightedElements: { sorted: overlaps ? [0, 1, 2, 3, 4, 5, 6, 7] : [] }, codeLine: 2, tracker: { result: overlaps ? "OVERLAP" : "NO OVERLAP" }, passCount: 1 });
    return { timeline: steps };
  }
};
