package THLab6.RMI.Bai9;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Scanner;

public class DauGiaClient extends UnicastRemoteObject implements DauGiaCallback {

    public DauGiaClient() throws RemoteException {
        super();
    }

    // Server goi ham nay moi khi co san pham moi hoac gia thau moi
    @Override
    public void nhanThongBao(String message) throws RemoteException {
        System.out.println("\n*** THONG BAO: " + message);
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.setProperty("java.rmi.server.hostname", "127.0.0.1"); // Client cung export doi tuong (callback)

            DauGia dg = (DauGia) Naming.lookup("rmi://localhost:1099/DauGiaObj");
            dg.registerClient(new DauGiaClient());

            System.out.print("Nhap ten cua ban: ");
            String ten = sc.nextLine().trim();

            while (true) {
                System.out.println("\n===== DAU GIA TRUC TUYEN (" + ten + ") =====");
                System.out.println("1. Them san pham dau gia (Seller)");
                System.out.println("2. Xem danh sach san pham");
                System.out.println("3. Dat gia thau (Bidder)");
                System.out.println("0. Thoat");
                System.out.print("Chon: ");
                int chon = Integer.parseInt(sc.nextLine());

                if (chon == 0) {
                    break;
                }

                switch (chon) {
                    case 1:
                        System.out.print("Nhap ten san pham: ");
                        String tenSp = sc.nextLine();
                        System.out.print("Nhap gia khoi diem: ");
                        double giaKd = Double.parseDouble(sc.nextLine());
                        if (dg.addProduct(tenSp, ten, giaKd)) {
                            System.out.println("Them san pham thanh cong!");
                        } else {
                            System.out.println("That bai! San pham da ton tai hoac gia khong hop le.");
                        }
                        break;
                    case 2:
                        List<SanPham> ds = dg.getProducts();
                        if (ds.isEmpty()) {
                            System.out.println("Chua co san pham nao.");
                        }
                        for (SanPham sp : ds) {
                            System.out.println(sp);
                        }
                        break;
                    case 3:
                        System.out.print("Nhap ten san pham: ");
                        String spBid = sc.nextLine();
                        System.out.print("Nhap gia thau: ");
                        double gia = Double.parseDouble(sc.nextLine());
                        if (dg.bid(spBid, ten, gia)) {
                            System.out.println("Dat gia thanh cong!");
                        } else {
                            System.out.println("That bai! Khong co san pham hoac gia phai cao hon gia hien tai.");
                        }
                        break;
                    default:
                        System.out.println("Lua chon khong hop le!");
                }
            }
            System.exit(0); // doi tuong callback van dang export nen phai thoat thu cong

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
