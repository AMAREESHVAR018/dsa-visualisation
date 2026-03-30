package dsa_handbook.chatgpt.organizedtopics;

/*
 * Topic: String Problems
 * Description: String algorithms solve matching, parsing, and transformation tasks.
 * Real-world usage: Input validation, text formatting, and search features.
 */
public class StringProblems {

    // Problem 1: Longest Palindromic Substring (Brute Force)
    // Explanation: Check every substring and keep the longest palindrome.
    // Approach: Generate all substrings; validate palindrome with two pointers.
    // Time Complexity: O(n^3)
    // Space Complexity: O(1)
    public static String longestPalindromeBruteForce(String s) {
        String best = "";
        for (int i = 0; i < s.length(); i++) {
            for (int j = i; j < s.length(); j++) {
                String sub = s.substring(i, j + 1);
                if (isPalindromeSimple(sub) && sub.length() > best.length()) {
                    best = sub;
                }
            }
        }
        return best;
    }

    // Problem 2: Longest Palindromic Substring (Expand Around Center)
    // Explanation: Expand from each character (odd) and between characters (even).
    // Approach: For each index, compute max palindrome length by expansion.
    // Time Complexity: O(n^2)
    // Space Complexity: O(1)
    public static String longestPalindromeCenter(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }
        int start = 0;
        int end = 0;

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

    // Problem 3: Longest Palindromic Substring (DP)
    // Explanation: dp[i][j] is true if substring i..j is palindrome.
    // Approach: Fill DP by increasing gap and track longest true window.
    // Time Complexity: O(n^2)
    // Space Complexity: O(n^2)
    public static String longestPalindromeDP(String s) {
        int n = s.length();
        if (n == 0) {
            return "";
        }

        boolean[][] dp = new boolean[n][n];
        int bestStart = 0;
        int bestLen = 1;

        for (int gap = 0; gap < n; gap++) {
            for (int i = 0, j = gap; j < n; i++, j++) {
                switch (gap) {
                    case 0:
                        dp[i][j] = true;
                        break;
                    case 1:
                        dp[i][j] = s.charAt(i) == s.charAt(j);
                        break;
                    default:
                        dp[i][j] = s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1];
                        break;
                }

                if (dp[i][j] && gap + 1 > bestLen) {
                    bestStart = i;
                    bestLen = gap + 1;
                }
            }
        }

        return s.substring(bestStart, bestStart + bestLen);
    }

    // Problem 4: Regular Expression Matching (Recursion)
    // Explanation: Match with support for '.' and '*'.
    // Approach: Try zero occurrence for '*', or consume one if first matches.
    // Time Complexity: Exponential worst case
    // Space Complexity: O(m + n)
    public static boolean regexMatchRecursive(String s, String p) {
        if (p.isEmpty()) {
            return s.isEmpty();
        }

        boolean firstMatch = !s.isEmpty() && (s.charAt(0) == p.charAt(0) || p.charAt(0) == '.');

        if (p.length() >= 2 && p.charAt(1) == '*') {
            return regexMatchRecursive(s, p.substring(2))
                    || (firstMatch && regexMatchRecursive(s.substring(1), p));
        }

        return firstMatch && regexMatchRecursive(s.substring(1), p.substring(1));
    }

    // Problem 5: Regular Expression Matching (DP)
    // Explanation: dp[i][j] means first i chars of s match first j chars of p.
    // Approach: Build table with normal char, '.', and '*' transitions.
    // Time Complexity: O(m * n)
    // Space Complexity: O(m * n)
    public static boolean regexMatchDP(String s, String p) {
        int m = s.length();
        int n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;

        for (int j = 2; j <= n; j++) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 2];
            }
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char pc = p.charAt(j - 1);
                char sc = s.charAt(i - 1);

                if (pc == sc || pc == '.') {
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (pc == '*') {
                    dp[i][j] = dp[i][j - 2];
                    char prev = p.charAt(j - 2);
                    if (prev == '.' || prev == sc) {
                        dp[i][j] = dp[i][j] || dp[i - 1][j];
                    }
                }
            }
        }

        return dp[m][n];
    }

    // Problem 6: String to Integer (atoi) - Manual Parse
    // Explanation: Parse sign and digits, stop at first invalid char.
    // Approach: Skip spaces, read sign, parse digits with overflow checks.
    // Time Complexity: O(n)
    // Space Complexity: O(1)
    public static int myAtoiManual(String s) {
        int i = 0;
        int n = s.length();
        int sign = 1;
        long value = 0;

        while (i < n && s.charAt(i) == ' ') {
            i++;
        }

        if (i < n && (s.charAt(i) == '+' || s.charAt(i) == '-')) {
            if (s.charAt(i) == '-') {
                sign = -1;
            }
            i++;
        }

        while (i < n && Character.isDigit(s.charAt(i))) {
            value = value * 10 + (s.charAt(i) - '0');

            long signed = sign * value;
            if (signed > Integer.MAX_VALUE) {
                return Integer.MAX_VALUE;
            }
            if (signed < Integer.MIN_VALUE) {
                return Integer.MIN_VALUE;
            }
            i++;
        }

        return (int) (sign * value);
    }

    // Problem 7: String to Integer (atoi) - Built-in Safe
    // Explanation: Use trim and Integer.parseInt with exception handling.
    // Approach: Parse directly; return 0 on invalid formats.
    // Time Complexity: O(n)
    // Space Complexity: O(1)
    public static int myAtoiBuiltIn(String s) {
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    // Problem 8: String to Integer (atoi) - FSM
    // Explanation: State machine approach for strict parsing control.
    // Approach: Move through states (start, sign, number) while reading chars.
    // Time Complexity: O(n)
    // Space Complexity: O(1)
    public static int myAtoiFSM(String s) {
        int state = 0; // 0=start, 1=sign-seen, 2=number
        int sign = 1;
        long value = 0;

        for (char c : s.toCharArray()) {
            if (state == 0) {
                if (c == ' ') {
                    // Stay in start state while leading spaces continue.
                } else if (c == '+') {
                    state = 1;
                } else if (c == '-') {
                    state = 1;
                    sign = -1;
                } else if (Character.isDigit(c)) {
                    state = 2;
                    value = c - '0';
                } else {
                    return 0;
                }
            } else {
                if (Character.isDigit(c)) {
                    state = 2;
                    value = value * 10 + (c - '0');
                    long signed = sign * value;
                    if (signed > Integer.MAX_VALUE) {
                        return Integer.MAX_VALUE;
                    }
                    if (signed < Integer.MIN_VALUE) {
                        return Integer.MIN_VALUE;
                    }
                } else {
                    break;
                }
            }
        }

        return (int) (sign * value);
    }

    // Problem 9: Merge Strings Alternately - Two Pointers
    // Explanation: Pick one char from each string in turns.
    // Approach: Move two indices and append while either string remains.
    // Time Complexity: O(m + n)
    // Space Complexity: O(m + n)
    public static String mergeAlternatelyTwoPointers(String w1, String w2) {
        StringBuilder result = new StringBuilder();
        int i = 0;
        int j = 0;

        while (i < w1.length() || j < w2.length()) {
            if (i < w1.length()) {
                result.append(w1.charAt(i++));
            }
            if (j < w2.length()) {
                result.append(w2.charAt(j++));
            }
        }
        return result.toString();
    }

    // Problem 10: Merge Strings Alternately - Index Loop
    // Explanation: Loop to max length and append if index exists in each word.
    // Approach: Single for-loop from 0 to max length.
    // Time Complexity: O(m + n)
    // Space Complexity: O(m + n)
    public static String mergeAlternatelyIndex(String w1, String w2) {
        StringBuilder result = new StringBuilder();
        int max = Math.max(w1.length(), w2.length());

        for (int i = 0; i < max; i++) {
            if (i < w1.length()) {
                result.append(w1.charAt(i));
            }
            if (i < w2.length()) {
                result.append(w2.charAt(i));
            }
        }

        return result.toString();
    }

    // Problem 11: Merge Strings Alternately - Recursive
    // Explanation: Merge first characters, then recurse on remaining substrings.
    // Approach: Base case when either string is empty.
    // Time Complexity: O(m + n) work, but substring may add overhead
    // Space Complexity: O(m + n)
    public static String mergeAlternatelyRecursive(String w1, String w2) {
        if (w1.isEmpty()) {
            return w2;
        }
        if (w2.isEmpty()) {
            return w1;
        }
        return w1.charAt(0) + "" + w2.charAt(0)
                + mergeAlternatelyRecursive(w1.substring(1), w2.substring(1));
    }

    // Problem 12: Palindrome String - Two Pointers
    // Explanation: Compare characters from both ends.
    // Approach: Move inward while chars match.
    // Time Complexity: O(n)
    // Space Complexity: O(1)
    public static boolean isPalindromeTwoPointers(String s) {
        int left = 0;
        int right = s.length() - 1;

        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    // Problem 13: Palindrome String - Reverse Compare
    // Explanation: Reverse string and compare with original.
    // Approach: Use StringBuilder reverse.
    // Time Complexity: O(n)
    // Space Complexity: O(n)
    public static boolean isPalindromeReverse(String s) {
        return s.equals(new StringBuilder(s).reverse().toString());
    }

    // Problem 14: Palindrome String - Recursive
    // Explanation: Check first and last characters recursively.
    // Approach: Recurse on inner substring range.
    // Time Complexity: O(n)
    // Space Complexity: O(n)
    public static boolean isPalindromeRecursive(String s) {
        return palindromeRec(s, 0, s.length() - 1);
    }

    private static boolean palindromeRec(String s, int left, int right) {
        if (left >= right) {
            return true;
        }
        if (s.charAt(left) != s.charAt(right)) {
            return false;
        }
        return palindromeRec(s, left + 1, right - 1);
    }

    private static int expand(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;
    }

    private static boolean isPalindromeSimple(String s) {
        int left = 0;
        int right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left++) != s.charAt(right--)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("Longest Palindrome: " + longestPalindromeCenter("babad"));
        System.out.println("Regex Match (aab vs c*a*b): " + regexMatchDP("aab", "c*a*b"));
        System.out.println("Atoi of '-42': " + myAtoiManual("  -42"));
        System.out.println("Merge Alternately: " + mergeAlternatelyTwoPointers("abc", "pqr"));
        System.out.println("Is Palindrome 'level': " + isPalindromeTwoPointers("level"));
    }
}
