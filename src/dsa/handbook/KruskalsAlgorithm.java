package dsa.handbook;

/*
==================================================
📌 PROBLEM: Kruskal Algorithm (Minimum Spanning Tree)
==================================================

For a connected weighted undirected graph,
MST is a set of n-1 edges with minimum total weight and no cycle.

You are given:
- number of nodes n
- weighted edge list

👉 Your task:
compute MST total weight, or return -1 if graph is disconnected.

--------------------------------------------------
🧠 1. INTUITION (DEEP UNDERSTANDING)
--------------------------------------------------

Kruskal picks edges globally by increasing weight.
Each picked edge must be safe: it must connect two different components.

DSU (Disjoint Set Union) tracks components efficiently:
- `find(x)` gives component representative
- `union(a,b)` merges components if different

--------------------------------------------------
❌ 2. WRONG APPROACH (BEGINNER MISTAKE)
--------------------------------------------------

Mistake:
Add smallest edges without cycle check.

Why wrong:
You can include a cheap cycle edge, wasting one of n-1 slots
and preventing MST construction.

--------------------------------------------------
🧪 3. STEP-BY-STEP DRY RUN (REAL EXAMPLE)
--------------------------------------------------

Edges sorted by weight:
(2,3,4), (0,3,5), (0,2,6), (0,1,10), (1,3,15)

Initial components: {0} {1} {2} {3}

1) take (2,3,4): merge -> weight=4
2) take (0,3,5): merge -> weight=9
3) skip (0,2,6): same component, would form cycle
4) take (0,1,10): merge -> weight=19

Used edges = n-1 = 3 -> done
MST weight = 19

--------------------------------------------------
📊 4. VISUAL REPRESENTATION
--------------------------------------------------

DSU forest shrinks number of components after each successful union.
Each accepted edge reduces components by exactly 1.

--------------------------------------------------
⚙️ 5. APPROACHES
--------------------------------------------------

Kruskal + DSU:
- sort edges: O(E log E)
- union/find near O(1) amortized
- total: O(E log E)

--------------------------------------------------
⚠️ 6. EDGE CASES
--------------------------------------------------

- disconnected graph -> not enough edges to reach n-1
- duplicate weights -> multiple valid MSTs possible
- parallel edges between same nodes

--------------------------------------------------
❌ 7. COMMON MISTAKES
--------------------------------------------------

- incorrect DSU union rank updates
- forgetting path compression in find
- not checking final `used == n-1`

--------------------------------------------------
🎯 8. INTERVIEW INSIGHT
--------------------------------------------------

Common follow-up:
Why is choosing lightest safe edge always valid?
Expected concept: cut property.

--------------------------------------------------
🧾 9. CLEAN CODE
--------------------------------------------------
- Keep Edge and DSU as focused helper types
- Keep cycle-check and accept logic in one place
- Return -1 explicitly for disconnected graphs

--------------------------------------------------
🧪 10. MAIN METHOD TESTING
--------------------------------------------------
Format:
Input -> Expected -> Actual

--------------------------------------------------
📌 11. MINI SUMMARY
--------------------------------------------------

Kruskal = sorted edges + DSU cycle guard.
Only safe edges are selected, yielding minimal spanning tree.

==================================================
*/
import java.util.*;

/*
Problem: Kruskal's Algorithm (MST)
Find minimum spanning tree for weighted undirected graph.
*/
public class KruskalsAlgorithm {
    static class Edge {
        int u, v, w;
        Edge(int u, int v, int w) { this.u = u; this.v = v; this.w = w; }
    }

    static class DSU {
        int[] p, r;
        DSU(int n) {
            p = new int[n]; r = new int[n];
            for (int i = 0; i < n; i++) p[i] = i;
        }
        int find(int x) { return p[x] == x ? x : (p[x] = find(p[x])); }
        boolean union(int a, int b) {
            a = find(a); b = find(b);
            if (a == b) return false;
            if (r[a] < r[b]) p[a] = b;
            else if (r[a] > r[b]) p[b] = a;
            else { p[b] = a; r[a]++; }
            return true;
        }
    }

    // Kruskal's MST.
    // Time: O(E log E), Space: O(V)
    public static int mstWeight(int n, List<Edge> edges) {
        edges.sort(Comparator.comparingInt(e -> e.w));
        DSU dsu = new DSU(n);
        int wt = 0, used = 0;
        for (Edge e : edges) {
            if (dsu.union(e.u, e.v)) {
                wt += e.w;
                used++;
                if (used == n - 1) break;
            }
        }
        return used == n - 1 ? wt : -1;
    }

    public static void main(String[] args) {
        List<Edge> edges = new ArrayList<>();

        edges.add(new Edge(0,1,10));
        edges.add(new Edge(0,2,6));
        edges.add(new Edge(0,3,5));
        edges.add(new Edge(1,3,15));
        edges.add(new Edge(2,3,4));
        System.out.println("Input: n=4, edges={(0,1,10),(0,2,6),(0,3,5),(1,3,15),(2,3,4)}");
        System.out.println("Expected: 19 | Actual: " + mstWeight(4, edges));
    }
}







