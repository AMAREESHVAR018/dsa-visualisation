import { parseNumberList } from "./helpers.js";

const DEFAULT_LIST = [10, 20, 30, 40, 50];

export const linkedListTraversalAlgorithm = {
  key: "linkedListTraversal",
  label: "Linked List Traversal",
  category: "tree-list",
  description: "Pointer movement through singly linked list nodes.",
  codeLines: [
    "Node current = head;",
    "while (current != null) {",
    "  visit(current.data);",
    "  current = current.next;",
    "}"
  ],
  inputSchema: [
    {
      id: "listText",
      label: "List values (comma separated)",
      type: "text",
      defaultValue: DEFAULT_LIST.join(",")
    }
  ],
  createDefaultInput() {
    return { listText: DEFAULT_LIST.join(",") };
  },
  buildTimeline(input) {
    const values = parseNumberList(input.listText, DEFAULT_LIST).slice(0, 12);
    const steps = [];
    const visited = Array.from({ length: values.length }, () => false);

    steps.push(makeStep({
      values,
      pointerIndex: 0,
      visited,
      description: "Start at head pointer.",
      why: "Traversal always starts from the first node in a singly linked list.",
      codeLine: 1,
      tracker: tracker(0, visited, values)
    }));

    for (let i = 0; i < values.length; i += 1) {
      visited[i] = true;
      steps.push(makeStep({
        values,
        pointerIndex: i,
        visited,
        description: `Visit node ${i} with value ${values[i]}`,
        why: "Current pointer references this node, so we process it before moving on.",
        codeLine: 3,
        tracker: tracker(i, visited, values)
      }));

      steps.push(makeStep({
        values,
        pointerIndex: i + 1,
        visited,
        description: "Move pointer to next node.",
        why: "Linked list traversal advances by following the next reference.",
        codeLine: 4,
        tracker: tracker(i + 1, visited, values)
      }));
    }

    steps.push(makeStep({
      values,
      pointerIndex: values.length,
      visited,
      description: "Pointer reached null. Traversal complete.",
      why: "When current becomes null, there are no more nodes to visit.",
      codeLine: 5,
      tracker: tracker(values.length, visited, values)
    }));

    return {
      timeline: steps,
      trackerKeys: ["pointer", "visitedCount", "visitedFlags", "values"]
    };
  }
};

function makeStep({ values, pointerIndex, visited, description, why, codeLine, tracker }) {
  return {
    description,
    why,
    stateSnapshot: {
      mode: "tree-list",
      values: [...values]
    },
    highlightedElements: {
      pointerIndex,
      visitedNodes: [...visited]
    },
    codeLine,
    tracker
  };
}

function tracker(pointerIndex, visited, values) {
  return {
    pointer: pointerIndex >= values.length ? "null" : pointerIndex,
    visitedCount: visited.filter(Boolean).length,
    visitedFlags: `[${visited.map(flag => (flag ? 1 : 0)).join(", ")}]`,
    values: `[${values.join(", ")}]`
  };
}
