package Lab2.Bai1;

public class Main {
    public static void main(String[] args) {
        // 1. Tạo ít nhất 2 sản phẩm
        SanPham sp1 = new SanPham("SP01", "Bàn phím cơ", 850000, 10);
        SanPham sp2 = new SanPham("SP02", "Chuột không dây", 350000, 5);

        System.out.println("=== THÔNG TIN BAN ĐẦU ===");
        sp1.hienThiThongTin();
        sp2.hienThiThongTin();

        // 2. Nhập thêm hàng cho sp1
        System.out.println("\n=== THỰC HIỆN NHẬP HÀNG (SP01) ===");
        sp1.nhapHang(5);
        sp1.hienThiThongTin();

        // 3. Thử bán hàng thành công với sp2
        System.out.println("\n=== BÁN HÀNG THÀNH CÔNG (SP02) ===");
        sp2.banHang(3);
        sp2.hienThiThongTin();

        // 4. Thử bán vượt quá số lượng tồn kho với sp2
        System.out.println("\n=== BÁN VƯỢT QUÁ TỒN KHO (SP02) ===");
        sp2.banHang(10);
        sp2.hienThiThongTin();
    }
}