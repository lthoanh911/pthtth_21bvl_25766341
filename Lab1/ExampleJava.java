
import java.util.Scanner;

public class ExampleJava {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Vui lòng nhập số hạng thứ 1: ");
        int soA = scanner.nextInt();
        System.out.print("Vui lòng nhập số hạng thứ 2: ");
        int soB = scanner.nextInt();
        int kq = soA + soB;
        System.out.println("Tính tổng " + soA + " + " + soB + " = " + kq);
        scanner.close();
    }
}