import java.util.Scanner;

public class Simple_Interest {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter Principal Amount (in ₹): ");
            int P = scanner.nextInt();
            System.out.print("Enter Rate of Interest (in %): ");
            float R = scanner.nextFloat();
            System.out.print("Enter Time Period (in years): ");
            int T = scanner.nextInt();
            float SI = (P * R * T) / 100;  // Simple Interest
            System.out.println("Simple Interest for the given amount is: Rs." + SI);
        }
    }
}