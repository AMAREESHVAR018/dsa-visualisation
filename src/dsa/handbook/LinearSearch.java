package dsa.handbook;

/*
==================================================
📌 PROBLEM: Linear Search
==================================================

Linear search is the baseline search technique.
It is the correct default whenever no ordering guarantee is given.

You are given:
- array a[]
- target value

👉 Your task:
Return the first index containing target, else -1.

--------------------------------------------------
🧠 1. INTUITION (DEEP UNDERSTANDING)
--------------------------------------------------

No ordering guarantee means we cannot safely skip any position.
So we scan sequentially until:
- target found -> return immediately
- array ends -> return -1

--------------------------------------------------
❌ 2. WRONG APPROACH (BEGINNER MISTAKE)
--------------------------------------------------

Mistake:
Try to force a faster method (binary search) on unsorted data.

Why wrong:
Binary search's half-elimination proof requires sorted order.
Without that, discarded half may still contain target.

--------------------------------------------------
🧪 3. STEP-BY-STEP DRY RUN (REAL EXAMPLE)
--------------------------------------------------

a=[4,2,7,1], target=7
- i=0 -> a[0]=4, not match
- i=1 -> a[1]=2, not match
- i=2 -> a[2]=7, match -> return 2

--------------------------------------------------
📊 4. VISUAL REPRESENTATION
--------------------------------------------------

Pointer sweep:
index: 0 -> 1 -> 2 -> stop

--------------------------------------------------
⚙️ 5. APPROACHES
--------------------------------------------------

1. Direct linear scan
- Time: O(n)
- Space: O(1)

2. Repeated-query optimization with hash map
- Build: O(n)
- Query: O(1) average
- Extra Space: O(n)

--------------------------------------------------
⚠️ 6. EDGE CASES
--------------------------------------------------

- empty array
- duplicates (return first occurrence)
- target absent

--------------------------------------------------
❌ 7. COMMON MISTAKES
--------------------------------------------------

- forgetting early return on first match
- returning last duplicate index by mistake
- null-array handling in custom wrappers

--------------------------------------------------
🎯 8. INTERVIEW INSIGHT
--------------------------------------------------

Interview follow-up:
If many searches are performed, discuss preprocessing tradeoff with hash map.

--------------------------------------------------
🧾 9. CLEAN CODE
--------------------------------------------------
- Keep single-query and multi-query methods separate
- Name index map intent clearly
- Keep first-occurrence behavior explicit

--------------------------------------------------
🧪 10. MAIN METHOD TESTING
--------------------------------------------------
Format:
Input -> Expected -> Actual

--------------------------------------------------
📌 11. MINI SUMMARY
--------------------------------------------------

Linear search is simple and always correct.
When data is unsorted and query count is low, it is usually the right tool.

==================================================
*/
import java.util.*;

/*
Problem: Linear Search in array
Input: [4,2,7,1], target=7
Output: 2
*/
public class LinearSearch {
    // Brute/Optimal (for unsorted arrays): linear scan.
    // Time: O(n), Space: O(1)
    public static int search(int[] a, int target) {
        for (int i = 0; i < a.length; i++) if (a[i] == target) return i;
        return -1;
    }

    // Better for multiple queries: preprocess with hashmap.
    // Build: O(n), Query: O(1)
    public static Map<Integer, Integer> buildIndex(int[] a) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < a.length; i++) map.putIfAbsent(a[i], i);
        return map;
    }

    public static void main(String[] args) {
        int[] a = {4,2,7,1,7};

        System.out.println("Input: [4,2,7,1,7], target=7");
        System.out.println("Expected: 2 | Actual: " + search(a, 7));
        System.out.println("Input: [4,2,7,1,7], target=3");
        System.out.println("Expected: -1 | Actual: " + search(a, 3));
        Map<Integer, Integer> idx = buildIndex(a);
        System.out.println("Index map query value=1 -> Expected: 3 | Actual: " + idx.getOrDefault(1, -1));
        System.out.println("Index map query value=9 -> Expected: -1 | Actual: " + idx.getOrDefault(9, -1));
    }
}







