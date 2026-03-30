package dsa.handbook;

/*
==================================================
📌 PROBLEM: Floyd-Warshall Algorithm
==================================================

This algorithm finds shortest paths between every pair of vertices.
It is a dynamic-programming solution over intermediate vertices.

You are given:
- weighted adjacency matrix
- INF meaning no direct edge

👉 Your task:
Compute all-pairs shortest distance matrix.

--------------------------------------------------
🧠 1. INTUITION (DEEP UNDERSTANDING)
--------------------------------------------------

Let `dist[i][j]` be shortest known path from i to j.
When we allow vertex `k` as intermediate,
each pair (i,j) can improve via path i->k->j.

Transition:
dist[i][j] = min(dist[i][j], dist[i][k] + dist[k][j])

DP layer meaning:
after finishing k-loop, paths may use only vertices [0..k] as intermediates.

--------------------------------------------------
❌ 2. WRONG APPROACH (BEGINNER MISTAKE)
--------------------------------------------------

Mistake:
Use loop order i-j-k or random ordering.

Why wrong:
It breaks DP layering semantics and may reuse partially updated states incorrectly.

--------------------------------------------------
🧪 3. STEP-BY-STEP DRY RUN (REAL EXAMPLE)
--------------------------------------------------

For sample matrix:
k=0: try routes through node 0
k=1: now routes through {0,1}
k=2: through {0,1,2}
k=3: through {0,1,2,3}

Example improvement:
dist[0][2] initially INF, but via 1 becomes 5 (0->1->2).

Final matrix for sample:
[0, 3, 5, 6]
[5, 0, 2, 3]
[3, 6, 0, 1]
[2, 5, 7, 0]

--------------------------------------------------
📊 4. VISUAL REPRESENTATION
--------------------------------------------------

Think of k as DP "time" axis.
At each k, whole matrix is refined once.

--------------------------------------------------
⚙️ 5. APPROACHES
--------------------------------------------------

1. Floyd-Warshall DP: O(V^3), O(V^2)
2. Alternative for sparse graphs: run Dijkstra/Bellman-Ford from each source

--------------------------------------------------
⚠️ 6. EDGE CASES
--------------------------------------------------

- disconnected pairs remain INF
- negative edges allowed
- negative cycle if any dist[i][i] < 0 after completion

--------------------------------------------------
❌ 7. COMMON MISTAKES
--------------------------------------------------

- adding INF values without guard
- wrong loop ordering
- forgetting clone/copy when needed

--------------------------------------------------
🎯 8. INTERVIEW INSIGHT
--------------------------------------------------

When asked "when use Floyd-Warshall?"
Answer: dense graph + all-pairs query scenario + manageable V.

--------------------------------------------------
🧾 9. CLEAN CODE
--------------------------------------------------
- Keep INF constant explicit
- Guard INF addition before summing
- Keep k-i-j nested order obvious

--------------------------------------------------
🧪 10. MAIN METHOD TESTING
--------------------------------------------------
Format:
Input -> Expected -> Actual

--------------------------------------------------
📌 11. MINI SUMMARY
--------------------------------------------------

Floyd-Warshall is matrix DP with controlled intermediate expansion.
The k-loop is the backbone of correctness.

==================================================
*/
import java.util.*;

/*
Problem: Floyd-Warshall Algorithm
All-pairs shortest paths in weighted graph.
*/
public class FloydWarshallAlgorithm {
    private static final int INF = 1_000_000_000;

    // Floyd-Warshall.
    // Time: O(V^3), Space: O(V^2)
    public static int[][] floydWarshall(int[][] graph) {
        int n = graph.length;
        int[][] dist = new int[n][n];
        for (int i = 0; i < n; i++) dist[i] = graph[i].clone();

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (dist[i][k] < INF && dist[k][j] < INF) {
                        dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                    }
                }
            }
        }
        return dist;
    }

    public static void main(String[] args) {
      int[][] g = {
            {0, 3, INF, 7},
            {8, 0, 2, INF},
            {5, INF, 0, 1},
            {2, INF, INF, 0}
        };
        int[][] d = floydWarshall(g);
        System.out.println("Expected rows:");
        System.out.println("[0, 3, 5, 6]");
        System.out.println("[5, 0, 2, 3]");
        System.out.println("[3, 6, 0, 1]");
        System.out.println("[2, 5, 7, 0]");
        System.out.println("Actual rows:");
        for (int[] row : d) System.out.println(Arrays.toString(row));
    }
}







