export const insertionSortAlgorithm = {
  key: "InsertionSort",
  label: "Insertion Sort",
  category: "array",
  description: "Build sorted array by inserting each element into correct position.",
  codeLines: ["for (int i = 1; i < n; i++) {", "  int key = arr[i];", "  int j = i - 1;", "  while (j >= 0 && arr[j] > key) {", "    arr[j+1] = arr[j]; j--;", "  }", "  arr[j+1] = key;", "}"],
  inputSchema: [{ id: "arr", label: "Array", type: "array", defaultValue: [64, 25, 12, 22, 11] }],
  createDefaultInput() { return { arr: [64, 25, 12, 22, 11] }; },
  buildTimeline({ arr = [] }) {
    const a = Array.isArray(arr) ? arr.slice() : [arr];
    const steps = [];
    steps.push({ description: `Insertion sort from index 1`, why: "First element already sorted", stateSnapshot: { mode: "array", values: a.slice() }, highlightedElements: { sorted: [0] }, codeLine: 1, tracker: { sorted: 1 }, passCount: 0 });
    for (let i = 1; i < a.length; i++) { const key = a[i]; let j = i - 1; while (j >= 0 && a[j] > key) { a[j+1] = a[j]; j--; } a[j+1] = key; steps.push({ description: `Insert ${key} into sorted portion`, why: "Element placed in correct order", stateSnapshot: { mode: "array", values: a.slice() }, highlightedElements: { sorted: Array.from({length: i + 1}, (_, x) => x), compare: [i] }, codeLine: 7, tracker: { i, key, sorted: i + 1 }, passCount: i }); }
    return { timeline: steps };
  }
};
