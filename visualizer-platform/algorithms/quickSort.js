import { parseNumberList } from "./helpers.js";

const DEFAULT_ARRAY = [10, 7, 8, 9, 1, 5];

export const quickSortAlgorithm = {
  key: "quickSort",
  label: "Quick Sort",
  category: "array",
  description: "In-place partitioning around pivots.",
  codeLines: [
    "quickSort(l, r):",
    "  if l >= r return",
    "  p = partition(l, r)",
    "  quickSort(l, p - 1)",
    "  quickSort(p + 1, r)",
    "partition: move values < pivot to left"
  ],
  inputSchema: [
    { id: "arrayText", label: "Array", type: "text", defaultValue: DEFAULT_ARRAY.join(",") }
  ],
  createDefaultInput() {
    return { arrayText: DEFAULT_ARRAY.join(",") };
  },
  buildTimeline(input) {
    const a = parseNumberList(input.arrayText, DEFAULT_ARRAY);
    const steps = [];
    let swaps = 0;

    steps.push(makeStep(a, "Initial array", "Quick sort partitions array around pivot values.", [], [], -1, 1, { swaps, range: "-" }));

    function partition(lo, hi) {
      const pivot = a[hi];
      let i = lo;
      steps.push(makeStep(a, `Choose pivot ${pivot} at index ${hi}`, "Pivot defines split point for smaller/larger elements.", [hi], [], hi, 6, { swaps, range: `[${lo}, ${hi}]` }));

      for (let j = lo; j < hi; j += 1) {
        steps.push(makeStep(a, `Compare a[${j}] with pivot`, "Move smaller elements to left partition.", [j, hi], [], hi, 6, { swaps, range: `[${lo}, ${hi}]` }));
        if (a[j] < pivot) {
          [a[i], a[j]] = [a[j], a[i]];
          swaps += 1;
          steps.push(makeStep(a, `Swap index ${i} and ${j}`, "This value belongs to left partition.", [i, j], [i, j], hi, 6, { swaps, range: `[${lo}, ${hi}]` }));
          i += 1;
        }
      }

      [a[i], a[hi]] = [a[hi], a[i]];
      swaps += 1;
      steps.push(makeStep(a, `Place pivot at index ${i}`, "Pivot is now in final sorted position.", [i, hi], [i], i, 3, { swaps, range: `[${lo}, ${hi}]` }));
      return i;
    }

    function sort(lo, hi) {
      if (lo >= hi) return;
      const p = partition(lo, hi);
      sort(lo, p - 1);
      sort(p + 1, hi);
    }

    sort(0, a.length - 1);
    steps.push(makeStep(a, "Array sorted", "All partitions reduced to size 1.", [], range(0, a.length), -1, 5, { swaps, range: "[0, n-1]" }));

    return { timeline: steps, trackerKeys: ["swaps", "range"] };
  }
};

function makeStep(values, description, why, compare, swapped, pivotIndex, codeLine, tracker) {
  return {
    description,
    why,
    stateSnapshot: { mode: "array", values: [...values] },
    highlightedElements: { compare, swapped, sorted: [], pivot: pivotIndex },
    codeLine,
    tracker
  };
}

function range(start, end) {
  const out = [];
  for (let i = start; i < end; i += 1) out.push(i);
  return out;
}
