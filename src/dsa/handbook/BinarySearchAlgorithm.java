package dsa.handbook;

/*
==================================================
📌 PROBLEM: Binary Search Algorithm Variants
==================================================

This file teaches one problem through three styles:
brute force, iterative binary search, and recursive binary search.

You are given:
- sorted array a[]
- target value

👉 Your task:
Return index of target or -1, while understanding how each approach differs.

--------------------------------------------------
🧠 1. INTUITION (DEEP UNDERSTANDING)
--------------------------------------------------

All binary-search variants rely on one invariant:
If target exists, it must lie inside the current interval [l..r].

After checking middle m:
- if a[m] < target, discard left half
- else discard right half

Iterative and recursive code look different,
but both preserve the same invariant.

--------------------------------------------------
❌ 2. WRONG APPROACH (BEGINNER MISTAKE)
--------------------------------------------------

Mistake 1:
Write recursive binary search without base case `l > r`.
Result: infinite recursion or stack overflow.

Mistake 2:
Update wrong boundary (e.g., `l = m` instead of `l = m + 1`).
Result: stuck window and infinite loop.

--------------------------------------------------
🧪 3. STEP-BY-STEP DRY RUN (REAL EXAMPLE)
--------------------------------------------------

a = [1,3,5,7,9,11], target = 11

Recursive path:
- [0..5], m=2, a[m]=5 < 11 -> search [3..5]
- [3..5], m=4, a[m]=9 < 11 -> search [5..5]
- [5..5], m=5, a[m]=11 -> found index 5

Iterative version performs the same interval shrinking without call stack.

--------------------------------------------------
📊 4. VISUAL REPRESENTATION
--------------------------------------------------

[1,3,5,7,9,11]
    [0..5] -> [3..5] -> [5..5]

Recursive calls mirror these same windows.

--------------------------------------------------
⚙️ 5. APPROACHES
--------------------------------------------------

1. Brute linear scan: O(n), O(1)
2. Iterative binary search: O(log n), O(1)
3. Recursive binary search: O(log n), O(log n) stack

--------------------------------------------------
⚠️ 6. EDGE CASES
--------------------------------------------------

- target at first index
- target at last index
- target absent
- empty array

--------------------------------------------------
❌ 7. COMMON MISTAKES
--------------------------------------------------

- mixing inclusive and exclusive bounds
- missing base condition in recursion
- calculating mid incorrectly in some languages

--------------------------------------------------
🎯 8. INTERVIEW INSIGHT
--------------------------------------------------

Interviewers often ask:
- Which version would you ship and why?
- How to modify for first/last occurrence?

Best answer usually: iterative for production simplicity, recursion for teaching.

--------------------------------------------------
🧾 9. CLEAN CODE
--------------------------------------------------
- Keep each approach in its own method
- Use consistent variable names across methods
- Keep recurrence relation compact and readable

--------------------------------------------------
🧪 10. MAIN METHOD TESTING
--------------------------------------------------
Format:
Input -> Expected -> Actual

--------------------------------------------------
📌 11. MINI SUMMARY
--------------------------------------------------

Different coding styles, same core invariant.
Mastering the invariant is more important than memorizing syntax.

==================================================
*/
public class BinarySearchAlgorithm {
    // Brute: linear search.
    // Time: O(n), Space: O(1)
    public static int brute(int[] a, int target) {
        for (int i = 0; i < a.length; i++) if (a[i] == target) return i;
        return -1;
    }

    // Iterative binary search.
    // Time: O(log n), Space: O(1)
    public static int iterative(int[] a, int target) {
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
    public static int recursive(int[] a, int target) {
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
        int[] targets = {7, 2, 11, 1, 13};
        int[] expected = {3, -1, 5, 0, -1};

        for (int i = 0; i < targets.length; i++) {
            int t = targets[i];
            int exp = expected[i];
            System.out.println("Input target=" + t);
            System.out.println("Expected: " + exp + " | Brute: " + brute(a, t) + " | Iterative: " + iterative(a, t) + " | Recursive: " + recursive(a, t));
        }
    }
}







