import java.util.Scanner;

public class Palindrome {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter a word: ");
            String strN = scanner.next();
            String reversedStrN = new StringBuilder(strN).reverse().toString();
            if (strN.equals(reversedStrN)) {
                System.out.println("The word " + strN + " is a Palindrome.");
            } else {
                System.out.println("The word " + strN + " is not a Palindrome.");
            }
        }
    }
}