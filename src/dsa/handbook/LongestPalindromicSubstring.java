package dsa.handbook;

/*
==================================================
📌 PROBLEM: Longest Palindromic Substring
==================================================

A palindrome reads same left-to-right and right-to-left.
Need longest contiguous palindrome inside given string.

You are given:
- string s

👉 Your task:
return the longest palindromic substring.

--------------------------------------------------
🧠 1. INTUITION (DEEP UNDERSTANDING)
--------------------------------------------------

Every palindrome is centered around:
- one character (odd length)
- or gap between two characters (even length)

So for every index i:
expand from (i,i) and (i,i+1),
then keep the longest expansion.

--------------------------------------------------
❌ 2. WRONG APPROACH (BEGINNER MISTAKE)
--------------------------------------------------

Beginner approach:
check every substring and test palindrome.

Why costly:
O(n^2) substrings and O(n) check each => O(n^3).

--------------------------------------------------
🧪 3. STEP-BY-STEP DRY RUN (REAL EXAMPLE)
--------------------------------------------------

s = "cbbd"

i=0:
- odd center (0,0) => "c"
- even center (0,1) => mismatch

i=1:
- odd center (1,1) => "b"
- even center (1,2) => "bb" (best now)

i=2:
- odd center (2,2) => "b"
- even center (2,3) => mismatch

i=3:
- odd center (3,3) => "d"

Final answer: "bb"

--------------------------------------------------
📊 4. VISUAL REPRESENTATION
--------------------------------------------------

Index map:
0:c 1:b 2:b 3:d

Best center is between 1 and 2.

--------------------------------------------------
⚙️ 5. APPROACHES
--------------------------------------------------

1. Brute all substrings: O(n^3)
2. Expand around center: O(n^2), O(1) extra space

--------------------------------------------------
⚠️ 6. EDGE CASES
--------------------------------------------------

- empty string => ""
- single char => itself
- all same chars => full string
- multiple answers with same max length (either valid)

--------------------------------------------------
❌ 7. COMMON MISTAKES
--------------------------------------------------

- forgetting even-length centers
- off-by-one while extracting substring
- updating best range with wrong length formula

--------------------------------------------------
🎯 8. INTERVIEW INSIGHT
--------------------------------------------------

Follow-up:
Can this be solved in O(n)?
Expected mention: Manacher's algorithm.

--------------------------------------------------
🧾 9. CLEAN CODE
--------------------------------------------------
- Keep brute and center approaches separate
- Keep center expansion in dedicated helper
- Maintain best as [start,end] window

--------------------------------------------------
🧪 10. MAIN METHOD TESTING
--------------------------------------------------
Format:
Input -> Expected -> Actual

--------------------------------------------------
📌 11. MINI SUMMARY
--------------------------------------------------

Center expansion is the most practical interview solution:
easy to reason about, significantly faster than brute force.

==================================================
*/
public class LongestPalindromicSubstring {
    // Brute Force: check all substrings.
    // Time: O(n^3), Space: O(1)
    public static String longestPalindromeBrute(String s) {
        if (s == null || s.isEmpty()) return "";
        String best = s.substring(0, 1);
        for (int i = 0; i < s.length(); i++) {
            for (int j = i; j < s.length(); j++) {
                if (isPalindrome(s, i, j) && (j - i + 1) > best.length()) {
                    best = s.substring(i, j + 1);
                }
            }
        }
        return best;
    }

    // Better/Optimal: expand around center.
    // Time: O(n^2), Space: O(1)
    public static String longestPalindromeCenter(String s) {
        if (s == null || s.isEmpty()) return "";
        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            int len1 = expand(s, i, i);
            int len2 = expand(s, i, i + 1);
            int len = Math.max(len1, len2);
            if (len > end - start + 1) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        return s.substring(start, end + 1);
    }

    private static boolean isPalindrome(String s, int l, int r) {
        while (l < r) {
            if (s.charAt(l++) != s.charAt(r--)) return false;
        }
        return true;
    }

    private static int expand(String s, int l, int r) {
        while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
            l--; r++;
        }
        return r - l - 1;
    }

    public static void main(String[] args) {
        String[] tests = {"babad", "cbbd", "a", "", "forgeeksskeegfor"};
        String[] expected = {"bab or aba", "bb", "a", "", "geeksskeeg"};
        for (int i = 0; i < tests.length; i++) {
            String t = tests[i];
            System.out.println("Input: " + t);
            System.out.println("Expected: " + expected[i] + " | Brute: " + longestPalindromeBrute(t));
            System.out.println("Expected: " + expected[i] + " | Center: " + longestPalindromeCenter(t));
            System.out.println();
        }
    }
}








