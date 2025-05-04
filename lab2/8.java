import java.util.Arrays;

public class Main {
    public static int[][] rotateLeft(int[][] mat) {
        int[][] rotatedMat = new int[mat[0].length][mat.length];

        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                rotatedMat[mat[0].length - j - 1][i] = mat[i][j];
            }
        }

        return rotatedMat;
    }


    public static void print2D(int[][] mat){
        for (int[] row : mat) System.out.println(Arrays.toString(row));
    }


    public static void main(String[] args) {
        int[][] nums = {
                {1, 2, 3},
                {4, 5, 6}
        };

        System.out.println("Перевёрнутый двумерный массив: ");
        print2D(rotateLeft(nums));
    }
}