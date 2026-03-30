package dsa.handbook;

/*
==================================================
📌 PROBLEM: Circular Linked List Traversal
==================================================

In a circular list, last node points back to first.
So there is no null terminator like normal linked list.

You are given:
- head of circular linked list

👉 Your task:
Print/traverse each node exactly once and stop safely.

--------------------------------------------------
🧠 1. INTUITION (DEEP UNDERSTANDING)
--------------------------------------------------

The stopping condition is not `cur == null`.
Correct condition is: stop when traversal returns to `head`.

This head-sentinel condition is the core invariant.

--------------------------------------------------
❌ 2. WRONG APPROACH (BEGINNER MISTAKE)
--------------------------------------------------

Common bug:
Use `while (cur != null)`.

Why it fails:
In valid circular list, cur is never null, so loop runs forever.

--------------------------------------------------
🧪 3. STEP-BY-STEP DRY RUN (REAL EXAMPLE)
--------------------------------------------------

List: 1 -> 2 -> 3 -> back to 1

Iterative do-while:
- cur=1, output "1", move cur=2
- cur=2, output "2", move cur=3
- cur=3, output "3", move cur=1
- cur == head -> stop

Final traversal: "1 2 3"

--------------------------------------------------
📊 4. VISUAL REPRESENTATION
--------------------------------------------------

head
 ↓
1 -> 2 -> 3
^         |
|_________|

--------------------------------------------------
⚙️ 5. APPROACHES
--------------------------------------------------

1. Iterative do-while traversal: O(n), O(1)
2. Recursive traversal with start sentinel: O(n), O(n) stack

--------------------------------------------------
⚠️ 6. EDGE CASES
--------------------------------------------------

- head = null -> empty output
- single node with next=head
- malformed list (if not truly circular, behavior undefined)

--------------------------------------------------
❌ 7. COMMON MISTAKES
--------------------------------------------------

- checking `cur.next == null` in circular context
- recursion without stop sentinel
- missing null-head guard

--------------------------------------------------
🎯 8. INTERVIEW INSIGHT
--------------------------------------------------

Follow-up often asked:
How to detect if an unknown list is circular first?
Expected mention: Floyd cycle detection.

--------------------------------------------------
🧾 9. CLEAN CODE
--------------------------------------------------
- Keep iterative and recursive methods separate
- Keep sentinel names (`start`, `head`) clear
- Return stable string format for easy testing

--------------------------------------------------
🧪 10. MAIN METHOD TESTING
--------------------------------------------------
Format:
Input -> Expected -> Actual

--------------------------------------------------
📌 11. MINI SUMMARY
--------------------------------------------------

Circular traversal is simple once stop condition is correct:
process nodes until pointer returns to head.

==================================================
*/
public class CircularLinkedListTraversal {
    static class Node {
        int val;
        Node next;
        Node(int v) { val = v; }
    }

    // Iterative traversal.
    // Time: O(n), Space: O(1)
    public static String traverseIterative(Node head) {
        if (head == null) return "";
        StringBuilder sb = new StringBuilder();
        Node cur = head;
        do {
            sb.append(cur.val).append(' ');
            cur = cur.next;
        } while (cur != head);
        return sb.toString().trim();
    }

    // Recursive traversal with stop condition.
    // Time: O(n), Space: O(n)
    public static String traverseRecursive(Node head) {
        if (head == null) return "";
        StringBuilder sb = new StringBuilder();
        rec(head, head, sb);
        return sb.toString().trim();
    }

    private static void rec(Node start, Node cur, StringBuilder sb) {
        sb.append(cur.val).append(' ');
        if (cur.next == start) return;
        rec(start, cur.next, sb);
    }

    public static void main(String[] args) {
        Node a = new Node(1), b = new Node(2), c = new Node(3);
        a.next = b; b.next = c; c.next = a;
        System.out.println("Input: circular list 1->2->3->1");
        System.out.println("Expected: 1 2 3 | Iterative: " + traverseIterative(a));
        System.out.println("Expected: 1 2 3 | Recursive: " + traverseRecursive(a));
    }
}







