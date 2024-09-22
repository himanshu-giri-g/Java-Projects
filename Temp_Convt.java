import java.util.Scanner;

public class Temp_Convt {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("---WELCOME TO TEMPERATURE CONVERTER---");
            System.out.println("1. Celsius to Kelvin\n2. Kelvin to Celsius");

            int a = scanner.nextInt();
            switch (a) {
                case 1 ->                 {
                        System.out.print("Enter Temperature to be converted (in Celsius): ");
                        int temp = scanner.nextInt();
                        float convT = celsiusToKelvin(temp);
                        System.out.println("The Temperature in Kelvin for the given temperature is: " + convT + " K");
                    }
                case 2 ->                 {
                        System.out.print("Enter Temperature to be converted (in Kelvin): ");
                        int temp = scanner.nextInt();
                        float convT = kelvinToCelsius(temp);
                        System.out.println("The Temperature in Celsius for the given temperature is: " + convT + " C");
                    }
                default -> System.out.println("ERROR ! Please Choose a valid option.");
            }
        }
    }

    public static float celsiusToKelvin(int temp) {
        return temp + 273;
    }

    public static float kelvinToCelsius(int temp) {
        return temp - 273;
    }
}