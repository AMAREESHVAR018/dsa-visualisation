package dsa.handbook;

/*
==================================================
📌 PROBLEM: Insertion Sort
==================================================

Insertion sort is a foundational stable sort.
It is frequently used in hybrid algorithms for small partitions.

You are given:
- unsorted array

👉 Your task:
sort by maintaining a sorted prefix and inserting the next element into it.

--------------------------------------------------
🧠 1. INTUITION (DEEP UNDERSTANDING)
--------------------------------------------------

At index i:
- key = a[i]
- shift all larger values in prefix [0..i-1] one position right
- place key at correct insertion location

Invariant after each i:
subarray [0..i] is sorted.

--------------------------------------------------
❌ 2. WRONG APPROACH (BEGINNER MISTAKE)
--------------------------------------------------

Mistake:
run nested swaps like bubble sort inside each step.

Why suboptimal:
extra writes and less clear invariant.
Insertion sort is naturally a shift-and-place algorithm.

--------------------------------------------------
🧪 3. STEP-BY-STEP DRY RUN (REAL EXAMPLE)
--------------------------------------------------

Input: [12,11,13,5]

i=1, key=11
- shift 12 right -> [12,12,13,5]
- place key at index 0 -> [11,12,13,5]

i=2, key=13
- no shift needed -> [11,12,13,5]

i=3, key=5
- shift 13 -> [11,12,13,13]
- shift 12 -> [11,12,12,13]
- shift 11 -> [11,11,12,13]
- place key -> [5,11,12,13]

--------------------------------------------------
📊 4. VISUAL REPRESENTATION
--------------------------------------------------

Sorted prefix grows monotonically:
[11] -> [11,12] -> [11,12,13] -> [5,11,12,13]

--------------------------------------------------
⚙️ 5. APPROACHES
--------------------------------------------------

1. Iterative insertion sort
- Worst: O(n^2)
- Best (already sorted): O(n)
- Space: O(1)

2. Recursive insertion sort
- Time: O(n^2)
- Stack space: O(n)

--------------------------------------------------
⚠️ 6. EDGE CASES
--------------------------------------------------

- already sorted array
- reverse sorted array
- duplicates
- empty/single-element array

--------------------------------------------------
❌ 7. COMMON MISTAKES
--------------------------------------------------

- overwriting key before storing it
- off-by-one after while loop
- wrong recursive size progression

--------------------------------------------------
🎯 8. INTERVIEW INSIGHT
--------------------------------------------------

Insertion sort is used by hybrid sorts for small partitions
because it has low overhead and good cache behavior.

--------------------------------------------------
🧾 9. CLEAN CODE
--------------------------------------------------
- Keep iterative and recursive variants separate
- Preserve key before shifts
- Keep while-loop condition explicit and readable

--------------------------------------------------
🧪 10. MAIN METHOD TESTING
--------------------------------------------------
Format:
Input -> Expected -> Actual

--------------------------------------------------
📌 11. MINI SUMMARY
--------------------------------------------------

Insertion sort is all about one invariant:
left prefix is always sorted after each iteration.
If you preserve this, correctness follows naturally.

==================================================
*/
import java.util.*;

/*
Problem: Insertion Sort
Build sorted prefix by inserting each element.
*/
public class InsertionSort {
    // Iterative insertion sort.
    // Time: O(n^2), Best-case O(n), Space: O(1)
    public static void insertionSort(int[] a) {
        for (int i = 1; i < a.length; i++) {
            int key = a[i], j = i - 1;
            while (j >= 0 && a[j] > key) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = key;
        }
    }

    // Recursive variant.
    // Time: O(n^2), Space: O(n)
    public static void insertionSortRecursive(int[] a, int n) {
        if (n <= 1) return;
        insertionSortRecursive(a, n - 1);
        int last = a[n - 1], j = n - 2;
        while (j >= 0 && a[j] > last) { a[j + 1] = a[j]; j--; }
        a[j + 1] = last;
    }

    public static void main(String[] args) {
        int[] a = {12,11,13,5,6};

        int[] b = a.clone();
        insertionSort(a);
        insertionSortRecursive(b, b.length);
        System.out.println("Input: [12, 11, 13, 5, 6]");
        System.out.println("Expected: [5, 6, 11, 12, 13] | Iterative: " + Arrays.toString(a));
        System.out.println("Expected: [5, 6, 11, 12, 13] | Recursive: " + Arrays.toString(b));
    }
}







