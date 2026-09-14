package Lab2.Bai2;

public class Main {
    public static void main(String[] args) {
        // Tạo 2 sinh viên
        SinhVien sv1 = new SinhVien("Lê Thị Hoàng Oanh", 1998, "TP.HCM", "SV001", "Công nghệ thông tin", 8.8);
        SinhVien sv2 = new SinhVien("Trần Thị Bình", 2005, "Bình Dương", "SV002", "Khoa học dữ liệu", 6.8);

        // Tạo 2 giảng viên
        GiangVien gv1 = new GiangVien("Lê Hoàng Nam", 1982, "Đồng Nai", "GV01", "Lập trình Java", 5000000, 3.5);
        GiangVien gv2 = new GiangVien("Phạm Minh Tuấn", 1978, "TP.HCM", "GV02", "Mạng máy tính", 5500000, 4.0);

        System.out.println("=== DANH SÁCH SINH VIÊN ===");
        sv1.hienThiThongTin();
        sv2.hienThiThongTin();

        System.out.println("\n=== DANH SÁCH GIẢNG VIÊN ===");
        gv1.hienThiThongTin();
        gv2.hienThiThongTin();
    }
}