package dsa_handbook.chatgpt.organizedtopics;

/*
 * Topic: Celebrity Problem
 * Description: Find a person known by everyone who knows nobody.
 * Real-world usage: Identifying a central figure in a directed relationship graph.
 */
import java.util.Stack;

public class CelebrityProblem {

    // Problem 1: Celebrity by Brute Force
    // Explanation: Check each person against every other person.
    // Approach: Candidate is valid if row has all 0 and column has all 1 except self.
    // Time Complexity: O(n^2)
    // Space Complexity: O(1)
    public static int findCelebrityBruteForce(int[][] mat) {
        int n = mat.length;

        for (int i = 0; i < n; i++) {
            boolean isCelebrity = true;
            for (int j = 0; j < n; j++) {
                if (i != j && (mat[i][j] == 1 || mat[j][i] == 0)) {
                    isCelebrity = false;
                    break;
                }
            }
            if (isCelebrity) {
                return i;
            }
        }

        return -1;
    }

    // Problem 2: Celebrity by Two-Pointer Elimination
    // Explanation: Eliminate non-celebrities in one pass and verify final candidate.
    // Approach: If candidate knows i, candidate cannot be celebrity.
    // Time Complexity: O(n)
    // Space Complexity: O(1)
    public static int findCelebrityOptimal(int[][] mat) {
        int n = mat.length;
        int candidate = 0;

        for (int i = 1; i < n; i++) {
            if (mat[candidate][i] == 1) {
                candidate = i;
            }
        }

        for (int i = 0; i < n; i++) {
            if (i != candidate && (mat[candidate][i] == 1 || mat[i][candidate] == 0)) {
                return -1;
            }
        }

        return candidate;
    }

    // Problem 3: Celebrity by Stack Elimination
    // Explanation: Pop two persons and keep only possible celebrity.
    // Approach: Push all in stack, compare pairwise, verify last person.
    // Time Complexity: O(n)
    // Space Complexity: O(n)
    public static int findCelebrityStack(int[][] mat) {
        int n = mat.length;
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < n; i++) {
            stack.push(i);
        }

        while (stack.size() > 1) {
            int a = stack.pop();
            int b = stack.pop();

            if (mat[a][b] == 1) {
                stack.push(b);
            } else {
                stack.push(a);
            }
        }

        int candidate = stack.pop();

        for (int i = 0; i < n; i++) {
            if (i != candidate && (mat[candidate][i] == 1 || mat[i][candidate] == 0)) {
                return -1;
            }
        }

        return candidate;
    }

    public static void main(String[] args) {
        int[][] mat = {
                {0, 1, 1},
                {0, 0, 0},
                {0, 1, 0}
        };
        System.out.println("Celebrity (Optimal): " + findCelebrityOptimal(mat));
    }
}
