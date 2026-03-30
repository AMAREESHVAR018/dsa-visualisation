import { cloneArray, parseNumberList } from "./helpers.js";

const DEFAULT_ARRAY = [9, 4, 7, 2, 6, 1, 8, 3, 5];

export const bubbleSortAlgorithm = {
  key: "bubbleSort",
  label: "Bubble Sort",
  category: "array",
  description: "Classic adjacent swap sorting with comparisons and pass optimization.",
  codeLines: [
    "for (int i = 0; i < n - 1; i++) {",
    "  for (int j = 0; j < n - i - 1; j++) {",
    "    if (a[j] > a[j + 1]) {",
    "      swap(a[j], a[j + 1]);",
    "    }",
    "  }",
    "}"
  ],
  inputSchema: [
    {
      id: "arrayText",
      label: "Array (comma separated)",
      type: "text",
      placeholder: "9,4,7,2,6,1,8,3,5",
      defaultValue: DEFAULT_ARRAY.join(",")
    }
  ],
  createDefaultInput() {
    return { arrayText: DEFAULT_ARRAY.join(",") };
  },
  buildTimeline(input) {
    const source = parseNumberList(input.arrayText, DEFAULT_ARRAY);
    const a = cloneArray(source);
    const steps = [];
    let comparisons = 0;
    let swaps = 0;

    steps.push(makeStep({
      description: "Starting Bubble Sort on the input array.",
      why: "We begin with no sorted suffix and compare adjacent pairs.",
      array: a,
      compare: [],
      swapped: [],
      sorted: [],
      tracker: { i: 0, j: 0, comparisons, swaps, n: a.length },
      codeLine: 1
    }));

    for (let i = 0; i < a.length - 1; i += 1) {
      for (let j = 0; j < a.length - i - 1; j += 1) {
        comparisons += 1;
        steps.push(makeStep({
          description: `Compare a[${j}] and a[${j + 1}]`,
          why: "Bubble Sort checks adjacent values and swaps only when out of order.",
          array: a,
          compare: [j, j + 1],
          swapped: [],
          sorted: buildSortedSuffix(a.length, i),
          tracker: { i, j, comparisons, swaps, n: a.length },
          codeLine: 2
        }));

        if (a[j] > a[j + 1]) {
          [a[j], a[j + 1]] = [a[j + 1], a[j]];
          swaps += 1;
          steps.push(makeStep({
            description: `Swap a[${j}] and a[${j + 1}]`,
            why: "The left value is greater, so swapping moves larger values rightward.",
            array: a,
            compare: [j, j + 1],
            swapped: [j, j + 1],
            sorted: buildSortedSuffix(a.length, i),
            tracker: { i, j, comparisons, swaps, n: a.length },
            codeLine: 4
          }));
        }
      }

      steps.push(makeStep({
        description: `Pass ${i + 1} completed`,
        why: "After each pass, the largest remaining value settles at the end.",
        array: a,
        compare: [],
        swapped: [],
        sorted: buildSortedSuffix(a.length, i + 1),
        tracker: { i, j: a.length - i - 1, comparisons, swaps, n: a.length },
        codeLine: 6
      }));
    }

    steps.push(makeStep({
      description: "Array is fully sorted.",
      why: "All passes finished, so every element is now in ascending order.",
      array: a,
      compare: [],
      swapped: [],
      sorted: Array.from({ length: a.length }, (_, idx) => idx),
      tracker: { i: a.length - 1, j: a.length - 1, comparisons, swaps, n: a.length },
      codeLine: 7
    }));

    return {
      timeline: steps,
      trackerKeys: ["i", "j", "comparisons", "swaps", "n"]
    };
  }
};

function buildSortedSuffix(length, passCount) {
  const sorted = [];
  for (let idx = length - passCount; idx < length; idx += 1) {
    if (idx >= 0 && idx < length) sorted.push(idx);
  }
  return sorted;
}

function makeStep({ description, why, array, compare, swapped, sorted, tracker, codeLine }) {
  return {
    description,
    why,
    stateSnapshot: {
      mode: "array",
      values: cloneArray(array)
    },
    highlightedElements: {
      compare,
      swapped,
      sorted
    },
    codeLine,
    tracker
  };
}
