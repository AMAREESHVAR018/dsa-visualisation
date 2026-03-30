package dsa.handbook;

/*
==================================================
📌 PROBLEM: Depth-First Search (DFS)
==================================================

DFS explores one path fully before trying siblings.
It is the traversal version of "go deep, then backtrack".

You are given:
- graph
- source node

👉 Your task:
Return traversal order in depth-first manner.

--------------------------------------------------
🧠 1. INTUITION (DEEP UNDERSTANDING)
--------------------------------------------------

When you enter a node:
1. mark visited
2. process node
3. recursively process each unvisited neighbor

The recursion stack naturally remembers the path,
so backtracking happens automatically.

--------------------------------------------------
❌ 2. WRONG APPROACH (BEGINNER MISTAKE)
--------------------------------------------------

Mistake:
Skip visited checks in graph traversal.

Why dangerous:
In cyclic graphs, recursion revisits same nodes forever.

--------------------------------------------------
🧪 3. STEP-BY-STEP DRY RUN (REAL EXAMPLE)
--------------------------------------------------

Graph edges: 0->{1,2}, 1->{3,4}
Start at 0

Call flow:
- visit 0, output=[0]
- go 1, output=[0,1]
- go 3, output=[0,1,3], backtrack
- go 4, output=[0,1,3,4], backtrack
- back at 0, go 2, output=[0,1,3,4,2]

Final DFS order: [0,1,3,4,2]

--------------------------------------------------
📊 4. VISUAL REPRESENTATION
--------------------------------------------------

Recursion stack snapshot:
0 -> 1 -> 3
then pop 3, explore 4, pop 4, pop 1, explore 2

--------------------------------------------------
⚙️ 5. APPROACHES
--------------------------------------------------

1. Matrix DFS: O(V^2)
2. Adjacency-list DFS: O(V+E)

Both use O(V) visited memory.

--------------------------------------------------
⚠️ 6. EDGE CASES
--------------------------------------------------

- source isolated
- disconnected graph (single DFS covers one component)
- graph with self-loop

--------------------------------------------------
❌ 7. COMMON MISTAKES
--------------------------------------------------

- forgetting visited mark before recursive calls
- expecting BFS-style level order from DFS
- assuming one DFS call covers disconnected graph

--------------------------------------------------
🎯 8. INTERVIEW INSIGHT
--------------------------------------------------

Interview follow-up:
Implement iterative DFS using explicit stack.
Explain how neighbor push order can change traversal sequence.

--------------------------------------------------
🧾 9. CLEAN CODE
--------------------------------------------------
- Keep matrix and list implementations separate
- Keep helper recursion methods private
- Keep visited marking at function entry

--------------------------------------------------
🧪 10. MAIN METHOD TESTING
--------------------------------------------------
Format:
Input -> Expected -> Actual

--------------------------------------------------
📌 11. MINI SUMMARY
--------------------------------------------------

DFS = visit, go deeper, backtrack.
Correctness depends on visited-check discipline.

==================================================
*/
import java.util.*;

/*
Problem: Depth-First Search (DFS) traversal of graph.
*/
public class DFS {
    // Brute-like DFS using adjacency matrix.
    // Time: O(V^2), Space: O(V)
    public static List<Integer> dfsMatrix(int[][] matrix, int src) {
        List<Integer> order = new ArrayList<>();
        boolean[] vis = new boolean[matrix.length];
        dfsMat(src, matrix, vis, order);
        return order;
    }

    private static void dfsMat(int u, int[][] m, boolean[] vis, List<Integer> order) {
        vis[u] = true;
        order.add(u);
        for (int v = 0; v < m.length; v++) {
            if (m[u][v] != 0 && !vis[v]) dfsMat(v, m, vis, order);
        }
    }

    // Optimal adjacency list DFS.
    // Time: O(V+E), Space: O(V)
    public static List<Integer> dfsList(List<List<Integer>> g, int src) {
        List<Integer> order = new ArrayList<>();
        boolean[] vis = new boolean[g.size()];
        dfsListRec(src, g, vis, order);
        return order;
    }

    private static void dfsListRec(int u, List<List<Integer>> g, boolean[] vis, List<Integer> order) {
        vis[u] = true;
        order.add(u);
        for (int v : g.get(u)) if (!vis[v]) dfsListRec(v, g, vis, order);
    }

    public static void main(String[] args) {
        List<List<Integer>> g = new ArrayList<>();

        for (int i = 0; i < 5; i++) g.add(new ArrayList<>());
        g.get(0).addAll(Arrays.asList(1,2));
        g.get(1).addAll(Arrays.asList(0,3,4));
        g.get(2).add(0);
        g.get(3).add(1);
        g.get(4).add(1);
        int[][] m = {{0,1,1,0,0},{1,0,0,1,1},{1,0,0,0,0},{0,1,0,0,0},{0,1,0,0,0}};
        System.out.println("Input: source=0");
        System.out.println("Expected: [0, 1, 3, 4, 2] | Matrix DFS: " + dfsMatrix(m, 0));
        System.out.println("Expected: [0, 1, 3, 4, 2] | List DFS: " + dfsList(g, 0));
    }
}







