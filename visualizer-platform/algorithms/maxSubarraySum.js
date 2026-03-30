export const maxSubarraySumAlgorithm = {
  key: "MaximumSubarraySum",
  label: "Maximum Subarray Sum",
  category: "array",
  description: "Find contiguous subarray with largest sum using Kadane's algorithm.",
  codeLines: ["int maxSum = arr[0];", "int currentSum = arr[0];", "for (int i = 1; i < n; i++) {", "  currentSum = Math.max(arr[i], currentSum + arr[i]);", "  maxSum = Math.max(maxSum, currentSum);", "}", "return maxSum;"],
  inputSchema: [{ id: "arr", label: "Array", type: "array", defaultValue: [-2, 1, -3, 4, -1, 2, 1, -5, 4] }],
  createDefaultInput() { return { arr: [-2, 1, -3, 4, -1, 2, 1, -5, 4] }; },
  buildTimeline({ arr = [] }) {
    const a = Array.isArray(arr) ? arr : [arr];
    const steps = [];
    steps.push({ description: `Kadanes: find max subarray sum`, why: "Track current and maximum", stateSnapshot: { mode: "array", values: a.slice() }, highlightedElements: { index: 0 }, codeLine: 1, tracker: { current: a[0], max: a[0] }, passCount: 0 });
    let maxSum = a[0], currentSum = a[0];
    for (let i = 1; i < a.length; i++) { currentSum = Math.max(a[i], currentSum + a[i]); maxSum = Math.max(maxSum, currentSum); steps.push({ description: `i=${i}: curr=${currentSum}, max=${maxSum}`, why: "Include element or restart", stateSnapshot: { mode: "array", values: a.slice() }, highlightedElements: { compare: [i], index: i }, codeLine: 4, tracker: { i, current: currentSum, max: maxSum }, passCount: i }); }
    return { timeline: steps };
  }
};
