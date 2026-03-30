package dsa_handbook.chatgpt.organizedtopics;

/*
 * Topic: Dynamic Programming
 * Description: DP solves problems by storing and reusing overlapping subproblem results.
 * Real-world usage: Cost optimization, route planning, and sequence analysis.
 */
public class DynamicProgramming {

    // Problem 1: Fibonacci (Tabulation)
    // Explanation: Compute fibonacci values from 0 to n in order.
    // Approach: dp[i] = dp[i-1] + dp[i-2].
    // Time Complexity: O(n)
    // Space Complexity: O(n)
    public static int fibonacciTabulation(int n) {
        if (n <= 1) return n;
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    // Problem 2: Fibonacci (Space Optimized)
    // Explanation: Store only two previous fibonacci values.
    // Approach: Keep variables a and b and update iteratively.
    // Time Complexity: O(n)
    // Space Complexity: O(1)
    public static int fibonacciOptimized(int n) {
        if (n <= 1) return n;
        int a = 0;
        int b = 1;
        for (int i = 2; i <= n; i++) {
            int c = a + b;
            a = b;
            b = c;
        }
        return b;
    }

    // Problem 3: Regular Expression Matching (DP)
    // Explanation: Match string with pattern containing '.' and '*'.
    // Approach: dp[i][j] stores whether prefixes match.
    // Time Complexity: O(m * n)
    // Space Complexity: O(m * n)
    public static boolean regexMatchDP(String s, String p) {
        int m = s.length();
        int n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;

        for (int j = 2; j <= n; j++) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 2];
            }
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char pc = p.charAt(j - 1);
                if (pc == '.' || pc == s.charAt(i - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (pc == '*') {
                    dp[i][j] = dp[i][j - 2];
                    char prev = p.charAt(j - 2);
                    if (prev == '.' || prev == s.charAt(i - 1)) {
                        dp[i][j] = dp[i][j] || dp[i - 1][j];
                    }
                }
            }
        }

        return dp[m][n];
    }

    // Problem 4: Maximum Subarray Sum (DP)
    // Explanation: Best sum ending at index i depends on i-1.
    // Approach: dp[i] = max(nums[i], dp[i-1] + nums[i]).
    // Time Complexity: O(n)
    // Space Complexity: O(n)
    public static int maxSubarrayDP(int[] nums) {
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int best = dp[0];

        for (int i = 1; i < nums.length; i++) {
            dp[i] = Math.max(nums[i], dp[i - 1] + nums[i]);
            best = Math.max(best, dp[i]);
        }

        return best;
    }

    public static void main(String[] args) {
        System.out.println("Fib Tabulation(10): " + fibonacciTabulation(10));
        System.out.println("Regex DP (aab, c*a*b): " + regexMatchDP("aab", "c*a*b"));
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println("Max Subarray DP: " + maxSubarrayDP(nums));
    }
}
