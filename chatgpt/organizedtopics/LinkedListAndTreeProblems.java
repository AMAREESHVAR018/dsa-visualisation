package dsa_handbook.chatgpt.organizedtopics;

/*
 * Topic: Linked List and Tree Problems
 * Description: Covers circular linked list traversal, cycle detection, and tree traversal.
 * Real-world usage: Playlist loops (circular list) and hierarchical data processing (trees).
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class LinkedListAndTreeProblems {

    public static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    // Problem 1: Circular Linked List Traversal (do-while)
    // Explanation: Traverse circular list exactly once from head.
    // Approach: Print current node, move next, stop when back at head.
    // Time Complexity: O(n)
    // Space Complexity: O(1)
    public static List<Integer> traverseCircularDoWhile(Node head) {
        List<Integer> result = new ArrayList<>();
        if (head == null) {
            return result;
        }

        Node temp = head;
        do {
            result.add(temp.data);
            temp = temp.next;
        } while (temp != head);

        return result;
    }

    // Problem 2: Circular Linked List Traversal (while + flag)
    // Explanation: Use flag to ensure first node is processed.
    // Approach: Loop while not returned to head or first iteration.
    // Time Complexity: O(n)
    // Space Complexity: O(1)
    public static List<Integer> traverseCircularWhileFlag(Node head) {
        List<Integer> result = new ArrayList<>();
        if (head == null) {
            return result;
        }

        Node temp = head;
        boolean first = true;

        while (temp != head || first) {
            first = false;
            result.add(temp.data);
            temp = temp.next;
        }

        return result;
    }

    // Problem 3: Circular Linked List Traversal (linear-style)
    // Explanation: Stop when next node points back to head.
    // Approach: Traverse until temp.next == head, then add last node.
    // Time Complexity: O(n)
    // Space Complexity: O(1)
    public static List<Integer> traverseCircularLinearStyle(Node head) {
        List<Integer> result = new ArrayList<>();
        if (head == null) {
            return result;
        }

        Node temp = head;
        while (temp.next != head) {
            result.add(temp.data);
            temp = temp.next;
        }
        result.add(temp.data);

        return result;
    }

    // Problem 4: Detect Cycle in Linked List
    // Explanation: Determine if linked list contains a loop.
    // Approach: Floyd's slow and fast pointers.
    // Time Complexity: O(n)
    // Space Complexity: O(1)
    public static boolean hasCycle(Node head) {
        Node slow = head;
        Node fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }

        return false;
    }

    // Problem 5: Inorder Traversal (Recursive)
    // Explanation: Visit left, root, right nodes of binary tree.
    // Approach: Recursive DFS in inorder sequence.
    // Time Complexity: O(n)
    // Space Complexity: O(h)
    public static List<Integer> inorderRecursive(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorderRec(root, result);
        return result;
    }

    private static void inorderRec(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        inorderRec(node.left, result);
        result.add(node.val);
        inorderRec(node.right, result);
    }

    // Problem 6: Inorder Traversal (Iterative)
    // Explanation: Use stack to simulate recursion.
    // Approach: Push left chain, process node, then go right.
    // Time Complexity: O(n)
    // Space Complexity: O(h)
    public static List<Integer> inorderIterative(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;

        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }

            current = stack.pop();
            result.add(current.val);
            current = current.right;
        }

        return result;
    }

    public static void main(String[] args) {
        Node a = new Node(1);
        Node b = new Node(2);
        Node c = new Node(3);
        a.next = b;
        b.next = c;
        c.next = a;
        System.out.println("Circular Traversal: " + traverseCircularDoWhile(a));

        TreeNode root = new TreeNode(2);
        root.left = new TreeNode(1);
        root.right = new TreeNode(3);
        System.out.println("Inorder Traversal: " + inorderIterative(root));
    }
}
