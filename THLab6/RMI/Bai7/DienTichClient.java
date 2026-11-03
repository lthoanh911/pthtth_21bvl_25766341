package THLab6.RMI.Bai7;

import java.rmi.Naming;
import java.util.Scanner;

public class DienTichClient {

    // Nhap so duong, nhap sai thi bat nhap lai
    private static double nhapSo(Scanner sc, String thongBao) {
        while (true) {
            System.out.print(thongBao);
            try {
                double x = Double.parseDouble(sc.nextLine());
                if (x > 0) {
                    return x;
                }
                System.out.println("Gia tri phai lon hon 0!");
            } catch (NumberFormatException e) {
                System.out.println("Vui long nhap so!");
            }
        }
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            DienTich dt = (DienTich) Naming.lookup("rmi://localhost:1099/DienTichObj");

            while (true) {
                System.out.println("\n===== TINH DIEN TICH =====");
                System.out.println("1. Hinh chu nhat");
                System.out.println("2. Hinh tron");
                System.out.println("3. Hinh tam giac");
                System.out.println("0. Thoat");
                System.out.print("Chon: ");
                int chon = Integer.parseInt(sc.nextLine());

                if (chon == 0) {
                    break;
                }

                switch (chon) {
                    case 1:
                        double w = nhapSo(sc, "Nhap chieu rong: ");
                        double h = nhapSo(sc, "Nhap chieu dai: ");
                        System.out.printf("Dien tich hinh chu nhat: %.2f%n", dt.rectangleArea(w, h));
                        break;
                    case 2:
                        double r = nhapSo(sc, "Nhap ban kinh: ");
                        System.out.printf("Dien tich hinh tron: %.2f%n", dt.circleArea(r));
                        break;
                    case 3:
                        double a = nhapSo(sc, "Nhap canh day: ");
                        double c = nhapSo(sc, "Nhap chieu cao: ");
                        System.out.printf("Dien tich tam giac: %.2f%n", dt.triangleArea(a, c));
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
