import java.util.Scanner;


public class MyClass {
  public static void main(String args[]) {
    Scanner userInput = new Scanner(System.in);
    
    
    System.out.println("Enter your amount of numbers");
    int amount = 0;
    do {
        amount = userInput.nextInt();
        if (amount < 1) 
            System.out.println("Amount shouldn't be lesser than 1");
    } while (amount < 1);
    
    
    System.out.println("Now enter your numbers");
    int [] numbers = new int[amount];
    for (int i = 0; i < amount; i++) 
        numbers[i] = userInput.nextInt();
    
    
    int result = 0;
    for (int i = 0; i < amount; i++) {
        if (i % 2 == 0)
            result += numbers[i];
        else
            result -= numbers[i];
    }
    
    
    System.out.println("Result equals " + result);
  }
}