import { buildCircleNodes, parseEdges } from "./helpers.js";

const DEFAULT_EDGES = [
  "0 1 4",
  "0 2 2",
  "1 2 1",
  "1 3 5",
  "2 3 8",
  "2 4 10",
  "3 4 2",
  "3 5 6",
  "4 5 2"
].join("\n");

export const dijkstraAlgorithm = {
  key: "dijkstra",
  label: "Dijkstra",
  category: "graph",
  description: "Greedy shortest path with non-negative weights.",
  codeLines: [
    "dist[source] = 0",
    "repeat V times:",
    "  pick unvisited node u with minimum dist",
    "  mark u visited",
    "  for each edge (u, v, w):",
    "    if dist[u] + w < dist[v]: dist[v] = dist[u] + w"
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
      .map((e, id) => ({ ...e, id }));

    const adjacency = Array.from({ length: nodeCount }, () => []);
    edges.forEach((edge, idx) => {
      adjacency[edge.u].push({ to: edge.v, w: edge.w, edgeIndex: idx });
      adjacency[edge.v].push({ to: edge.u, w: edge.w, edgeIndex: idx });
    });

    const dist = Array.from({ length: nodeCount }, () => Infinity);
    const visited = Array.from({ length: nodeCount }, () => false);
    const nodes = buildCircleNodes(nodeCount);
    const steps = [];
    let relaxations = 0;

    dist[source] = 0;
    steps.push(makeStep(nodes, edges, dist, visited, -1, -1, "Initialize source distance to 0", "Shortest path from source to itself is always 0.", 1, 0, relaxations));

    for (let pass = 1; pass <= nodeCount; pass += 1) {
      let u = -1;
      let best = Infinity;
      for (let i = 0; i < nodeCount; i += 1) {
        if (!visited[i] && dist[i] < best) {
          best = dist[i];
          u = i;
        }
      }

      if (u === -1) break;

      visited[u] = true;
      steps.push(makeStep(nodes, edges, dist, visited, u, -1, `Select next closest node ${u}`, "Greedy choice picks minimum temporary distance.", 3, pass, relaxations));

      for (const edge of adjacency[u]) {
        if (visited[edge.to]) continue;
        const canRelax = dist[u] !== Infinity && dist[u] + edge.w < dist[edge.to];

        steps.push(makeStep(nodes, edges, dist, visited, u, edge.edgeIndex, `Inspect edge ${u} -> ${edge.to}`, "Try improving neighbor distance through current node.", 5, pass, relaxations));

        if (canRelax) {
          dist[edge.to] = dist[u] + edge.w;
          relaxations += 1;
          steps.push(makeStep(nodes, edges, dist, visited, edge.to, edge.edgeIndex, `Update dist[${edge.to}] to ${fmt(dist[edge.to])}`, "A shorter path is found using this edge.", 6, pass, relaxations));
        }
      }
    }

    steps.push(makeStep(nodes, edges, dist, visited, -1, -1, "Dijkstra complete", "All reachable nodes have final shortest distances.", 2, nodeCount, relaxations));

    return { timeline: steps, trackerKeys: ["pass", "currentNode", "relaxations", "dist", "visited"] };
  }
};

function makeStep(nodes, edges, dist, visited, currentNode, currentEdge, description, why, codeLine, pass, relaxations) {
  return {
    description,
    why,
    passCount: pass,
    stateSnapshot: {
      mode: "graph",
      nodes: nodes.map((node, index) => ({ ...node, label: `${index} (${fmt(dist[index])})` })),
      edges,
      directed: false
    },
    highlightedElements: {
      currentNode,
      currentEdge,
      visitedNodes: [...visited]
    },
    codeLine,
    tracker: {
      pass,
      currentNode: currentNode < 0 ? "-" : currentNode,
      relaxations,
      dist: `[${dist.map(fmt).join(", ")}]`,
      visited: `[${visited.map(v => (v ? 1 : 0)).join(", ")}]`
    }
  };
}

function fmt(value) {
  return value === Infinity ? "inf" : String(value);
}
