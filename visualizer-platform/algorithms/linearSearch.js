export const linearSearchAlgorithm = {
  key: "LinearSearch",
  label: "Linear Search",
  category: "array",
  description: "Search for element by checking each position sequentially.",
  codeLines: ["for (int i = 0; i < n; i++) {", "  if (arr[i] == target)", "    return i;", "}", "return -1;"],
  inputSchema: [{ id: "arr", label: "Array", type: "array", defaultValue: [10, 20, 30, 40, 50] }, { id: "target", label: "Target", type: "number", defaultValue: 30 }],
  createDefaultInput() { return { arr: [10, 20, 30, 40, 50], target: 30 }; },
  buildTimeline({ arr = [], target = 0 }) {
    const values = Array.isArray(arr) ? arr.slice() : [arr];
    const steps = [];

    steps.push({
      description: `Search for ${target} in [${values.join(", ")}]`,
      why: "Check each element",
      stateSnapshot: { mode: "array", values: values.slice() },
      highlightedElements: { compare: [0] },
      codeLine: 1,
      tracker: { target, found: false },
      passCount: 0
    });

    let found = -1;
    for (let i = 0; i < values.length; i++) {
      const match = values[i] === target;
      const description = match
        ? `Found ${target} at index ${i}`
        : `arr[${i}] = ${values[i]} is not ${target}`;

      steps.push({
        description,
        why: match ? "Match found" : "Continue searching",
        stateSnapshot: { mode: "array", values: values.slice() },
        highlightedElements: {
          compare: [i],
          sorted: match ? [i] : []
        },
        codeLine: match ? 2 : 1,
        tracker: { checking: i, value: values[i], match },
        passCount: i + 1
      });

      if (match) {
        found = i;
        break;
      }
    }

    if (found === -1) {
      steps.push({
        description: "Element not found, return -1",
        why: "All positions checked",
        stateSnapshot: { mode: "array", values: values.slice() },
        highlightedElements: {},
        codeLine: 5,
        tracker: { result: -1 },
        passCount: values.length
      });
    }

    return { timeline: steps };
  }
};
