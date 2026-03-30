import { boolArray, buildCircleNodes, parseEdges } from "./helpers.js";

const DEFAULT_BFS_EDGES = [
  "0 1 1",
  "0 2 1",
  "1 3 1",
  "2 3 1",
  "2 4 1",
  "3 5 1",
  "4 5 1"
].join("\n");

export const bfsAlgorithm = {
  key: "bfs",
  label: "Breadth-First Search",
  category: "graph",
  description: "Layer-by-layer traversal using a queue.",
  codeLines: [
    "queue.add(source)",
    "visited[source] = true",
    "while queue not empty:",
    "  node = queue.remove()",
    "  for each neighbor of node:",
    "    if not visited[neighbor]:",
    "      visited[neighbor] = true; queue.add(neighbor)"
  ],
  inputSchema: [
    { id: "nodeCount", label: "Node count", type: "number", defaultValue: "6" },
    { id: "source", label: "Source node", type: "number", defaultValue: "0" },
    { id: "edgeText", label: "Edges (u v w per line)", type: "textarea", defaultValue: DEFAULT_BFS_EDGES }
  ],
  createDefaultInput() {
    return { nodeCount: "6", source: "0", edgeText: DEFAULT_BFS_EDGES };
  },
  buildTimeline(input) {
    const nodeCount = Math.max(2, Math.min(12, Number(input.nodeCount) || 6));
    const source = Math.max(0, Math.min(nodeCount - 1, Number(input.source) || 0));
    const edges = parseEdges(input.edgeText, parseEdges(DEFAULT_BFS_EDGES, []))
      .filter(edge => edge.u >= 0 && edge.u < nodeCount && edge.v >= 0 && edge.v < nodeCount)
      .map((edge, index) => ({ ...edge, id: index }));

    const adjacency = Array.from({ length: nodeCount }, () => []);
    edges.forEach((edge, index) => {
      adjacency[edge.u].push({ to: edge.v, edgeIndex: index });
      adjacency[edge.v].push({ to: edge.u, edgeIndex: index });
    });

    const nodes = buildCircleNodes(nodeCount);
    const visited = boolArray(nodeCount, false);
    const queue = [source];
    const steps = [];
    let iteration = 0;

    visited[source] = true;
    steps.push(makeStep({
      nodes,
      edges,
      visited,
      queue,
      currentNode: source,
      currentEdge: -1,
      description: `Initialize queue with source ${source}`,
      why: "BFS begins at the source and explores neighbors level by level.",
      codeLine: 1,
      tracker: makeTracker(visited, queue, source, iteration)
    }));

    while (queue.length > 0) {
      iteration += 1;
      const node = queue.shift();

      steps.push(makeStep({
        nodes,
        edges,
        visited,
        queue,
        currentNode: node,
        currentEdge: -1,
        description: `Dequeue node ${node}`,
        why: "The front of the queue is processed first to keep breadth-first order.",
        codeLine: 4,
        tracker: makeTracker(visited, queue, node, iteration)
      }));

      for (const neighbor of adjacency[node]) {
        steps.push(makeStep({
          nodes,
          edges,
          visited,
          queue,
          currentNode: node,
          currentEdge: neighbor.edgeIndex,
          description: `Inspect neighbor ${neighbor.to} from node ${node}`,
          why: "Every outgoing edge is checked once node is dequeued.",
          codeLine: 5,
          tracker: makeTracker(visited, queue, node, iteration)
        }));

        if (!visited[neighbor.to]) {
          visited[neighbor.to] = true;
          queue.push(neighbor.to);

          steps.push(makeStep({
            nodes,
            edges,
            visited,
            queue,
            currentNode: neighbor.to,
            currentEdge: neighbor.edgeIndex,
            description: `Visit and enqueue node ${neighbor.to}`,
            why: "Unvisited neighbor becomes discovered and is queued for future expansion.",
            codeLine: 7,
            tracker: makeTracker(visited, queue, neighbor.to, iteration)
          }));
        }
      }
    }

    steps.push(makeStep({
      nodes,
      edges,
      visited,
      queue,
      currentNode: -1,
      currentEdge: -1,
      description: "BFS complete",
      why: "Queue is empty, so every reachable vertex has been visited.",
      codeLine: 3,
      tracker: makeTracker(visited, queue, -1, iteration)
    }));

    return {
      timeline: steps,
      trackerKeys: ["iteration", "currentNode", "queue", "visited"]
    };
  }
};

function makeStep({ nodes, edges, visited, queue, currentNode, currentEdge, description, why, codeLine, tracker }) {
  return {
    description,
    why,
    stateSnapshot: {
      mode: "graph",
      nodes: nodes.map(node => ({ ...node, label: String(node.id) })),
      edges,
      directed: false
    },
    highlightedElements: {
      visitedNodes: [...visited],
      currentNode,
      currentEdge,
      queue: [...queue]
    },
    codeLine,
    tracker
  };
}

function makeTracker(visited, queue, currentNode, iteration) {
  return {
    iteration,
    currentNode: currentNode < 0 ? "-" : currentNode,
    queue: `[${queue.join(", ")}]`,
    visited: `[${visited.map(value => (value ? 1 : 0)).join(", ")}]`
  };
}
