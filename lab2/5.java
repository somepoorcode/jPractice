import java.util.Scanner;

public class Main {
    public static int[] findTarget(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (arr[i] + arr[j] == target) {
                    return new int[] {arr[i], arr[j]};
                }
            }
        }

        return null;
    }

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        System.out.println("Введите элементы массива через пробел: ");
        String[] userNums = scn.nextLine().trim().split(" ");
        int[] nums = new int[userNums.length];
        for (int i = 0; i < userNums.length; i++) {
            nums[i] = Integer.parseInt(userNums[i]);
        }

        System.out.println("Введите целевое значение: ");
        int target = scn.nextInt();

        int[] sumNums = findTarget(nums, target);
        System.out.println("Пара элементов при складывании возвращающая сумму " + target + ": ");
        System.out.println(sumNums[0] + " " + sumNums[1]);

    }
}