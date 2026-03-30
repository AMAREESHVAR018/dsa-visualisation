package dsa.handbook;

/*
==================================================
📌 PROBLEM: Quick Sort
==================================================

Quick sort places one pivot in final position each partition,
then recursively solves left and right sides.

You are given:
- unsorted integer array

👉 Your task:
sort the array in ascending order using in-place partitioning.

--------------------------------------------------
🧠 1. INTUITION (DEEP UNDERSTANDING)
--------------------------------------------------

Partition invariant (Lomuto):
- a[l..i-1] <= pivot
- a[i..j-1] > pivot

When scan ends, swap pivot into index i.
Now pivot is in its final sorted location.

--------------------------------------------------
❌ 2. WRONG APPROACH (BEGINNER MISTAKE)
--------------------------------------------------

Mistake:
always picking poor pivot (like last element on sorted input)
without understanding worst-case behavior.

Why problematic:
partition becomes highly unbalanced,
leading to recursion depth O(n) and time O(n^2).

--------------------------------------------------
🧪 3. STEP-BY-STEP DRY RUN (REAL EXAMPLE)
--------------------------------------------------

arr = [10,7,8,9,1,5], pivot=5
after partition -> [1,5,8,9,10,7], pivot index=1

left side [1] already sorted.
right side [8,9,10,7] partition with pivot 7 -> [7,9,10,8]
continue recursively until fully sorted: [1,5,7,8,9,10]

--------------------------------------------------
📊 4. VISUAL REPRESENTATION
--------------------------------------------------

Index j scans elements,
index i marks boundary for values <= pivot.

--------------------------------------------------
⚙️ 5. APPROACHES
--------------------------------------------------

Lomuto quicksort:
- Average: O(n log n)
- Worst: O(n^2)
- Extra space: O(log n) recursion average

--------------------------------------------------
⚠️ 6. EDGE CASES
--------------------------------------------------

- empty array
- single element
- already sorted input with deterministic pivot
- many duplicate values

--------------------------------------------------
❌ 7. COMMON MISTAKES
--------------------------------------------------

- recursing with wrong bounds around pivot index
- forgetting base case l >= r
- assuming quicksort is stable (it is not)

--------------------------------------------------
🎯 8. INTERVIEW INSIGHT
--------------------------------------------------

Strong follow-up:
"How do you avoid worst-case?"
Answer: randomized pivot or median-of-three.

--------------------------------------------------
🧾 9. CLEAN CODE
--------------------------------------------------
- Keep partition isolated in helper
- Keep swaps simple and consistent
- Ensure boundaries are explicit in recursive calls

--------------------------------------------------
🧪 10. MAIN METHOD TESTING
--------------------------------------------------
Format:
Input -> Expected -> Actual

--------------------------------------------------
📌 11. MINI SUMMARY
--------------------------------------------------

If partition is correct, recursion naturally sorts both halves.
Most quicksort bugs are partition-boundary bugs.

==================================================
*/
import java.util.*;

/*
Problem: Quick Sort
Partition-based in-place sorting.
*/
public class QuickSort {
    // Lomuto partition quicksort.
    // Avg Time: O(n log n), Worst: O(n^2), Space: O(log n)
    public static void quickSort(int[] a) {
        sort(a, 0, a.length - 1);
    }

    private static void sort(int[] a, int l, int r) {
        if (l >= r) return;
        int p = partition(a, l, r);
        sort(a, l, p - 1);
        sort(a, p + 1, r);
    }

    private static int partition(int[] a, int l, int r) {
        int pivot = a[r], i = l;
        for (int j = l; j < r; j++) {
            if (a[j] <= pivot) {
                int t = a[i]; a[i] = a[j]; a[j] = t;
                i++;
            }
        }
        int t = a[i]; a[i] = a[r]; a[r] = t;
        return i;
    }

    public static void main(String[] args) {
        int[] a = {10,7,8,9,1,5};

        quickSort(a);
        System.out.println("Input: [10, 7, 8, 9, 1, 5]");
        System.out.println("Expected: [1, 5, 7, 8, 9, 10] | Actual: " + Arrays.toString(a));
    }
}







