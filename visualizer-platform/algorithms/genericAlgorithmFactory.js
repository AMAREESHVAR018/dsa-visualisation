import { buildCircleNodes, parseEdges, parseNumberList } from "./helpers.js";

export function createGenericAlgorithm(className, category) {
  const label = humanize(className);

  if (category === "graph") {
    return {
      key: className,
      label,
      category: "graph",
      description: `${className}.java visualized with generic graph progression.`,
      codeLines: [
        "initialize state",
        "repeat until complete:",
        "  inspect next edge/node",
        "  update tracker values",
        "finish"
      ],
      inputSchema: [
        { id: "nodeCount", label: "Node count", type: "number", defaultValue: "5" },
        { id: "edgeText", label: "Edges (u v w per line)", type: "textarea", defaultValue: "0 1 3\n0 2 2\n1 3 4\n2 3 1\n3 4 2" }
      ],
      createDefaultInput() {
        return {
          nodeCount: "5",
          edgeText: "0 1 3\n0 2 2\n1 3 4\n2 3 1\n3 4 2"
        };
      },
      buildTimeline(input) {
        const nodeCount = Math.max(2, Math.min(12, Number(input.nodeCount) || 5));
        const edges = parseEdges(input.edgeText, parseEdges("0 1 3\n0 2 2\n1 3 4\n2 3 1\n3 4 2", []))
          .filter(e => e.u >= 0 && e.u < nodeCount && e.v >= 0 && e.v < nodeCount)
          .map((e, id) => ({ ...e, id }));

        const nodes = buildCircleNodes(nodeCount);
        const visited = Array.from({ length: nodeCount }, () => false);
        const timeline = [];

        timeline.push(stepGraph(nodes, edges, visited, -1, -1, `${label}: initialize graph state`, "Start from default graph input.", 1, { stage: "init" }));

        edges.forEach((edge, index) => {
          visited[edge.u] = true;
          visited[edge.v] = true;
          timeline.push(stepGraph(nodes, edges, visited, index, edge.v, `Inspect edge ${edge.u} -> ${edge.v}`, "Generic graph walkthrough highlights current edge and visited nodes.", 3, { stage: `edge-${index + 1}` }));
        });

        timeline.push(stepGraph(nodes, edges, visited, -1, -1, `${label}: walkthrough complete`, "All edges processed for conceptual understanding.", 5, { stage: "done" }));

        return { timeline, trackerKeys: ["stage"] };
      }
    };
  }

  if (category === "matrix") {
    return {
      key: className,
      label,
      category: "graph",
      description: `${className}.java visualized as matrix state transitions.`,
      codeLines: [
        "initialize table",
        "for each iteration:",
        "  compare candidate transitions",
        "  update table if improved"
      ],
      inputSchema: [
        { id: "matrixText", label: "Matrix rows (use inf)", type: "textarea", defaultValue: "0,2,inf\ninf,0,3\n1,inf,0" }
      ],
      createDefaultInput() {
        return { matrixText: "0,2,inf\ninf,0,3\n1,inf,0" };
      },
      buildTimeline(input) {
        const matrix = parseMatrix(input.matrixText);
        const timeline = [];
        timeline.push(stepMatrix(matrix, { k: -1, i: -1, j: -1 }, `${label}: initial matrix`, "Initial matrix loaded.", 1, { phase: "init" }));

        for (let k = 0; k < matrix.length; k += 1) {
          timeline.push(stepMatrix(matrix, { k, i: k, j: k }, `Iteration using pivot ${k}`, "Pivot row and column are highlighted for this phase.", 2, { phase: `pivot-${k}` }));
        }

        return { timeline, trackerKeys: ["phase"] };
      }
    };
  }

  if (category === "tree-list") {
    return {
      key: className,
      label,
      category: "tree-list",
      description: `${className}.java visualized as pointer traversal steps.`,
      codeLines: [
        "current = head",
        "while current != null:",
        "  process current",
        "  current = current.next"
      ],
      inputSchema: [
        { id: "listText", label: "Values", type: "text", defaultValue: "10,20,30,40" }
      ],
      createDefaultInput() {
        return { listText: "10,20,30,40" };
      },
      buildTimeline(input) {
        const values = parseNumberList(input.listText, [10, 20, 30, 40]).slice(0, 12);
        const visited = Array.from({ length: values.length }, () => false);
        const timeline = [];

        timeline.push(stepList(values, visited, 0, `${label}: pointer at head`, "Start traversal from first node.", 1, { pointer: 0 }));

        for (let i = 0; i < values.length; i += 1) {
          visited[i] = true;
          timeline.push(stepList(values, visited, i, `Visit node ${i}`, "Node processed, then pointer advances.", 3, { pointer: i }));
        }

        timeline.push(stepList(values, visited, values.length, `${label}: traversal complete`, "Pointer reached null.", 4, { pointer: "null" }));

        return { timeline, trackerKeys: ["pointer"] };
      }
    };
  }

  return {
    key: className,
    label,
    category: "array",
    description: `${className}.java visualized with generic array-state progression.`,
    codeLines: [
      "prepare input",
      "for each element/window:",
      "  evaluate condition",
      "  update best/tracker",
      "return answer"
    ],
    inputSchema: [
      { id: "arrayText", label: "Array", type: "text", defaultValue: "5,1,4,2,8,3" }
    ],
    createDefaultInput() {
      return { arrayText: "5,1,4,2,8,3" };
    },
    buildTimeline(input) {
      const values = parseNumberList(input.arrayText, [5, 1, 4, 2, 8, 3]);
      const timeline = [];
      let best = Number.NEGATIVE_INFINITY;

      timeline.push(stepArray(values, [], [], [], `${label}: initial array`, "Start with the provided input values.", 1, { index: "-", best: "-" }));

      values.forEach((value, index) => {
        if (value > best) best = value;
        timeline.push(stepArray(values, [index], [], range(0, index), `Inspect index ${index}`, "Evaluate current element and update tracker.", 3, { index, best }));
      });

      timeline.push(stepArray(values, [], [], range(0, values.length), `${label}: walkthrough complete`, "All elements processed.", 5, { index: values.length - 1, best }));
      return { timeline, trackerKeys: ["index", "best"] };
    }
  };
}

export function inferCategory(className) {
  const graphNames = ["BellmanFordAlgorithm", "BFS", "DFS", "DijkstrasAlgorithm", "KruskalsAlgorithm", "PrimsAlgorithm"];
  if (graphNames.includes(className)) return "graph";
  if (className === "FloydWarshallAlgorithm") return "matrix";
  if (className.includes("LinkedList") || className.includes("Tree") || className.includes("TreeTraversal")) return "tree-list";
  
  // String algorithms
  const stringKeywords = ["String", "Palindrome", "Atoi", "Regex", "Replace"];
  if (stringKeywords.some(kw => className.includes(kw))) return "string";
  
  return "array";
}

function humanize(value) {
  return value.replace(/([a-z])([A-Z])/g, "$1 $2").replace(/OfA/g, "Of A").replace(/Using/g, " Using ").trim();
}

function stepArray(values, compare, swapped, sorted, description, why, codeLine, tracker) {
  return {
    description,
    why,
    stateSnapshot: { mode: "array", values: [...values] },
    highlightedElements: { compare, swapped, sorted },
    codeLine,
    tracker
  };
}

function stepGraph(nodes, edges, visitedNodes, currentEdge, updatedNode, description, why, codeLine, tracker) {
  return {
    description,
    why,
    stateSnapshot: {
      mode: "graph",
      nodes: nodes.map((node, index) => ({ ...node, label: String(index) })),
      edges,
      directed: true
    },
    highlightedElements: {
      visitedNodes: [...visitedNodes],
      currentEdge,
      updatedNode
    },
    codeLine,
    tracker
  };
}

function stepMatrix(matrix, pivot, description, why, codeLine, tracker) {
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

function stepList(values, visitedNodes, pointerIndex, description, why, codeLine, tracker) {
  return {
    description,
    why,
    stateSnapshot: { mode: "tree-list", values: [...values] },
    highlightedElements: { visitedNodes: [...visitedNodes], pointerIndex },
    codeLine,
    tracker
  };
}

function parseMatrix(text) {
  const rows = (text || "").split(/\r?\n/).map(line => line.trim()).filter(Boolean);
  if (!rows.length) {
    return [
      [0, 2, Infinity],
      [Infinity, 0, 3],
      [1, Infinity, 0]
    ];
  }

  return rows.map(row => row.split(/[\s,]+/).map(token => {
    if (token.toLowerCase() === "inf") return Infinity;
    const n = Number(token);
    return Number.isFinite(n) ? n : Infinity;
  }));
}

function range(start, end) {
  const out = [];
  for (let i = start; i < end; i += 1) out.push(i);
  return out;
}
