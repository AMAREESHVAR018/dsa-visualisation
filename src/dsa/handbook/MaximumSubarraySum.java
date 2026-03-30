package dsa.handbook;

/*
==================================================
📌 PROBLEM: Maximum Subarray Sum
==================================================

Need maximum sum among all contiguous subarrays.
Subarray must be continuous, not arbitrary picks.

You are given:
- integer array

👉 Your task:
Return largest contiguous sum.

--------------------------------------------------
🧠 1. INTUITION (DEEP UNDERSTANDING)
--------------------------------------------------

Kadane state:
cur = best subarray sum ending at current index.

Transition:
cur = max(a[i], cur + a[i])
best = max(best, cur)

Interpretation:
At each index, either start fresh at a[i], or extend previous segment.

--------------------------------------------------
❌ 2. WRONG APPROACH (BEGINNER MISTAKE)
--------------------------------------------------

Mistake:
Force `cur` to 0 whenever negative.

Why wrong:
Fails all-negative arrays where correct answer is largest (least negative) element.

--------------------------------------------------
🧪 3. STEP-BY-STEP DRY RUN (REAL EXAMPLE)
--------------------------------------------------

arr=[-2,1,-3,4,-1,2,1,-5,4]

start cur=best=-2
i=1 val=1 -> cur=max(1,-1)=1, best=1
i=2 val=-3 -> cur=max(-3,-2)=-2, best=1
i=3 val=4 -> cur=max(4,2)=4, best=4
i=4 val=-1 -> cur=max(-1,3)=3, best=4
i=5 val=2 -> cur=max(2,5)=5, best=5
i=6 val=1 -> cur=max(1,6)=6, best=6

Answer = 6

--------------------------------------------------
📊 4. VISUAL REPRESENTATION
--------------------------------------------------

Track two running values per index:
- cur (best ending here)
- best (global best so far)

--------------------------------------------------
⚙️ 5. APPROACHES
--------------------------------------------------

1. Brute all subarrays: O(n^2)
2. Kadane DP: O(n), O(1)

--------------------------------------------------
⚠️ 6. EDGE CASES
--------------------------------------------------

- single element
- all negatives
- mix of large positive and negative values

--------------------------------------------------
❌ 7. COMMON MISTAKES
--------------------------------------------------

- initializing best incorrectly
- handling all-negative arrays wrong
- confusing subsequence with subarray

--------------------------------------------------
🎯 8. INTERVIEW INSIGHT
--------------------------------------------------

Common extension:
Return start/end indices of best subarray too.
Same Kadane state with extra index bookkeeping.

--------------------------------------------------
🧾 9. CLEAN CODE
--------------------------------------------------
- Keep brute and Kadane methods separate
- Keep variable names short but meaningful (cur, best)
- Initialize from first element for correctness

--------------------------------------------------
🧪 10. MAIN METHOD TESTING
--------------------------------------------------
Format:
Input -> Expected -> Actual

--------------------------------------------------
📌 11. MINI SUMMARY
--------------------------------------------------

Kadane works because each index decides:
start new subarray or extend previous best-ending-here.

==================================================
*/
import java.util.*;

/*
Problem: Maximum Subarray Sum
Find contiguous subarray with maximum sum.
*/
public class MaximumSubarraySum {
    // Brute Force all subarrays.
    // Time: O(n^2), Space: O(1)
    public static int maxSubarrayBrute(int[] a) {
        int best = Integer.MIN_VALUE;
        for (int i = 0; i < a.length; i++) {
            int sum = 0;
            for (int j = i; j < a.length; j++) {
                sum += a[j];
                best = Math.max(best, sum);
            }
        }
        return best;
    }

    // Optimal Kadane.
    // Time: O(n), Space: O(1)
    public static int maxSubarrayKadane(int[] a) {
        int best = a[0], cur = a[0];
        for (int i = 1; i < a.length; i++) {
            cur = Math.max(a[i], cur + a[i]);
            best = Math.max(best, cur);
        }
        return best;
    }

    public static void main(String[] args) {
        int[][] tests = {{-2,1,-3,4,-1,2,1,-5,4},{1},{-3,-1,-2},{5,-1,2}};
        int[] expected = {6,1,-1,6};
        for (int i = 0; i < tests.length; i++) {
            int[] t = tests[i];
            int exp = expected[i];
            System.out.println("Input: " + Arrays.toString(t));
            System.out.println("Expected: " + exp + " | Brute: " + maxSubarrayBrute(t) + " | Kadane: " + maxSubarrayKadane(t));
            System.out.println();
        }
    }
}







