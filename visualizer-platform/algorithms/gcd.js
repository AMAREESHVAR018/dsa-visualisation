export const gcdAlgorithm = {
  key: "GCDOfTwoNumbers",
  label: "GCD of Two Numbers",
  category: "array",
  description: "Find greatest common divisor using Euclidean algorithm.",
  codeLines: ["while (b != 0) {", "  int temp = b;", "  b = a % b;", "  a = temp;", "}", "return a;"],
  inputSchema: [{ id: "a", label: "Number A", type: "number", defaultValue: 48 }, { id: "b", label: "Number B", type: "number", defaultValue: 18 }],
  createDefaultInput() { return { a: 48, b: 18 }; },
  buildTimeline({ a = 48, b = 18 }) {
    let x = Math.abs(parseInt(a)), y = Math.abs(parseInt(b));
    const steps = [];
    steps.push({ description: `Find GCD(${x}, ${y})`, why: "Euclidean algorithm", stateSnapshot: { mode: "array", values: [x, y] }, highlightedElements: { compare: [0, 1] }, codeLine: 1, tracker: { a: x, b: y, step: 0 }, passCount: 0 });
    let step = 1;
    while (y !== 0) { const temp = y; const rem = x % y; y = rem; x = temp; steps.push({ description: `${x} % ${temp} = ${y}`, why: "Euclidean step", stateSnapshot: { mode: "array", values: [x, y] }, highlightedElements: { sorted: y === 0 ? [0] : [] }, codeLine: 4, tracker: { step, a: x, b: y }, passCount: step }); step++; }
    steps.push({ description: `GCD = ${x}`, why: "When b=0, a is GCD", stateSnapshot: { mode: "array", values: [x] }, highlightedElements: { sorted: [0] }, codeLine: 6, tracker: { result: x, steps: step }, passCount: step });
    return { timeline: steps };
  }
};
