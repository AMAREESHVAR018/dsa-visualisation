package dsa_handbook.chatgpt.organizedtopics;

/*
 * Topic: Data Structure Problems
 * Description: Common array, stack, queue, linked list, and tree interview problems.
 * Real-world usage: Building efficient data processing pipelines and application backends.
 */
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DataStructureProblems {

    // Problem 1: Second Largest Element
    // Explanation: Find second highest distinct value in array.
    // Approach: Track first and second maximum while scanning once.
    // Time Complexity: O(n)
    // Space Complexity: O(1)
    public static int secondLargest(int[] arr) {
        int first = Integer.MIN_VALUE;
        int second = Integer.MIN_VALUE;

        for (int val : arr) {
            if (val > first) {
                second = first;
                first = val;
            } else if (val > second && val != first) {
                second = val;
            }
        }
        return second;
    }

    // Problem 2: Reverse Array
    // Explanation: Reverse array in place.
    // Approach: Swap left and right pointers moving inward.
    // Time Complexity: O(n)
    // Space Complexity: O(1)
    public static void reverseArray(int[] arr) {
        int left = 0;
        int right = arr.length - 1;
        while (left < right) {
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }
    }

    // Problem 3: Move All Zeros to End
    // Explanation: Keep non-zero values in front and zeros at end.
    // Approach: Two-pointer compaction of non-zero values.
    // Time Complexity: O(n)
    // Space Complexity: O(1)
    public static void moveZerosToEnd(int[] arr) {
        int write = 0;
        for (int value : arr) {
            if (value != 0) {
                arr[write++] = value;
            }
        }
        while (write < arr.length) {
            arr[write++] = 0;
        }
    }

    // Problem 4: Find Duplicate Elements
    // Explanation: Return values appearing more than once.
    // Approach: Use set to track seen values and duplicates.
    // Time Complexity: O(n)
    // Space Complexity: O(n)
    public static Set<Integer> findDuplicates(int[] arr) {
        Set<Integer> seen = new HashSet<>();
        Set<Integer> duplicates = new HashSet<>();
        for (int value : arr) {
            if (!seen.add(value)) {
                duplicates.add(value);
            }
        }
        return duplicates;
    }

    // Problem 5: Rotate Array Left by k
    // Explanation: Shift elements left and wrap around.
    // Approach: Reverse first part, second part, then whole array.
    // Time Complexity: O(n)
    // Space Complexity: O(1)
    public static void rotateLeft(int[] arr, int k) {
        int n = arr.length;
        k = ((k % n) + n) % n;
        reverse(arr, 0, k - 1);
        reverse(arr, k, n - 1);
        reverse(arr, 0, n - 1);
    }

    // Problem 6: Rotate Array Right by k
    // Explanation: Shift elements right and wrap around.
    // Approach: Reverse whole array, then two parts.
    // Time Complexity: O(n)
    // Space Complexity: O(1)
    public static void rotateRight(int[] arr, int k) {
        int n = arr.length;
        k = ((k % n) + n) % n;
        reverse(arr, 0, n - 1);
        reverse(arr, 0, k - 1);
        reverse(arr, k, n - 1);
    }

    // Problem 7: Find Missing Number (0..n)
    // Explanation: One number is missing from range 0 to n.
    // Approach: Use arithmetic sum formula.
    // Time Complexity: O(n)
    // Space Complexity: O(1)
    public static int missingNumber(int[] arr) {
        int n = arr.length;
        int expected = n * (n + 1) / 2;
        int actual = 0;
        for (int value : arr) {
            actual += value;
        }
        return expected - actual;
    }

    // Problem 8: Two Sum
    // Explanation: Find indices of two numbers whose sum equals target.
    // Approach: Sort copy with indices using brute-force fallback here for clarity.
    // Time Complexity: O(n^2)
    // Space Complexity: O(1)
    public static int[] twoSumBruteForce(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] + arr[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }

    // Problem 9: Stack Using Array
    // Explanation: Custom fixed-size stack with push, pop, peek.
    // Approach: Track top index and update on each operation.
    // Time Complexity: O(1) per operation
    // Space Complexity: O(n)
    public static class ArrayStack {
        private final int[] data;
        private int top;

        public ArrayStack(int capacity) {
            this.data = new int[capacity];
            this.top = -1;
        }

        public boolean push(int value) {
            if (top + 1 == data.length) return false;
            data[++top] = value;
            return true;
        }

        public Integer pop() {
            if (top == -1) return null;
            return data[top--];
        }

        public Integer peek() {
            if (top == -1) return null;
            return data[top];
        }
    }

    // Problem 10: Queue Using Array
    // Explanation: Circular queue with enqueue and dequeue.
    // Approach: Use front/rear pointers with modulo arithmetic.
    // Time Complexity: O(1) per operation
    // Space Complexity: O(n)
    public static class ArrayQueue {
        private final int[] data;
        private int front;
        private int size;

        public ArrayQueue(int capacity) {
            data = new int[capacity];
            front = 0;
            size = 0;
        }

        public boolean enqueue(int value) {
            if (size == data.length) return false;
            int rear = (front + size) % data.length;
            data[rear] = value;
            size++;
            return true;
        }

        public Integer dequeue() {
            if (size == 0) return null;
            int value = data[front];
            front = (front + 1) % data.length;
            size--;
            return value;
        }

        public Integer peek() {
            if (size == 0) return null;
            return data[front];
        }
    }

    private static void reverse(int[] arr, int left, int right) {
        while (left < right) {
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }
    }

    public static void main(String[] args) {
        int[] arr = {4, 1, 9, 7, 9};
        System.out.println("Second Largest: " + secondLargest(arr));

        int[] rotate = {1, 2, 3, 4, 5};
        rotateRight(rotate, 2);
        System.out.println("Rotate Right by 2: " + Arrays.toString(rotate));

        ArrayStack stack = new ArrayStack(5);
        stack.push(10);
        stack.push(20);
        System.out.println("Stack Peek: " + stack.peek());

        ArrayQueue queue = new ArrayQueue(5);
        queue.enqueue(11);
        queue.enqueue(22);
        System.out.println("Queue Dequeue: " + queue.dequeue());
    }
}
