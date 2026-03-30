package dsa.handbook;

/*
==================================================
📌 PROBLEM: Factorial of a Number
==================================================

Factorial appears in counting and combinatorics.
It multiplies all integers from 1 to n, with 0! = 1.

You are given:
- integer n

👉 Your task:
Return n! for valid n >= 0.

--------------------------------------------------
🧠 1. INTUITION (DEEP UNDERSTANDING)
--------------------------------------------------

Factorial has direct recurrence:
n! = n * (n-1)!

Equivalent iterative view:
start ans=1 and multiply 2..n.

--------------------------------------------------
❌ 2. WRONG APPROACH (BEGINNER MISTAKE)
--------------------------------------------------

Mistake:
Ignore invalid negative input.

Why wrong:
Factorial is undefined for negative integers in this context.
Code should reject invalid input explicitly.

--------------------------------------------------
🧪 3. STEP-BY-STEP DRY RUN (REAL EXAMPLE)
--------------------------------------------------

n = 5 (iterative)
- ans=1
- i=2 -> ans=2
- i=3 -> ans=6
- i=4 -> ans=24
- i=5 -> ans=120

Output: 120

--------------------------------------------------
📊 4. VISUAL REPRESENTATION
--------------------------------------------------

5! = 5 * 4 * 3 * 2 * 1

Recursive chain:
5! -> 5*4! -> 5*4*3! -> ... -> 5*4*3*2*1

--------------------------------------------------
⚙️ 5. APPROACHES
--------------------------------------------------

1. Recursive factorial: O(n) time, O(n) stack
2. Iterative factorial: O(n) time, O(1) extra space

--------------------------------------------------
⚠️ 6. EDGE CASES
--------------------------------------------------

- n=0 -> 1
- n=1 -> 1
- n<0 -> illegal argument

--------------------------------------------------
❌ 7. COMMON MISTAKES
--------------------------------------------------

- incorrect base case
- forgetting overflow limits of long for large n
- skipping input validation

--------------------------------------------------
🎯 8. INTERVIEW INSIGHT
--------------------------------------------------

Follow-up:
How to handle very large n?
Expected answer: use BigInteger.

--------------------------------------------------
🧾 9. CLEAN CODE
--------------------------------------------------
- Validate input first in both methods
- Keep recursive and iterative methods separate
- Keep test values simple and verifiable

--------------------------------------------------
🧪 10. MAIN METHOD TESTING
--------------------------------------------------
Format:
Input -> Expected -> Actual

--------------------------------------------------
📌 11. MINI SUMMARY
--------------------------------------------------

Factorial is simple but tests fundamentals:
base cases, input validation, and recursion vs iteration.

==================================================
*/
public class FactorialOfANumber {
    // Brute recursion.
    // Time: O(n), Space: O(n)
    public static long factorialRecursive(int n) {
        if (n < 0) throw new IllegalArgumentException("Negative input not allowed");
        if (n <= 1) return 1;
        return n * factorialRecursive(n - 1);
    }

    // Optimal iterative.
    // Time: O(n), Space: O(1)
    public static long factorialIterative(int n) {
        if (n < 0) throw new IllegalArgumentException("Negative input not allowed");
        long ans = 1;
        for (int i = 2; i <= n; i++) ans *= i;
        return ans;
    }

    public static void main(String[] args) {
        int[] tests = {0,1,5,10};
        long[] expected = {1,1,120,3628800};

        for (int i = 0; i < tests.length; i++) {
            int t = tests[i];
            long exp = expected[i];
            System.out.println("Input: n=" + t);
            System.out.println("Expected: " + exp + " | Recursive: " + factorialRecursive(t) + " | Iterative: " + factorialIterative(t));
        }
    }
}







