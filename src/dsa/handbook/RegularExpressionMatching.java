package dsa.handbook;

/*
==================================================
📌 PROBLEM: Regular Expression Matching
==================================================

Implement a tiny regex matcher with only two special tokens:
.  matches any single character
*  means zero or more of the previous token

You are given:
- text s
- pattern p

👉 Your task:
return whether full string matches full pattern.

--------------------------------------------------
🧠 1. INTUITION (DEEP UNDERSTANDING)
--------------------------------------------------

State meaning:
match(i,j) => does s[i:] match p[j:]?

If next pattern char is '*', we have two branches:
- skip token+* pair: match(i, j+2)
- consume one char (if first matches): match(i+1, j)

--------------------------------------------------
❌ 2. WRONG APPROACH (BEGINNER MISTAKE)
--------------------------------------------------

Mistake:
greedy-only handling for '*'.

Why wrong:
correct solution needs branching/backtracking or DP over states.
Single greedy path misses valid alternatives.

--------------------------------------------------
🧪 3. STEP-BY-STEP DRY RUN (REAL EXAMPLE)
--------------------------------------------------

s="aab", p="c*a*b"

match(0,0): c* can be skipped -> match(0,2)
at a*: consume 'a' -> match(1,2)
consume 'a' -> match(2,2)
skip a* -> match(2,4)
match 'b' with 'b' -> true

--------------------------------------------------
📊 4. VISUAL REPRESENTATION
--------------------------------------------------

DP table dp[i][j] means s[i:] matches p[j:].
Fill from back to front so dependencies are already computed.

--------------------------------------------------
⚙️ 5. APPROACHES
--------------------------------------------------

1. Plain recursion: exponential worst case
2. Bottom-up DP: O(m*n) time, O(m*n) space

--------------------------------------------------
⚠️ 6. EDGE CASES
--------------------------------------------------

- empty string with pattern like a*b*c* should match
- empty pattern only matches empty string
- patterns with trailing * groups

--------------------------------------------------
❌ 7. COMMON MISTAKES
--------------------------------------------------

- treating '*' as standalone wildcard
- forgetting that '*' applies to previous token only
- not handling j+1 bounds safely

--------------------------------------------------
🎯 8. INTERVIEW INSIGHT
--------------------------------------------------

Interviewers often ask for exact transition equations.
Be ready to explain skip branch and consume branch clearly.

--------------------------------------------------
🧾 9. CLEAN CODE
--------------------------------------------------
- Keep brute recursion for concept
- Keep DP method for performance
- Use clear first-match boolean in both approaches

--------------------------------------------------
🧪 10. MAIN METHOD TESTING
--------------------------------------------------
Format:
Input -> Expected -> Actual

--------------------------------------------------
📌 11. MINI SUMMARY
--------------------------------------------------

Regex matching becomes manageable once modeled as (i,j) states.
DP avoids repeated recomputation from recursive branching.

==================================================
*/
public class RegularExpressionMatching {
    // Brute Force recursion.
    // Time: Exponential, Space: O(m+n) recursion stack.
    public static boolean isMatchBrute(String s, String p) {
        return dfs(s, p, 0, 0);
    }

    private static boolean dfs(String s, String p, int i, int j) {
        if (j == p.length()) return i == s.length();
        boolean first = i < s.length() && (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.');
        if (j + 1 < p.length() && p.charAt(j + 1) == '*') {
            return dfs(s, p, i, j + 2) || (first && dfs(s, p, i + 1, j));
        }
        return first && dfs(s, p, i + 1, j + 1);
    }

    // Optimal DP (bottom-up).
    // Time: O(m*n), Space: O(m*n)
    public static boolean isMatchDP(String s, String p) {
        int m = s.length(), n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[m][n] = true;
        for (int j = n - 2; j >= 0; j--) {
            if (p.charAt(j + 1) == '*') dp[m][j] = dp[m][j + 2];
        }
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                boolean first = s.charAt(i) == p.charAt(j) || p.charAt(j) == '.';
                if (j + 1 < n && p.charAt(j + 1) == '*') {
                    dp[i][j] = dp[i][j + 2] || (first && dp[i + 1][j]);
                } else {
                    dp[i][j] = first && dp[i + 1][j + 1];
                }
            }
        }
        return dp[0][0];
    }

    public static void main(String[] args) {
        String[][] tests = {
            {"aa","a"},
            {"aa","a*"},
            {"ab",".*"},
            {"aab","c*a*b"},
            {"mississippi","mis*is*p*."}
        };
        boolean[] expected = {false, true, true, true, false};

        for (int i = 0; i < tests.length; i++) {
            String s = tests[i][0], p = tests[i][1];
            System.out.println("Input: s=\"" + s + "\", p=\"" + p + "\"");
            System.out.println("Expected: " + expected[i] + " | Brute: " + isMatchBrute(s, p) + " | DP: " + isMatchDP(s, p));
            System.out.println();
        }
    }
}







