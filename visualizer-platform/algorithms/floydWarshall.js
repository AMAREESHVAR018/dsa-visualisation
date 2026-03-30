export const floydWarshallAlgorithm = {
  key: "floydWarshall",
  label: "Floyd-Warshall",
  category: "matrix",
  description: "All-pairs shortest paths using dynamic programming on intermediate vertices.",
  codeLines: [
    "dist = adjacencyMatrix",
    "for k in vertices:",
    "  for i in vertices:",
    "    for j in vertices:",
    "      dist[i][j] = min(dist[i][j], dist[i][k] + dist[k][j])"
  ],
  inputSchema: [
    {
      id: "matrixText",
      label: "Matrix rows (use inf)",
      type: "textarea",
      defaultValue: "0,3,inf,7\n8,0,2,inf\n5,inf,0,1\n2,inf,inf,0"
    }
  ],
  createDefaultInput() {
    return { matrixText: "0,3,inf,7\n8,0,2,inf\n5,inf,0,1\n2,inf,inf,0" };
  },
  buildTimeline(input) {
    const matrix = parseMatrix(input.matrixText);
    const n = matrix.length;
    const steps = [];
    let updates = 0;

    steps.push(makeStep(matrix, { k: -1, i: -1, j: -1 }, "Initialize distance matrix", "Start with direct edge distances.", 1, { k: "-", i: "-", j: "-", updates }));

    for (let k = 0; k < n; k += 1) {
      for (let i = 0; i < n; i += 1) {
        for (let j = 0; j < n; j += 1) {
          const throughK = matrix[i][k] + matrix[k][j];
          const before = matrix[i][j];

          steps.push(makeStep(matrix, { k, i, j }, `Check i=${i}, j=${j} via k=${k}`, "Try path i -> k -> j against current best.", 4, { k, i, j, updates }));

          if (throughK < before) {
            matrix[i][j] = throughK;
            updates += 1;
            steps.push(makeStep(matrix, { k, i, j }, `Update dist[${i}][${j}] to ${fmt(throughK)}`, "Intermediate vertex k improves shortest path.", 5, { k, i, j, updates }));
          }
        }
      }
    }

    steps.push(makeStep(matrix, { k: -1, i: -1, j: -1 }, "All-pairs shortest paths complete", "No more intermediate vertices left to test.", 5, { k: "-", i: "-", j: "-", updates }));
    return { timeline: steps, trackerKeys: ["k", "i", "j", "updates"] };
  }
};

function makeStep(matrix, pivot, description, why, codeLine, tracker) {
  return {
    description,
    why,
    stateSnapshot: {
      mode: "matrix",
      matrix: matrix.map(row => [...row])
    },
    highlightedElements: { pivot },
    codeLine,
    tracker
  };
}

function parseMatrix(text) {
  const rows = (text || "").split(/\r?\n/).map(line => line.trim()).filter(Boolean);
  const matrix = rows.map(row => row.split(/[\s,]+/).map(token => {
    if (token.toLowerCase() === "inf") return Infinity;
    const n = Number(token);
    return Number.isFinite(n) ? n : Infinity;
  }));

  const size = matrix.length || 4;
  if (!matrix.length) {
    return [
      [0, 3, Infinity, 7],
      [8, 0, 2, Infinity],
      [5, Infinity, 0, 1],
      [2, Infinity, Infinity, 0]
    ];
  }

  return matrix.map((row, i) => {
    const padded = row.slice(0, size);
    while (padded.length < size) padded.push(i === padded.length ? 0 : Infinity);
    return padded;
  });
}

function fmt(v) {
  return v === Infinity ? "inf" : String(v);
}
