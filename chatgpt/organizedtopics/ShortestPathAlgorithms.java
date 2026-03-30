package dsa_handbook.chatgpt.organizedtopics;

/*
 * Topic: Shortest Path Algorithms
 * Description: These algorithms find minimum travel cost between nodes in a graph.
 * Real-world usage: GPS apps finding shortest route between two places.
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class ShortestPathAlgorithms {

    public static class WeightedEdge {
        int to;
        int weight;

        public WeightedEdge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    public static class Edge {
        int from;
        int to;
        int weight;

        public Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

    // Problem 1: Dijkstra's Algorithm
    // Explanation: Finds shortest paths from one source when weights are non-negative.
    // Approach: Use min-heap; always expand the currently cheapest node.
    // Time Complexity: O((V + E) log V)
    // Space Complexity: O(V)
    public static int[] dijkstra(List<List<WeightedEdge>> graph, int source) {
        int n = graph.size();
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
        pq.offer(new int[]{source, 0});

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int node = current[0];
            int distance = current[1];

            if (distance > dist[node]) {
                continue;
            }

            for (WeightedEdge edge : graph.get(node)) {
                int newDist = distance + edge.weight;
                if (newDist < dist[edge.to]) {
                    dist[edge.to] = newDist;
                    pq.offer(new int[]{edge.to, newDist});
                }
            }
        }
        return dist;
    }

    // Problem 2: Bellman-Ford Algorithm
    // Explanation: Finds shortest paths even with negative edges.
    // Approach: Relax all edges V-1 times and then detect negative cycle.
    // Time Complexity: O(V * E)
    // Space Complexity: O(V)
    public static int[] bellmanFord(int vertices, List<Edge> edges, int source) {
        int[] dist = new int[vertices];
        Arrays.fill(dist, Integer.MAX_VALUE / 2);
        dist[source] = 0;

        for (int i = 1; i < vertices; i++) {
            for (Edge edge : edges) {
                if (dist[edge.from] + edge.weight < dist[edge.to]) {
                    dist[edge.to] = dist[edge.from] + edge.weight;
                }
            }
        }

        for (Edge edge : edges) {
            if (dist[edge.from] + edge.weight < dist[edge.to]) {
                throw new IllegalStateException("Negative cycle detected");
            }
        }

        return dist;
    }

    // Problem 3: Floyd-Warshall Algorithm
    // Explanation: Finds shortest paths between all pairs of nodes.
    // Approach: Use each node as intermediate and update matrix.
    // Time Complexity: O(V^3)
    // Space Complexity: O(V^2)
    public static int[][] floydWarshall(int[][] matrix) {
        int n = matrix.length;
        int[][] dist = new int[n][n];

        for (int i = 0; i < n; i++) {
            System.arraycopy(matrix[i], 0, dist[i], 0, n);
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }
        return dist;
    }

    public static void main(String[] args) {
        List<List<WeightedEdge>> graph = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            graph.add(new ArrayList<>());
        }
        graph.get(0).add(new WeightedEdge(1, 4));
        graph.get(0).add(new WeightedEdge(2, 1));
        graph.get(2).add(new WeightedEdge(1, 2));

        int[] dijkstraDist = dijkstra(graph, 0);
        System.out.println("Dijkstra dist to 1: " + dijkstraDist[1]);

        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(0, 1, 4));
        edges.add(new Edge(0, 2, 1));
        edges.add(new Edge(2, 1, 2));
        int[] bellman = bellmanFord(3, edges, 0);
        System.out.println("Bellman-Ford dist to 1: " + bellman[1]);

        int inf = 1_000_000;
        int[][] matrix = {
                {0, 4, 1},
                {inf, 0, inf},
                {inf, 2, 0}
        };
        int[][] allPairs = floydWarshall(matrix);
        System.out.println("Floyd-Warshall dist 0->1: " + allPairs[0][1]);
    }
}
