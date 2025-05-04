import java.util.Scanner;

public class Main {
    public static String findLongestStr(String s){
        String longestStr = "";
        int maxNum = 0;

        int currentNum = 0;
        String currentStr = "";
        for (int i = 0; i <= (s.length() - 2); ++i)
        {
            if (currentStr.isEmpty()) currentStr += s.charAt(i);

            if ((s.charAt(i) != s.charAt(i+1)) && !(currentStr.contains(Character.toString(s.charAt(i+1))))) {
                currentNum += 1;
                currentStr += s.charAt(i + 1);
                if (currentNum > maxNum) {
                    maxNum = currentNum;
                    longestStr = currentStr;
                }
            }
            else {
                currentNum = 0;
                currentStr = "";
            }

        }

        return longestStr;
    }

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        System.out.print("Введите строку: ");
        String s = scn.nextLine();

        System.out.println("В строке " + s + " наибольшая уникальная подстрока:\n" + findLongestStr(s));
    }
}