import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static int[] findMaxInRows(int[][] mat) {
        int rows = mat.length;
        int[] result = new int[rows];
        for (int i = 0; i < rows; i++) {
            int max = mat[i][0];
            for (int j = 1; j < mat[i].length; j++) {
                if (mat[i][j] > max) {
                    max = mat[i][j];
                }
            }
            result[i] = max;
        }
        return result;
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

        int[] maxInRows = findMaxInRows(mat);
        System.out.println("Максимальные значения в каждой строке: ");
        System.out.println(Arrays.toString(maxInRows));
    }
}