import { boolArray, buildCircleNodes, parseEdges } from "./helpers.js";

const DEFAULT_EDGES = [
  "0 1 1",
  "0 2 1",
  "1 3 1",
  "2 4 1",
  "4 5 1"
].join("\n");

export const dfsAlgorithm = {
  key: "dfs",
  label: "Depth-First Search",
  category: "graph",
  description: "Stack-based traversal that explores one path deeply before backtracking.",
  codeLines: [
    "stack.push(source)",
    "while stack not empty:",
    "  node = stack.pop()",
    "  if visited[node] continue",
    "  visited[node] = true",
    "  for each neighbor of node:",
    "    stack.push(neighbor)"
  ],
  inputSchema: [
    { id: "nodeCount", label: "Node count", type: "number", defaultValue: "6" },
    { id: "source", label: "Source node", type: "number", defaultValue: "0" },
    { id: "edgeText", label: "Edges (u v w per line)", type: "textarea", defaultValue: DEFAULT_EDGES }
  ],
  createDefaultInput() {
    return { nodeCount: "6", source: "0", edgeText: DEFAULT_EDGES };
  },
  buildTimeline(input) {
    const nodeCount = Math.max(2, Math.min(12, Number(input.nodeCount) || 6));
    const source = Math.max(0, Math.min(nodeCount - 1, Number(input.source) || 0));
    const edges = parseEdges(input.edgeText, parseEdges(DEFAULT_EDGES, []))
      .filter(e => e.u >= 0 && e.u < nodeCount && e.v >= 0 && e.v < nodeCount)
      .map((e, index) => ({ ...e, id: index }));

    const adjacency = Array.from({ length: nodeCount }, () => []);
    edges.forEach((edge, edgeIndex) => {
      adjacency[edge.u].push({ to: edge.v, edgeIndex });
      adjacency[edge.v].push({ to: edge.u, edgeIndex });
    });

    const nodes = buildCircleNodes(nodeCount);
    const visited = boolArray(nodeCount, false);
    const stack = [source];
    const steps = [];
    let iteration = 0;

    steps.push(makeStep(nodes, edges, visited, stack, source, -1, "Push source node to stack", "DFS starts from source and dives via stack order.", 1, iteration));

    while (stack.length > 0) {
      iteration += 1;
      const node = stack.pop();
      steps.push(makeStep(nodes, edges, visited, stack, node, -1, `Pop node ${node}`, "LIFO pop chooses the deepest discovered node.", 3, iteration));

      if (visited[node]) {
        steps.push(makeStep(nodes, edges, visited, stack, node, -1, `Node ${node} already visited`, "Skip to prevent revisiting cycles.", 4, iteration));
        continue;
      }

      visited[node] = true;
      steps.push(makeStep(nodes, edges, visited, stack, node, -1, `Mark node ${node} visited`, "First visit marks node to avoid reprocessing.", 5, iteration));

      const neighbors = [...adjacency[node]].reverse();
      for (const next of neighbors) {
        stack.push(next.to);
        steps.push(makeStep(nodes, edges, visited, stack, next.to, next.edgeIndex, `Push neighbor ${next.to}`, "Neighbor is scheduled for depth-first exploration.", 7, iteration));
      }
    }

    steps.push(makeStep(nodes, edges, visited, stack, -1, -1, "DFS complete", "Stack empty means traversal is done.", 2, iteration));

    return { timeline: steps, trackerKeys: ["iteration", "currentNode", "stack", "visited"] };
  }
};

function makeStep(nodes, edges, visited, stack, currentNode, currentEdge, description, why, codeLine, iteration) {
  return {
    description,
    why,
    stateSnapshot: {
      mode: "graph",
      nodes: nodes.map(n => ({ ...n, label: String(n.id) })),
      edges,
      directed: false
    },
    highlightedElements: {
      visitedNodes: [...visited],
      currentNode,
      currentEdge,
      queue: [...stack]
    },
    codeLine,
    tracker: {
      iteration,
      currentNode: currentNode < 0 ? "-" : currentNode,
      stack: `[${stack.join(", ")}]`,
      visited: `[${visited.map(v => (v ? 1 : 0)).join(", ")}]`
    }
  };
}
