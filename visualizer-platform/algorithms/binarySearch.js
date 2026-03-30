import { parseNumberList } from "./helpers.js";

const DEFAULT_ARRAY = [1, 3, 5, 7, 9, 11, 13, 15];

export const binarySearchAlgorithm = {
  key: "binarySearch",
  label: "Binary Search",
  category: "array",
  description: "Interval-halving search on a sorted array.",
  codeLines: [
    "int l = 0, r = n - 1;",
    "while (l <= r) {",
    "  int m = l + (r - l) / 2;",
    "  if (a[m] == target) return m;",
    "  if (a[m] < target) l = m + 1;",
    "  else r = m - 1;",
    "}",
    "return -1;"
  ],
  inputSchema: [
    { id: "arrayText", label: "Sorted array", type: "text", defaultValue: DEFAULT_ARRAY.join(",") },
    { id: "target", label: "Target", type: "number", defaultValue: "11" }
  ],
  createDefaultInput() {
    return { arrayText: DEFAULT_ARRAY.join(","), target: "11" };
  },
  buildTimeline(input) {
    const values = parseNumberList(input.arrayText, DEFAULT_ARRAY).sort((a, b) => a - b);
    const target = Number(input.target);
    const steps = [];

    let l = 0;
    let r = values.length - 1;
    let iterations = 0;

    steps.push(step(values, { l, r, m: -1, found: -1 }, `Start search for ${target}`, "Binary search keeps a shrinking valid window [l..r].", 1, iterations, target));

    while (l <= r) {
      iterations += 1;
      const m = l + Math.floor((r - l) / 2);

      steps.push(step(values, { l, r, m, found: -1 }, `Check middle index ${m}`, "Middle divides the search space into two halves.", 3, iterations, target));

      if (values[m] === target) {
        steps.push(step(values, { l, r, m, found: m }, `Found target ${target} at index ${m}`, "Match at middle means search is complete.", 4, iterations, target));
        return { timeline: steps, trackerKeys: ["l", "r", "m", "iterations", "target"] };
      }

      if (values[m] < target) {
        l = m + 1;
        steps.push(step(values, { l, r, m, found: -1 }, "Move left boundary right", "Target is larger than middle value, so left half is discarded.", 5, iterations, target));
      } else {
        r = m - 1;
        steps.push(step(values, { l, r, m, found: -1 }, "Move right boundary left", "Target is smaller than middle value, so right half is discarded.", 6, iterations, target));
      }
    }

    steps.push(step(values, { l, r, m: -1, found: -1 }, `Target ${target} not found`, "Window is empty, so target does not exist in the array.", 8, iterations, target));
    return { timeline: steps, trackerKeys: ["l", "r", "m", "iterations", "target"] };
  }
};

function step(values, { l, r, m, found }, description, why, codeLine, iterations, target) {
  const compare = [];
  if (l >= 0 && l < values.length) compare.push(l);
  if (r >= 0 && r < values.length && r !== l) compare.push(r);
  if (m >= 0 && m < values.length) compare.push(m);

  return {
    description,
    why,
    stateSnapshot: { mode: "array", values: [...values] },
    highlightedElements: {
      compare,
      swapped: [],
      sorted: found >= 0 ? [found] : [],
      currentNode: m
    },
    codeLine,
    tracker: {
      l,
      r,
      m: m >= 0 ? m : "-",
      iterations,
      target
    }
  };
}
