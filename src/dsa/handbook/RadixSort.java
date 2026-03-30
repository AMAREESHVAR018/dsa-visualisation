package dsa.handbook;

/*
==================================================
📌 PROBLEM: Radix Sort (LSD)
==================================================

Radix sort processes numbers digit by digit.
LSD version starts from units place, then tens, hundreds, and so on.

You are given:
- array of non-negative integers

👉 Your task:
sort numbers in ascending order using stable digit passes.

--------------------------------------------------
🧠 1. INTUITION (DEEP UNDERSTANDING)
--------------------------------------------------

At each exponent exp (1,10,100,...):
perform counting sort by digit (value/exp)%10.

Critical requirement:
each digit pass must be stable,
so ordering from previous lower-significance pass is preserved.

--------------------------------------------------
❌ 2. WRONG APPROACH (BEGINNER MISTAKE)
--------------------------------------------------

Mistake:
use unstable grouping by digit.

Why wrong:
radix correctness depends on stability across passes.
Without stability, higher-digit passes can destroy lower-digit ordering.

--------------------------------------------------
🧪 3. STEP-BY-STEP DRY RUN (REAL EXAMPLE)
--------------------------------------------------

arr=[170,45,75,90,802,24,2,66]

exp=1  -> [170,90,802,2,24,45,75,66]
exp=10 -> [802,2,24,45,66,170,75,90]
exp=100-> [2,24,45,66,75,90,170,802]

--------------------------------------------------
📊 4. VISUAL REPRESENTATION
--------------------------------------------------

Think of ten buckets (0..9) per pass,
but implemented via counting/prefix sums for linear performance.

--------------------------------------------------
⚙️ 5. APPROACHES
--------------------------------------------------

1. Comparison sort fallback: O(n log n)
2. LSD radix (base 10): O(d*(n+10))

--------------------------------------------------
⚠️ 6. EDGE CASES
--------------------------------------------------

- empty array
- repeated values
- large max value with more digit passes
- negatives are unsupported in this implementation

--------------------------------------------------
❌ 7. COMMON MISTAKES
--------------------------------------------------

- forgetting to process all exponents until max/exp == 0
- iterating forward while placing into output (breaks stability)
- not validating non-negative input

--------------------------------------------------
🎯 8. INTERVIEW INSIGHT
--------------------------------------------------

Common interview check:
"Why traverse from right to left in counting placement?"
Answer: to preserve stability.

--------------------------------------------------
🧾 9. CLEAN CODE
--------------------------------------------------
- Keep digit-pass logic in dedicated helper
- Use clear names: exp, cnt, out
- Throw fast for negative values to avoid silent wrong output

--------------------------------------------------
🧪 10. MAIN METHOD TESTING
--------------------------------------------------
Format:
Input -> Expected -> Actual

--------------------------------------------------
📌 11. MINI SUMMARY
--------------------------------------------------

Radix sort works only when each digit pass is stable.
LSD to MSD progression then yields globally sorted order.

==================================================
*/
import java.util.*;

/*
Problem: Radix Sort (LSD base-10)
Sort non-negative integers.
*/
public class RadixSort {
    // Brute fallback using comparison sort.
    // Time: O(n^2), Space: O(1)
    public static void bruteSort(int[] a) {
        Arrays.sort(a);
    }

    // Optimal radix sort.
    // Time: O(d*(n + base)), Space: O(n + base)
    public static void radixSort(int[] a) {
        int max = 0;
        for (int v : a) {
            if (v < 0) throw new IllegalArgumentException("Radix sort needs non-negative integers.");
            max = Math.max(max, v);
        }
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingByDigit(a, exp);
        }
    }

    private static void countingByDigit(int[] a, int exp) {
        int n = a.length;
        int[] out = new int[n];
        int[] cnt = new int[10];
        for (int v : a) cnt[(v / exp) % 10]++;
        for (int i = 1; i < 10; i++) cnt[i] += cnt[i - 1];
        for (int i = n - 1; i >= 0; i--) {
            int d = (a[i] / exp) % 10;
            out[--cnt[d]] = a[i];
        }
        System.arraycopy(out, 0, a, 0, n);
    }

    public static void main(String[] args) {
        int[] a = {170,45,75,90,802,24,2,66};

        int[] b = a.clone();
        bruteSort(b);
        radixSort(a);
        System.out.println("Input: [170, 45, 75, 90, 802, 24, 2, 66]");
        System.out.println("Expected: [2, 24, 45, 66, 75, 90, 170, 802] | Fallback sort: " + Arrays.toString(b));
        System.out.println("Expected: [2, 24, 45, 66, 75, 90, 170, 802] | Radix sort: " + Arrays.toString(a));
    }
}







