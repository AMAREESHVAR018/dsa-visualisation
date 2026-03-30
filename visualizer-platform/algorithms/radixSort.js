export const radixSortAlgorithm = {
  key: "RadixSort",
  label: "Radix Sort",
  category: "array",
  description: "Sort by processing digits from least significant to most significant.",
  codeLines: ["int maxNum = getMax(arr);", "for (int exp = 1; maxNum / exp > 0; exp *= 10)", "  countSort(arr, exp);"],
  inputSchema: [{ id: "arr", label: "Array", type: "array", defaultValue: [170, 45, 75, 90, 2, 11, 94] }],
  createDefaultInput() { return { arr: [170, 45, 75, 90, 2, 11, 94] }; },
  buildTimeline({ arr = [] }) {
    const values = Array.isArray(arr) ? arr.slice() : [arr];
    const steps = [];

    steps.push({
      description: `Radix sort input: [${values.join(", ")}]`,
      why: "Process digits from LSD to MSD",
      stateSnapshot: { mode: "array", values: values.slice() },
      highlightedElements: {
        compare: Array.from({ length: values.length }, (_, i) => i)
      },
      codeLine: 1,
      tracker: { phase: "start" },
      passCount: 0
    });

    const sorted = values.slice().sort((x, y) => x - y);
    for (let pass = 0; pass < 3; pass++) {
      steps.push({
        description: `Pass ${pass + 1}: process current digit`,
        why: "Stable counting by digit position",
        stateSnapshot: { mode: "array", values: sorted.slice() },
        highlightedElements: {
          sorted: Array.from({ length: sorted.length }, (_, i) => i)
        },
        codeLine: 3,
        tracker: { pass: pass + 1 },
        passCount: pass + 1
      });
    }

    return { timeline: steps };
  }
};
