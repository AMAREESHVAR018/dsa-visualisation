package dsa.handbook;

/*
==================================================
📌 PROBLEM: Check Palindrome String
==================================================

Imagine validating a username, token, or phrase where symmetry matters.
A palindrome reads the same from left to right and right to left.

You are given:
- Input: a string s
- Output: true if palindrome, else false

👉 Your task:
Check mirrored characters and decide if the full string is symmetric.

--------------------------------------------------
🧠 1. INTUITION (DEEP UNDERSTANDING)
--------------------------------------------------

If a string is palindrome, then:
- first char must match last char
- second char must match second-last char
- and so on

So we only need to compare pairs from both ends.
The moment one pair mismatches, answer is false.

--------------------------------------------------
❌ 2. WRONG APPROACH (BEGINNER MISTAKE)
--------------------------------------------------

Common beginner mistake:
- only compare first and last character once

Example: "abca"
- first == last ('a' == 'a') looks fine
- but middle pair ('b' vs 'c') fails

So one outer check is not enough; all mirrored pairs must be validated.

--------------------------------------------------
🧪 3. STEP-BY-STEP DRY RUN (REAL EXAMPLE)
--------------------------------------------------

Case 1: s = "madam"
- l=0, r=4 -> 'm' == 'm' -> move inward
- l=1, r=3 -> 'a' == 'a' -> move inward
- l=2, r=2 -> center reached -> true

Case 2: s = "hello"
- l=0, r=4 -> 'h' != 'o' -> false immediately

--------------------------------------------------
📊 4. VISUAL REPRESENTATION
--------------------------------------------------

madam
^   ^
 l   r

Pointers keep converging toward the center.

--------------------------------------------------
⚙️ 5. APPROACHES
--------------------------------------------------

1. Brute: reverse string then compare
- Time: O(n)
- Space: O(n)

2. Optimal: two pointers
- Time: O(n)
- Space: O(1)

--------------------------------------------------
⚠️ 6. EDGE CASES
--------------------------------------------------

- empty string -> true
- single character -> true
- even length like "abba"
- first mismatch at outer boundary

--------------------------------------------------
❌ 7. COMMON MISTAKES
--------------------------------------------------

- forgetting to move both pointers
- using loop condition incorrectly
- ignoring immediate early return on mismatch

--------------------------------------------------
🎯 8. INTERVIEW INSIGHT
--------------------------------------------------

Typical follow-up:
- case-insensitive palindrome
- ignore spaces/punctuation

Core extension is same idea: normalize input, then apply two pointers.

--------------------------------------------------
🧾 9. CLEAN CODE
--------------------------------------------------
- Keep brute and optimal methods separate
- Use `l` and `r` consistently for readability
- Return early on mismatch to keep logic clear

--------------------------------------------------
🧪 10. MAIN METHOD TESTING
--------------------------------------------------
Format:
Input -> Expected -> Actual

Include both positive and negative cases.

--------------------------------------------------
📌 11. MINI SUMMARY
--------------------------------------------------

Compare mirrored pairs while moving inward.
If any pair mismatches, not palindrome.

==================================================
*/
public class CheckPalindromeString {
    // Brute: reverse string and compare.
    // Time: O(n), Space: O(n)
    public static boolean isPalindromeBrute(String s) {
        StringBuilder rev = new StringBuilder(s).reverse();
        return s.equals(rev.toString());
    }

    // Optimal two pointers.
    // Time: O(n), Space: O(1)
    public static boolean isPalindromeOptimal(String s) {
        int l = 0, r = s.length() - 1;
        while (l < r) {
            if (s.charAt(l) != s.charAt(r)) return false;
            l++;
            r--;
        }
        return true;
    }

    public static void main(String[] args) {
        String[] tests = {"madam", "racecar", "hello", "a", ""};
        boolean[] expected = {true, true, false, true, true};

        for (int i = 0; i < tests.length; i++) {
            String t = tests[i];
            boolean exp = expected[i];
            boolean brute = isPalindromeBrute(t);
            boolean optimal = isPalindromeOptimal(t);

            System.out.println("Input: \"" + t + "\"");
            System.out.println("Expected: " + exp + " | Brute: " + brute + " | Optimal: " + optimal);
        }
    }
}







