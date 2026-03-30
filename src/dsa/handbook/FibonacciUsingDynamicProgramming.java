package dsa.handbook;

/*
==================================================
📌 PROBLEM: Fibonacci using Dynamic Programming
==================================================

Naive Fibonacci recursion recomputes same subproblems many times.
Dynamic programming avoids that repetition.

You are given:
- n

👉 Your task:
Compute fib(n) efficiently using memoization/tabulation ideas.

--------------------------------------------------
🧠 1. INTUITION (DEEP UNDERSTANDING)
--------------------------------------------------

Fibonacci relation:
fib(n) = fib(n-1) + fib(n-2)

DP insight:
if previous values are already known, each new fib value is O(1) to compute.

--------------------------------------------------
❌ 2. WRONG APPROACH (BEGINNER MISTAKE)
--------------------------------------------------

Mistake:
Use plain recursion for large n.

Why bad:
same states (like fib(30), fib(29), etc.) are recomputed exponentially many times.

--------------------------------------------------
🧪 3. STEP-BY-STEP DRY RUN (REAL EXAMPLE)
--------------------------------------------------

For n=10 (iterative rolling window):
- start a=0, b=1
- i=2 -> c=1, update a=1,b=1
- i=3 -> c=2, update a=1,b=2
- i=4 -> c=3, update a=2,b=3
- ...
- i=10 -> b=55

Output: fib(10)=55

--------------------------------------------------
📊 4. VISUAL REPRESENTATION
--------------------------------------------------

Rolling window:
[a,b] -> next c=a+b -> shift to [b,c]

Only last two values are needed.

--------------------------------------------------
⚙️ 5. APPROACHES
--------------------------------------------------

1. Brute recursion: O(2^n)
2. Memoization: O(n), O(n)
3. Iterative optimal: O(n), O(1)

--------------------------------------------------
⚠️ 6. EDGE CASES
--------------------------------------------------

- n=0 -> 0
- n=1 -> 1
- small n where loops should not run unnecessarily

--------------------------------------------------
❌ 7. COMMON MISTAKES
--------------------------------------------------

- wrong starting pair (a,b)
- forgetting to initialize memo with sentinel values
- off-by-one in loop bounds

--------------------------------------------------
🎯 8. INTERVIEW INSIGHT
--------------------------------------------------

Follow-up:
Can we do better than O(n)?
Expected mention: matrix exponentiation or fast doubling O(log n).

--------------------------------------------------
🧾 9. CLEAN CODE
--------------------------------------------------
- Keep brute, memo, and optimal methods separate
- Keep base case checks first
- Use clear variable progression for iterative method

--------------------------------------------------
🧪 10. MAIN METHOD TESTING
--------------------------------------------------
Format:
Input -> Expected -> Actual

--------------------------------------------------
📌 11. MINI SUMMARY
--------------------------------------------------

Fibonacci is the easiest way to learn DP mindset:
cache repeated work and compute each state once.

==================================================
*/
public class FibonacciUsingDynamicProgramming {
    // Brute recursion.
    // Time: O(2^n), Space: O(n)
    public static int fibBrute(int n) {
        if (n <= 1) return n;
        return fibBrute(n - 1) + fibBrute(n - 2);
    }

    // Memoization.
    // Time: O(n), Space: O(n)
    public static int fibMemo(int n, int[] dp) {
        if (n <= 1) return n;
        if (dp[n] != -1) return dp[n];
        return dp[n] = fibMemo(n - 1, dp) + fibMemo(n - 2, dp);
    }

    // Best iterative constant space.
    // Time: O(n), Space: O(1)
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

    public static void main(String[] args) {
        int n = 10;

        int[] dp = new int[n + 1];
        java.util.Arrays.fill(dp, -1);
        System.out.println("Input: n=6, n=10");
        System.out.println("Expected: 8 | Brute fib(6): " + fibBrute(6));
        System.out.println("Expected: 55 | Memo fib(10): " + fibMemo(n, dp));
        System.out.println("Expected: 55 | Optimal fib(10): " + fibOptimal(n));
    }
}







