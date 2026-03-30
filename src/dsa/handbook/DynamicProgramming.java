package dsa.handbook;

/*
==================================================
📌 PROBLEM: Dynamic Programming Classics Pack
==================================================

This file groups multiple classic DP problems:
Fibonacci, Knapsack, LCS, Coin Change, and LIS.

You are given:
- different inputs per problem

👉 Your task:
Understand the common DP workflow: state -> transition -> base case.

--------------------------------------------------
🧠 1. INTUITION (DEEP UNDERSTANDING)
--------------------------------------------------

DP means: solve overlapping subproblems once and reuse them.

Universal checklist:
1. define what dp[...] stores
2. write recurrence (transition)
3. set base values
4. fill in dependency-safe order

--------------------------------------------------
❌ 2. WRONG APPROACH (BEGINNER MISTAKE)
--------------------------------------------------

Mistake:
Start coding loops without defining state meaning.

Why harmful:
You may fill table in wrong direction or read uninitialized dependencies.

--------------------------------------------------
🧪 3. STEP-BY-STEP DRY RUN (REAL EXAMPLE)
--------------------------------------------------

Fibonacci n=10 (tab style):
dp[0]=0, dp[1]=1
dp[2]=1, dp[3]=2, dp[4]=3, dp[5]=5
...
dp[10]=55

Same pattern appears elsewhere:
- Knapsack: choose take/skip state
- LCS: match/no-match state
- Coin change: min coins from smaller amounts

--------------------------------------------------
📊 4. VISUAL REPRESENTATION
--------------------------------------------------

DP tables are dependency graphs.
Every cell depends on previously solved cells.

--------------------------------------------------
⚙️ 5. APPROACHES
--------------------------------------------------

1. Top-down memoization
2. Bottom-up tabulation
3. Space-optimized rolling states (where possible)

Complexities vary:
- Fib: O(n)
- Knapsack: O(nW)
- LCS: O(mn)
- Coin change: O(amount * n)
- LIS (patience method): O(n log n)

--------------------------------------------------
⚠️ 6. EDGE CASES
--------------------------------------------------

- empty strings/sequences
- zero capacity/zero amount
- impossible coin change result
- small n values like 0 or 1

--------------------------------------------------
❌ 7. COMMON MISTAKES
--------------------------------------------------

- wrong base initialization
- wrong transition source indexes
- wrong loop direction in 1D-optimized DP

--------------------------------------------------
🎯 8. INTERVIEW INSIGHT
--------------------------------------------------

Interview scoring is high when you state:
"dp[i] means ..." before writing code.
That proves control over logic, not memorization.

--------------------------------------------------
🧾 9. CLEAN CODE
--------------------------------------------------
- Keep each DP problem in separate method
- Name states clearly (`dp`, `tail`, etc.)
- Keep transition close to comments describing meaning

--------------------------------------------------
🧪 10. MAIN METHOD TESTING
--------------------------------------------------
Format:
Input -> Expected -> Actual

--------------------------------------------------
📌 11. MINI SUMMARY
--------------------------------------------------

DP success comes from precise state definition.
Once state is correct, recurrence and loops become natural.

==================================================
*/
import java.util.*;

/*
Problem: Dynamic Programming - Classic Problems Pack
Includes Fibonacci, 0/1 Knapsack, LCS, Coin Change, LIS.
*/
public class DynamicProgramming {
    // Fibonacci
    // Brute recursion: Time O(2^n), Space O(n)
    public static int fibBrute(int n) {
        if (n <= 1) return n;
        return fibBrute(n - 1) + fibBrute(n - 2);
    }

    // Better memoization: Time O(n), Space O(n)
    public static int fibMemo(int n, int[] dp) {
        if (n <= 1) return n;
        if (dp[n] != -1) return dp[n];
        return dp[n] = fibMemo(n - 1, dp) + fibMemo(n - 2, dp);
    }

    // Optimal tabulation: Time O(n), Space O(1)
    public static int fibOptimal(int n) {
        if (n <= 1) return n;
        int a = 0, b = 1;
        for (int i = 2; i <= n; i++) {
            int c = a + b;
            a = b;
            b = c;
        }
        return b;
    }

    // 0/1 Knapsack tabulation
    // Time O(n*W), Space O(n*W)
    public static int knapsack(int[] wt, int[] val, int W) {
        int n = wt.length;
        int[][] dp = new int[n + 1][W + 1];
        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= W; w++) {
                dp[i][w] = dp[i - 1][w];
                if (wt[i - 1] <= w) {
                    dp[i][w] = Math.max(dp[i][w], val[i - 1] + dp[i - 1][w - wt[i - 1]]);
                }
            }
        }
        return dp[n][W];
    }

    // LCS
    // Time O(m*n), Space O(m*n)
    public static int lcs(String a, String b) {
        int m = a.length(), n = b.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) dp[i][j] = 1 + dp[i - 1][j - 1];
                else dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }
        return dp[m][n];
    }

    // Coin Change (minimum coins)
    // Time O(amount * n), Space O(amount)
    public static int coinChangeMinCoins(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        for (int a = 1; a <= amount; a++) {
            for (int c : coins) {
                if (c <= a) dp[a] = Math.min(dp[a], 1 + dp[a - c]);
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }

    // LIS using patience sorting.
    // Time O(n log n), Space O(n)
    public static int lisOptimal(int[] a) {
        int[] tail = new int[a.length];
        int len = 0;
        for (int x : a) {
            int i = Arrays.binarySearch(tail, 0, len, x);
            if (i < 0) i = -(i + 1);
            tail[i] = x;
            if (i == len) len++;
        }
        return len;
    }

    public static void main(String[] args) {
        int n = 10;

        int[] memo = new int[n + 1];
        Arrays.fill(memo, -1);
        System.out.println("Expected: 8 | Fib brute(6): " + fibBrute(6));
        System.out.println("Expected: 55 | Fib memo(10): " + fibMemo(n, memo));
        System.out.println("Expected: 55 | Fib opt(10): " + fibOptimal(n));

        System.out.println("Expected: 9 | Knapsack: " + knapsack(new int[]{1,3,4,5}, new int[]{1,4,5,7}, 7));
        System.out.println("Expected: 3 | LCS(abcde, ace): " + lcs("abcde", "ace"));
        System.out.println("Expected: 3 | CoinChange(11): " + coinChangeMinCoins(new int[]{1,2,5}, 11));
        System.out.println("Expected: 4 | LIS: " + lisOptimal(new int[]{10,9,2,5,3,7,101,18}));
    }
}







