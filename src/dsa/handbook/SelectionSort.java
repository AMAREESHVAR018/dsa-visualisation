package dsa.handbook;

/*
==================================================
📌 PROBLEM: Selection Sort
==================================================

Selection sort fixes one position per pass.
For index i, it selects minimum from suffix i..n-1 and places it at i.

You are given:
- unsorted array

👉 Your task:
sort in ascending order by repeated minimum selection.

--------------------------------------------------
🧠 1. INTUITION (DEEP UNDERSTANDING)
--------------------------------------------------

Invariant after pass i:
prefix 0..i is sorted and contains smallest i+1 values.

Each pass:
scan suffix for minIdx, then perform one swap.

--------------------------------------------------
❌ 2. WRONG APPROACH (BEGINNER MISTAKE)
--------------------------------------------------

Mistake:
swap every time a smaller value is found during scan.

Why weak:
it still sorts but does unnecessary writes.
Selection sort is valued for at most one swap per pass.

--------------------------------------------------
🧪 3. STEP-BY-STEP DRY RUN (REAL EXAMPLE)
--------------------------------------------------

arr=[64,25,12,22,11]
i=0 -> min=11 at idx4 -> [11,25,12,22,64]
i=1 -> min=12 at idx2 -> [11,12,25,22,64]
i=2 -> min=22 at idx3 -> [11,12,22,25,64]

--------------------------------------------------
📊 4. VISUAL REPRESENTATION
--------------------------------------------------

Visual split:
left side sorted prefix, right side unsorted suffix.

--------------------------------------------------
⚙️ 5. APPROACHES
--------------------------------------------------

Selection sort complexity:
- Time: O(n^2) in all cases
- Space: O(1)

--------------------------------------------------
⚠️ 6. EDGE CASES
--------------------------------------------------

- empty array
- single element
- duplicates
- already sorted input

--------------------------------------------------
❌ 7. COMMON MISTAKES
--------------------------------------------------

- not resetting minIdx at each outer loop
- looping j from 0 instead of i+1
- unnecessary self-swap confusion (harmless but noisy)

--------------------------------------------------
🎯 8. INTERVIEW INSIGHT
--------------------------------------------------

Interview insight:
Selection sort has fewer swaps than bubble sort,
which can matter when write cost is high.

--------------------------------------------------
🧾 9. CLEAN CODE
--------------------------------------------------
- Keep scan and swap logic compact
- Use descriptive min index variable
- Keep demonstration input small and readable

--------------------------------------------------
🧪 10. MAIN METHOD TESTING
--------------------------------------------------
Format:
Input -> Expected -> Actual

--------------------------------------------------
📌 11. MINI SUMMARY
--------------------------------------------------

Selection sort is simple:
find minimum in remaining part and place it once.

==================================================
*/
import java.util.*;

/*
Problem: Selection Sort
Repeatedly place minimum element at current index.
*/
public class SelectionSort {
    // Standard selection sort (brute/optimal for this strategy).
    // Time: O(n^2), Space: O(1)
    public static void selectionSort(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < a[minIdx]) minIdx = j;
            }
            int t = a[i]; a[i] = a[minIdx]; a[minIdx] = t;
        }
    }

    public static void main(String[] args) {
        int[] a = {64,25,12,22,11};

        selectionSort(a);
        System.out.println("Input: [64, 25, 12, 22, 11]");
        System.out.println("Expected: [11, 12, 22, 25, 64] | Actual: " + Arrays.toString(a));
    }
}







