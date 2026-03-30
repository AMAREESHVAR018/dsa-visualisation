export const fibonacciDPAlgorithm = {
  key: "FibonacciUsingDynamicProgramming",
  label: "Fibonacci Using Dynamic Programming",
  category: "array",
  description: "Compute Fibonacci sequence using DP with memoization.",
  codeLines: ["long[] memo = new long[n+1];", "memo[0]=0; memo[1]=1;", "for (int i=2; i<=n; i++)", "  memo[i] = memo[i-1] + memo[i-2];", "return memo[n];"],
  inputSchema: [{ id: "n", label: "nth Fibonacci (1-15)", type: "number", defaultValue: 10 }],
  createDefaultInput() { return { n: 10 }; },
  buildTimeline({ n = 10 }) {
    const num = Math.min(Math.max(parseInt(n), 1), 15);
    const steps = [];
    const arr = Array(num + 1);
    arr[0] = 0; arr[1] = 1;
    steps.push({ description: `Build Fib(${num}) DP array`, why: "Base: F(0)=0, F(1)=1", stateSnapshot: { mode: "array", array: arr.slice() }, highlightedElements: {}, codeLine: 2, tracker: { dp: "init" }, passCount: 0 });
    for (let i = 2; i <= num; i++) { arr[i] = arr[i-1] + arr[i-2]; steps.push({ description: `F(${i})=${arr[i-1]}+${arr[i-2]}=${arr[i]}`, why: "Sum previous pair", stateSnapshot: { mode: "array", array: arr.slice() }, highlightedElements: {}, codeLine: 4, tracker: { i, result: arr[i] }, passCount: i }); }
    return { timeline: steps };
  }
};
