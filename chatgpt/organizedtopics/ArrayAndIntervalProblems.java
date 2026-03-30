package dsa_handbook.chatgpt.organizedtopics;

/*
 * Topic: Array and Interval Problems
 * Description: Arrays and intervals are common in coding rounds for logic and optimization.
 * Real-world usage: Scheduling meetings, stock analysis, and occupancy planning.
 */
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Stack;

public class ArrayAndIntervalProblems {

    // Problem 1: Buildings Receiving Sunlight (Brute Force)
    // Explanation: A building gets sunlight if all left buildings are shorter.
    // Approach: For each building, scan all previous buildings.
    // Time Complexity: O(n^2)
    // Space Complexity: O(1)
    public static int countBuildingsBruteForce(int[] arr) {
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            boolean canSee = true;
            for (int j = 0; j < i; j++) {
                if (arr[j] >= arr[i]) {
                    canSee = false;
                    break;
                }
            }
            if (canSee) {
                count++;
            }
        }
        return count;
    }

    // Problem 2: Buildings Receiving Sunlight (Optimal)
    // Explanation: Keep max height so far from left to right.
    // Approach: Count building if current height > maxSeen.
    // Time Complexity: O(n)
    // Space Complexity: O(1)
    public static int countBuildingsOptimal(int[] arr) {
        int maxSeen = Integer.MIN_VALUE;
        int count = 0;

        for (int h : arr) {
            if (h > maxSeen) {
                count++;
                maxSeen = h;
            }
        }

        return count;
    }

    // Problem 3: Buildings Receiving Sunlight (Stack)
    // Explanation: Maintain decreasing stack of visible heights.
    // Approach: Remove smaller/equal heights before pushing current.
    // Time Complexity: O(n)
    // Space Complexity: O(n)
    public static int countBuildingsStack(int[] arr) {
        Stack<Integer> stack = new Stack<>();
        for (int h : arr) {
            while (!stack.isEmpty() && stack.peek() <= h) {
                stack.pop();
            }
            stack.push(h);
        }
        return stack.size();
    }

    // Problem 4: Maximum Difference in Array (Optimal)
    // Explanation: Find max arr[j] - arr[i] where j > i.
    // Approach: Track minimum so far and best difference.
    // Time Complexity: O(n)
    // Space Complexity: O(1)
    public static int maxDifferenceOptimal(int[] arr) {
        int min = arr[0];
        int best = Integer.MIN_VALUE;

        for (int i = 1; i < arr.length; i++) {
            best = Math.max(best, arr[i] - min);
            min = Math.min(min, arr[i]);
        }
        return best;
    }

    // Problem 5: Maximum Difference in Array (Brute Force)
    // Explanation: Try all pairs i < j.
    // Approach: Nested loops over all index pairs.
    // Time Complexity: O(n^2)
    // Space Complexity: O(1)
    public static int maxDifferenceBruteForce(int[] arr) {
        int best = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                best = Math.max(best, arr[j] - arr[i]);
            }
        }
        return best;
    }

    // Problem 6: Rectangle Overlap (Geometry)
    // Explanation: Rectangles overlap unless one is completely left/right/up/down.
    // Approach: Return false for non-overlap conditions.
    // Time Complexity: O(1)
    // Space Complexity: O(1)
    public static boolean rectangleOverlapGeometry(int[] l1, int[] r1, int[] l2, int[] r2) {
        return !(l1[0] >= r2[0]
                || l2[0] >= r1[0]
                || r1[1] >= l2[1]
                || r2[1] >= l1[1]);
    }

    // Problem 7: Rectangle Overlap (Area-based)
    // Explanation: Compute intersection width and height.
    // Approach: Overlap exists if both overlap dimensions are positive.
    // Time Complexity: O(1)
    // Space Complexity: O(1)
    public static boolean rectangleOverlapArea(int[] l1, int[] r1, int[] l2, int[] r2) {
        int xOverlap = Math.min(r1[0], r2[0]) - Math.max(l1[0], l2[0]);
        int yOverlap = Math.min(l1[1], l2[1]) - Math.max(r1[1], r2[1]);
        return xOverlap > 0 && yOverlap > 0;
    }

    // Problem 8: Maximum Subarray Sum (Brute Force)
    // Explanation: Evaluate all subarray sums and keep max.
    // Approach: Double loop with rolling sum.
    // Time Complexity: O(n^2)
    // Space Complexity: O(1)
    public static int maxSubarrayBruteForce(int[] nums) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int sum = 0;
            for (int j = i; j < nums.length; j++) {
                sum += nums[j];
                max = Math.max(max, sum);
            }
        }
        return max;
    }

    // Problem 9: Maximum Subarray Sum (Kadane)
    // Explanation: Best subarray ending at i is either nums[i] or extend previous.
    // Approach: Track current best ending and global best.
    // Time Complexity: O(n)
    // Space Complexity: O(1)
    public static int maxSubarrayKadane(int[] nums) {
        int current = nums[0];
        int best = nums[0];

        for (int i = 1; i < nums.length; i++) {
            current = Math.max(nums[i], current + nums[i]);
            best = Math.max(best, current);
        }
        return best;
    }

    // Problem 10: Maximum Subarray Sum (DP)
    // Explanation: dp[i] stores best subarray sum ending at i.
    // Approach: Fill dp array and track maximum.
    // Time Complexity: O(n)
    // Space Complexity: O(n)
    public static int maxSubarrayDP(int[] nums) {
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int best = dp[0];

        for (int i = 1; i < nums.length; i++) {
            dp[i] = Math.max(nums[i], dp[i - 1] + nums[i]);
            best = Math.max(best, dp[i]);
        }
        return best;
    }

    // Problem 11: Maximum Subarray Sum (Divide and Conquer)
    // Explanation: Max sum lies in left part, right part, or crossing mid.
    // Approach: Recursively compute three candidates and return maximum.
    // Time Complexity: O(n log n)
    // Space Complexity: O(log n)
    public static int maxSubarrayDivideConquer(int[] nums) {
        return maxSubarrayRec(nums, 0, nums.length - 1);
    }

    private static int maxSubarrayRec(int[] nums, int left, int right) {
        if (left == right) {
            return nums[left];
        }

        int mid = left + (right - left) / 2;
        int leftBest = maxSubarrayRec(nums, left, mid);
        int rightBest = maxSubarrayRec(nums, mid + 1, right);

        int sum = 0;
        int bestLeftSum = Integer.MIN_VALUE;
        for (int i = mid; i >= left; i--) {
            sum += nums[i];
            bestLeftSum = Math.max(bestLeftSum, sum);
        }

        sum = 0;
        int bestRightSum = Integer.MIN_VALUE;
        for (int i = mid + 1; i <= right; i++) {
            sum += nums[i];
            bestRightSum = Math.max(bestRightSum, sum);
        }

        int cross = bestLeftSum + bestRightSum;
        return Math.max(Math.max(leftBest, rightBest), cross);
    }

    // Problem 12: Minimum Platforms Required (Two Pointers)
    // Explanation: Count overlapping arrival/departure intervals.
    // Approach: Sort arrivals/departures separately; sweep pointers.
    // Time Complexity: O(n log n)
    // Space Complexity: O(1) extra (excluding sorting)
    public static int minimumPlatformsTwoPointers(int[] arr, int[] dep) {
        int[] a = Arrays.copyOf(arr, arr.length);
        int[] d = Arrays.copyOf(dep, dep.length);
        Arrays.sort(a);
        Arrays.sort(d);

        int i = 0;
        int j = 0;
        int current = 0;
        int best = 0;

        while (i < a.length && j < d.length) {
            if (a[i] <= d[j]) {
                current++;
                i++;
            } else {
                current--;
                j++;
            }
            best = Math.max(best, current);
        }
        return best;
    }

    // Problem 13: Minimum Platforms Required (Min Heap)
    // Explanation: Track earliest departure among active trains.
    // Approach: Sort by arrival, pop heap when platform frees.
    // Time Complexity: O(n log n)
    // Space Complexity: O(n)
    public static int minimumPlatformsHeap(int[] arr, int[] dep) {
        int n = arr.length;
        int[][] trains = new int[n][2];
        for (int i = 0; i < n; i++) {
            trains[i][0] = arr[i];
            trains[i][1] = dep[i];
        }

        Arrays.sort(trains, (x, y) -> Integer.compare(x[0], y[0]));
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.offer(trains[0][1]);

        int maxPlatforms = 1;
        for (int i = 1; i < n; i++) {
            if (pq.peek() < trains[i][0]) {
                pq.poll();
            }
            pq.offer(trains[i][1]);
            maxPlatforms = Math.max(maxPlatforms, pq.size());
        }

        return maxPlatforms;
    }

    // Problem 14: Minimum Platforms Required (Brute Force)
    // Explanation: Count overlaps for each train against all others.
    // Approach: For each interval, compare with all later intervals.
    // Time Complexity: O(n^2)
    // Space Complexity: O(1)
    public static int minimumPlatformsBruteForce(int[] arr, int[] dep) {
        int n = arr.length;
        int max = 1;

        for (int i = 0; i < n; i++) {
            int platforms = 1;
            for (int j = i + 1; j < n; j++) {
                if (arr[i] <= dep[j] && arr[j] <= dep[i]) {
                    platforms++;
                }
            }
            max = Math.max(max, platforms);
        }

        return max;
    }

    public static void main(String[] args) {
        int[] heights = {7, 4, 8, 2, 9};
        System.out.println("Sunlight Buildings (Optimal): " + countBuildingsOptimal(heights));

        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println("Max Subarray (Kadane): " + maxSubarrayKadane(nums));

        int[] arr = {900, 940, 950, 1100, 1500, 1800};
        int[] dep = {910, 1200, 1120, 1130, 1900, 2000};
        System.out.println("Minimum Platforms: " + minimumPlatformsTwoPointers(arr, dep));
    }
}
