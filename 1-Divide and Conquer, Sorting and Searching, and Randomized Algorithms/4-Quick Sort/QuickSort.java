import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class QuickSort {

    /**
     * Sorts the given array using Quick Sort.
     * @param nums array to sort
     */
    public void quickSort(int[] nums) {
        // Check whether the input array is null or empty
        if ((nums == null) || (nums.length == 0)) {
            return;
        }

        quickSortHelper(nums, 0, nums.length - 1);
    }

    /**
     * Private helper method to sort the given sub-array recursively using
     * QuickSort.
     * @param nums array to sort
     * @param left left bound
     * @param right right bound
     */
    private void quickSortHelper(int[] nums, int left, int right) {
        // Base case
        if (left >= right) {
            return;
        }
        // Recursive case
        // Choose a pivot from the given sub-array, and move it to the left
        choosePivot(nums, left, right, true);
        int pivotIdx = partition(nums, left, right);
        quickSortHelper(nums, left, pivotIdx - 1);
        quickSortHelper(nums, pivotIdx + 1, right);
    }

    /**
     * Helper method to choose a pivot from the given sub-array, and move it to
     * the left.
     * @param nums array to sort
     * @param left left bound
     * @param right right bound
     * @param randomly whether to randomly choose the pivot
     */
    private void choosePivot(int[] nums, int left, int right, boolean randomly) {
        int pivotIdx = 0;
        if (randomly) {
            // [Randomized] Randomly choose a pivot from the given sub-array
            Random randomGenerator = new Random();
            pivotIdx = left + randomGenerator.nextInt(right + 1 - left);
        } else {
            // [Deterministic] Use the median of medians as the pivot

            // Create the sorted parts
            ArrayList<int[]> sortedParts = new ArrayList<int[]>();
            int i = left;
            while (i <= right) {
                int numOfElems = Math.min(5, right + 1 - left);
                int[] part = new int[numOfElems];
                System.arraycopy(nums, i, part, 0, numOfElems);
                Arrays.sort(part);
                sortedParts.add(part);
                i += numOfElems;
            }
            // Take out the medians of the sorted parts
            int[] medians = new int[sortedParts.size()];
            for (int j = 0;  j < sortedParts.size(); ++j) {
                int[] sortedPart = sortedParts.get(j);
                medians[j] = sortedPart[sortedPart.length / 2];
            }
            // Use the median of medians as the pivot
            Arrays.sort(medians);
            pivotIdx = medians.length / 2;
        }
        // Move the pivot to the left
        if (pivotIdx != left) {
            swap(nums, pivotIdx, left);
        }
    }

    /**
     * Helper method to swap two elements in the given array.
     * @param array given array
     * @param i index of the first element
     * @param j index of the second element
     */
    private void swap(int[] array, int i, int j) {
        int tmp = array[j];
        array[j] = array[i];
        array[i] = tmp;
    }

    /**
     * Helper method to partition the given part of the array.
     * @param nums array to partition
     * @param left left bound 
     * @param right right bound
     * @return
     */
    private int partition(int[] nums, int left, int right) {
        // The pivot has already been moved to the left.
        int pivot = nums[left];

        // Iterate over the sub-array, use a pointer to keep track of the smaller part, and swap the current number with the pointer as necessary
        int smallerPtr = left + 1;
        int i = left + 1;
        while (true) {
            while ((i <= right) && (nums[i] > pivot)) {
                ++i;
            }
            if (i > right) {
                break;
            }
            if (i != smallerPtr) {
                swap(nums, i, smallerPtr);
            }
            ++smallerPtr;
            ++i;
        }
        if (left != (smallerPtr - 1)) {
            swap(nums, left, smallerPtr - 1);
        }
        return smallerPtr - 1;
    }

}