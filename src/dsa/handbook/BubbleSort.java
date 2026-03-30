package dsa.handbook;

/*
==================================================
📌 PROBLEM: Bubble Sort
==================================================

Bubble sort repeatedly fixes adjacent inversions.
After each pass, the largest remaining element "bubbles" to its final position.

You are given:
- unsorted integer array

👉 Your task:
Sort array in ascending order using adjacent swaps.

--------------------------------------------------
🧠 1. INTUITION (DEEP UNDERSTANDING)
--------------------------------------------------

Compare neighboring elements only.
If left > right, swap.

Invariant after pass i:
last i elements are already in correct final positions.

--------------------------------------------------
❌ 2. WRONG APPROACH (BEGINNER MISTAKE)
--------------------------------------------------

Mistake:
Always run all n-1 passes even when array is already sorted.

Why poor:
Wastes time on near-sorted input.
Use `swapped` flag to stop early.

--------------------------------------------------
🧪 3. STEP-BY-STEP DRY RUN (REAL EXAMPLE)
--------------------------------------------------

Input: [5,1,4,2,8]

Pass 1:
- (5,1) swap -> [1,5,4,2,8]
- (5,4) swap -> [1,4,5,2,8]
- (5,2) swap -> [1,4,2,5,8]
- (5,8) ok

Pass 2:
- (1,4) ok
- (4,2) swap -> [1,2,4,5,8]
- (4,5) ok

Array sorted.

--------------------------------------------------
📊 4. VISUAL REPRESENTATION
--------------------------------------------------

Largest element drifts right each pass:
[5,1,4,2,8] -> [...,8]

--------------------------------------------------
⚙️ 5. APPROACHES
--------------------------------------------------

1. Basic bubble sort: O(n^2)
2. Optimized bubble sort with early stop:
    best O(n), worst O(n^2)

Both are in-place and stable.

--------------------------------------------------
⚠️ 6. EDGE CASES
--------------------------------------------------

- already sorted array
- reverse sorted array
- duplicates
- single-element / empty array

--------------------------------------------------
❌ 7. COMMON MISTAKES
--------------------------------------------------

- wrong inner bound causing out-of-range access
- not resetting `swapped` each outer pass
- swapping on `>=` can break stability expectations

--------------------------------------------------
🎯 8. INTERVIEW INSIGHT
--------------------------------------------------

Bubble sort is mostly pedagogical.
Interviewers use it to test loop invariants and stability understanding.

--------------------------------------------------
🧾 9. CLEAN CODE
--------------------------------------------------
- Keep swap helper isolated
- Keep brute and optimized methods separate
- Name loop boundaries clearly

--------------------------------------------------
🧪 10. MAIN METHOD TESTING
--------------------------------------------------
Format:
Input -> Expected -> Actual

--------------------------------------------------
📌 11. MINI SUMMARY
--------------------------------------------------

Bubble sort fixes local inversions repeatedly.
Optimization is simply: stop when a full pass makes zero swaps.

==================================================
*/
import java.util.*;

/*
Problem: Bubble Sort
Sort array in ascending order.
*/
public class BubbleSort {
    // Brute/basic bubble sort.
    // Time: O(n^2), Space: O(1)
    public static void bubbleSortBrute(int[] a) {
        int n = a.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (a[j] > a[j + 1]) swap(a, j, j + 1);
            }
        }
    }

    // Better: stop early if already sorted.
    // Best-case Time: O(n), Worst: O(n^2), Space: O(1)
    public static void bubbleSortOptimized(int[] a) {
        int n = a.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (a[j] > a[j + 1]) {
                    swap(a, j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    private static void swap(int[] a, int i, int j) { int t = a[i]; a[i] = a[j]; a[j] = t; }

    public static void main(String[] args) {
        int[][] tests = {{5,1,4,2,8},{1,2,3},{3,2,1},{7}};
        String[] expected = {
                "[1, 2, 4, 5, 8]",
                "[1, 2, 3]",
                "[1, 2, 3]",
                "[7]"
        };

        for (int i = 0; i < tests.length; i++) {
            int[] t = tests[i];
            int[] b = t.clone(), c = t.clone();
            bubbleSortBrute(b);
            bubbleSortOptimized(c);
            System.out.println("Input: " + Arrays.toString(t));
            System.out.println("Expected: " + expected[i] + " | Brute: " + Arrays.toString(b));
            System.out.println("Expected: " + expected[i] + " | Optimized: " + Arrays.toString(c));
            System.out.println();
        }
    }
}







