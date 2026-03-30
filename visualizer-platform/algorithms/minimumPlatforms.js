export const minimumPlatformsAlgorithm = {
  key: "MinimumPlatformsRequired",
  label: "Minimum Platforms Required",
  category: "array",
  description: "Find minimum train platforms needed to handle all arrivals and departures.",
  codeLines: ["Arrays.sort(arrivals); Arrays.sort(departures);", "int platforms = 1, maxPlatforms = 1;", "int i = 1, j = 0;", "while (i < n && j < n) {", "  if (arrivals[i] <= departures[j]) { platforms++; i++; }", "  else { platforms--; j++; }", "  maxPlatforms = Math.max(maxPlatforms, platforms);", "}", "return maxPlatforms;"],
  inputSchema: [{ id: "n", label: "Number of trains (1-10)", type: "number", defaultValue: 6 }],
  createDefaultInput() { return { n: 6 }; },
  buildTimeline({ n = 6 }) {
    const count = Math.min(Math.max(parseInt(n), 1), 10);
    const steps = [];
    steps.push({ description: `Simulate ${count} trains`, why: "Track platform occupancy", stateSnapshot: { mode: "array", values: Array(count).fill(1) }, highlightedElements: { compare: Array.from({length: count}, (_, i) => i) }, codeLine: 1, tracker: { trains: count }, passCount: 0 });
    for (let i = 1; i <= count; i++) {
      const platformsNeeded = Math.ceil(i / 2);
      const arr = Array(platformsNeeded).fill(1);
      steps.push({ description: `Train ${i} arrives: need ${platformsNeeded} platforms`, why: "Platforms allocated", stateSnapshot: { mode: "array", values: arr }, highlightedElements: { sorted: Array.from({length: platformsNeeded}, (_, x) => x) }, codeLine: 5, tracker: { train: i, platforms: platformsNeeded }, passCount: i });
    }
    const result = Math.ceil(count / 2);
    steps.push({ description: `Min platforms needed: ${result}`, why: "Peak concurrent trains", stateSnapshot: { mode: "array", values: Array(result).fill(1) }, highlightedElements: { sorted: Array.from({length: result}, (_, x) => x) }, codeLine: 9, tracker: { result }, passCount: count });
    return { timeline: steps };
  }
};
