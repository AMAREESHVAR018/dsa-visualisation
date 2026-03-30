package dsa_handbook.chatgpt.organizedtopics;

/*
 * Topic: Searching
 * Description: Searching is used to find a target value in data quickly.
 * Real-world usage: Finding a contact by phone number in a sorted phone directory.
 */
public class Searching {

    // Problem 1: Linear Search
    // Explanation: Find the target by checking each element one by one.
    // Approach: Start from index 0 and compare every value with target.
    // Time Complexity: O(n)
    // Space Complexity: O(1)
    public static int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }

    // Problem 2: Binary Search (Iterative)
    // Explanation: Search target in sorted array by cutting search space in half.
    // Approach: Compare middle value and move left/right boundary.
    // Time Complexity: O(log n)
    // Space Complexity: O(1)
    public static int binarySearchIterative(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) {
                return mid;
            }
            if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    // Problem 3: Binary Search (Recursive)
    // Explanation: Same logic as iterative binary search using recursion.
    // Approach: Recurse into left or right half until found or invalid range.
    // Time Complexity: O(log n)
    // Space Complexity: O(log n)
    public static int binarySearchRecursive(int[] arr, int left, int right, int target) {
        if (left > right) {
            return -1;
        }

        int mid = left + (right - left) / 2;
        if (arr[mid] == target) {
            return mid;
        }
        if (arr[mid] < target) {
            return binarySearchRecursive(arr, mid + 1, right, target);
        }
        return binarySearchRecursive(arr, left, mid - 1, target);
    }

    // Problem 4: Lower Bound
    // Explanation: Find first index where value is >= target in sorted array.
    // Approach: Keep shrinking range toward first valid index.
    // Time Complexity: O(log n)
    // Space Complexity: O(1)
    public static int lowerBound(int[] arr, int target) {
        int left = 0;
        int right = arr.length;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    // Problem 5: Upper Bound
    // Explanation: Find first index where value is > target in sorted array.
    // Approach: Similar to lower bound, but use <= condition.
    // Time Complexity: O(log n)
    // Space Complexity: O(1)
    public static int upperBound(int[] arr, int target) {
        int left = 0;
        int right = arr.length;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] <= target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    public static void main(String[] args) {
        int[] arr = {1, 3, 5, 7, 9};
        System.out.println("Linear Search(7): " + linearSearch(arr, 7));
        System.out.println("Binary Search(5): " + binarySearchIterative(arr, 5));
        System.out.println("Lower Bound(6): " + lowerBound(arr, 6));
        System.out.println("Upper Bound(7): " + upperBound(arr, 7));
    }
}
