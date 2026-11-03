package THLab6.RMI.Bai6;

import java.rmi.Naming;
import java.util.Scanner;

public class TaiKhoanClient {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            TaiKhoan tk = (TaiKhoan) Naming.lookup("rmi://localhost:1099/TaiKhoanObj");

            while (true) {
                System.out.println("\n===== NGAN HANG =====");
                System.out.println("1. Kiem tra so du");
                System.out.println("2. Gui tien");
                System.out.println("3. Rut tien");
                System.out.println("0. Thoat");
                System.out.print("Chon: ");
                int chon = Integer.parseInt(sc.nextLine());

                if (chon == 0) {
                    break;
                }

                switch (chon) {
                    case 1:
                        System.out.printf("So du: %,.0f VND%n", tk.getBalance());
                        break;
                    case 2:
                        System.out.print("Nhap so tien gui: ");
                        double gui = Double.parseDouble(sc.nextLine());
                        if (gui <= 0) {
                            System.out.println("So tien phai lon hon 0!");
                        } else {
                            tk.deposit(gui);
                            System.out.printf("Gui thanh cong! So du: %,.0f VND%n", tk.getBalance());
                        }
                        break;
                    case 3:
                        System.out.print("Nhap so tien rut: ");
                        double rut = Double.parseDouble(sc.nextLine());
                        if (tk.withdraw(rut)) {
                            System.out.printf("Rut thanh cong! So du: %,.0f VND%n", tk.getBalance());
                        } else {
                            System.out.println("Rut that bai! So tien khong hop le hoac vuot qua so du.");
                        }
                        break;
                    default:
                        System.out.println("Lua chon khong hop le!");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
