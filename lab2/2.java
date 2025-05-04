import java.util.Arrays;

public class Main {
    public static int[] uniteArrays(int[] arr1, int[] arr2) {
        int[] arr3 = new int[arr1.length + arr2.length];
        for (int i = 0; i < (arr1.length + arr2.length); ++i) {
            if (i < arr1.length) arr3[i] = arr1[i];
            else arr3[i] = arr2[i - arr1.length];
        }

        Arrays.sort(arr3);
        return arr3;
    }

    public static void main(String[] args) {
        int[] nums1 = {10, 20, 40};
        int[] nums2 = {30, 50, 60};
        int[] nums3 = uniteArrays(nums1, nums2);

        System.out.println("Отстортированный объединённый массив: " + Arrays.toString(nums3));
    }
}