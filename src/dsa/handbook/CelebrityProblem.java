package dsa.handbook;

/*
==================================================
📌 PROBLEM: Celebrity Problem
==================================================

Imagine a party with N people.

A "celebrity" is defined as:
✔ Everyone knows them
❌ They know nobody

You are given a matrix M:
- M[a][b] = 1 → Person 'a' knows person 'b'
- M[a][b] = 0 → Person 'a' does NOT know person 'b'

👉 Your task:
Find the index of the celebrity.
If no celebrity exists, return -1.

--------------------------------------------------
🧠 1. INTUITION (DEEP UNDERSTANDING)
--------------------------------------------------

Let’s think logically:

👉 If A knows B → A CANNOT be celebrity  
(because celebrity knows nobody)

👉 If A does NOT know B → B CANNOT be celebrity  
(because celebrity must be known by everyone)

🔥 This gives a powerful elimination rule:
👉 In every comparison, we eliminate ONE person

So instead of checking everyone fully,
we can reduce the problem step by step.

--------------------------------------------------
❌ 2. WRONG APPROACH (BEGINNER MISTAKE)
--------------------------------------------------

A beginner might think:

👉 “Let me check every person fully”

For each person:
- Check row → they know nobody
- Check column → everyone knows them

This works BUT:
❌ Time Complexity = O(n²)
❌ Repeats unnecessary checks

We can do better.

--------------------------------------------------
🧪 3. STEP-BY-STEP DRY RUN (REAL EXAMPLE)
--------------------------------------------------

Matrix:

    0 1 2
0 [ 0 1 1 ]
1 [ 0 0 1 ]
2 [ 0 0 0 ]

Expected Answer → 2

----------------------------------
Step 1: Candidate Selection
----------------------------------

Start:
c = 0

i = 1:
M[0][1] = 1 → 0 knows 1  
👉 So 0 cannot be celebrity  
👉 Update: c = 1

i = 2:
M[1][2] = 1 → 1 knows 2  
👉 So 1 cannot be celebrity  
👉 Update: c = 2

Now candidate = 2

----------------------------------
Step 2: Verification
----------------------------------

Check:
- Row of 2 → should be all 0
- Column of 2 → should be all 1 (except self)

Row:
M[2][0] = 0  
M[2][1] = 0 ✔

Column:
M[0][2] = 1  
M[1][2] = 1 ✔

✅ Person 2 is celebrity

--------------------------------------------------
📊 4. VISUAL REPRESENTATION
--------------------------------------------------

Think like this:

Each comparison removes one person:

0 vs 1 → remove 0  
1 vs 2 → remove 1  

👉 Only ONE remains → possible celebrity

--------------------------------------------------
⚙️ 5. APPROACHES
--------------------------------------------------

----------------------------------
🔹 APPROACH 1: Brute Force
----------------------------------

Check every person:
- Do they know nobody?
- Does everyone know them?

Time Complexity: O(n²)
Space: O(1)

----------------------------------
🔹 APPROACH 2: Optimal (Elimination)
----------------------------------

Step 1: Find candidate in O(n)
Step 2: Verify candidate in O(n)

Total: O(n)

👉 WHY IT WORKS:
Each comparison removes exactly ONE non-celebrity.

--------------------------------------------------
⚠️ 6. EDGE CASES
--------------------------------------------------

- n = 1 → that person is celebrity
- No celebrity → return -1
- Everyone knows everyone → no celebrity
- All zeros → no celebrity

--------------------------------------------------
❌ 7. COMMON MISTAKES
--------------------------------------------------

❌ Forgetting verification step  
❌ Mixing row and column logic  
❌ Checking self (i == c)  
❌ Assuming candidate is always valid  

--------------------------------------------------
🎯 8. INTERVIEW INSIGHT
--------------------------------------------------

Interviewer expects:

✔ Can you reduce O(n²) → O(n)?  
✔ Can you explain elimination logic clearly?  
✔ Do you verify final candidate?  

Follow-up:
👉 What if matrix is not given explicitly?

--------------------------------------------------
🧾 9. CLEAN CODE
--------------------------------------------------
- Clear variable names
- Separate logic into methods
- Comments explain WHY, not WHAT

--------------------------------------------------
🧪 10. MAIN METHOD TESTING
--------------------------------------------------
Format:
Input → Expected → Actual

--------------------------------------------------
📌 11. MINI SUMMARY
--------------------------------------------------

Core Idea:
👉 Use elimination to reduce candidates

Key Trick:
👉 If A knows B → eliminate A  
👉 Else → eliminate B  

Final Step:
👉 Always verify the last candidate

==================================================
*/

public class CelebrityProblem {

    // ----------------------------------
    // 🔹 BRUTE FORCE APPROACH
    // ----------------------------------
    public static int findCelebrityBrute(int[][] M) {
        int n = M.length;

        for (int c = 0; c < n; c++) {
            boolean isCelebrity = true;

            for (int i = 0; i < n; i++) {
                if (i == c) continue;

                // If candidate knows someone OR someone doesn't know candidate
                if (M[c][i] == 1 || M[i][c] == 0) {
                    isCelebrity = false;
                    break;
                }
            }

            if (isCelebrity) return c;
        }

        return -1;
    }

    // ----------------------------------
    // 🔹 OPTIMAL APPROACH (ELIMINATION)
    // ----------------------------------
    public static int findCelebrityOptimal(int[][] M) {
        int n = M.length;

        // Step 1: Find candidate
        int candidate = 0;

        for (int i = 1; i < n; i++) {
            if (M[candidate][i] == 1) {
                // candidate knows i → eliminate candidate
                candidate = i;
            }
            // else i is eliminated automatically
        }

        // Step 2: Verify candidate
        for (int i = 0; i < n; i++) {
            if (i == candidate) continue;

            if (M[candidate][i] == 1 || M[i][candidate] == 0) {
                return -1;
            }
        }

        return candidate;
    }

    // ----------------------------------
    // 🔹 MAIN METHOD (TESTING)
    // ----------------------------------
    public static void main(String[] args) {

        int[][] m1 = {
                {0, 1, 1},
                {0, 0, 1},
                {0, 0, 0}
        }; // Expected: 2

        int[][] m2 = {
                {0, 1},
                {1, 0}
        }; // Expected: -1

        System.out.println("Test Case 1:");
        System.out.println("Expected: 2");
        System.out.println("Brute: " + findCelebrityBrute(m1));
        System.out.println("Optimal: " + findCelebrityOptimal(m1));

        System.out.println("\nTest Case 2:");
        System.out.println("Expected: -1");
        System.out.println("Brute: " + findCelebrityBrute(m2));
        System.out.println("Optimal: " + findCelebrityOptimal(m2));
    }
}


