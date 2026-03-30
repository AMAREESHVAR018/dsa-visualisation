package dsa_handbook.chatgpt.organizedtopics;

/*
 * Topic: Minimum Spanning Tree (MST)
 * Description: MST connects all nodes with minimum total edge cost.
 * Real-world usage: Designing minimum-cost cable layout for offices.
 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class MinimumSpanningTree {

    public static class Edge {
        int u;
        int v;
        int w;

        public Edge(int u, int v, int w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }
    }

    public static class WeightedEdge {
        int to;
        int weight;

        public WeightedEdge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    private static class DSU {
        private final int[] parent;
        private final int[] rank;

        DSU(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        boolean union(int a, int b) {
            int pa = find(a);
            int pb = find(b);
            if (pa == pb) {
                return false;
            }
            if (rank[pa] < rank[pb]) {
                parent[pa] = pb;
            } else if (rank[pb] < rank[pa]) {
                parent[pb] = pa;
            } else {
                parent[pb] = pa;
                rank[pa]++;
            }
            return true;
        }
    }

    // Problem 1: Kruskal's Algorithm
    // Explanation: Build MST by taking smallest valid edge one by one.
    // Approach: Sort edges by weight and use DSU to avoid cycles.
    // Time Complexity: O(E log E)
    // Space Complexity: O(V)
    public static int kruskal(int vertices, List<Edge> edges) {
        edges.sort(Comparator.comparingInt(e -> e.w));
        DSU dsu = new DSU(vertices);
        int totalWeight = 0;

        for (Edge edge : edges) {
            if (dsu.union(edge.u, edge.v)) {
                totalWeight += edge.w;
            }
        }

        return totalWeight;
    }

    // Problem 2: Prim's Algorithm
    // Explanation: Grow MST from a start node by always adding cheapest boundary edge.
    // Approach: Use min-heap to pick next minimum edge to unvisited node.
    // Time Complexity: O((V + E) log V)
    // Space Complexity: O(V)
    public static int prim(List<List<WeightedEdge>> graph) {
        int n = graph.size();
        boolean[] visited = new boolean[n];
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
        pq.offer(new int[]{0, 0});

        int totalWeight = 0;
        int usedNodes = 0;

        while (!pq.isEmpty() && usedNodes < n) {
            int[] current = pq.poll();
            int node = current[0];
            int weight = current[1];

            if (visited[node]) {
                continue;
            }

            visited[node] = true;
            usedNodes++;
            totalWeight += weight;

            for (WeightedEdge edge : graph.get(node)) {
                if (!visited[edge.to]) {
                    pq.offer(new int[]{edge.to, edge.weight});
                }
            }
        }

        if (usedNodes != n) {
            throw new IllegalStateException("Graph is disconnected");
        }

        return totalWeight;
    }

    public static void main(String[] args) {
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(0, 1, 10));
        edges.add(new Edge(0, 2, 6));
        edges.add(new Edge(1, 2, 5));

        System.out.println("Kruskal MST weight: " + kruskal(3, edges));

        List<List<WeightedEdge>> graph = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            graph.add(new ArrayList<>());
        }
        graph.get(0).add(new WeightedEdge(1, 10));
        graph.get(1).add(new WeightedEdge(0, 10));
        graph.get(0).add(new WeightedEdge(2, 6));
        graph.get(2).add(new WeightedEdge(0, 6));
        graph.get(1).add(new WeightedEdge(2, 5));
        graph.get(2).add(new WeightedEdge(1, 5));

        System.out.println("Prim MST weight: " + prim(graph));
    }
}
