import { buildCircleNodes, parseEdges } from "./helpers.js";

const DEFAULT_EDGES = [
  "0 1 4",
  "0 2 2",
  "1 2 1",
  "1 3 5",
  "2 3 8",
  "2 4 10",
  "3 4 2"
].join("\n");

export const primAlgorithm = {
  key: "prim",
  label: "Prim MST",
  category: "graph",
  description: "Grow minimum spanning tree from a start node using cut edges.",
  codeLines: [
    "visited[source] = true",
    "while MST has fewer than V-1 edges:",
    "  choose minimum edge crossing visited -> unvisited",
    "  add edge to MST",
    "  mark new node visited"
  ],
  inputSchema: [
    { id: "nodeCount", label: "Node count", type: "number", defaultValue: "5" },
    { id: "source", label: "Source node", type: "number", defaultValue: "0" },
    { id: "edgeText", label: "Edges (u v w per line)", type: "textarea", defaultValue: DEFAULT_EDGES }
  ],
  createDefaultInput() {
    return { nodeCount: "5", source: "0", edgeText: DEFAULT_EDGES };
  },
  buildTimeline(input) {
    const nodeCount = Math.max(2, Math.min(12, Number(input.nodeCount) || 5));
    const source = Math.max(0, Math.min(nodeCount - 1, Number(input.source) || 0));
    const edges = parseEdges(input.edgeText, parseEdges(DEFAULT_EDGES, []))
      .filter(e => e.u >= 0 && e.u < nodeCount && e.v >= 0 && e.v < nodeCount)
      .map((e, id) => ({ ...e, id }));

    const visited = Array.from({ length: nodeCount }, () => false);
    const mst = new Set();
    const nodes = buildCircleNodes(nodeCount);
    const steps = [];

    visited[source] = true;
    let mstWeight = 0;

    steps.push(makeStep(nodes, edges, visited, mst, -1, `Start from source ${source}`, "Prim begins with one visited node and expands outward.", 1, { mstWeight, edgesTaken: 0 }));

    while (mst.size < nodeCount - 1) {
      let chosen = null;
      let chosenIndex = -1;

      for (let i = 0; i < edges.length; i += 1) {
        const e = edges[i];
        const cross = visited[e.u] !== visited[e.v];
        if (!cross) continue;
        if (!chosen || e.w < chosen.w) {
          chosen = e;
          chosenIndex = i;
        }
      }

      if (!chosen) break;

      steps.push(makeStep(nodes, edges, visited, mst, chosenIndex, `Pick lightest crossing edge ${chosen.u}-${chosen.v}`, "This edge minimally expands the current visited set.", 3, { mstWeight, edgesTaken: mst.size }));

      mst.add(chosen.id);
      mstWeight += chosen.w;
      visited[chosen.u] = true;
      visited[chosen.v] = true;

      steps.push(makeStep(nodes, edges, visited, mst, chosenIndex, `Add edge ${chosen.u}-${chosen.v} to MST`, "Edge is safe and keeps tree minimal.", 4, { mstWeight, edgesTaken: mst.size }));
    }

    return { timeline: steps, trackerKeys: ["mstWeight", "edgesTaken"] };
  }
};

function makeStep(nodes, edges, visited, mst, currentEdge, description, why, codeLine, tracker) {
  const edge = edges[currentEdge];
  const updatedNode = edge ? (visited[edge.v] ? edge.v : edge.u) : undefined;

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
      currentEdge,
      visitedNodes: [...visited],
      mstEdges: [...mst],
      updatedNode
    },
    codeLine,
    tracker
  };
}
