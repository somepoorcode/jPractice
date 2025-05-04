public class Main {
    public static int maxSum(int[] arr) {
        int maxSum = 0;

        int currentMax = 0;
        for (int i = 0; i < arr.length; ++i) {
            int currentSum = currentMax + arr[i];
            if (currentSum > arr[i]) {
                currentMax = currentSum;
            }
            else currentMax = arr[i];

            if (currentMax > maxSum) {
                maxSum = currentMax;
            }
        }

        return maxSum;
    }

    public static void main(String[] args) {
        int[] nums = {-10, -20, -40, -10, 5, 30, 100, -90};

        System.out.println("Максимальная сумма подмассива: " + maxSum(nums));
    }
}