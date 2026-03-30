package dsa.handbook;

/*
==================================================
📌 PROBLEM: String to Integer (atoi)
==================================================

Implement atoi behavior manually with deterministic parsing phases.
Only valid leading format should contribute to numeric value.

You are given:
- string possibly containing spaces, sign, digits, and other chars

👉 Your task:
return parsed 32-bit signed integer with overflow clamping.

--------------------------------------------------
🧠 1. INTUITION (DEEP UNDERSTANDING)
--------------------------------------------------

Parsing phases:
1. skip leading spaces
2. optional sign
3. consume contiguous digits
4. stop at first non-digit

Overflow must be checked before multiplying by 10.

--------------------------------------------------
❌ 2. WRONG APPROACH (BEGINNER MISTAKE)
--------------------------------------------------

Mistake:
try parsing entire trimmed string directly.

Why wrong:
inputs like "4193 with words" are valid and should return 4193,
not throw parsing error.

--------------------------------------------------
🧪 3. STEP-BY-STEP DRY RUN (REAL EXAMPLE)
--------------------------------------------------

s = "   -42abc"
skip spaces -> sign = -1
read digits 4,2 -> value 42
stop at 'a' -> return -42

--------------------------------------------------
📊 4. VISUAL REPRESENTATION
--------------------------------------------------

Single pointer moves through parser states:
spaces -> sign -> digits -> stop.

--------------------------------------------------
⚙️ 5. APPROACHES
--------------------------------------------------

1. Cleanup + parseInt try/catch: workable but indirect
2. Manual state parser with proactive overflow guard: O(n), O(1)

--------------------------------------------------
⚠️ 6. EDGE CASES
--------------------------------------------------

- empty string
- sign only (+, -)
- overflow and underflow inputs
- text before digits should return 0

--------------------------------------------------
❌ 7. COMMON MISTAKES
--------------------------------------------------

- doing overflow check after res = res*10 + d
- accepting sign after digit phase
- not stopping on first non-digit after digit run

--------------------------------------------------
🎯 8. INTERVIEW INSIGHT
--------------------------------------------------

Interviewers focus heavily on exact edge behavior.
Being precise with phase transitions is usually the key differentiator.

--------------------------------------------------
🧾 9. CLEAN CODE
--------------------------------------------------
- Keep brute and manual parser implementations separate
- Use clear variable names: i, sign, res
- Keep overflow condition explicit and readable

--------------------------------------------------
🧪 10. MAIN METHOD TESTING
--------------------------------------------------
Format:
Input -> Expected -> Actual

--------------------------------------------------
📌 11. MINI SUMMARY
--------------------------------------------------

Model atoi as a finite parser with strict phases.
Clamp on overflow before arithmetic update.

==================================================
*/
public class StringToIntegerAtoi {
    // Brute with parsing after cleanup (limited safety).
    // Time: O(n), Space: O(n)
    public static int atoiBrute(String s) {
        s = s.trim();
        if (s.isEmpty()) return 0;
        StringBuilder sb = new StringBuilder();
        int i = 0;
        if (s.charAt(i) == '+' || s.charAt(i) == '-') sb.append(s.charAt(i++));
        while (i < s.length() && Character.isDigit(s.charAt(i))) sb.append(s.charAt(i++));
        if (sb.length() == 0 || sb.toString().equals("+") || sb.toString().equals("-")) return 0;
        try {
            return Integer.parseInt(sb.toString());
        } catch (NumberFormatException ex) {
            return sb.charAt(0) == '-' ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }
    }

    // Optimal manual parse with overflow guard.
    // Time: O(n), Space: O(1)
    public static int atoiOptimal(String s) {
        int i = 0, n = s.length();
        while (i < n && s.charAt(i) == ' ') i++;
        int sign = 1;
        if (i < n && (s.charAt(i) == '+' || s.charAt(i) == '-')) sign = s.charAt(i++) == '-' ? -1 : 1;
        int res = 0;
        while (i < n && Character.isDigit(s.charAt(i))) {
            int d = s.charAt(i++) - '0';
            if (res > (Integer.MAX_VALUE - d) / 10) {
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            res = res * 10 + d;
        }
        return res * sign;
    }

    public static void main(String[] args) {
        String[] tests = {"42", "   -42", "4193 with words", "words 987", "-91283472332", "+1"};
        int[] expected = {42, -42, 4193, 0, Integer.MIN_VALUE, 1};

        for (int i = 0; i < tests.length; i++) {
            String t = tests[i];
            System.out.println("Input: \"" + t + "\"");
            System.out.println("Expected: " + expected[i] + " | Brute: " + atoiBrute(t) + " | Optimal: " + atoiOptimal(t));
            System.out.println();
        }
    }
}







