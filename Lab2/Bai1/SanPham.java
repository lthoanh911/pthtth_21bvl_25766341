package Lab2.Bai1;

public class SanPham {
    private String maSP;
    private String tenSP;
    private double donGia;
    private int soLuongTon;

    // Constructor đầy đủ tham số
    public SanPham(String maSP, String tenSP, double donGia, int soLuongTon) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.donGia = donGia;
        this.soLuongTon = soLuongTon;
    }

    // Getters & Setters
    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    // Phương thức tính thành tiền
    public double tinhThanhTien() {
        return this.donGia * this.soLuongTon;
    }

    // Nhập hàng
    public void nhapHang(int soLuongNhap) {
        if (soLuongNhap > 0) {
            this.soLuongTon += soLuongNhap;
            System.out.println("-> Nhập hàng thành công! Đã thêm: " + soLuongNhap);
        } else {
            System.out.println("-> Số lượng nhập không hợp lệ (phải lớn hơn 0).");
        }
    }

    // Bán hàng
    public boolean banHang(int soLuongBan) {
        if (soLuongBan > 0 && soLuongBan <= this.soLuongTon) {
            this.soLuongTon -= soLuongBan;
            System.out.println("-> Bán hàng thành công! Đã bán: " + soLuongBan);
            return true;
        } else {
            System.out.println("-> Bán hàng thất bại! Số lượng tồn không đủ hoặc số lượng bán không hợp lệ.");
            return false;
        }
    }

    // Hiển thị thông tin
    public void hienThiThongTin() {
        System.out.printf("Mã SP: %s | Tên: %-15s | Đơn giá: %,.0f VND | Tồn kho: %-3d | Thành tiền: %,.0f VND%n",
                maSP, tenSP, donGia, soLuongTon, tinhThanhTien());
    }
}