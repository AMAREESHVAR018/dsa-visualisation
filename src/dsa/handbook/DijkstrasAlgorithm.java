package dsa.handbook;

/*
==================================================
📌 PROBLEM: Dijkstra Algorithm
==================================================

Dijkstra solves shortest path from one source when all edge weights are non-negative.
It is greedy, but the greediness is mathematically safe under non-negative weights.

You are given:
- weighted directed graph
- source node

👉 Your task:
Compute minimum distance from source to every vertex.

--------------------------------------------------
🧠 1. INTUITION (DEEP UNDERSTANDING)
--------------------------------------------------

At each step, take node with smallest tentative distance.
That node is finalized because no future path can beat it (non-negative weights).

Then relax outgoing edges:
if dist[u] + w < dist[v], update dist[v].

--------------------------------------------------
❌ 2. WRONG APPROACH (BEGINNER MISTAKE)
--------------------------------------------------

Mistake:
Use Dijkstra on graphs with negative edges.

Why wrong:
Finalized-node guarantee breaks, and results can be incorrect.

--------------------------------------------------
🧪 3. STEP-BY-STEP DRY RUN (REAL EXAMPLE)
--------------------------------------------------

From source 0:
dist = [0, INF, INF, INF, INF]

Pop 0:
- relax 0->1(2) => dist[1]=2
- relax 0->2(4) => dist[2]=4

Pop 1:
- relax 1->2(1) => dist[2]=3
- relax 1->3(7) => dist[3]=9

Pop 2:
- relax 2->4(3) => dist[4]=6

Pop 4 then 3, no better updates.

Final: [0,2,3,9,6]

--------------------------------------------------
📊 4. VISUAL REPRESENTATION
--------------------------------------------------

Priority queue stores (node, currentDistance).
Smallest distance node gets processed first.

--------------------------------------------------
⚙️ 5. APPROACHES
--------------------------------------------------

1. Brute pick-min (visited + linear scan): O(V^2)
2. Min-heap priority queue: O((V+E)logV)

--------------------------------------------------
⚠️ 6. EDGE CASES
--------------------------------------------------

- disconnected nodes remain INF
- zero-weight edges are valid
- negative edges are invalid for Dijkstra

--------------------------------------------------
❌ 7. COMMON MISTAKES
--------------------------------------------------

- not ignoring stale PQ entries
- forgetting overflow-safe checks around INF
- assuming undirected when graph is directed

--------------------------------------------------
🎯 8. INTERVIEW INSIGHT
--------------------------------------------------

Typical question:
Why does greedy choice work?
Expected answer: non-negative weights preserve finalized shortest distances.

--------------------------------------------------
🧾 9. CLEAN CODE
--------------------------------------------------
- Keep `Edge` type minimal and clear
- Keep brute and heap versions separate
- Keep relax condition identical across both

--------------------------------------------------
🧪 10. MAIN METHOD TESTING
--------------------------------------------------
Format:
Input -> Expected -> Actual

--------------------------------------------------
📌 11. MINI SUMMARY
--------------------------------------------------

Dijkstra = greedy finalization + repeated relaxation.
Non-negative weights are the contract that makes it correct.

==================================================
*/
import java.util.*;

/*
Problem: Dijkstra's Algorithm (non-negative weights)
Find shortest path from source to all vertices.
*/
public class DijkstrasAlgorithm {
  public static class Edge {
        int to, wt;
        Edge(int t, int w) { to = t; wt = w; }
    }

    // Brute: O(V^2) using visited + linear min selection.
    // Time: O(V^2 + E), Space: O(V)
    public static int[] dijkstraBrute(List<List<Edge>> g, int src) {
        int n = g.size();
        int[] dist = new int[n];
        boolean[] vis = new boolean[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;
        for (int i = 0; i < n; i++) {
            int u = -1;
            for (int v = 0; v < n; v++) {
                if (!vis[v] && (u == -1 || dist[v] < dist[u])) u = v;
            }
            if (u == -1 || dist[u] == Integer.MAX_VALUE) break;
            vis[u] = true;
            for (Edge e : g.get(u)) {
                if (dist[u] + e.wt < dist[e.to]) dist[e.to] = dist[u] + e.wt;
            }
        }
        return dist;
    }

    // Optimal with priority queue.
    // Time: O((V+E) log V), Space: O(V)
    public static int[] dijkstraOptimal(List<List<Edge>> g, int src) {
        int n = g.size();
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.offer(new int[]{src, 0});
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int u = cur[0], d = cur[1];
            if (d != dist[u]) continue;
            for (Edge e : g.get(u)) {
                if (dist[u] != Integer.MAX_VALUE && dist[u] + e.wt < dist[e.to]) {
                    dist[e.to] = dist[u] + e.wt;
                    pq.offer(new int[]{e.to, dist[e.to]});
                }
            }
        }
        return dist;
    }

    public static void main(String[] args) {
      int n = 5;
        List<List<Edge>> g = new ArrayList<>();
        for (int i = 0; i < n; i++) g.add(new ArrayList<>());
        g.get(0).add(new Edge(1,2)); g.get(0).add(new Edge(2,4));
        g.get(1).add(new Edge(2,1)); g.get(1).add(new Edge(3,7));
        g.get(2).add(new Edge(4,3));
        g.get(3).add(new Edge(4,1));
        System.out.println("Input: source=0");
        System.out.println("Expected: [0, 2, 3, 9, 6] | Brute: " + Arrays.toString(dijkstraBrute(g, 0)));
        System.out.println("Expected: [0, 2, 3, 9, 6] | Optimal: " + Arrays.toString(dijkstraOptimal(g, 0)));
    }
}







