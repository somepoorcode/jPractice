import java.util.Arrays;

public class Main {
    public static int[][] rotateRight(int[][] mat) {
        int[][] rotatedMat = new int[mat[0].length][mat.length];

        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                rotatedMat[j][mat.length - i - 1] = mat[i][j];
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
        print2D(rotateRight(nums));
    }
}