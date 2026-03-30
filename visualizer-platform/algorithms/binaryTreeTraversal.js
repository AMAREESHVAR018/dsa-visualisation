export const binaryTreeTraversalAlgorithm = {
  key: "binaryTreeTraversal",
  label: "Binary Tree Level Traversal",
  category: "tree-list",
  description: "Top-down tree traversal with queue-based pointer movement.",
  codeLines: [
    "queue.add(root)",
    "while queue not empty:",
    "  node = queue.remove()",
    "  visit(node)",
    "  if node.left != null: queue.add(node.left)",
    "  if node.right != null: queue.add(node.right)"
  ],
  inputSchema: [
    {
      id: "nodeText",
      label: "Tree nodes level-order (use null for empty)",
      type: "text",
      defaultValue: "10,5,15,2,7,12,20"
    }
  ],
  createDefaultInput() {
    return { nodeText: "10,5,15,2,7,12,20" };
  },
  buildTimeline(input) {
    const raw = (input.nodeText || "").split(",").map(token => token.trim()).filter(Boolean);
    const values = raw.length ? raw.slice(0, 31).map(parseToken) : [10, 5, 15, 2, 7, 12, 20];
    const steps = [];

    if (!values.length || values[0] === null) {
      return {
        timeline: [{
          description: "Tree is empty.",
          why: "No root node is available, traversal ends immediately.",
          stateSnapshot: { mode: "tree-list", kind: "tree", nodes: [], edges: [] },
          highlightedElements: { currentNode: -1, queueIndices: [] },
          codeLine: 1,
          tracker: { queue: "[]", visited: "[]", currentNode: "-" }
        }],
        trackerKeys: ["queue", "visited", "currentNode"]
      };
    }

    const nodes = buildTreeNodes(values);
    const queue = [0];
    const visited = new Set();

    steps.push(makeStep(nodes, queue, visited, 0, "Initialize queue with root node", "Level-order traversal starts from root.", 1));

    while (queue.length > 0) {
      const current = queue.shift();
      visited.add(current);

      steps.push(makeStep(nodes, queue, visited, current, `Visit node ${nodes[current].value}`, "Dequeuing gives the next node in level order.", 3));

      const left = current * 2 + 1;
      const right = current * 2 + 2;

      if (nodes[left]) {
        queue.push(left);
        steps.push(makeStep(nodes, queue, visited, left, `Queue left child ${nodes[left].value}`, "Left child is discovered and queued.", 5));
      }

      if (nodes[right]) {
        queue.push(right);
        steps.push(makeStep(nodes, queue, visited, right, `Queue right child ${nodes[right].value}`, "Right child is discovered and queued.", 6));
      }
    }

    steps.push(makeStep(nodes, queue, visited, -1, "Traversal complete", "Queue is empty, all reachable nodes visited.", 2));

    return {
      timeline: steps,
      trackerKeys: ["queue", "visited", "currentNode"]
    };
  }
};

function parseToken(token) {
  if (token.toLowerCase() === "null") return null;
  const value = Number(token);
  return Number.isFinite(value) ? value : null;
}

function buildTreeNodes(values) {
  const present = [];
  const depthSpacing = 70;
  const width = 760;

  for (let i = 0; i < values.length; i += 1) {
    if (values[i] === null) continue;
    const depth = Math.floor(Math.log2(i + 1));
    const levelStart = Math.pow(2, depth) - 1;
    const indexInLevel = i - levelStart;
    const levelCount = Math.pow(2, depth);
    const gap = width / (levelCount + 1);

    present[i] = {
      id: i,
      value: values[i],
      depth,
      x: Math.round(gap * (indexInLevel + 1)),
      y: 60 + depth * depthSpacing
    };
  }

  return present;
}

function makeStep(nodes, queue, visited, currentNode, description, why, codeLine) {
  const edges = [];
  nodes.forEach((node, index) => {
    if (!node) return;
    const left = index * 2 + 1;
    const right = index * 2 + 2;
    if (nodes[left]) edges.push({ u: index, v: left, w: "" });
    if (nodes[right]) edges.push({ u: index, v: right, w: "" });
  });

  return {
    description,
    why,
    stateSnapshot: {
      mode: "tree-list",
      kind: "tree",
      nodes: nodes.filter(Boolean).map(node => ({
        id: node.id,
        x: node.x,
        y: node.y,
        label: String(node.value)
      })),
      edges
    },
    highlightedElements: {
      currentNode,
      queueIndices: [...queue],
      visitedNodes: nodes.map((node, index) => Boolean(node) && visited.has(index))
    },
    codeLine,
    tracker: {
      queue: `[${queue.map(index => nodes[index]?.value).filter(Boolean).join(", ")}]`,
      visited: `[${[...visited].map(index => nodes[index]?.value).filter(Boolean).join(", ")}]`,
      currentNode: currentNode < 0 ? "-" : nodes[currentNode]?.value
    }
  };
}
