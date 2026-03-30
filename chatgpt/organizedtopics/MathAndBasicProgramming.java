package dsa_handbook.chatgpt.organizedtopics;

/*
 * Topic: Math and Basic Programming
 * Description: Covers number-based logic and basic coding fundamentals.
 * Real-world usage: Financial calculations, data validation, and coding test basics.
 */
import java.util.ArrayList;
import java.util.List;

public class MathAndBasicProgramming {

    // Problem 1: Factorial (Iterative)
    // Explanation: Multiply all numbers from 1 to n.
    // Approach: Loop from 2 to n and multiply result.
    // Time Complexity: O(n)
    // Space Complexity: O(1)
    public static long factorialIterative(int n) {
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    // Problem 2: Factorial (Recursive)
    // Explanation: n! = n * (n-1)!
    // Approach: Base case for 0 and 1, else recursive multiply.
    // Time Complexity: O(n)
    // Space Complexity: O(n)
    public static long factorialRecursive(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }
        return n * factorialRecursive(n - 1);
    }

    // Problem 3: Factorial (DP)
    // Explanation: Build factorial values bottom-up.
    // Approach: Store previous answers in array.
    // Time Complexity: O(n)
    // Space Complexity: O(n)
    public static long factorialDP(int n) {
        long[] dp = new long[n + 1];
        dp[0] = 1;
        for (int i = 1; i <= n; i++) {
            dp[i] = i * dp[i - 1];
        }
        return dp[n];
    }

    // Problem 4: Prime Check
    // Explanation: Prime has only 1 and itself as divisors.
    // Approach: Try divisors from 2 to sqrt(n).
    // Time Complexity: O(sqrt n)
    // Space Complexity: O(1)
    public static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    // Problem 5: Reverse Number
    // Explanation: Reverse digits of a number.
    // Approach: Use modulo to extract digits and rebuild reversed number.
    // Time Complexity: O(d)
    // Space Complexity: O(1)
    public static int reverseNumber(int n) {
        int result = 0;
        while (n != 0) {
            result = result * 10 + n % 10;
            n /= 10;
        }
        return result;
    }

    // Problem 6: Sum of Digits
    // Explanation: Add each digit in number.
    // Approach: Repeatedly take n % 10 and divide by 10.
    // Time Complexity: O(d)
    // Space Complexity: O(1)
    public static int sumOfDigits(int n) {
        int sum = 0;
        n = Math.abs(n);
        while (n > 0) {
            sum += n % 10;
            n /= 10;
        }
        return sum;
    }

    // Problem 7: Armstrong Number
    // Explanation: Number equals sum of each digit raised to digit count power.
    // Approach: Count digits and compute powered sum.
    // Time Complexity: O(d)
    // Space Complexity: O(1)
    public static boolean isArmstrong(int n) {
        String s = Integer.toString(n);
        int digits = s.length();
        int sum = 0;
        int temp = n;

        while (temp > 0) {
            int d = temp % 10;
            sum += (int) Math.pow(d, digits);
            temp /= 10;
        }

        return sum == n;
    }

    // Problem 8: Fibonacci (Tabulation DP)
    // Explanation: Build Fibonacci using previous two values.
    // Approach: Use DP array from 0..n.
    // Time Complexity: O(n)
    // Space Complexity: O(n)
    public static int fibonacciDP(int n) {
        if (n <= 1) {
            return n;
        }

        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];
    }

    // Problem 9: Fibonacci (Space Optimized)
    // Explanation: Keep only last two values.
    // Approach: Iterate with a, b and compute next.
    // Time Complexity: O(n)
    // Space Complexity: O(1)
    public static int fibonacciOptimized(int n) {
        if (n <= 1) {
            return n;
        }

        int a = 0;
        int b = 1;
        for (int i = 2; i <= n; i++) {
            int c = a + b;
            a = b;
            b = c;
        }

        return b;
    }

    // Problem 10: Fibonacci (Recursive)
    // Explanation: Direct recursive definition of Fibonacci.
    // Approach: fib(n) = fib(n-1) + fib(n-2).
    // Time Complexity: O(2^n)
    // Space Complexity: O(n)
    public static int fibonacciRecursive(int n) {
        if (n <= 1) {
            return n;
        }
        return fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
    }

    // Problem 11: Palindrome Number
    // Explanation: Number is palindrome if it reads same forward and backward.
    // Approach: Reverse number and compare with original.
    // Time Complexity: O(d)
    // Space Complexity: O(1)
    public static boolean isPalindromeNumber(int n) {
        if (n < 0) {
            return false;
        }
        return n == reverseNumber(n);
    }

    // Problem 12: Swap Two Numbers (With Temp)
    // Explanation: Use temporary variable to swap values.
    // Approach: temp = a; a = b; b = temp.
    // Time Complexity: O(1)
    // Space Complexity: O(1)
    public static int[] swapWithTemp(int a, int b) {
        int temp = a;
        a = b;
        b = temp;
        return new int[]{a, b};
    }

    // Problem 13: Swap Two Numbers (Without Temp)
    // Explanation: Swap using arithmetic operations.
    // Approach: a = a+b; b = a-b; a = a-b.
    // Time Complexity: O(1)
    // Space Complexity: O(1)
    public static int[] swapWithoutTemp(int a, int b) {
        a = a + b;
        b = a - b;
        a = a - b;
        return new int[]{a, b};
    }

    // Problem 14: Count Digits
    // Explanation: Count how many digits are in the number.
    // Approach: Repeatedly divide by 10.
    // Time Complexity: O(d)
    // Space Complexity: O(1)
    public static int countDigits(int n) {
        n = Math.abs(n);
        if (n == 0) {
            return 1;
        }
        int count = 0;
        while (n > 0) {
            count++;
            n /= 10;
        }
        return count;
    }

    // Problem 15: GCD (Euclidean Iterative)
    // Explanation: Greatest common divisor using remainder reduction.
    // Approach: Repeat (a, b) = (b, a % b) until b is 0.
    // Time Complexity: O(log(min(a,b)))
    // Space Complexity: O(1)
    public static int gcdIterative(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // Problem 16: GCD (Recursive)
    // Explanation: Recursive form of Euclidean algorithm.
    // Approach: gcd(a, b) = gcd(b, a % b) until b == 0.
    // Time Complexity: O(log(min(a,b)))
    // Space Complexity: O(log(min(a,b)))
    public static int gcdRecursive(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        if (b == 0) {
            return a;
        }
        return gcdRecursive(b, a % b);
    }

    // Problem 17: GCD (Brute Force)
    // Explanation: Try all divisors up to min(a,b).
    // Approach: Keep largest divisor dividing both numbers.
    // Time Complexity: O(min(a,b))
    // Space Complexity: O(1)
    public static int gcdBruteForce(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        int limit = Math.min(a, b);
        int result = 1;
        for (int i = 1; i <= limit; i++) {
            if (a % i == 0 && b % i == 0) {
                result = i;
            }
        }
        return result;
    }

    // Problem 18: Replace 0 with 5 (String)
    // Explanation: Convert number to string and replace each '0'.
    // Approach: Use String replace and parse back.
    // Time Complexity: O(d)
    // Space Complexity: O(d)
    public static int replaceZeroWithFiveString(int num) {
        String value = Integer.toString(num).replace('0', '5');
        return Integer.parseInt(value);
    }

    // Problem 19: Replace 0 with 5 (Math)
    // Explanation: Build result digit by digit while replacing zeros.
    // Approach: Extract digit by modulo; if zero, convert to five.
    // Time Complexity: O(d)
    // Space Complexity: O(1)
    public static int replaceZeroWithFiveMath(int num) {
        if (num == 0) {
            return 5;
        }

        int place = 1;
        int result = 0;

        while (num > 0) {
            int digit = num % 10;
            if (digit == 0) {
                digit = 5;
            }
            result += digit * place;
            place *= 10;
            num /= 10;
        }

        return result;
    }

    // Problem 20: Replace 0 with 5 (Recursive)
    // Explanation: Recursively process digits from most significant side.
    // Approach: Replace last digit then combine with recursive prefix.
    // Time Complexity: O(d)
    // Space Complexity: O(d)
    public static int replaceZeroWithFiveRecursive(int num) {
        if (num == 0) {
            return 0;
        }
        int digit = num % 10;
        if (digit == 0) {
            digit = 5;
        }
        return replaceZeroWithFiveRecursive(num / 10) * 10 + digit;
    }

    // Problem 21: Print Pattern (Increasing Triangle)
    // Explanation: Build lines with 1 to n stars.
    // Approach: For each row i, append i stars.
    // Time Complexity: O(n^2)
    // Space Complexity: O(n^2) output list
    public static List<String> increasingStarPattern(int n) {
        List<String> lines = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            lines.add("*".repeat(i));
        }
        return lines;
    }

    // Problem 22: Print Pattern (Decreasing Triangle)
    // Explanation: Build lines with n to 1 stars.
    // Approach: For each row i from n down to 1, append i stars.
    // Time Complexity: O(n^2)
    // Space Complexity: O(n^2) output list
    public static List<String> decreasingStarPattern(int n) {
        List<String> lines = new ArrayList<>();
        for (int i = n; i >= 1; i--) {
            lines.add("*".repeat(i));
        }
        return lines;
    }

    public static void main(String[] args) {
        System.out.println("Factorial(5): " + factorialIterative(5));
        System.out.println("Prime(29): " + isPrime(29));
        System.out.println("GCD(54,24): " + gcdIterative(54, 24));
        System.out.println("Fib(10): " + fibonacciOptimized(10));
        System.out.println("Replace 0 with 5 in 1020: " + replaceZeroWithFiveMath(1020));
    }
}
