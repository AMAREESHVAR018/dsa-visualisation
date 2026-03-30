export const srcTemplates = [
  {
    className: "BubbleSort",
    label: "BubbleSort.java sample",
    algorithmKey: "BubbleSort",
    inputValues: { arrayText: "9,4,7,2,6,1,8,3,5" }
  },
  {
    className: "SelectionSort",
    label: "SelectionSort.java sample",
    algorithmKey: "SelectionSort",
    inputValues: { arrayText: "29,10,14,37,13,5,22" }
  },
  {
    className: "MergeSort",
    label: "MergeSort.java sample",
    algorithmKey: "MergeSort",
    inputValues: { arrayText: "38,27,43,3,9,82,10" }
  },
  {
    className: "QuickSort",
    label: "QuickSort.java sample",
    algorithmKey: "QuickSort",
    inputValues: { arrayText: "10,7,8,9,1,5" }
  },
  {
    className: "BinarySearch",
    label: "BinarySearch.java sample",
    algorithmKey: "BinarySearch",
    inputValues: { arrayText: "1,3,5,7,9,11,13,15", target: "11" }
  },
  {
    className: "BinarySearchAlgorithm",
    label: "BinarySearchAlgorithm.java sample",
    algorithmKey: "BinarySearchAlgorithm",
    inputValues: { arrayText: "1,3,5,7,9,11,13,15", target: "7" }
  },
  {
    className: "BFS",
    label: "BFS.java sample",
    algorithmKey: "BFS",
    inputValues: {
      nodeCount: "6",
      source: "0",
      edgeText: "0 1 1\n0 2 1\n1 3 1\n2 3 1\n2 4 1\n3 5 1\n4 5 1"
    }
  },
  {
    className: "DFS",
    label: "DFS.java sample",
    algorithmKey: "DFS",
    inputValues: {
      nodeCount: "6",
      source: "0",
      edgeText: "0 1 1\n0 2 1\n1 3 1\n2 4 1\n4 5 1"
    }
  },
  {
    className: "BellmanFordAlgorithm",
    label: "BellmanFordAlgorithm.java sample",
    algorithmKey: "BellmanFordAlgorithm",
    inputValues: {
      nodeCount: "5",
      source: "0",
      edgeText: "0 1 6\n0 2 7\n1 2 8\n1 3 5\n1 4 -4\n2 3 -3\n2 4 9\n3 1 -2\n4 3 7"
    }
  },
  {
    className: "DijkstrasAlgorithm",
    label: "DijkstrasAlgorithm.java sample",
    algorithmKey: "DijkstrasAlgorithm",
    inputValues: {
      nodeCount: "6",
      source: "0",
      edgeText: "0 1 4\n0 2 2\n1 2 1\n1 3 5\n2 3 8\n2 4 10\n3 4 2\n3 5 6\n4 5 2"
    }
  },
  {
    className: "FloydWarshallAlgorithm",
    label: "FloydWarshallAlgorithm.java sample",
    algorithmKey: "FloydWarshallAlgorithm",
    inputValues: { matrixText: "0,3,inf,7\n8,0,2,inf\n5,inf,0,1\n2,inf,inf,0" }
  },
  {
    className: "KruskalsAlgorithm",
    label: "KruskalsAlgorithm.java sample",
    algorithmKey: "KruskalsAlgorithm",
    inputValues: { nodeCount: "5", edgeText: "0 1 4\n0 2 2\n1 2 1\n1 3 5\n2 3 8\n2 4 10\n3 4 2" }
  },
  {
    className: "PrimsAlgorithm",
    label: "PrimsAlgorithm.java sample",
    algorithmKey: "PrimsAlgorithm",
    inputValues: { nodeCount: "5", source: "0", edgeText: "0 1 4\n0 2 2\n1 2 1\n1 3 5\n2 3 8\n2 4 10\n3 4 2" }
  },
  {
    className: "CircularLinkedListTraversal",
    label: "CircularLinkedListTraversal.java sample",
    algorithmKey: "CircularLinkedListTraversal",
    inputValues: { listText: "10,20,30,40,50" }
  }
];

export function getTemplateByClassName(className) {
  return srcTemplates.find(template => template.className === className);
}
