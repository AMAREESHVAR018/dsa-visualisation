package dsa.handbook;

/*
==================================================
📌 PROBLEM: Counting Sort
==================================================

Counting sort is useful when values are integers in a limited range.
Instead of comparing elements, it counts frequency of each value.

You are given:
- array of non-negative integers

👉 Your task:
Sort the array using frequency counting.

--------------------------------------------------
🧠 1. INTUITION (DEEP UNDERSTANDING)
--------------------------------------------------

For each value v, increment count[v].
Then rebuild output by writing each number i exactly count[i] times.

Core invariant:
count array fully captures multiset of input values.

--------------------------------------------------
❌ 2. WRONG APPROACH (BEGINNER MISTAKE)
--------------------------------------------------

Mistake:
Directly use counting sort without validating input domain.

Why it fails:
Negative numbers create invalid count indices.

Another pitfall:
Apply counting sort when max value k is huge,
which can waste memory.

--------------------------------------------------
🧪 3. STEP-BY-STEP DRY RUN (REAL EXAMPLE)
--------------------------------------------------

Input: [4,2,2,8,3,3,1]

max = 8
count after scan:
count[1]=1, count[2]=2, count[3]=2, count[4]=1, count[8]=1

Rebuild output:
- write 1 once
- write 2 twice
- write 3 twice
- write 4 once
- write 8 once

Result: [1,2,2,3,3,4,8]

--------------------------------------------------
📊 4. VISUAL REPRESENTATION
--------------------------------------------------

count index = value
count[value] = occurrences

It behaves like a histogram that we flatten in sorted order.

--------------------------------------------------
⚙️ 5. APPROACHES
--------------------------------------------------

1. Comparison sort fallback: O(n^2)
2. Counting sort (optimal for bounded k): O(n + k), space O(k)

--------------------------------------------------
⚠️ 6. EDGE CASES
--------------------------------------------------

- empty array
- all values same
- contains zero
- contains negative value (should reject)

--------------------------------------------------
❌ 7. COMMON MISTAKES
--------------------------------------------------

- forgetting domain check for negative numbers
- not handling empty array before max calculation
- using when k is too large relative to n

--------------------------------------------------
🎯 8. INTERVIEW INSIGHT
--------------------------------------------------

Interview follow-up:
How to make counting sort stable for key-value pairs?
Answer: use prefix sums and reverse placement.

--------------------------------------------------
🧾 9. CLEAN CODE
--------------------------------------------------
- Keep brute and counting methods separate
- Validate constraints early
- Keep count build and output build in clear phases

--------------------------------------------------
🧪 10. MAIN METHOD TESTING
--------------------------------------------------
Format:
Input -> Expected -> Actual

--------------------------------------------------
📌 11. MINI SUMMARY
--------------------------------------------------

Counting sort trades comparisons for frequency memory.
It is excellent when value range is small and known.

==================================================
*/
import java.util.*;

/*
Problem: Counting Sort
Sort non-negative integers with limited range.
*/
public class CountingSort {
    // Brute comparison sort fallback.
    // Time: O(n^2), Space: O(1)
    public static void bruteSort(int[] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < a[i]) {
                    int t = a[i]; a[i] = a[j]; a[j] = t;
                }
            }
        }
    }

    // Optimal counting sort for non-negative values.
    // Time: O(n + k), Space: O(k)
    public static int[] countingSort(int[] a) {
        if (a.length == 0) return new int[0];
        int max = 0;
        for (int v : a) {
            if (v < 0) throw new IllegalArgumentException("Counting sort needs non-negative integers.");
            max = Math.max(max, v);
        }
        int[] cnt = new int[max + 1];
        for (int v : a) cnt[v]++;
        int idx = 0;
        int[] out = new int[a.length];
        for (int i = 0; i <= max; i++) {
            while (cnt[i]-- > 0) out[idx++] = i;
        }
        return out;
    }

    public static void main(String[] args) {
        int[] a = {4,2,2,8,3,3,1};

        int[] b = a.clone();
        bruteSort(b);
        System.out.println("Input: [4, 2, 2, 8, 3, 3, 1]");
        System.out.println("Expected: [1, 2, 2, 3, 3, 4, 8] | Brute: " + Arrays.toString(b));
        System.out.println("Expected: [1, 2, 2, 3, 3, 4, 8] | Counting: " + Arrays.toString(countingSort(a)));
    }
}







