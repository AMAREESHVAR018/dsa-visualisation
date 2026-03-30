/**
 * Dynamic Programming - Fibonacci Sequence
 * Compute nth Fibonacci number using bottom-up DP
 */

export const dynamicProgrammingAlgorithm = {
  key: "DynamicProgramming",
  label: "Dynamic Programming (Fibonacci)",
  category: "array",
  description:
    "Compute Fibonacci number using bottom-up DP approach with memoization array.",
  codeLines: [
    "if (n <= 1) return n;",
    "long[] dp = new long[n + 1];",
    "dp[0] = 0; dp[1] = 1;",
    "for (int i = 2; i <= n; i++) {",
    "  dp[i] = dp[i - 1] + dp[i - 2];",
    "}",
    "return dp[n];"
  ],
  inputSchema: [
    {
      id: "n",
      label: "nth Fibonacci (1-20)",
      type: "number",
      placeholder: "e.g., 10",
      defaultValue: 10
    }
  ],
  createDefaultInput() {
    return { n: 10 };
  },
  buildTimeline({ n = 10 }) {
    const num = Math.min(Math.max(parseInt(n), 1), 20);
    const steps = [];
    const dp = Array(num + 1).fill(0);

    steps.push(makeStep({
      description: `Compute Fibonacci(${num}) using DP array`,
      why: "Build up from F(0) and F(1) to F(n)",
      dp,
      index: 0,
      state: "init",
      tracker: { n: num, base: "F(0)=0, F(1)=1" },
      codeLine: 1
    }));

    // Base cases
    dp[0] = 0;
    dp[1] = 1;

    steps.push(makeStep({
      description: "Initialize dp array with base cases: dp[0]=0, dp[1]=1",
      why: "Fibonacci base cases: F(0)=0, F(1)=1",
      dp: dp.slice(),
      index: 1,
      state: "base",
      tracker: { dp0: 0, dp1: 1, complete: false },
      codeLine: 3
    }));

    // Fill DP array
    for (let i = 2; i <= num; i++) {
      dp[i] = dp[i - 1] + dp[i - 2];

      steps.push(makeStep({
        description: `Compute dp[${i}] = dp[${i - 1}] + dp[${i - 2}] = ${dp[i - 1]} + ${dp[i - 2]} = ${dp[i]}`,
        why: "Each Fibonacci number is sum of previous two",
        dp: dp.slice(),
        index: i,
        state: "compute",
        tracker: {
          i,
          val1: dp[i - 1],
          val2: dp[i - 2],
          result: dp[i],
          progress: `${i}/${num}`
        },
        codeLine: 5
      }));
    }

    steps.push(makeStep({
      description: `Complete! Fibonacci(${num}) = ${dp[num]}`,
      why: `Final DP array computed`,
      dp: dp.slice(),
      index: num,
      state: "done",
      tracker: {
        n: num,
        result: dp[num],
        sequence: `[${dp.map((v, i) => `F(${i})=${v}`).join(", ")}]`
      },
      codeLine: 7
    }));

    return { timeline: steps };
  }
};

function makeStep({
  description,
  why,
  dp,
  index,
  state,
  tracker,
  codeLine
}) {
  return {
    description,
    why,
    stateSnapshot: {
      mode: "array",
      array: dp,
      index,
      dp,
      state
    },
    highlightedElements: {
      state,
      currentIndex: index
    },
    codeLine,
    tracker,
    passCount: index
  };
}
