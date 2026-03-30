/**
 * Buildings Receiving Sunlight Algorithm
 * Track which buildings can see the sunset (no taller buildings to the right)
 */

export const buildingsReceivingSunlightAlgorithm = {
  key: "BuildingsReceivingSunlight",
  label: "Buildings Receiving Sunlight",
  category: "array",
  description: "Find buildings that can see sunset by tracking max height from right to left.",
  codeLines: [
    "List<Integer> result = new ArrayList<>();",
    "int maxHeight = 0;",
    "for (int i = heights.length - 1; i >= 0; i--) {",
    "  if (heights[i] > maxHeight) {",
    "    result.add(i);",
    "    maxHeight = heights[i];",
    "  }",
    "}",
    "Collections.reverse(result);",
    "return result;"
  ],
  inputSchema: [
    {
      id: "heights",
      label: "Building Heights",
      type: "array",
      placeholder: "e.g., [3,0,0,2,7,4,4,3,9]",
      defaultValue: [3, 0, 0, 2, 7, 4, 4, 3, 9]
    }
  ],
  createDefaultInput() {
    return { heights: [3, 0, 0, 2, 7, 4, 4, 3, 9] };
  },
  buildTimeline({ heights = [] }) {
    const arr = Array.isArray(heights) ? heights : [heights];
    const steps = [];
    const sunlightBuildings = [];
    let maxFromRight = 0;

    steps.push(makeStep({
      description: `Start from rightmost building (index ${arr.length - 1}), track max height`,
      why: "Buildings to the right that are taller block sunlight",
      arr,
      index: arr.length - 1,
      maxHeight: 0,
      sunlit: [],
      state: "init",
      tracker: { maxHeight: 0, sunlit: "none" },
      codeLine: 1
    }));

    for (let i = arr.length - 1; i >= 0; i--) {
      const height = arr[i];
      const canSeeSunset = height > maxFromRight;

      steps.push(makeStep({
        description: `Check building[${i}] height=${height}, maxHeight=${maxFromRight}`,
        why: canSeeSunset
          ? `Building[${i}] is taller than maxHeight - can see sunset!`
          : `Building[${i}] is blocked by taller building to right`,
        arr,
        index: i,
        maxHeight: maxFromRight,
        sunlit: sunlightBuildings.slice(),
        state: canSeeSunset ? "sunlit" : "blocked",
        tracker: {
          building: `[${i}]=${height}`,
          maxRight: maxFromRight,
          canSee: canSeeSunset ? "✓" : "✗"
        },
        codeLine: 3
      }));

      if (canSeeSunset) {
        sunlightBuildings.unshift(i);
        maxFromRight = height;

        steps.push(makeStep({
          description: `Building[${i}] added to result. Update maxHeight = ${height}`,
          why: "This building is the new tallest from right",
          arr,
          index: i,
          maxHeight: height,
          sunlit: sunlightBuildings.slice(),
          state: "added",
          tracker: {
            added: `[${i}]`,
            maxHeight: height,
            sunlit: `[${sunlightBuildings.join(", ")}]`
          },
          codeLine: 5
        }));
      }
    }

    steps.push(makeStep({
      description: `Complete! Buildings with sunlight: ${sunlightBuildings.join(", ")}`,
      why: "All buildings processed",
      arr,
      index: -1,
      maxHeight: maxFromRight,
      sunlit: sunlightBuildings,
      state: "done",
      tracker: {
        result: `[${sunlightBuildings.map(i => arr[i]).join(", ")}]`,
        count: sunlightBuildings.length
      },
      codeLine: 10
    }));

    return { timeline: steps };
  }
};

function makeStep({
  description,
  why,
  arr,
  index,
  maxHeight,
  sunlit,
  state,
  tracker,
  codeLine
}) {
  return {
    description,
    why,
    stateSnapshot: {
      mode: "array",
      values: arr,
      index,
      maxHeight,
      sunlit,
      state
    },
    highlightedElements: {
      compare: index >= 0 ? [index] : [],
      sorted: sunlit,
      state
    },
    codeLine,
    tracker,
    passCount: arr.length - index
  };
}
