import java.util.Scanner;


public class MyClass {
  public static void main(String args[]) {
    Scanner userInput = new Scanner(System.in);
    
    
    System.out.println("Введите кол-во дорог:");
    int roadsCount = userInput.nextInt();
    
    
    int[] tunnelsCount = new int[roadsCount];
    int[][] tunnelsHeights = new int[roadsCount][100];
    for (int i = 0; i < roadsCount; i++) {
        System.out.println("Введите кол-во туннелей для дороги номер " + (i + 1) + ": ");
        tunnelsCount[i] = userInput.nextInt();
        System.out.println("Введите высоту туннелей: ");
        tunnelsHeights[i] = new int[tunnelsCount[i]];
        for (int j = 0; j < tunnelsCount[i]; j++)
            tunnelsHeights[i][j] = userInput.nextInt();
    }
    
    
    int chosenRoad = 0;
    int minMaxHeight = 0;
    for (int i = 0; i < roadsCount; i++) {
        int minHeight = 10000;
        for (int j = 0; j < tunnelsCount[i]; j++) {
            if (tunnelsHeights[i][j] < minHeight)
                minHeight = tunnelsHeights[i][j];
        }
        if (minHeight > minMaxHeight) {
            minMaxHeight = minHeight;
            chosenRoad = i + 1;
        }
    }
    
    
    System.out.println("Выбрано: ");
    System.out.println(chosenRoad + " " + minMaxHeight);
  }
}
