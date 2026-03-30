package dsa_handbook.chatgpt.organizedtopics;

/*
 * Topic: Sorting
 * Description: Sorting arranges data in increasing or decreasing order.
 * Real-world usage: Sorting products by price or rating in shopping apps.
 */
import java.util.Arrays;

public class Sorting {

    // Problem 1: Bubble Sort (Standard)
    // Explanation: Repeatedly swap adjacent elements if they are in wrong order.
    // Approach: Push largest unsorted value to end in each pass.
    // Time Complexity: O(n^2)
    // Space Complexity: O(1)
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }

    // Problem 2: Bubble Sort (Optimized)
    // Explanation: Stop early if no swap happens in a pass.
    // Approach: Track swapped flag and break if array is already sorted.
    // Time Complexity: O(n^2) worst, O(n) best
    // Space Complexity: O(1)
    public static void bubbleSortOptimized(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
    }

    // Problem 3: Selection Sort
    // Explanation: Pick minimum from unsorted part and place it at current index.
    // Approach: For each i, find min index from i..n-1 and swap.
    // Time Complexity: O(n^2)
    // Space Complexity: O(1)
    public static void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            swap(arr, i, minIndex);
        }
    }

    // Problem 4: Insertion Sort
    // Explanation: Insert each element into correct position in sorted left part.
    // Approach: Shift larger elements to right, then place key.
    // Time Complexity: O(n^2) worst, O(n) best
    // Space Complexity: O(1)
    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    // Problem 5: Merge Sort
    // Explanation: Divide array into halves, sort each half, then merge.
    // Approach: Recursively split and merge using helper array.
    // Time Complexity: O(n log n)
    // Space Complexity: O(n)
    public static void mergeSort(int[] arr) {
        if (arr.length <= 1) {
            return;
        }
        mergeSort(arr, 0, arr.length - 1);
    }

    private static void mergeSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int mid = left + (right - left) / 2;
        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }

    private static void merge(int[] arr, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int i = left;
        int j = mid + 1;
        int k = 0;

        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }
        while (i <= mid) {
            temp[k++] = arr[i++];
        }
        while (j <= right) {
            temp[k++] = arr[j++];
        }

        System.arraycopy(temp, 0, arr, left, temp.length);
    }

    // Problem 6: Quick Sort
    // Explanation: Pick pivot and partition into smaller and larger values.
    // Approach: Partition around pivot, then sort left and right recursively.
    // Time Complexity: O(n log n) average, O(n^2) worst
    // Space Complexity: O(log n) recursion average
    public static void quickSort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[] arr, int low, int high) {
        if (low >= high) {
            return;
        }
        int pivotIndex = partition(arr, low, high);
        quickSort(arr, low, pivotIndex - 1);
        quickSort(arr, pivotIndex + 1, high);
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                swap(arr, i, j);
                i++;
            }
        }
        swap(arr, i, high);
        return i;
    }

    // Problem 7: Heap Sort
    // Explanation: Build max-heap, then extract largest repeatedly.
    // Approach: Heapify from bottom, swap root with end, reduce heap size.
    // Time Complexity: O(n log n)
    // Space Complexity: O(1)
    public static void heapSort(int[] arr) {
        int n = arr.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i);
            heapify(arr, i, 0);
        }
    }

    private static void heapify(int[] arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }

        if (largest != i) {
            swap(arr, i, largest);
            heapify(arr, n, largest);
        }
    }

    // Problem 8: Counting Sort
    // Explanation: Count frequency of each value and rebuild array in order.
    // Approach: Use count array from min to max.
    // Time Complexity: O(n + k)
    // Space Complexity: O(k)
    public static int[] countingSort(int[] arr) {
        if (arr.length == 0) {
            return arr;
        }

        int min = arr[0];
        int max = arr[0];
        for (int value : arr) {
            min = Math.min(min, value);
            max = Math.max(max, value);
        }

        int[] count = new int[max - min + 1];
        for (int value : arr) {
            count[value - min]++;
        }

        int index = 0;
        for (int i = 0; i < count.length; i++) {
            while (count[i] > 0) {
                arr[index++] = i + min;
                count[i]--;
            }
        }
        return arr;
    }

    // Problem 9: Radix Sort
    // Explanation: Sort numbers digit by digit from least significant digit.
    // Approach: Reuse stable counting sort for each digit place.
    // Time Complexity: O(d * (n + b))
    // Space Complexity: O(n + b)
    public static void radixSort(int[] arr) {
        if (arr.length == 0) {
            return;
        }

        int max = Arrays.stream(arr).max().orElse(0);
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSortByDigit(arr, exp);
        }
    }

    private static void countingSortByDigit(int[] arr, int exp) {
        int n = arr.length;
        int[] output = new int[n];
        int[] count = new int[10];

        for (int value : arr) {
            int digit = (value / exp) % 10;
            count[digit]++;
        }

        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            int digit = (arr[i] / exp) % 10;
            output[count[digit] - 1] = arr[i];
            count[digit]--;
        }

        System.arraycopy(output, 0, arr, 0, n);
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr1 = {5, 1, 4, 2, 8};
        bubbleSortOptimized(arr1);
        System.out.println("Bubble Sorted: " + Arrays.toString(arr1));

        int[] arr2 = {9, 4, 6, 1, 3};
        mergeSort(arr2);
        System.out.println("Merge Sorted: " + Arrays.toString(arr2));

        int[] arr3 = {170, 45, 75, 90, 802, 24, 2, 66};
        radixSort(arr3);
        System.out.println("Radix Sorted: " + Arrays.toString(arr3));
    }
}
