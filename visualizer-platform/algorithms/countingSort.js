export const countingSortAlgorithm = {
  key: "CountingSort",
  label: "Counting Sort",
  category: "array",
  description: "Count occurrences of each element and place them in order.",
  codeLines: ["int[] count = new int[max + 1];", "for (int x : arr) count[x]++;", "for (int i = 1; i <= max; i++)", "  count[i] += count[i-1];", "for (int i = n-1; i >= 0; i--)", "  result[--count[arr[i]]] = arr[i];"],
  inputSchema: [{ id: "arr", label: "Array", type: "array", defaultValue: [4, 2, 2, 8, 3, 3, 1] }],
  createDefaultInput() { return { arr: [4, 2, 2, 8, 3, 3, 1] }; },
  buildTimeline({ arr = [] }) {
    const a = Array.isArray(arr) ? arr.slice() : [arr];
    const steps = [];
    steps.push({ description: `Count sort: [${a.join(", ")}]`, why: "Count occurrences", stateSnapshot: { mode: "array", values: a.slice() }, highlightedElements: { compare: Array.from({length: a.length}, (_, i) => i) }, codeLine: 1, tracker: { step: "init" }, passCount: 0 });
    const sorted = a.slice().sort((x, y) => x - y);
    for (let i = 0; i < sorted.length; i++) {
      steps.push({ description: `Place ${sorted[i]} at position ${i}`, why: "Element in correct position", stateSnapshot: { mode: "array", values: sorted.slice(0, i + 1).concat(a.slice(i + 1)) }, highlightedElements: { sorted: Array.from({length: i + 1}, (_, x) => x) }, codeLine: 6, tracker: { progress: i + 1, total: sorted.length }, passCount: i });
    }
    return { timeline: steps };
  }
};
