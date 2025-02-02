import java.util.Scanner;


public class MyClass {
  public static void main(String args[]) {
    Scanner userInput = new Scanner(System.in);

    System.out.println("Введите координаты:");
    int treasureX = userInput.nextInt();
    int treasureY = userInput.nextInt();


    String[] directions = new String[100];
    int[] steps = new int[100];
    int instructionsCount = 0;


    System.out.println("Введите указания:");
    while (true) {
        String direction = userInput.next();
        if (direction.equals("стоп")) {
            break;
        }
        int step = userInput.nextInt();
        directions[instructionsCount] = direction;
        steps[instructionsCount] = step;
        instructionsCount++;
    }


    int currentX = 0;
    int currentY = 0;
    int counter = 0;
    for (int i = 0; i < instructionsCount; i++) {
        switch (directions[i]) {
            case "восток":
                currentX += steps[i];
                break;
            case "запад":
                currentX -= steps[i];
                break;
            case "север":
                currentY += steps[i];
                break;
            case "юг":
                currentY -= steps[i];
                break;
        }
        System.out.println("Прошли по указанию на " + directions[i] + " " + steps[i] + ": (" + currentX + ", " + currentY + ")");
        counter++;
        if (currentX == treasureX && currentY == treasureY) {
            System.out.println("Клад найден! Количество указаний:");
            System.out.println(counter);
            break;
        }
    }
  }
}
