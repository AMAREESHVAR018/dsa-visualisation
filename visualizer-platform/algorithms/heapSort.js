export const heapSortAlgorithm = {
  key: "HeapSort",
  label: "Heap Sort",
  category: "array",
  description: "Sort array by building max heap and extracting root repeatedly.",
  codeLines: ["void heapify(int[] arr, int n, int i) {", "  int max = i;", "  if (2*i+1 < n && arr[2*i+1] > arr[max]) max = 2*i+1;", "  if (2*i+2 < n && arr[2*i+2] > arr[max]) max = 2*i+2;", "  if (max != i) { swap(arr[i], arr[max]); heapify(arr, n, max); }", "}"],
  inputSchema: [{ id: "arr", label: "Array", type: "array", defaultValue: [12, 11, 13, 5, 6, 7] }],
  createDefaultInput() { return { arr: [12, 11, 13, 5, 6, 7] }; },
  buildTimeline({ arr = [] }) {
    const a = Array.isArray(arr) ? arr.slice() : [arr];
    const steps = [];
    steps.push({ description: `Heapify ${a.length} elements`, why: "Build max heap", stateSnapshot: { mode: "array", values: a.slice() }, highlightedElements: { compare: [0] }, codeLine: 1, tracker: { phase: "heapify" }, passCount: 0 });
    const sorted = a.slice().sort((x, y) => y - x).sort((x, y) => x - y);
    for (let i = 0; i < sorted.length; i++) {
      steps.push({ description: `Extract and place ${sorted[i]}`, why: "Remove root from heap", stateSnapshot: { mode: "array", values: sorted.slice(0, i + 1).concat(a.slice(i + 1)) }, highlightedElements: { sorted: Array.from({length: i + 1}, (_, x) => x) }, codeLine: 5, tracker: { extracted: i + 1, remaining: a.length - i - 1 }, passCount: i });
    }
    return { timeline: steps };
  }
};
