import { parseNumberList } from "./helpers.js";

const DEFAULT_ARRAY = [29, 10, 14, 37, 13, 5, 22];

export const selectionSortAlgorithm = {
  key: "selectionSort",
  label: "Selection Sort",
  category: "array",
  description: "Repeatedly selects the minimum from the unsorted suffix.",
  codeLines: [
    "for (int i = 0; i < n - 1; i++) {",
    "  int min = i;",
    "  for (int j = i + 1; j < n; j++) {",
    "    if (a[j] < a[min]) min = j;",
    "  }",
    "  swap(a[i], a[min]);",
    "}"
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
    let comparisons = 0;
    let swaps = 0;

    steps.push(makeStep(a, { i: 0, j: "-", min: 0, comparisons, swaps }, "Start selection sort", "Each pass places one element in final position.", [], [], [], 1));

    for (let i = 0; i < a.length - 1; i += 1) {
      let min = i;
      for (let j = i + 1; j < a.length; j += 1) {
        comparisons += 1;
        steps.push(makeStep(a, { i, j, min, comparisons, swaps }, `Compare index ${j} with current min ${min}`, "Find smallest element in unsorted region.", [j, min], [], range(0, i), 3));
        if (a[j] < a[min]) {
          min = j;
          steps.push(makeStep(a, { i, j, min, comparisons, swaps }, `Update min index to ${min}`, "New minimum candidate found.", [j, min], [], range(0, i), 4));
        }
      }
      [a[i], a[min]] = [a[min], a[i]];
      swaps += 1;
      steps.push(makeStep(a, { i, j: "-", min, comparisons, swaps }, `Swap index ${i} and ${min}`, "Place selected minimum at front of unsorted region.", [i, min], [i, min], range(0, i + 1), 6));
    }

    steps.push(makeStep(a, { i: a.length - 1, j: "-", min: a.length - 1, comparisons, swaps }, "Array sorted", "All positions are finalized.", [], [], range(0, a.length), 7));
    return { timeline: steps, trackerKeys: ["i", "j", "min", "comparisons", "swaps"] };
  }
};

function makeStep(values, tracker, description, why, compare, swapped, sorted, codeLine) {
  return {
    description,
    why,
    stateSnapshot: { mode: "array", values: [...values] },
    highlightedElements: { compare, swapped, sorted },
    codeLine,
    tracker
  };
}

function range(start, end) {
  const out = [];
  for (let i = start; i < end; i += 1) out.push(i);
  return out;
}
