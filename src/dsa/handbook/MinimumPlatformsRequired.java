package dsa.handbook;

/*
==================================================
📌 PROBLEM: Minimum Platforms Required
==================================================

At a railway station, each train occupies a platform from arrival to departure.
Need minimum number of platforms so no train waits.

You are given:
- arrival times array
- departure times array

👉 Your task:
compute peak overlap count of active trains.

--------------------------------------------------
🧠 1. INTUITION (DEEP UNDERSTANDING)
--------------------------------------------------

Sort arrivals and departures independently.
Use two pointers:
- next arrival means one more platform occupied
- next departure means one platform freed

Track maximum occupied platforms during sweep.

--------------------------------------------------
❌ 2. WRONG APPROACH (BEGINNER MISTAKE)
--------------------------------------------------

Mistake:
pairwise overlap checks for all train pairs.

Why weak:
works for small n, but too slow for large schedules.
Sweep-line captures same idea efficiently.

--------------------------------------------------
🧪 3. STEP-BY-STEP DRY RUN (REAL EXAMPLE)
--------------------------------------------------

arr=[900,940,950,1100,1500,1800]
dep=[910,1120,1130,1200,1900,2000] after sorting

900<=910 => cur=1, ans=1
940>910 => cur=0
940<=1120 => cur=1
950<=1120 => cur=2, ans=2
1100<=1120 => cur=3, ans=3

Peak is 3.

--------------------------------------------------
📊 4. VISUAL REPRESENTATION
--------------------------------------------------

Two sorted event timelines:
arrivals pointer i, departures pointer j.
Process whichever event happens first.

--------------------------------------------------
⚙️ 5. APPROACHES
--------------------------------------------------

1. Brute overlap count: O(n^2)
2. Sort + two pointers: O(n log n)

--------------------------------------------------
⚠️ 6. EDGE CASES
--------------------------------------------------

- single train
- all trains overlap fully
- equal arrival and departure times (policy-sensitive)

--------------------------------------------------
❌ 7. COMMON MISTAKES
--------------------------------------------------

- wrong tie policy (arr[i] <= dep[j] vs arr[i] < dep[j])
- forgetting to clone arrays before sorting in tests

--------------------------------------------------
🎯 8. INTERVIEW INSIGHT
--------------------------------------------------

This is a textbook sweep-line problem.
Important: explain tie handling clearly based on platform reuse policy.

--------------------------------------------------
🧾 9. CLEAN CODE
--------------------------------------------------
- Keep brute and optimal methods separate
- Keep pointers and counters clearly named
- Use cloned arrays in tests so both methods see same input

--------------------------------------------------
🧪 10. MAIN METHOD TESTING
--------------------------------------------------
Format:
Input -> Expected -> Actual

--------------------------------------------------
📌 11. MINI SUMMARY
--------------------------------------------------

Sort arrivals and departures, then sweep earliest event.
Maximum active count is the required platforms.

==================================================
*/
import java.util.*;

/*
Problem: Minimum Platforms Required
Given arrival and departure times, find minimum number of platforms needed so no train waits.
*/
public class MinimumPlatformsRequired {
    // Brute Force timeline overlap.
    // Time: O(n^2), Space: O(1)
    public static int minPlatformsBrute(int[] arr, int[] dep) {
        int n = arr.length, ans = 1;
        for (int i = 0; i < n; i++) {
            int cnt = 1;
            for (int j = i + 1; j < n; j++) {
                if (!(dep[i] < arr[j] || dep[j] < arr[i])) cnt++;
            }
            ans = Math.max(ans, cnt);
        }
        return ans;
    }

    // Optimal sorting + two pointers.
    // Time: O(n log n), Space: O(1) extra if sort in-place
    public static int minPlatformsOptimal(int[] arr, int[] dep) {
        Arrays.sort(arr); Arrays.sort(dep);
        int i = 0, j = 0, cur = 0, ans = 0;
        while (i < arr.length && j < dep.length) {
            if (arr[i] <= dep[j]) { cur++; ans = Math.max(ans, cur); i++; }
            else { cur--; j++; }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] arr = {900, 940, 950, 1100, 1500, 1800};
        int[] dep = {910, 1200, 1120, 1130, 1900, 2000};
        int expected = 3;

        System.out.println("Input arrivals: " + Arrays.toString(arr));
        System.out.println("Input departures: " + Arrays.toString(dep));
        System.out.println("Expected: " + expected + " | Brute: " + minPlatformsBrute(arr.clone(), dep.clone()));
        System.out.println("Expected: " + expected + " | Optimal: " + minPlatformsOptimal(arr.clone(), dep.clone()));
    }
}







