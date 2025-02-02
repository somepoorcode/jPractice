import java.util.Scanner;


public class MyClass {
  public static void main(String args[]) {
    Scanner userInput = new Scanner(System.in);
    
    
    System.out.println("Введите число:");
    int number = userInput.nextInt();
    
    
    int[] digits = new int[3];
    for (int i = 0; i < 3; i++) {
        digits[i] = number % 10;
        number = number / 10;
    }
    
    if ((digits[0] * digits [1] * digits[2]) == (digits[0] + digits [1] + digits[2]))
        System.out.println("Число дважды чётное");
    else 
        System.out.println("Число не дважды чётное");
    
  }
} 
