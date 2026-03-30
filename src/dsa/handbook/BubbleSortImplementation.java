package dsa.handbook;

/*
==================================================
📌 PROBLEM: Bubble Sort Implementation (Practice Variant)
==================================================

This file reinforces bubble sort with two implementations in one place.
Goal is to compare baseline and optimized behavior on same input.

You are given:
- unsorted array

👉 Your task:
Implement and validate both basic and optimized bubble sort.

--------------------------------------------------
🧠 1. INTUITION (DEEP UNDERSTANDING)
--------------------------------------------------

Adjacent comparison is enough because each swap fixes one local inversion.
Repeated passes propagate large values toward the right boundary.

--------------------------------------------------
❌ 2. WRONG APPROACH (BEGINNER MISTAKE)
--------------------------------------------------

Beginner issue:
Forget to reinitialize `swapped=false` each pass.

Consequence:
Early-termination logic breaks and may stop too early or too late.

--------------------------------------------------
🧪 3. STEP-BY-STEP DRY RUN (REAL EXAMPLE)
--------------------------------------------------

Input: [5,1,4,2,8]

Pass 1 -> [1,4,2,5,8]
Pass 2 -> [1,2,4,5,8]
Pass 3 -> no swaps (optimized version stops)

--------------------------------------------------
📊 4. VISUAL REPRESENTATION
--------------------------------------------------

Each pass pushes one maximum to final place.
Right side gradually becomes sorted suffix.

--------------------------------------------------
⚙️ 5. APPROACHES
--------------------------------------------------

1. Basic bubble sort: always full passes, O(n^2)
2. Optimized bubble sort: early break when no swaps, best O(n)

--------------------------------------------------
⚠️ 6. EDGE CASES
--------------------------------------------------

- sorted input (optimization should stop quickly)
- duplicate elements
- size 0 or 1 arrays

--------------------------------------------------
❌ 7. COMMON MISTAKES
--------------------------------------------------

- incorrect inner loop bound
- not cloning input while comparing two methods
- comparing wrong output arrays in tests

--------------------------------------------------
🎯 8. INTERVIEW INSIGHT
--------------------------------------------------

This is often asked to evaluate fundamentals, not production readiness.
Explain invariants and stability clearly.

--------------------------------------------------
🧾 9. CLEAN CODE
--------------------------------------------------
- Keep basic and optimized methods independent
- Keep swap block tiny and readable
- Use same input clone for fair comparison

--------------------------------------------------
🧪 10. MAIN METHOD TESTING
--------------------------------------------------
Format:
Input -> Expected -> Actual

--------------------------------------------------
📌 11. MINI SUMMARY
--------------------------------------------------

Optimization does not change correctness, only avoids unnecessary passes.

==================================================
*/
import java.util.*;

/*
Problem: Bubble sort implementation (Set 2)
*/
public class BubbleSortImplementation {
    // Basic bubble sort.
    // Time: O(n^2), Space: O(1)
    public static void sortBasic(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = 0; j < a.length - 1 - i; j++) {
                if (a[j] > a[j + 1]) {
                    int t = a[j]; a[j] = a[j + 1]; a[j + 1] = t;
                }
            }
        }
    }

    // Optimized bubble sort with early break.
    // Time: Best O(n), Worst O(n^2), Space: O(1)
    public static void sortOptimized(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < a.length - 1 - i; j++) {
                if (a[j] > a[j + 1]) {
                    int t = a[j]; a[j] = a[j + 1]; a[j + 1] = t;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    public static void main(String[] args) {
        int[] a = {5,1,4,2,8};

        int[] b = a.clone();
        sortBasic(a);
        sortOptimized(b);
        System.out.println("Input: [5, 1, 4, 2, 8]");
        System.out.println("Expected: [1, 2, 4, 5, 8] | Basic: " + Arrays.toString(a));
        System.out.println("Expected: [1, 2, 4, 5, 8] | Optimized: " + Arrays.toString(b));
    }
}







