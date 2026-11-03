package THLab6.RMI.Bai2;

import java.rmi.Naming;
import java.util.Scanner;

public class TongClient {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            Tong t = (Tong) Naming.lookup("rmi://localhost:1099/TongObj");

            System.out.print("Nhap a: ");
            int a = sc.nextInt();
            System.out.print("Nhap b: ");
            int b = sc.nextInt();

            int kq = t.add(a, b);
            System.out.println("Server tra ve: " + a + " + " + b + " = " + kq);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
