package dsa.handbook;

/*
==================================================
📌 PROBLEM: Bellman-Ford Algorithm
==================================================

Imagine routing money transfers where fees can be negative (discount edges).
We still need shortest paths and must detect invalid cycles that keep reducing cost forever.

You are given:
- number of vertices V
- directed weighted edge list
- source vertex

👉 Your task:
Compute shortest path from source to all vertices, and detect negative cycle.

--------------------------------------------------
🧠 1. INTUITION (DEEP UNDERSTANDING)
--------------------------------------------------

Bellman-Ford relaxes all edges repeatedly.
After 1 full pass, shortest paths using <=1 edge are correct.
After 2 passes, paths using <=2 edges are correct.
...
After V-1 passes, all simple shortest paths are finalized.

If one more pass can still improve, graph contains a negative cycle.

--------------------------------------------------
❌ 2. WRONG APPROACH (BEGINNER MISTAKE)
--------------------------------------------------

Mistake:
Do only one pass over edges.
Why wrong:
Some shortest paths need multiple hops.
Single pass cannot propagate updates through long chains.

--------------------------------------------------
🧪 3. STEP-BY-STEP DRY RUN (REAL EXAMPLE)
--------------------------------------------------

Start from source 0
dist = [0, INF, INF, INF, INF]

Pass 1:
- 0->1 (6) => dist[1]=6
- 0->2 (7) => dist[2]=7

Pass 2:
- 1->4 (-4) => dist[4]=2
- 2->3 (-3) => dist[3]=4

Pass 3:
- 3->1 (-2) => dist[1]=2 (improved via longer path)

Pass 4:
- no improvement => stable

Extra cycle check pass:
- if any edge still improves, negative cycle exists.

--------------------------------------------------
📊 4. VISUAL REPRESENTATION
--------------------------------------------------

Think of dist[] as a table updated pass by pass.
Each pass pushes better values one edge farther.

--------------------------------------------------
⚙️ 5. APPROACHES
--------------------------------------------------

1. Dijkstra (faster) but not valid with negative edges.
2. Bellman-Ford (correct with negative edges): O(V*E), space O(V).

--------------------------------------------------
⚠️ 6. EDGE CASES
--------------------------------------------------

- unreachable vertices remain INF
- negative edges but no negative cycle
- reachable negative cycle must raise error/fail signal

--------------------------------------------------
❌ 7. COMMON MISTAKES
--------------------------------------------------

- missing INF guard before dist[u] + w
- forgetting final negative cycle detection pass
- using undirected interpretation accidentally

--------------------------------------------------
🎯 8. INTERVIEW INSIGHT
--------------------------------------------------

Classic comparison question: Bellman-Ford vs Dijkstra.
Correct answer: Bellman-Ford is slower but handles negative weights and cycle detection.

--------------------------------------------------
🧾 9. CLEAN CODE
--------------------------------------------------
- Keep `Edge` model explicit
- Keep relax logic in one method
- Throw clear exception for negative cycle

--------------------------------------------------
🧪 10. MAIN METHOD TESTING
--------------------------------------------------
Format:
Input -> Expected -> Actual

--------------------------------------------------
📌 11. MINI SUMMARY
--------------------------------------------------

Repeated relaxations spread shortest distances through the graph.
One extra pass is the proof-check for negative cycles.

==================================================
*/
import java.util.*;

/*
Problem: Bellman-Ford Algorithm
Handles negative edges and detects negative cycles.
*/
public class BellmanFordAlgorithm {
  public static class Edge {
        int u, v, w;
        Edge(int u, int v, int w) { this.u = u; this.v = v; this.w = w; }
    }

    // Bellman-Ford (optimal standard for negative edges).
    // Time: O(V*E), Space: O(V)
    public static int[] bellmanFord(int vCount, List<Edge> edges, int src) {
        int[] dist = new int[vCount];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        for (int i = 1; i <= vCount - 1; i++) {
            boolean relaxed = false;
            for (Edge e : edges) {
                if (dist[e.u] != Integer.MAX_VALUE && dist[e.u] + e.w < dist[e.v]) {
                    dist[e.v] = dist[e.u] + e.w;
                    relaxed = true;
                }
            }
            if (!relaxed) break;
        }

        for (Edge e : edges) {
            if (dist[e.u] != Integer.MAX_VALUE && dist[e.u] + e.w < dist[e.v]) {
                throw new IllegalStateException("Negative weight cycle detected.");
            }
        }
        return dist;
    }

    public static void main(String[] args) {
        List<Edge> edges = new ArrayList<>();

        edges.add(new Edge(0,1,6));
        edges.add(new Edge(0,2,7));
        edges.add(new Edge(1,2,8));
        edges.add(new Edge(1,3,5));
        edges.add(new Edge(1,4,-4));
        edges.add(new Edge(2,3,-3));
        edges.add(new Edge(2,4,9));
        edges.add(new Edge(3,1,-2));
        edges.add(new Edge(4,3,7));
        edges.add(new Edge(4,0,2));
        System.out.println("Input: V=5, source=0, weighted directed edges");
        System.out.println("Expected: [0, 2, 7, 4, 2] | Actual: " + Arrays.toString(bellmanFord(5, edges, 0)));
    }
}








