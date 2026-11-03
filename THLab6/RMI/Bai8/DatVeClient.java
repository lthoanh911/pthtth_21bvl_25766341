package THLab6.RMI.Bai8;

import java.rmi.Naming;
import java.util.Scanner;

public class DatVeClient {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            DatVe dv = (DatVe) Naming.lookup("rmi://localhost:1099/DatVeObj");

            while (true) {
                System.out.println("\n===== DAT VE MAY BAY =====");
                System.out.println("Chuyen bay: VN123, VJ456, QH789");
                System.out.println("1. Kiem tra ghe trong");
                System.out.println("2. Dat ve");
                System.out.println("3. Huy ve");
                System.out.println("0. Thoat");
                System.out.print("Chon: ");
                int chon = Integer.parseInt(sc.nextLine());

                if (chon == 0) {
                    break;
                }

                System.out.print("Nhap ma chuyen bay: ");
                String ma = sc.nextLine();

                switch (chon) {
                    case 1:
                        int trong = dv.availableSeats(ma);
                        if (trong == -1) {
                            System.out.println("Khong co chuyen bay " + ma);
                        } else {
                            System.out.println("Chuyen bay " + ma + " con " + trong + " ghe trong");
                        }
                        break;
                    case 2:
                        System.out.print("Nhap so ghe can dat: ");
                        int dat = Integer.parseInt(sc.nextLine());
                        if (dv.bookTicket(ma, dat)) {
                            System.out.println("Dat ve thanh cong! Con " + dv.availableSeats(ma) + " ghe trong");
                        } else {
                            System.out.println("Dat ve that bai! Sai ma chuyen bay hoac khong du ghe.");
                        }
                        break;
                    case 3:
                        System.out.print("Nhap so ghe can huy: ");
                        int huy = Integer.parseInt(sc.nextLine());
                        if (dv.cancelBooking(ma, huy)) {
                            System.out.println("Huy ve thanh cong! Con " + dv.availableSeats(ma) + " ghe trong");
                        } else {
                            System.out.println("Huy ve that bai! Sai ma chuyen bay hoac so ghe huy vuot qua so ghe da dat.");
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
