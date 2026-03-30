package dsa.handbook;

/*
==================================================
📌 PROBLEM: Merge Strings Alternately
==================================================

Create a new string by taking characters alternately from two words.
If one word ends early, append the remaining part of the other.

You are given:
- word1
- word2

👉 Your task:
merge alternately while preserving character order within each word.

--------------------------------------------------
🧠 1. INTUITION (DEEP UNDERSTANDING)
--------------------------------------------------

Use one shared index i.
At each step:
- append word1[i] if available
- append word2[i] if available

Continue until both strings are fully consumed.

--------------------------------------------------
❌ 2. WRONG APPROACH (BEGINNER MISTAKE)
--------------------------------------------------

Mistake:
using repeated String concatenation in loop.

Why problematic:
Strings are immutable in Java.
Each concatenation creates a new object, making performance degrade.

--------------------------------------------------
🧪 3. STEP-BY-STEP DRY RUN (REAL EXAMPLE)
--------------------------------------------------

word1 = "ab", word2 = "pqrs"
i=0 -> append 'a', 'p' -> "ap"
i=1 -> append 'b', 'q' -> "apbq"
i=2 -> only word2 has 'r' -> "apbqr"
i=3 -> only word2 has 's' -> "apbqrs"

--------------------------------------------------
📊 4. VISUAL REPRESENTATION
--------------------------------------------------

Think of two lanes feeding one output lane,
pulling one character per lane per round.

--------------------------------------------------
⚙️ 5. APPROACHES
--------------------------------------------------

1. Repeated concatenation: O((m+n)^2)
2. StringBuilder: O(m+n)

--------------------------------------------------
⚠️ 6. EDGE CASES
--------------------------------------------------

- one empty word
- both empty
- unequal lengths

--------------------------------------------------
❌ 7. COMMON MISTAKES
--------------------------------------------------

- forgetting leftover characters from longer word
- increment mistakes in index loop

--------------------------------------------------
🎯 8. INTERVIEW INSIGHT
--------------------------------------------------

Natural extension:
merge k strings in round-robin order.
Same pattern, generalized over list of strings.

--------------------------------------------------
🧾 9. CLEAN CODE
--------------------------------------------------
- Keep brute and optimal versions separate
- Use StringBuilder capacity to reduce reallocations
- Keep index handling simple and symmetric

--------------------------------------------------
🧪 10. MAIN METHOD TESTING
--------------------------------------------------
Format:
Input -> Expected -> Actual

--------------------------------------------------
📌 11. MINI SUMMARY
--------------------------------------------------

One index plus conditional append solves this cleanly.
StringBuilder gives linear performance.

==================================================
*/
public class MergeStringsAlternately {
    // Brute via string concatenation.
    // Time: O((m+n)^2), Space: O(1)
    public static String mergeBrute(String a, String b) {
        String res = "";
        int i = 0;
        while (i < a.length() || i < b.length()) {
            if (i < a.length()) res += a.charAt(i);
            if (i < b.length()) res += b.charAt(i);
            i++;
        }
        return res;
    }

    // Optimal using StringBuilder.
    // Time: O(m+n), Space: O(m+n)
    public static String mergeOptimal(String a, String b) {
        StringBuilder sb = new StringBuilder(a.length() + b.length());
        int i = 0;
        while (i < a.length() || i < b.length()) {
            if (i < a.length()) sb.append(a.charAt(i));
            if (i < b.length()) sb.append(b.charAt(i));
            i++;
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("Input: word1=abc, word2=pqr");
        System.out.println("Expected: apbqcr | Brute: " + mergeBrute("abc","pqr") + " | Optimal: " + mergeOptimal("abc","pqr"));

        System.out.println("Input: word1=ab, word2=pqrs");
        System.out.println("Expected: apbqrs | Brute: " + mergeBrute("ab","pqrs") + " | Optimal: " + mergeOptimal("ab","pqrs"));

        System.out.println("Input: word1=abcd, word2=pq");
        System.out.println("Expected: apbqcd | Brute: " + mergeBrute("abcd","pq") + " | Optimal: " + mergeOptimal("abcd","pq"));
    }
}







