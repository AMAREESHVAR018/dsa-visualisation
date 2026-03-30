package dsa.handbook;

/*
==================================================
📌 PROBLEM: Heap Sort
==================================================

Heap sort is a comparison sort that uses heap data structure rules
but stores the heap directly inside the array (no separate tree object).

You are given:
- unsorted integer array

👉 Your task:
sort it ascending using max-heap extraction.

--------------------------------------------------
🧠 1. INTUITION (DEEP UNDERSTANDING)
--------------------------------------------------

Two phases:
1. Build max-heap so largest value moves to index 0.
2. Swap root with last unsorted index, reduce heap size, heapify root.

Invariant:
- Prefix [0..heapSize-1] is a valid max-heap.
- Suffix [heapSize..n-1] is already sorted.

--------------------------------------------------
❌ 2. WRONG APPROACH (BEGINNER MISTAKE)
--------------------------------------------------

Beginner mistake:
Call heapify with full n every extraction.

Why wrong:
Sorted suffix gets disturbed again, breaking correctness.
Heapify must operate only on active heap size.

--------------------------------------------------
🧪 3. STEP-BY-STEP DRY RUN (REAL EXAMPLE)
--------------------------------------------------

Input: [12,11,13,5,6,7]

Build max-heap:
- root becomes 13

Extraction 1:
- swap a[0] and a[5] => [7,11,12,5,6,13]
- heapify first 5 => [12,11,7,5,6,13]

Extraction 2:
- swap a[0] and a[4] => [6,11,7,5,12,13]
- heapify first 4 => [11,6,7,5,12,13]

Continue until heap size = 1
Final: [5,6,7,11,12,13]

--------------------------------------------------
📊 4. VISUAL REPRESENTATION
--------------------------------------------------

Index mapping in array-heap:
- parent = i
- left child = 2*i + 1
- right child = 2*i + 2

Largest element always bubbles to root after heapify.

--------------------------------------------------
⚙️ 5. APPROACHES
--------------------------------------------------

Heap sort (in-place):
- Time: O(n log n)
- Extra Space: O(1)

--------------------------------------------------
⚠️ 6. EDGE CASES
--------------------------------------------------

- empty array
- single element
- repeated values
- already sorted and reverse sorted arrays

--------------------------------------------------
❌ 7. COMMON MISTAKES
--------------------------------------------------

- wrong heap boundary in heapify
- forgetting to build heap bottom-up
- using min-heap comparison in max-heap sort

--------------------------------------------------
🎯 8. INTERVIEW INSIGHT
--------------------------------------------------

Heap sort is in-place but not stable.
Great follow-up comparison: merge sort (stable, extra space) vs heap sort (in-place, unstable).

--------------------------------------------------
🧾 9. CLEAN CODE
--------------------------------------------------
- Keep `heapify` separate and deterministic
- Pass heap size explicitly every call
- Keep swap blocks tiny and local

--------------------------------------------------
🧪 10. MAIN METHOD TESTING
--------------------------------------------------
Format:
Input -> Expected -> Actual

--------------------------------------------------
📌 11. MINI SUMMARY
--------------------------------------------------

Heap sort repeatedly places current maximum at the end.
Correct heap boundary handling is the core implementation detail.

==================================================
*/
import java.util.*;

/*
Problem: Heap Sort
Use max-heap to sort array.
*/
public class HeapSort {
    // Heap sort.
    // Time: O(n log n), Space: O(1)
    public static void heapSort(int[] a) {
        int n = a.length;
        for (int i = n / 2 - 1; i >= 0; i--) heapify(a, n, i);
        for (int i = n - 1; i > 0; i--) {
            int t = a[0]; a[0] = a[i]; a[i] = t;
            heapify(a, i, 0);
        }
    }

    private static void heapify(int[] a, int n, int i) {
        int largest = i, l = 2 * i + 1, r = 2 * i + 2;
        if (l < n && a[l] > a[largest]) largest = l;
        if (r < n && a[r] > a[largest]) largest = r;
        if (largest != i) {
            int t = a[i]; a[i] = a[largest]; a[largest] = t;
            heapify(a, n, largest);
        }
    }

    public static void main(String[] args) {
        int[] a = {12,11,13,5,6,7};

        heapSort(a);
        System.out.println("Input: [12, 11, 13, 5, 6, 7]");
        System.out.println("Expected: [5, 6, 7, 11, 12, 13] | Actual: " + Arrays.toString(a));
    }
}







