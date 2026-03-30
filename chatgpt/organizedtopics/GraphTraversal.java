package dsa_handbook.chatgpt.organizedtopics;

/*
 * Topic: Graph Traversal
 * Description: Traversal methods visit nodes of a graph in a specific order.
 * Real-world usage: Finding connected users in a social network graph.
 */
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class GraphTraversal {

    // Problem 1: Breadth-First Search (BFS)
    // Explanation: Visit nodes level by level from start node.
    // Approach: Use queue, visit current node, then push unvisited neighbors.
    // Time Complexity: O(V + E)
    // Space Complexity: O(V)
    public static List<Integer> bfs(List<List<Integer>> graph, int start) {
        boolean[] visited = new boolean[graph.size()];
        Queue<Integer> queue = new ArrayDeque<>();
        List<Integer> order = new ArrayList<>();

        visited[start] = true;
        queue.offer(start);

        while (!queue.isEmpty()) {
            int node = queue.poll();
            order.add(node);

            for (int next : graph.get(node)) {
                if (!visited[next]) {
                    visited[next] = true;
                    queue.offer(next);
                }
            }
        }
        return order;
    }

    // Problem 2: Depth-First Search (DFS)
    // Explanation: Go deep into one path before backtracking.
    // Approach: Recursively visit unvisited neighbors.
    // Time Complexity: O(V + E)
    // Space Complexity: O(V)
    public static List<Integer> dfs(List<List<Integer>> graph, int start) {
        boolean[] visited = new boolean[graph.size()];
        List<Integer> order = new ArrayList<>();
        dfsHelper(graph, start, visited, order);
        return order;
    }

    private static void dfsHelper(List<List<Integer>> graph, int node, boolean[] visited, List<Integer> order) {
        visited[node] = true;
        order.add(node);

        for (int next : graph.get(node)) {
            if (!visited[next]) {
                dfsHelper(graph, next, visited, order);
            }
        }
    }

    public static void main(String[] args) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            graph.add(new ArrayList<>());
        }
        graph.get(0).add(1);
        graph.get(0).add(2);
        graph.get(1).add(3);
        graph.get(2).add(4);

        System.out.println("BFS from 0: " + bfs(graph, 0));
        System.out.println("DFS from 0: " + dfs(graph, 0));
    }
}
