package dsa.handbook;

/*
==================================================
📌 PROBLEM: GCD of Two Numbers
==================================================

GCD (Greatest Common Divisor) means:
the largest integer that divides both numbers without remainder.

Real use cases:
- reduce fractions (e.g., 42/56 -> 3/4)
- simplify ratio problems
- modular arithmetic and number theory questions

You are given:
- two integers a and b

👉 Your task:
find gcd(a, b).

--------------------------------------------------
🧠 1. INTUITION (DEEP UNDERSTANDING)
--------------------------------------------------

Euclid's key identity:
gcd(a, b) = gcd(b, a % b)

Why this is powerful:
- gcd does not change after replacing (a,b) with (b, a%b)
- second value strictly decreases
- eventually b becomes 0

At that point, a is the answer.

--------------------------------------------------
❌ 2. WRONG APPROACH (BEGINNER MISTAKE)
--------------------------------------------------

Beginner idea:
"Try every divisor from min(a,b) down to 1"

It works, but is too slow for large inputs.
For example, numbers around 10^9 make brute force impractical.

--------------------------------------------------
🧪 3. STEP-BY-STEP DRY RUN (REAL EXAMPLE)
--------------------------------------------------

Example: gcd(48, 18)

Step 1:
a=48, b=18
r = 48 % 18 = 12
update -> a=18, b=12

Step 2:
a=18, b=12
r = 18 % 12 = 6
update -> a=12, b=6

Step 3:
a=12, b=6
r = 12 % 6 = 0
update -> a=6, b=0

Stop condition reached (b==0)
Answer = 6

--------------------------------------------------
📊 4. VISUAL REPRESENTATION
--------------------------------------------------

Remainder chain:
48 -> 18 -> 12 -> 6 -> 0

Pair transitions:
(48,18) -> (18,12) -> (12,6) -> (6,0)

--------------------------------------------------
⚙️ 5. APPROACHES
--------------------------------------------------

1. Brute force divisor scan
- Time: O(min(a,b))
- Space: O(1)

2. Euclidean algorithm (optimal)
- Time: O(log(min(a,b)))
- Space: O(1)

--------------------------------------------------
⚠️ 6. EDGE CASES
--------------------------------------------------

- gcd(0, x) = |x|
- gcd(x, 0) = |x|
- gcd(0, 0) returns 0 in this implementation
- negative numbers should be normalized with abs

--------------------------------------------------
❌ 7. COMMON MISTAKES
--------------------------------------------------

- forgetting abs() and getting negative gcd
- dividing by zero in custom logic
- using brute force in large constraints

--------------------------------------------------
🎯 8. INTERVIEW INSIGHT
--------------------------------------------------

Common interview follow-up:
"Why does gcd(a,b)=gcd(b,a%b) hold?"

Expected explanation:
If a = b*q + r, any common divisor of (a,b) also divides r,
and any common divisor of (b,r) also divides a.
So the set of common divisors is the same.

--------------------------------------------------
🧾 9. CLEAN CODE
--------------------------------------------------
- Keep brute and optimal methods separate
- Normalize sign at method entry
- Keep loop update compact and clear

--------------------------------------------------
🧪 10. MAIN METHOD TESTING
--------------------------------------------------
Format:
Input -> Expected -> Actual

Include:
- standard case
- co-prime case
- zero case
- negative input case

--------------------------------------------------
📌 11. MINI SUMMARY
--------------------------------------------------

Euclid algorithm is fast because it preserves gcd while shrinking numbers.
Remainder operation is the entire trick.

==================================================
*/
public class GCDOfTwoNumbers {
    // Brute force from min(a,b) downwards.
    // Time: O(min(a,b)), Space: O(1)
    public static int gcdBrute(int a, int b) {
        a = Math.abs(a); b = Math.abs(b);
        if (a == 0) return b;
        if (b == 0) return a;
        int m = Math.min(a, b);
        for (int i = m; i >= 1; i--) if (a % i == 0 && b % i == 0) return i;
        return 1;
    }

    // Optimal Euclidean iterative.
    // Time: O(log min(a,b)), Space: O(1)
    public static int gcdOptimal(int a, int b) {
        a = Math.abs(a); b = Math.abs(b);
        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return a;
    }

    public static void main(String[] args) {
        int[][] tests = {
                {48, 18},
                {100, 25},
                {7, 13},
                {0, 5},
                {-24, 18},
                {0, 0}
        };
        int[] expected = {6, 25, 1, 5, 6, 0};

        for (int i = 0; i < tests.length; i++) {
            int a = tests[i][0];
            int b = tests[i][1];
            int exp = expected[i];

            int brute = gcdBrute(a, b);
            int optimal = gcdOptimal(a, b);

            System.out.println("Input: (" + a + ", " + b + ")");
            System.out.println("Expected: " + exp + " | Brute: " + brute + " | Optimal: " + optimal);
        }
    }
}







