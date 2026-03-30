package dsa.handbook;

/*
==================================================
📌 PROBLEM: Buildings Receiving Sunlight
==================================================

Imagine sunset from the right side of a skyline.
A building is visible only if no taller building blocks it on the right.

You are given:
- heights array from left to right

👉 Your task:
Return indices of buildings that receive sunlight from the right.

--------------------------------------------------
🧠 1. INTUITION (DEEP UNDERSTANDING)
--------------------------------------------------

From right to left, keep `maxSoFar`.
If current height >= maxSoFar, it is visible.
Then update maxSoFar.

Why this works:
maxSoFar exactly represents tallest blocker on the right side seen so far.

--------------------------------------------------
❌ 2. WRONG APPROACH (BEGINNER MISTAKE)
--------------------------------------------------

Brute-force thought:
For each i, scan all j>i to check blockers.

Why poor:
Repeatedly rescans the same right region, giving O(n^2).

--------------------------------------------------
🧪 3. STEP-BY-STEP DRY RUN (REAL EXAMPLE)
--------------------------------------------------

arr = [3,5,4,4,3,1,3,2]

Right-to-left scan:
- i=7, h=2, max=-INF -> visible, max=2
- i=6, h=3, 3>=2 -> visible, max=3
- i=5, h=1, 1<3 -> blocked
- i=4, h=3, 3>=3 -> visible, max=3
- i=3, h=4, 4>=3 -> visible, max=4
- i=2, h=4, 4>=4 -> visible, max=4
- i=1, h=5, 5>=4 -> visible, max=5
- i=0, h=3, 3<5 -> blocked

Visible indices collected from right: [7,6,4,3,2,1]
Reverse for left-to-right output: [1,2,3,4,6,7]

--------------------------------------------------
📊 4. VISUAL REPRESENTATION
--------------------------------------------------

Heights:  3 5 4 4 3 1 3 2
Indices:  0 1 2 3 4 5 6 7
Sun from right -> keep new local maxima when scanning backward.

--------------------------------------------------
⚙️ 5. APPROACHES
--------------------------------------------------

1. Brute force (for each building scan right): O(n^2)
2. Optimal one pass from right with running max: O(n)

--------------------------------------------------
⚠️ 6. EDGE CASES
--------------------------------------------------

- single building always visible
- strictly increasing heights -> only last may be visible from right
- strictly decreasing heights -> all visible
- equal heights policy depends on `>=` vs `>`

--------------------------------------------------
❌ 7. COMMON MISTAKES
--------------------------------------------------

- wrong comparator (`>` instead of `>=`) changes equal-height behavior
- forgetting to reverse result list
- scanning left-to-right with wrong invariant

--------------------------------------------------
🎯 8. INTERVIEW INSIGHT
--------------------------------------------------

Interview extension:
Find visibility from left side too.
Same invariant, opposite scan direction.

--------------------------------------------------
🧾 9. CLEAN CODE
--------------------------------------------------
- Keep brute and optimal methods separate
- Name running variable as `max` or `maxSoFar`
- Reverse only once at the end

--------------------------------------------------
🧪 10. MAIN METHOD TESTING
--------------------------------------------------
Format:
Input -> Expected -> Actual

--------------------------------------------------
📌 11. MINI SUMMARY
--------------------------------------------------

Track a right-side running maximum.
Each new building that meets or beats it is sunlight-visible.

==================================================
*/
import java.util.*;

/*
Problem: Buildings Receiving Sunlight
Given building heights from left to right, find indices that can see sunlight from right side.
Input: [3,5,4,4,3,1,3,2]
Output: [1,3,6,7]
*/
public class BuildingsReceivingSunlight {
    // Brute Force: for each building, check all buildings on right.
    // Time: O(n^2), Space: O(1)
    public static List<Integer> visibleBrute(int[] h) {
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < h.length; i++) {
            boolean ok = true;
            for (int j = i + 1; j < h.length; j++) {
                if (h[j] > h[i]) { ok = false; break; }
            }
            if (ok) ans.add(i);
        }
        return ans;
    }

    // Optimal: scan from right and keep max height.
    // Time: O(n), Space: O(1) extra (excluding output)
    public static List<Integer> visibleOptimal(int[] h) {
        List<Integer> ans = new ArrayList<>();
        int max = Integer.MIN_VALUE;
        for (int i = h.length - 1; i >= 0; i--) {
            if (h[i] >= max) {
                ans.add(i);
                max = h[i];
            }
        }
        Collections.reverse(ans);
        return ans;
    }

    public static void main(String[] args) {
        int[][] tests = {{3,5,4,4,3,1,3,2},{1,2,3,4},{4,3,2,1},{5}};
        String[] expected = {
                "[1, 2, 3, 4, 6, 7]",
                "[3]",
                "[0, 1, 2, 3]",
                "[0]"
        };
        for (int i = 0; i < tests.length; i++) {
            int[] t = tests[i];
            System.out.println("Input: " + Arrays.toString(t));
            System.out.println("Expected: " + expected[i] + " | Brute: " + visibleBrute(t));
            System.out.println("Expected: " + expected[i] + " | Optimal: " + visibleOptimal(t));
            System.out.println();
        }
    }
}








