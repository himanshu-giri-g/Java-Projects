import java.util.Scanner;

public class Armstrong {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter a Number: ");
            int a = scanner.nextInt();
            String b = String.valueOf(a);
            int c = b.length();
            int sum = 0;
            for (char i : b.toCharArray()) {
                sum += Math.pow(Character.getNumericValue(i), c);
            }
            if (sum == a) {
                System.out.println("The No. " + a + " is an Armstrong Number.");
            } else {
                System.out.println("The No. " + a + " is not an Armstrong Number.");
            }
        }
    }
}