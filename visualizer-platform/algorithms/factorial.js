export const factorialAlgorithm = {
  key: "FactorialOfANumber",
  label: "Factorial Of A Number",
  category: "array",
  description: "Calculate factorial n! = n × (n-1) × ... × 1",
  codeLines: ["long factorial(int n) {", "  if (n <= 1) return 1;", "  long result = 1;", "  for (int i = 2; i <= n; i++)", "    result *= i;", "  return result;", "}"],
  inputSchema: [{ id: "n", label: "Number (1-20)", type: "number", defaultValue: 5 }],
  createDefaultInput() { return { n: 5 }; },
  buildTimeline({ n = 5 }) {
    const num = Math.min(Math.max(parseInt(n), 1), 20);
    const steps = [];
    steps.push({ description: `Calculate ${num}!`, why: "Initialize result = 1", stateSnapshot: { mode: "array", values: [1] }, highlightedElements: { sorted: [0] }, codeLine: 3, tracker: { result: 1, step: 0 }, passCount: 0 });
    let result = 1;
    for (let i = 2; i <= num; i++) { result *= i; steps.push({ description: `${i-1}! × ${i} = ${result}`, why: "Multiply by next number", stateSnapshot: { mode: "array", values: [result] }, highlightedElements: { sorted: [0], index: 0 }, codeLine: 5, tracker: { i, result, step: i - 1 }, passCount: i }); }
    return { timeline: steps };
  }
};
