import { bankAccountAlgorithm } from "./bankAccount.js";
import { bellmanFordAlgorithm } from "./bellmanFord.js";
import { bfsAlgorithm } from "./bfs.js";
import { binarySearchAlgorithm } from "./binarySearch.js";
import { binaryTreeTraversalAlgorithm } from "./binaryTreeTraversal.js";
import { buildingsReceivingSunlightAlgorithm } from "./buildingsReceivingSunlight.js";
import { bubbleSortAlgorithm } from "./bubbleSort.js";
import { celebrityProblemAlgorithm } from "./celebrityProblem.js";
import { checkPalindromeStringAlgorithm } from "./checkPalindromeString.js";
import { countingSortAlgorithm } from "./countingSort.js";
import { dfsAlgorithm } from "./dfs.js";
import { dijkstraAlgorithm } from "./dijkstra.js";
import { dynamicProgrammingAlgorithm } from "./dynamicProgramming.js";
import { factorialAlgorithm } from "./factorial.js";
import { fibonacciDPAlgorithm } from "./fibonacciDP.js";
import { floydWarshallAlgorithm } from "./floydWarshall.js";
import { gcdAlgorithm } from "./gcd.js";
import { heapSortAlgorithm } from "./heapSort.js";
import { insertionSortAlgorithm } from "./insertionSort.js";
import { createGenericAlgorithm, inferCategory } from "./genericAlgorithmFactory.js";
import { kruskalAlgorithm } from "./kruskal.js";
import { linearSearchAlgorithm } from "./linearSearch.js";
import { linkedListTraversalAlgorithm } from "./linkedListTraversal.js";
import { longestPalindromicSubstringAlgorithm } from "./longestPalindromicSubstring.js";
import { maxDifferenceAlgorithm } from "./maxDifference.js";
import { maxSubarraySumAlgorithm } from "./maxSubarraySum.js";
import { mergeSortAlgorithm } from "./mergeSort.js";
import { mergeStringsAlternatelyAlgorithm } from "./mergeStringsAlternately.js";
import { minimumPlatformsAlgorithm } from "./minimumPlatforms.js";
import { primAlgorithm } from "./prim.js";
import { quickSortAlgorithm } from "./quickSort.js";
import { radixSortAlgorithm } from "./radixSort.js";
import { rectangleOverlapAlgorithm } from "./rectangleOverlap.js";
import { regularExpressionMatchingAlgorithm } from "./regularExpressionMatching.js";
import { replaceZeroWithFiveInIntegerAlgorithm } from "./replaceZeroWithFiveInInteger.js";
import { selectionSortAlgorithm } from "./selectionSort.js";
import { stringToIntegerAtoiAlgorithm } from "./stringToIntegerAtoi.js";
import { SRC_CLASS_NAMES } from "./srcClassList.js";

const customByClass = {
  BankAccount: bankAccountAlgorithm,
  BubbleSort: bubbleSortAlgorithm,
  BubbleSortImplementation: bubbleSortAlgorithm,
  SelectionSort: selectionSortAlgorithm,
  MergeSort: mergeSortAlgorithm,
  QuickSort: quickSortAlgorithm,
  BinarySearch: binarySearchAlgorithm,
  BinarySearchAlgorithm: binarySearchAlgorithm,
  BellmanFordAlgorithm: bellmanFordAlgorithm,
  DijkstrasAlgorithm: dijkstraAlgorithm,
  FloydWarshallAlgorithm: floydWarshallAlgorithm,
  KruskalsAlgorithm: kruskalAlgorithm,
  PrimsAlgorithm: primAlgorithm,
  BFS: bfsAlgorithm,
  DFS: dfsAlgorithm,
  CircularLinkedListTraversal: linkedListTraversalAlgorithm,
  BinaryTreeTraversal: binaryTreeTraversalAlgorithm,
  BuildingsReceivingSunlight: buildingsReceivingSunlightAlgorithm,
  CelebrityProblem: celebrityProblemAlgorithm,
  CheckPalindromeString: checkPalindromeStringAlgorithm,
  CountingSort: countingSortAlgorithm,
  DynamicProgramming: dynamicProgrammingAlgorithm,
  FactorialOfANumber: factorialAlgorithm,
  FibonacciUsingDynamicProgramming: fibonacciDPAlgorithm,
  GCDOfTwoNumbers: gcdAlgorithm,
  HeapSort: heapSortAlgorithm,
  InsertionSort: insertionSortAlgorithm,
  LinearSearch: linearSearchAlgorithm,
  LongestPalindromicSubstring: longestPalindromicSubstringAlgorithm,
  MaximumDifferenceInArray: maxDifferenceAlgorithm,
  MaximumSubarraySum: maxSubarraySumAlgorithm,
  MergeStringsAlternately: mergeStringsAlternatelyAlgorithm,
  MinimumPlatformsRequired: minimumPlatformsAlgorithm,
  RadixSort: radixSortAlgorithm,
  RectangleOverlap: rectangleOverlapAlgorithm,
  RegularExpressionMatching: regularExpressionMatchingAlgorithm,
  ReplaceZeroWithFiveInInteger: replaceZeroWithFiveInIntegerAlgorithm,
  StringToIntegerAtoi: stringToIntegerAtoiAlgorithm
};

export const algorithmRegistry = SRC_CLASS_NAMES.map(className => {
  const custom = customByClass[className];
  if (custom) {
    return {
      ...custom,
      key: className,
      label: className.replace(/([a-z])([A-Z])/g, "$1 $2")
    };
  }

  return createGenericAlgorithm(className, inferCategory(className));
});

const aliases = {
  bubbleSort: "BubbleSort",
  selectionSort: "SelectionSort",
  mergeSort: "MergeSort",
  quickSort: "QuickSort",
  binarySearch: "BinarySearch",
  bellmanFord: "BellmanFordAlgorithm",
  dijkstra: "DijkstrasAlgorithm",
  floydWarshall: "FloydWarshallAlgorithm",
  kruskal: "KruskalsAlgorithm",
  prim: "PrimsAlgorithm",
  bfs: "BFS",
  dfs: "DFS",
  linkedListTraversal: "CircularLinkedListTraversal",
  binaryTreeTraversal: "BinaryTreeTraversal",
  linkedList: "CircularLinkedListTraversal",
  palindrome: "CheckPalindromeString",
  longestPalindrome: "LongestPalindromicSubstring",
  atoi: "StringToIntegerAtoi",
  mergeStrings: "MergeStringsAlternately",
  replaceZero: "ReplaceZeroWithFiveInInteger",
  regex: "RegularExpressionMatching"
};

export function getAlgorithmByKey(key) {
  const normalized = aliases[key] || key;
  return algorithmRegistry.find(algorithm => algorithm.key === normalized) || algorithmRegistry[0];
}
