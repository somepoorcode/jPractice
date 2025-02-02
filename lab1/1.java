import java.util.Scanner;

public class MyClass {
  public static void main(String args[]) {
    Scanner myObj = new Scanner(System.in);
    System.out.println("Enter your number");
    int number = myObj.nextInt();
    
    int counter = 0;
    while (number != 1) {
        if (number % 2 == 0) {
            number = number / 2;
            counter += 1;
        }
        else {
            number = number * 3 + 1;
            counter += 1;
        };
    };
    
    System.out.println("Number of steps = " + counter);
  }
}