package dsa.handbook;

/*
==================================================
📌 PROBLEM: Maximum Difference in Array
==================================================

Need maximum value of a[j] - a[i] such that j > i.
Order matters, so this is like one buy then one sell.

You are given:
- integer array a[]

👉 Your task:
find the best order-respecting difference.

--------------------------------------------------
🧠 1. INTUITION (DEEP UNDERSTANDING)
--------------------------------------------------

At each index i:
- assume a[i] is sell value
- best buy candidate is minimum value seen before i

So difference candidate is a[i] - minSoFar.
Track maximum candidate as answer.

--------------------------------------------------
❌ 2. WRONG APPROACH (BEGINNER MISTAKE)
--------------------------------------------------

Mistake:
Sort array and do max-min.

Why wrong:
Sorting destroys original positions,
but problem requires j to appear after i.

--------------------------------------------------
🧪 3. STEP-BY-STEP DRY RUN (REAL EXAMPLE)
--------------------------------------------------

arr = [2,3,10,6,4,8,1]

start: minSoFar=2, best=1 (3-2)
i=2 val=10 -> candidate=8, best=8
i=3 val=6 -> candidate=4, best=8
i=4 val=4 -> candidate=2, best=8
i=5 val=8 -> candidate=6, best=8
i=6 val=1 -> candidate=-1, minSoFar=1

final answer: 8

--------------------------------------------------
📊 4. VISUAL REPRESENTATION
--------------------------------------------------

Prefix minimum line vs current value.
Vertical gap gives candidate difference at each index.

--------------------------------------------------
⚙️ 5. APPROACHES
--------------------------------------------------

1. Brute pair check: O(n^2), O(1)
2. One-pass prefix-min: O(n), O(1)

--------------------------------------------------
⚠️ 6. EDGE CASES
--------------------------------------------------

- length < 2 -> invalid
- strictly decreasing -> answer can be negative
- duplicates

--------------------------------------------------
❌ 7. COMMON MISTAKES
--------------------------------------------------

- updating min and best in confusing order
- assuming result cannot be negative
- forgetting j > i condition

--------------------------------------------------
🎯 8. INTERVIEW INSIGHT
--------------------------------------------------

This maps directly to "Best Time to Buy and Sell Stock" (one transaction).
State explanation often matters more than final code.

--------------------------------------------------
🧾 9. CLEAN CODE
--------------------------------------------------
- Keep brute and optimal methods separate
- Validate size before one-pass logic
- Name running variables semantically (`minSoFar`, `best`)

--------------------------------------------------
🧪 10. MAIN METHOD TESTING
--------------------------------------------------
Format:
Input -> Expected -> Actual

--------------------------------------------------
📌 11. MINI SUMMARY
--------------------------------------------------

Track smallest prefix value and best gap so far.
That one invariant gives O(n) solution.

==================================================
*/
import java.util.*;

/*
Problem: Maximum difference in array where larger element appears after smaller.
*/
public class MaximumDifferenceInArray {
    // Brute force all pairs.
    // Time: O(n^2), Space: O(1)
    public static int maxDiffBrute(int[] a) {
        int best = Integer.MIN_VALUE;
        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                best = Math.max(best, a[j] - a[i]);
            }
        }
        return best;
    }

    // Optimal one pass.
    // Time: O(n), Space: O(1)
    public static int maxDiffOptimal(int[] a) {
        if (a.length < 2) throw new IllegalArgumentException("Need at least two elements");
        int minSoFar = a[0], best = a[1] - a[0];
        for (int i = 1; i < a.length; i++) {
            best = Math.max(best, a[i] - minSoFar);
            minSoFar = Math.min(minSoFar, a[i]);
        }
        return best;
    }

    public static void main(String[] args) {
        int[][] tests = {{2,3,10,6,4,8,1},{7,9,5,6,3,2},{1,2}};
        int[] expected = {8, 2, 1};

        for (int i = 0; i < tests.length; i++) {
            int[] t = tests[i];
            int exp = expected[i];
            System.out.println("Input: " + Arrays.toString(t));
            System.out.println("Expected: " + exp + " | Brute: " + maxDiffBrute(t) + " | Optimal: " + maxDiffOptimal(t));
        }
    }
}







