package dsa.handbook;

/*
==================================================
📌 PROBLEM: Rectangle Overlap
==================================================

Two axis-aligned rectangles overlap in positive area
only when overlap exists on both x-axis and y-axis projections.

You are given:
- rectangle a as [x1,y1,x2,y2]
- rectangle b as [x1,y1,x2,y2]

👉 Your task:
return true only when intersection area is strictly positive.

--------------------------------------------------
🧠 1. INTUITION (DEEP UNDERSTANDING)
--------------------------------------------------

Reduce 2D problem to two 1D interval checks:
- x intervals overlap with positive width
- y intervals overlap with positive height

Both must be true.

--------------------------------------------------
❌ 2. WRONG APPROACH (BEGINNER MISTAKE)
--------------------------------------------------

Mistake:
check only whether a corner of one rectangle lies inside the other.

Why wrong:
rectangles can overlap without containing each other's corners.
Edge-only touches also need strict handling.

--------------------------------------------------
🧪 3. STEP-BY-STEP DRY RUN (REAL EXAMPLE)
--------------------------------------------------

r1=[0,0,2,2], r2=[1,1,3,3]
x overlap: (1,2) width>0, y overlap: (1,2) height>0 -> true

r1=[0,0,2,2], r3=[2,2,4,4]
x overlap width=0, y overlap height=0 -> false

--------------------------------------------------
📊 4. VISUAL REPRESENTATION
--------------------------------------------------

Project both rectangles on x-axis and y-axis separately.
Positive overlap on both axes implies area overlap.

--------------------------------------------------
⚙️ 5. APPROACHES
--------------------------------------------------

Constant-time interval logic: O(1), O(1)

--------------------------------------------------
⚠️ 6. EDGE CASES
--------------------------------------------------

- touching edge or corner only -> false
- negative coordinates
- one rectangle fully inside another -> true

--------------------------------------------------
❌ 7. COMMON MISTAKES
--------------------------------------------------

- using non-strict comparisons and accidentally counting edge-touch as overlap
- mixing x and y indices in rectangle arrays

--------------------------------------------------
🎯 8. INTERVIEW INSIGHT
--------------------------------------------------

Natural extension:
compute overlap area with
max(0, min(x2)-max(x1)) * max(0, min(y2)-max(y1)).

--------------------------------------------------
🧾 9. CLEAN CODE
--------------------------------------------------
- Keep both formulations (intersection-size and separation test)
- Use strict inequalities for positive area
- Keep rectangle coordinate conventions consistent

--------------------------------------------------
🧪 10. MAIN METHOD TESTING
--------------------------------------------------
Format:
Input -> Expected -> Actual

--------------------------------------------------
📌 11. MINI SUMMARY
--------------------------------------------------

Rectangle overlap is interval overlap on both axes.
Strictly positive width and height are required.

==================================================
*/
public class RectangleOverlap {
    // Brute-like coordinate check.
    // Time: O(1), Space: O(1)
    public static boolean isOverlapBrute(int[] a, int[] b) {
        int left = Math.max(a[0], b[0]);
        int right = Math.min(a[2], b[2]);
        int bottom = Math.max(a[1], b[1]);
        int top = Math.min(a[3], b[3]);
        return right > left && top > bottom;
    }

    // Optimal separation logic.
    // Time: O(1), Space: O(1)
    public static boolean isOverlapOptimal(int[] a, int[] b) {
        if (a[2] <= b[0] || b[2] <= a[0]) return false;
        if (a[3] <= b[1] || b[3] <= a[1]) return false;
        return true;
    }

    public static void main(String[] args) {
        int[] r1 = {0,0,2,2}, r2 = {1,1,3,3}, r3 = {2,2,4,4};
        System.out.println("r1 vs r2 Expected: true | Brute: " + isOverlapBrute(r1, r2) + " | Optimal: " + isOverlapOptimal(r1, r2));
        System.out.println("r1 vs r3 Expected: false | Brute: " + isOverlapBrute(r1, r3) + " | Optimal: " + isOverlapOptimal(r1, r3));
    }
}







