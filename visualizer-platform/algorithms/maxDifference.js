export const maxDifferenceAlgorithm = {
  key: "MaximumDifferenceInArray",
  label: "Maximum Difference In Array",
  category: "array",
  description: "Find largest difference between any two elements where first comes before second.",
  codeLines: ["int maxDiff = 0;", "int minVal = arr[0];", "for (int i = 1; i < n; i++) {", "  maxDiff = Math.max(maxDiff, arr[i] - minVal);", "  minVal = Math.min(minVal, arr[i]);", "}", "return maxDiff;"],
  inputSchema: [{ id: "arr", label: "Array", type: "array", defaultValue: [7, 1, 5, 3, 6, 4] }],
  createDefaultInput() { return { arr: [7, 1, 5, 3, 6, 4] }; },
  buildTimeline({ arr = [] }) {
    const a = Array.isArray(arr) ? arr : [arr];
    const steps = [];
    steps.push({ description: `Find max(arr[j] - arr[i]) i<j`, why: "Track min and max diff", stateSnapshot: { mode: "array", values: a.slice() }, highlightedElements: { index: 0 }, codeLine: 1, tracker: { minVal: a[0], maxDiff: 0 }, passCount: 0 });
    let minVal = a[0], maxDiff = 0;
    for (let i = 1; i < a.length; i++) { const diff = a[i] - minVal; if (diff > maxDiff) maxDiff = diff; if (a[i] < minVal) minVal = a[i]; steps.push({ description: `arr[${i}]=${a[i]}, diff=${diff}, max=${maxDiff}`, why: "Update min and max", stateSnapshot: { mode: "array", values: a.slice() }, highlightedElements: { compare: [i], index: i }, codeLine: 4, tracker: { i, minVal, maxDiff }, passCount: i }); }
    return { timeline: steps };
  }
};
