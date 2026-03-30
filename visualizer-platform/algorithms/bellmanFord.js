import { buildCircleNodes, cloneArray, parseEdges } from "./helpers.js";

const DEFAULT_EDGE_TEXT = [
  "0 1 6",
  "0 2 7",
  "1 2 8",
  "1 3 5",
  "1 4 -4",
  "2 3 -3",
  "2 4 9",
  "3 1 -2",
  "4 3 7"
].join("\n");

export const bellmanFordAlgorithm = {
  key: "bellmanFord",
  label: "Bellman-Ford",
  category: "graph",
  description: "Single-source shortest path with support for negative edges and cycle detection.",
  codeLines: [
    "dist[source] = 0",
    "repeat V - 1 times:",
    "  for each edge (u, v, w):",
    "    if dist[u] != inf and dist[u] + w < dist[v]:",
    "      dist[v] = dist[u] + w",
    "for each edge (u, v, w):",
    "  if dist[u] + w < dist[v]: negative cycle"
  ],
  inputSchema: [
    {
      id: "nodeCount",
      label: "Node count",
      type: "number",
      defaultValue: "5"
    },
    {
      id: "source",
      label: "Source node",
      type: "number",
      defaultValue: "0"
    },
    {
      id: "edgeText",
      label: "Edges (u v w per line)",
      type: "textarea",
      defaultValue: DEFAULT_EDGE_TEXT
    }
  ],
  createDefaultInput() {
    return { nodeCount: "5", source: "0", edgeText: DEFAULT_EDGE_TEXT };
  },
  buildTimeline(input) {
    const nodeCount = clampNodeCount(Number(input.nodeCount) || 5);
    const source = clampIndex(Number(input.source) || 0, nodeCount);
    const fallbackEdges = parseEdges(DEFAULT_EDGE_TEXT, []);
    const edges = parseEdges(input.edgeText, fallbackEdges).filter(
      edge => edge.u >= 0 && edge.u < nodeCount && edge.v >= 0 && edge.v < nodeCount
    );

    const nodes = buildCircleNodes(nodeCount);
    const dist = Array.from({ length: nodeCount }, () => Infinity);
    dist[source] = 0;

    const steps = [];
    let relaxations = 0;

    steps.push(makeStep({
      nodes,
      edges,
      dist,
      description: `Initialize distances: source ${source} = 0, others = inf`,
      why: "Bellman-Ford starts from one known source distance and expands shortest paths.",
      currentEdge: -1,
      updatedNode: -1,
      passCount: 0,
      codeLine: 1,
      negativeCycle: false,
      tracker: buildTracker({ dist, source, pass: 0, currentEdge: null, relaxations, updatedNode: null, negativeCycle: false })
    }));

    for (let pass = 1; pass <= nodeCount - 1; pass += 1) {
      let changed = false;

      for (let edgeIndex = 0; edgeIndex < edges.length; edgeIndex += 1) {
        const edge = edges[edgeIndex];
        const canRelax = dist[edge.u] !== Infinity && dist[edge.u] + edge.w < dist[edge.v];

        steps.push(makeStep({
          nodes,
          edges,
          dist,
          description: `Pass ${pass}: inspect edge ${edge.u} -> ${edge.v} (w=${edge.w})`,
          why: "Each pass tries to improve shortest paths by relaxing every edge.",
          currentEdge: edgeIndex,
          updatedNode: -1,
          passCount: pass,
          codeLine: 3,
          negativeCycle: false,
          tracker: buildTracker({
            dist,
            source,
            pass,
            currentEdge: `${edge.u}->${edge.v}`,
            relaxations,
            updatedNode: null,
            negativeCycle: false
          })
        }));

        if (canRelax) {
          dist[edge.v] = dist[edge.u] + edge.w;
          changed = true;
          relaxations += 1;

          steps.push(makeStep({
            nodes,
            edges,
            dist,
            description: `Relax edge ${edge.u} -> ${edge.v}. dist[${edge.v}] updated to ${fmtDist(dist[edge.v])}`,
            why: "A shorter path was found through the current edge, so we update distance.",
            currentEdge: edgeIndex,
            updatedNode: edge.v,
            passCount: pass,
            codeLine: 5,
            negativeCycle: false,
            tracker: buildTracker({
              dist,
              source,
              pass,
              currentEdge: `${edge.u}->${edge.v}`,
              relaxations,
              updatedNode: edge.v,
              negativeCycle: false
            })
          }));
        }
      }

      steps.push(makeStep({
        nodes,
        edges,
        dist,
        description: `Pass ${pass} finished${changed ? " with updates" : " with no updates"}`,
        why: changed
          ? "At least one edge improved the shortest path in this pass."
          : "No edge improved any path, distances are stable now.",
        currentEdge: -1,
        updatedNode: -1,
        passCount: pass,
        codeLine: 2,
        negativeCycle: false,
        tracker: buildTracker({
          dist,
          source,
          pass,
          currentEdge: null,
          relaxations,
          updatedNode: null,
          negativeCycle: false
        })
      }));

      if (!changed) break;
    }

    let negativeCycleFound = false;
    for (let edgeIndex = 0; edgeIndex < edges.length; edgeIndex += 1) {
      const edge = edges[edgeIndex];
      if (dist[edge.u] !== Infinity && dist[edge.u] + edge.w < dist[edge.v]) {
        negativeCycleFound = true;

        steps.push(makeStep({
          nodes,
          edges,
          dist,
          description: `Negative cycle detected using edge ${edge.u} -> ${edge.v}`,
          why: "A further relaxation after V-1 passes proves a reachable negative cycle.",
          currentEdge: edgeIndex,
          updatedNode: edge.v,
          passCount: nodeCount - 1,
          codeLine: 7,
          negativeCycle: true,
          tracker: buildTracker({
            dist,
            source,
            pass: nodeCount - 1,
            currentEdge: `${edge.u}->${edge.v}`,
            relaxations,
            updatedNode: edge.v,
            negativeCycle: true
          })
        }));

        break;
      }
    }

    if (!negativeCycleFound) {
      steps.push(makeStep({
        nodes,
        edges,
        dist,
        description: "No negative cycle reachable from source.",
        why: "No edge can be relaxed after V-1 passes, so shortest paths are valid.",
        currentEdge: -1,
        updatedNode: -1,
        passCount: nodeCount - 1,
        codeLine: 6,
        negativeCycle: false,
        tracker: buildTracker({
          dist,
          source,
          pass: nodeCount - 1,
          currentEdge: null,
          relaxations,
          updatedNode: null,
          negativeCycle: false
        })
      }));
    }

    return {
      timeline: steps,
      trackerKeys: ["source", "pass", "currentEdge", "relaxations", "updatedNode", "negativeCycle", "dist"]
    };
  }
};

function makeStep({ nodes, edges, dist, description, why, currentEdge, updatedNode, passCount, codeLine, negativeCycle, tracker }) {
  return {
    description,
    why,
    passCount,
    negativeCycle,
    stateSnapshot: {
      mode: "graph",
      nodes: nodes.map(node => ({
        ...node,
        label: `${node.id} (${fmtDist(dist[node.id])})`
      })),
      edges: edges.map((edge, index) => ({
        ...edge,
        id: index
      })),
      dist: cloneArray(dist),
      directed: true
    },
    highlightedElements: {
      currentEdge,
      updatedNode,
      visitedNodes: dist.map(value => value !== Infinity),
      negativeCycle
    },
    codeLine,
    tracker
  };
}

function buildTracker({ dist, source, pass, currentEdge, relaxations, updatedNode, negativeCycle }) {
  return {
    source,
    pass,
    currentEdge: currentEdge || "-",
    relaxations,
    updatedNode: updatedNode === null ? "-" : updatedNode,
    negativeCycle,
    dist: `[${dist.map(fmtDist).join(", ")}]`
  };
}

function fmtDist(value) {
  return value === Infinity ? "inf" : String(value);
}

function clampNodeCount(value) {
  const n = Number.isFinite(value) ? Math.floor(value) : 5;
  return Math.max(2, Math.min(n, 12));
}

function clampIndex(value, nodeCount) {
  if (!Number.isFinite(value)) return 0;
  return Math.max(0, Math.min(Math.floor(value), nodeCount - 1));
}
