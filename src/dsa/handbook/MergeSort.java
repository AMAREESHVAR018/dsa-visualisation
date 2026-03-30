package dsa.handbook;

/*
==================================================
📌 PROBLEM: Merge Sort
==================================================

Merge sort is a classic divide-and-conquer stable sorting algorithm.
It splits array into halves, sorts each half, then merges in linear time.

You are given:
- unsorted integer array

👉 Your task:
sort the array ascending with merge sort.

--------------------------------------------------
🧠 1. INTUITION (DEEP UNDERSTANDING)
--------------------------------------------------

Core idea:
sorting becomes easier when array is broken into smaller subproblems.

Recursive invariant:
- each recursive call returns with subarray sorted
- merge step combines two sorted subarrays into one sorted segment

--------------------------------------------------
❌ 2. WRONG APPROACH (BEGINNER MISTAKE)
--------------------------------------------------

Mistake:
attempt to merge before both halves are fully sorted.

Why wrong:
merge logic assumes sorted inputs.
If either half is unsorted, final merged segment is not guaranteed sorted.

--------------------------------------------------
🧪 3. STEP-BY-STEP DRY RUN (REAL EXAMPLE)
--------------------------------------------------

Input: [38,27,43,3]

Split:
[38,27] and [43,3]

Sort left:
[38] + [27] -> merge -> [27,38]

Sort right:
[43] + [3] -> merge -> [3,43]

Final merge:
[27,38] + [3,43] -> [3,27,38,43]

--------------------------------------------------
📊 4. VISUAL REPRESENTATION
--------------------------------------------------

Recursion tree goes down during split,
and sorted values come up during merge.

--------------------------------------------------
⚙️ 5. APPROACHES
--------------------------------------------------

Top-down merge sort:
- Time: O(n log n)
- Space: O(n)

--------------------------------------------------
⚠️ 6. EDGE CASES
--------------------------------------------------

- empty array
- single element array
- duplicates (algorithm remains stable)

--------------------------------------------------
❌ 7. COMMON MISTAKES
--------------------------------------------------

- wrong copy-back boundaries after merge
- mid calculation mistakes
- forgetting to append leftover elements from one half

--------------------------------------------------
🎯 8. INTERVIEW INSIGHT
--------------------------------------------------

Merge sort is stable and predictable O(n log n).
It is preferred for external sorting due to sequential merge behavior.

--------------------------------------------------
🧾 9. CLEAN CODE
--------------------------------------------------
- Keep split and merge as separate helpers
- Reuse temp array to avoid repeated allocations
- Keep pointer roles (i,j,k) clear

--------------------------------------------------
🧪 10. MAIN METHOD TESTING
--------------------------------------------------
Format:
Input -> Expected -> Actual

--------------------------------------------------
📌 11. MINI SUMMARY
--------------------------------------------------

Merge sort combines two guarantees:
recursive decomposition + linear merge of sorted halves.

==================================================
*/
import java.util.*;

/*
Problem: Merge Sort
Divide-and-conquer stable sorting algorithm.
*/
public class MergeSort {
    // Top-down merge sort.
    // Time: O(n log n), Space: O(n)
    public static void mergeSort(int[] a) {
        int[] temp = new int[a.length];
        sort(a, 0, a.length - 1, temp);
    }

    private static void sort(int[] a, int l, int r, int[] temp) {
        if (l >= r) return;
        int m = l + (r - l) / 2;
        sort(a, l, m, temp);
        sort(a, m + 1, r, temp);
        merge(a, l, m, r, temp);
    }

    private static void merge(int[] a, int l, int m, int r, int[] temp) {
        int i = l, j = m + 1, k = l;
        while (i <= m && j <= r) temp[k++] = (a[i] <= a[j]) ? a[i++] : a[j++];
        while (i <= m) temp[k++] = a[i++];
        while (j <= r) temp[k++] = a[j++];
        for (int p = l; p <= r; p++) a[p] = temp[p];
    }

    public static void main(String[] args) {
        int[] a = {38,27,43,3,9,82,10};

        mergeSort(a);
        System.out.println("Input: [38, 27, 43, 3, 9, 82, 10]");
        System.out.println("Expected: [3, 9, 10, 27, 38, 43, 82] | Actual: " + Arrays.toString(a));
    }
}







