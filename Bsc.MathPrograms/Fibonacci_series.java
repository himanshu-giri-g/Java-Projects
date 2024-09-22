public class Fibonacci_series {
    public static void main(String[] args) {
        int n = 10;
        int num1;
        int num2 = 1;
        int nextNumber = num2;
        int count = 1;
        while (count <= n) {
            System.out.print(nextNumber + " ");
            count++;
            num1 = num2;
            num2 = nextNumber;
            nextNumber = num1 + num2;
        }
        System.out.println();
    }
}