import java.util.Scanner;

public class Main {
    public static int sum2D(int[][] mat) {
        int sum = 0;
        for (int[] row : mat) {
            for (int i : row) {
                sum += i;
            }
        }

        return sum;
    }


    public static int[][] input2D(Scanner scn) {
        System.out.print("Введите число строк: ");
        int rowsNum = scn.nextInt();
        System.out.print("Введите число столбцов: ");
        int colNum = scn.nextInt();

        int[][] mat = new int[rowsNum][colNum];
        System.out.println("Введите элементы матрицы построчно, через пробел:");
        for (int i = 0; i < rowsNum; i++) {
            System.out.print("Строка " + (i + 1) + ": ");
            for (int j = 0; j < colNum; j++) {
                mat[i][j] = scn.nextInt();
            }
        }
        return mat;
    }


    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        int[][] mat = input2D(scn);


        int matSum = sum2D(mat);
        System.out.println("Сумма элементов двумерного массива: " + matSum);

    }
}