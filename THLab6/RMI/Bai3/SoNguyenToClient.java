package THLab6.RMI.Bai3;

import java.rmi.Naming;
import java.util.Scanner;

public class SoNguyenToClient {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            SoNguyenTo s = (SoNguyenTo) Naming.lookup("rmi://localhost:1099/SoNguyenToObj");

            while (true) {
                System.out.print("Nhap n (so am de thoat): ");
                int n = sc.nextInt();
                if (n < 0) {
                    break;
                }

                if (s.isPrime(n)) {
                    System.out.println(n + " la so nguyen to");
                } else {
                    System.out.println(n + " khong phai so nguyen to");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
