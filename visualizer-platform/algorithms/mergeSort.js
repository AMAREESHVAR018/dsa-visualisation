import { parseNumberList } from "./helpers.js";

const DEFAULT_ARRAY = [38, 27, 43, 3, 9, 82, 10];

export const mergeSortAlgorithm = {
  key: "mergeSort",
  label: "Merge Sort",
  category: "array",
  description: "Divide-and-conquer sorting with stable merging.",
  codeLines: [
    "mergeSort(l, r):",
    "  if l >= r return",
    "  mid = (l + r) / 2",
    "  mergeSort(l, mid)",
    "  mergeSort(mid+1, r)",
    "  merge(l, mid, r)"
  ],
  inputSchema: [
    { id: "arrayText", label: "Array", type: "text", defaultValue: DEFAULT_ARRAY.join(",") }
  ],
  createDefaultInput() {
    return { arrayText: DEFAULT_ARRAY.join(",") };
  },
  buildTimeline(input) {
    const a = parseNumberList(input.arrayText, DEFAULT_ARRAY);
    const aux = new Array(a.length);
    const steps = [];
    let merges = 0;

    steps.push(step(a, "Initial array", "Merge sort recursively splits and merges ranges.", [], [], 1, { merges, range: "-" }));

    function merge(lo, mid, hi) {
      for (let i = lo; i <= hi; i += 1) aux[i] = a[i];
      let i = lo;
      let j = mid + 1;

      for (let k = lo; k <= hi; k += 1) {
        if (i > mid) a[k] = aux[j++];
        else if (j > hi) a[k] = aux[i++];
        else if (aux[j] < aux[i]) a[k] = aux[j++];
        else a[k] = aux[i++];

        steps.push(step(a, `Merge write at index ${k} for [${lo}..${hi}]`, "Merge picks smallest front element from two sorted halves.", [k], [], 6, { merges, range: `[${lo}, ${hi}]` }));
      }
      merges += 1;
    }

    function sort(lo, hi) {
      if (lo >= hi) return;
      const mid = Math.floor((lo + hi) / 2);
      steps.push(step(a, `Split [${lo}..${hi}] at ${mid}`, "Divide array until single-element segments.", [lo, mid, hi], [], 3, { merges, range: `[${lo}, ${hi}]` }));
      sort(lo, mid);
      sort(mid + 1, hi);
      merge(lo, mid, hi);
    }

    sort(0, a.length - 1);
    steps.push(step(a, "Array sorted", "All merge levels complete.", [], range(0, a.length), 6, { merges, range: "[0, n-1]" }));

    return { timeline: steps, trackerKeys: ["merges", "range"] };
  }
};

function step(values, description, why, compare, sorted, codeLine, tracker) {
  return {
    description,
    why,
    stateSnapshot: { mode: "array", values: [...values] },
    highlightedElements: { compare, swapped: [], sorted },
    codeLine,
    tracker
  };
}

function range(start, end) {
  const out = [];
  for (let i = start; i < end; i += 1) out.push(i);
  return out;
}
