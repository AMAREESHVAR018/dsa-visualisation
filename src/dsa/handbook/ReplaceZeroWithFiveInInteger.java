package dsa.handbook;

/*
==================================================
📌 PROBLEM: Replace 0 with 5 in Integer
==================================================

Replace every digit 0 in an integer with 5,
while preserving the original digit order.

You are given:
- integer n

👉 Your task:
return transformed integer where each 0 digit becomes 5.

--------------------------------------------------
🧠 1. INTUITION (DEEP UNDERSTANDING)
--------------------------------------------------

This is a per-digit mapping task:
- if digit is 0, replace with 5
- otherwise keep digit as is

Main challenge:
rebuild the number without reversing digit order.

--------------------------------------------------
❌ 2. WRONG APPROACH (BEGINNER MISTAKE)
--------------------------------------------------

Mistake:
process from least-significant digit and append directly,
which reverses digit sequence.

Why wrong:
digit order is part of number meaning.
Incorrect reconstruction gives wrong final value.

--------------------------------------------------
🧪 3. STEP-BY-STEP DRY RUN (REAL EXAMPLE)
--------------------------------------------------

n = 1020
digits: 1,0,2,0
mapped: 1,5,2,5
answer: 1525

Special case:
n = 0 -> 5

--------------------------------------------------
📊 4. VISUAL REPRESENTATION
--------------------------------------------------

Input digit stream -> mapped digit stream -> rebuilt number.

--------------------------------------------------
⚙️ 5. APPROACHES
--------------------------------------------------

1. String replacement: O(d), O(d)
2. Recursive arithmetic rebuild: O(d), O(d) stack

--------------------------------------------------
⚠️ 6. EDGE CASES
--------------------------------------------------

- n = 0 must return 5
- numbers without zero remain unchanged
- large values near int limit

--------------------------------------------------
❌ 7. COMMON MISTAKES
--------------------------------------------------

- forgetting explicit n=0 case
- rebuilding in reverse order
- assuming negative numbers are part of problem when not specified

--------------------------------------------------
🎯 8. INTERVIEW INSIGHT
--------------------------------------------------

Common follow-up:
solve without string conversion.
Recursive arithmetic method demonstrates stronger number handling.

--------------------------------------------------
🧾 9. CLEAN CODE
--------------------------------------------------
- Keep string and arithmetic approaches separate
- Use tiny helper for recursive rebuild
- Handle zero as explicit base case

--------------------------------------------------
🧪 10. MAIN METHOD TESTING
--------------------------------------------------
Format:
Input -> Expected -> Actual

--------------------------------------------------
📌 11. MINI SUMMARY
--------------------------------------------------

Replace-zero-with-five is simple mapping,
but correctness depends on preserving digit order.

==================================================
*/
public class ReplaceZeroWithFiveInInteger {
    // Brute using string conversion.
    // Time: O(d), Space: O(d)
    public static int replaceBrute(int n) {
        if (n == 0) return 5;
        String s = String.valueOf(n).replace('0', '5');
        return Integer.parseInt(s);
    }

    // Optimal recursive digit processing.
    // Time: O(d), Space: O(d) recursion
    public static int replaceOptimal(int n) {
        if (n == 0) return 5;
        return helper(n);
    }

    private static int helper(int n) {
        if (n == 0) return 0;
        int d = n % 10;
        if (d == 0) d = 5;
        return helper(n / 10) * 10 + d;
    }

    public static void main(String[] args) {
        int[] tests = {1020, 0, 999, 100000};
        int[] expected = {1525, 5, 999, 155555};

        for (int i = 0; i < tests.length; i++) {
            int t = tests[i];
            System.out.println("Input: " + t);
            System.out.println("Expected: " + expected[i] + " | Brute: " + replaceBrute(t) + " | Optimal: " + replaceOptimal(t));
            System.out.println();
        }
    }
}







