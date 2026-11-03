package THLab6.RMI.Bai4;

import java.rmi.Naming;
import java.util.Scanner;

public class DanhBaClient {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            DanhBa db = (DanhBa) Naming.lookup("rmi://localhost:1099/DanhBaObj");

            while (true) {
                System.out.println("\n===== QUAN LY DANH BA =====");
                System.out.println("1. Them lien he");
                System.out.println("2. Sua lien he");
                System.out.println("3. Tim lien he");
                System.out.println("4. Xoa lien he");
                System.out.println("0. Thoat");
                System.out.print("Chon: ");
                int chon = Integer.parseInt(sc.nextLine());

                if (chon == 0) {
                    break;
                }

                System.out.print("Nhap ten: ");
                String name = sc.nextLine();

                switch (chon) {
                    case 1:
                        System.out.print("Nhap so dien thoai: ");
                        db.addContact(name, sc.nextLine());
                        System.out.println("Da them!");
                        break;
                    case 2:
                        if (db.findContact(name) == null) {
                            System.out.println("Khong tim thay " + name);
                        } else {
                            System.out.print("Nhap so dien thoai moi: ");
                            db.addContact(name, sc.nextLine());
                            System.out.println("Da sua!");
                        }
                        break;
                    case 3:
                        String phone = db.findContact(name);
                        if (phone == null) {
                            System.out.println("Khong tim thay " + name);
                        } else {
                            System.out.println(name + ": " + phone);
                        }
                        break;
                    case 4:
                        if (db.deleteContact(name)) {
                            System.out.println("Da xoa " + name);
                        } else {
                            System.out.println("Khong tim thay " + name);
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
