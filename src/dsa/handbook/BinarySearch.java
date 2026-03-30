package dsa.handbook;

/*
==================================================
📌 PROBLEM: Binary Search
==================================================

Imagine searching a word in a sorted dictionary.
You do not start from page 1; you jump to the middle and discard half repeatedly.

You are given:
- Input: sorted array `a[]`, target value
- Output: index of target, or -1 if missing

👉 Your task:
Use sorted order to shrink the search window fast.

--------------------------------------------------
🧠 1. INTUITION (DEEP UNDERSTANDING)
--------------------------------------------------

Key invariant:
If target exists, it is always inside current window [l..r].

At each step:
- pick middle index m
- compare a[m] with target
- keep only one half that can still contain target

This is why time drops from O(n) to O(log n).

--------------------------------------------------
❌ 2. WRONG APPROACH (BEGINNER MISTAKE)
--------------------------------------------------

Common mistake:
Use linear scan even though array is sorted.

Why it fails conceptually:
You ignore the strongest information in input: sorted order.
That wastes comparisons and misses the divide-and-conquer advantage.

--------------------------------------------------
🧪 3. STEP-BY-STEP DRY RUN (REAL EXAMPLE)
--------------------------------------------------

a = [1, 3, 5, 7, 9], target = 7

Start: l=0, r=4
- m=2, a[m]=5
- 5 < 7, so target must be right side
- update l=3

Now: l=3, r=4
- m=3, a[m]=7
- found at index 3

--------------------------------------------------
📊 4. VISUAL REPRESENTATION
--------------------------------------------------

[1, 3, 5, 7, 9]
 l     m     r

after eliminating left half:
[      7, 9]
    l r

--------------------------------------------------
⚙️ 5. APPROACHES
--------------------------------------------------

1. Brute force scan
- Time O(n), Space O(1)

2. Iterative binary search
- Time O(log n), Space O(1)

3. Recursive binary search
- Time O(log n), Space O(log n) due to call stack

--------------------------------------------------
⚠️ 6. EDGE CASES
--------------------------------------------------

- empty array
- target smaller than first or larger than last
- target absent in middle
- duplicates (simple search returns any valid index)

--------------------------------------------------
❌ 7. COMMON MISTAKES
--------------------------------------------------

- using wrong loop condition and missing last candidate
- wrong boundary updates causing infinite loop
- overflow-prone mid formula `(l + r) / 2` in other languages

--------------------------------------------------
🎯 8. INTERVIEW INSIGHT
--------------------------------------------------

Interviewers often ask variants:
- first occurrence
- last occurrence
- insertion position

All variants reuse same window invariant with adjusted conditions.

--------------------------------------------------
🧾 9. CLEAN CODE
--------------------------------------------------
- Keep brute, iterative, recursive methods separate
- Use same naming (`l`, `r`, `m`) consistently
- Keep boundary updates right next to comparison

--------------------------------------------------
🧪 10. MAIN METHOD TESTING
--------------------------------------------------
Format:
Input -> Expected -> Actual

--------------------------------------------------
📌 11. MINI SUMMARY
--------------------------------------------------

Binary search works by discarding half the invalid space every step.
Sorted order is the reason this elimination is always safe.

==================================================
*/
public class BinarySearch {
    // Brute Force linear scan.
    // Time: O(n), Space: O(1)
    public static int searchBrute(int[] a, int target) {
        for (int i = 0; i < a.length; i++) if (a[i] == target) return i;
        return -1;
    }

    // Iterative binary search.
    // Time: O(log n), Space: O(1)
    public static int searchIterative(int[] a, int target) {
        int l = 0, r = a.length - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (a[m] == target) return m;
            if (a[m] < target) l = m + 1;
            else r = m - 1;
        }
        return -1;
    }

    // Recursive binary search.
    // Time: O(log n), Space: O(log n)
    public static int searchRecursive(int[] a, int target) {
        return rec(a, 0, a.length - 1, target);
    }

    private static int rec(int[] a, int l, int r, int t) {
        if (l > r) return -1;
        int m = l + (r - l) / 2;
        if (a[m] == t) return m;
        return a[m] < t ? rec(a, m + 1, r, t) : rec(a, l, m - 1, t);
    }

    public static void main(String[] args) {
        int[] a = {1,3,5,7,9,11};
        int[] targets = {5,6,1,11,-1};
        int[] expected = {2,-1,0,5,-1};
        for (int i = 0; i < targets.length; i++) {
            int t = targets[i];
            int exp = expected[i];
            System.out.println("Input target=" + t);
            System.out.println("Expected: " + exp + " | Brute: " + searchBrute(a, t) + " | Iterative: " + searchIterative(a, t) + " | Recursive: " + searchRecursive(a, t));
        }
    }
}







