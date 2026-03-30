package dsa.handbook;

/*
==================================================
📌 PROBLEM: Prim Algorithm (MST)
==================================================

Build a network connecting all cities with minimum total cable cost.
At each step, extend the current connected component with the cheapest valid edge.

You are given:
- weighted undirected graph

👉 Your task:
compute total weight of Minimum Spanning Tree (MST), or report impossible if disconnected.

--------------------------------------------------
🧠 1. INTUITION (DEEP UNDERSTANDING)
--------------------------------------------------

Prim's invariant:
we maintain a visited set that already forms part of MST,
and always choose the minimum-weight edge crossing from visited to unvisited.

Why this works:
the minimum crossing edge is always safe to include (cut property).

--------------------------------------------------
❌ 2. WRONG APPROACH (BEGINNER MISTAKE)
--------------------------------------------------

Mistake:
pick globally smallest unused edge without checking frontier.

Why wrong:
you may create a cycle or connect two unvisited components,
which does not properly grow one MST tree from the current component.

--------------------------------------------------
🧪 3. STEP-BY-STEP DRY RUN (REAL EXAMPLE)
--------------------------------------------------

Graph (sample):
0-1(2), 0-3(6), 1-2(3), 1-4(5), 1-3(8), 2-4(7), 3-4(9)

Start at 0:
- take 0-1 (2), total=2
- take 1-2 (3), total=5
- take 1-4 (5), total=10
- take 0-3 (6), total=16

All 5 nodes connected, answer = 16.

--------------------------------------------------
📊 4. VISUAL REPRESENTATION
--------------------------------------------------

Visited nodes form one connected component.
Priority queue stores candidate frontier edges by weight.

--------------------------------------------------
⚙️ 5. APPROACHES
--------------------------------------------------

1. Matrix + key array (no heap): O(V^2)
2. Adjacency list + min heap: O(E log V)

--------------------------------------------------
⚠️ 6. EDGE CASES
--------------------------------------------------

- disconnected graph -> no MST across all vertices
- graph with single node -> MST cost 0
- duplicate edges between same pair

--------------------------------------------------
❌ 7. COMMON MISTAKES
--------------------------------------------------

- forgetting visited check when polling heap
- not handling disconnected graph at end
- summing infinite key values in naive matrix variant

--------------------------------------------------
🎯 8. INTERVIEW INSIGHT
--------------------------------------------------

Prim is usually preferred for dense graph with adjacency matrix
or when expanding from one source feels natural.
Kruskal is often cleaner with edge list + DSU.

--------------------------------------------------
🧾 9. CLEAN CODE
--------------------------------------------------
- Keep matrix and heap variants separate
- Use small helper Edge class for readability
- In heap variant, count visited nodes to detect disconnection

--------------------------------------------------
🧪 10. MAIN METHOD TESTING
--------------------------------------------------
Format:
Input -> Expected -> Actual

--------------------------------------------------
📌 11. MINI SUMMARY
--------------------------------------------------

Prim grows MST by repeatedly taking minimum frontier edge.
Correctness depends on always crossing from visited to unvisited.

==================================================
*/
import java.util.*;

/*
Problem: Prim's Algorithm (MST)
Find minimum spanning tree for weighted undirected graph.
*/
public class PrimsAlgorithm {
  public static class Edge {
        int to, wt;
        Edge(int t, int w) { to = t; wt = w; }
    }

    // Brute: O(V^2) variant with key array.
    // Time: O(V^2), Space: O(V)
    public static int primBrute(int[][] graph) {
        int n = graph.length;
        int[] key = new int[n];
        boolean[] mst = new boolean[n];
        Arrays.fill(key, Integer.MAX_VALUE);
        key[0] = 0;
        int ans = 0;
        for (int i = 0; i < n; i++) {
            int u = -1;
            for (int v = 0; v < n; v++) if (!mst[v] && (u == -1 || key[v] < key[u])) u = v;
            mst[u] = true;
            ans += key[u] == Integer.MAX_VALUE ? 0 : key[u];
            for (int v = 0; v < n; v++) {
                if (graph[u][v] != 0 && !mst[v] && graph[u][v] < key[v]) key[v] = graph[u][v];
            }
        }
        return ans;
    }

    // Optimal adjacency list + min-heap.
    // Time: O(E log V), Space: O(V)
    public static int primOptimal(List<List<Edge>> g) {
        int n = g.size(), ans = 0, visited = 0;
        boolean[] vis = new boolean[n];
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.offer(new int[]{0,0});
        while (!pq.isEmpty() && visited < n) {
            int[] cur = pq.poll();
            int u = cur[0], w = cur[1];
            if (vis[u]) continue;
            vis[u] = true;
            visited++;
            ans += w;
            for (Edge e : g.get(u)) if (!vis[e.to]) pq.offer(new int[]{e.to, e.wt});
        }
        return visited == n ? ans : -1;
    }

        public static void main(String[] args) {
            int[][] matrix = {
            {0,2,0,6,0},
            {2,0,3,8,5},
            {0,3,0,0,7},
            {6,8,0,0,9},
            {0,5,7,9,0}
        };
                int expected = 16;
                System.out.println("Expected: " + expected + " | Matrix Prim: " + primBrute(matrix));

        List<List<Edge>> g = new ArrayList<>();
        for (int i = 0; i < 5; i++) g.add(new ArrayList<>());
        addUndirected(g,0,1,2); addUndirected(g,0,3,6); addUndirected(g,1,2,3);
        addUndirected(g,1,3,8); addUndirected(g,1,4,5); addUndirected(g,2,4,7); addUndirected(g,3,4,9);
        System.out.println("Expected: " + expected + " | Heap Prim: " + primOptimal(g));
    }

    private static void addUndirected(List<List<Edge>> g, int u, int v, int w) {
        g.get(u).add(new Edge(v,w));
        g.get(v).add(new Edge(u,w));
    }
}







