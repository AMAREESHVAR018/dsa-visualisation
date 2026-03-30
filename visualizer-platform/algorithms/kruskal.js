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

export const kruskalAlgorithm = {
  key: "kruskal",
  label: "Kruskal MST",
  category: "graph",
  description: "Minimum spanning tree by sorted edges and disjoint-set union.",
  codeLines: [
    "sort edges by weight",
    "for each edge (u, v, w):",
    "  if find(u) != find(v):",
    "    union(u, v)",
    "    add edge to MST"
  ],
  inputSchema: [
    { id: "nodeCount", label: "Node count", type: "number", defaultValue: "5" },
    { id: "edgeText", label: "Edges (u v w per line)", type: "textarea", defaultValue: DEFAULT_EDGES }
  ],
  createDefaultInput() {
    return { nodeCount: "5", edgeText: DEFAULT_EDGES };
  },
  buildTimeline(input) {
    const nodeCount = Math.max(2, Math.min(12, Number(input.nodeCount) || 5));
    const edges = parseEdges(input.edgeText, parseEdges(DEFAULT_EDGES, []))
      .filter(e => e.u >= 0 && e.u < nodeCount && e.v >= 0 && e.v < nodeCount)
      .map((e, id) => ({ ...e, id }))
      .sort((a, b) => a.w - b.w);

    const parent = Array.from({ length: nodeCount }, (_, i) => i);
    const rank = Array.from({ length: nodeCount }, () => 0);
    const mst = new Set();
    const nodes = buildCircleNodes(nodeCount);
    const steps = [];

    steps.push(makeStep(nodes, edges, mst, -1, "Sort edges by weight", "Kruskal starts from lightest edges.", 1, { mstWeight: 0, edgesTaken: 0 }));

    const find = x => (parent[x] === x ? x : (parent[x] = find(parent[x])));
    const union = (a, b) => {
      a = find(a);
      b = find(b);
      if (a === b) return false;
      if (rank[a] < rank[b]) parent[a] = b;
      else if (rank[a] > rank[b]) parent[b] = a;
      else {
        parent[b] = a;
        rank[a] += 1;
      }
      return true;
    };

    let mstWeight = 0;

    for (let i = 0; i < edges.length; i += 1) {
      const edge = edges[i];
      steps.push(makeStep(nodes, edges, mst, edge.id, `Inspect edge ${edge.u}-${edge.v} (w=${edge.w})`, "Choose edge if it does not form a cycle.", 2, { mstWeight, edgesTaken: mst.size }));

      if (union(edge.u, edge.v)) {
        mst.add(edge.id);
        mstWeight += edge.w;
        steps.push(makeStep(nodes, edges, mst, edge.id, `Take edge ${edge.u}-${edge.v}`, "Endpoints were in different components.", 5, { mstWeight, edgesTaken: mst.size }));
      } else {
        steps.push(makeStep(nodes, edges, mst, edge.id, `Skip edge ${edge.u}-${edge.v}`, "Adding this edge would create a cycle.", 3, { mstWeight, edgesTaken: mst.size }));
      }
    }

    return { timeline: steps, trackerKeys: ["mstWeight", "edgesTaken"] };
  }
};

function makeStep(nodes, edges, mst, currentEdgeId, description, why, codeLine, tracker) {
  const edge = edges.find(e => e.id === currentEdgeId);
  const updatedNode = edge ? edge.v : undefined;

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
      currentEdge: edges.findIndex(e => e.id === currentEdgeId),
      visitedNodes: nodes.map(() => false),
      mstEdges: [...mst],
      updatedNode
    },
    codeLine,
    tracker
  };
}
