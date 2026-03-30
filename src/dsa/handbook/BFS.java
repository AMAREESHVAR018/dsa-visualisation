package dsa.handbook;

/*
==================================================
📌 PROBLEM: Breadth-First Search (BFS)
==================================================

Think of finding closest people in a social network.
You visit all 1-hop friends first, then 2-hop friends, and so on.

You are given:
- a graph
- a source vertex

👉 Your task:
Traverse nodes in increasing distance from the source.

--------------------------------------------------
🧠 1. INTUITION (DEEP UNDERSTANDING)
--------------------------------------------------

BFS uses FIFO queue.
The first discovered node is processed first, so levels stay ordered.

Invariant:
When a node is dequeued, it is the earliest reachable unprocessed node.

--------------------------------------------------
❌ 2. WRONG APPROACH (BEGINNER MISTAKE)
--------------------------------------------------

Mistake:
Use DFS and expect level order.

Why wrong:
DFS goes deep before siblings, so it breaks distance layering.

Another mistake:
Mark visited when popping instead of pushing, causing duplicate enqueues.

--------------------------------------------------
🧪 3. STEP-BY-STEP DRY RUN (REAL EXAMPLE)
--------------------------------------------------

Graph: 0->{1,2}, 1->{3,4}
Start at 0

Initial:
queue=[0], visited={0}

Step 1:
dequeue 0, output=[0]
enqueue 1,2 -> queue=[1,2], visited={0,1,2}

Step 2:
dequeue 1, output=[0,1]
enqueue 3,4 -> queue=[2,3,4], visited={0,1,2,3,4}

Step 3:
dequeue 2, output=[0,1,2]
no new neighbors -> queue=[3,4]

Step 4:
dequeue 3 -> output=[0,1,2,3]

Step 5:
dequeue 4 -> output=[0,1,2,3,4]

--------------------------------------------------
📊 4. VISUAL REPRESENTATION
--------------------------------------------------

Level 0: 0
Level 1: 1,2
Level 2: 3,4

BFS output follows level order.

--------------------------------------------------
⚙️ 5. APPROACHES
--------------------------------------------------

1. Adjacency matrix BFS
- Time O(V^2), Space O(V)

2. Adjacency list BFS (optimal sparse graphs)
- Time O(V+E), Space O(V)

--------------------------------------------------
⚠️ 6. EDGE CASES
--------------------------------------------------

- source has no edges
- disconnected graph (BFS covers only source component)
- self-loop edges

--------------------------------------------------
❌ 7. COMMON MISTAKES
--------------------------------------------------

- late visited marking
- forgetting to initialize queue with source
- expecting traversal of all components from one source

--------------------------------------------------
🎯 8. INTERVIEW INSIGHT
--------------------------------------------------

BFS is the base for shortest path in unweighted graphs.
Distance can be tracked by level or parent array.

--------------------------------------------------
🧾 9. CLEAN CODE
--------------------------------------------------
- Keep matrix and list versions separate
- Use `vis` and `q` consistently
- Keep enqueue and visited assignment together

--------------------------------------------------
🧪 10. MAIN METHOD TESTING
--------------------------------------------------
Format:
Input -> Expected -> Actual

--------------------------------------------------
📌 11. MINI SUMMARY
--------------------------------------------------

Queue order is the heart of BFS.
It guarantees near-to-far traversal from the source.

==================================================
*/
import java.util.*;

/*
Problem: Breadth-First Search (BFS) traversal of graph.
Input: adjacency list graph, source node.
Output: BFS order.
*/
public class BFS {
    // Brute-like using adjacency matrix traversal.
    // Time: O(V^2), Space: O(V)
    public static List<Integer> bfsMatrix(int[][] matrix, int src) {
        int n = matrix.length;
        List<Integer> order = new ArrayList<>();
        boolean[] vis = new boolean[n];
        Queue<Integer> q = new LinkedList<>();
        q.offer(src);
        vis[src] = true;
        while (!q.isEmpty()) {
            int u = q.poll();
            order.add(u);
            for (int v = 0; v < n; v++) {
                if (matrix[u][v] != 0 && !vis[v]) {
                    vis[v] = true;
                    q.offer(v);
                }
            }
        }
        return order;
    }

    // Optimal adjacency list BFS.
    // Time: O(V+E), Space: O(V)
    public static List<Integer> bfsList(List<List<Integer>> g, int src) {
        List<Integer> order = new ArrayList<>();
        boolean[] vis = new boolean[g.size()];
        Queue<Integer> q = new LinkedList<>();
        q.offer(src);
        vis[src] = true;
        while (!q.isEmpty()) {
            int u = q.poll();
            order.add(u);
            for (int v : g.get(u)) {
                if (!vis[v]) {
                    vis[v] = true;
                    q.offer(v);
                }
            }
        }
        return order;
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
        System.out.println("Input: source=0 on sample graph");
        System.out.println("Expected: [0, 1, 2, 3, 4] | Matrix BFS: " + bfsMatrix(m, 0));
        System.out.println("Expected: [0, 1, 2, 3, 4] | List BFS: " + bfsList(g, 0));
    }
}







