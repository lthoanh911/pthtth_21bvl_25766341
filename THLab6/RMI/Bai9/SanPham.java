package THLab6.RMI.Bai9;

import java.io.Serializable;

// Gui qua mang nen phai Serializable
public class SanPham implements Serializable {
    private String ten;
    private String nguoiBan;
    private double giaHienTai;
    private String nguoiDatCao; // null neu chua co ai dat gia

    public SanPham(String ten, String nguoiBan, double giaKhoiDiem) {
        this.ten = ten;
        this.nguoiBan = nguoiBan;
        this.giaHienTai = giaKhoiDiem;
    }

    public String getTen() {
        return ten;
    }

    public String getNguoiBan() {
        return nguoiBan;
    }

    public double getGiaHienTai() {
        return giaHienTai;
    }

    public String getNguoiDatCao() {
        return nguoiDatCao;
    }

    public void datGia(String nguoiDat, double gia) {
        this.nguoiDatCao = nguoiDat;
        this.giaHienTai = gia;
    }

    @Override
    public String toString() {
        return String.format("%-15s | Nguoi ban: %-8s | Gia hien tai: %,.0f | Nguoi dat cao nhat: %s",
                ten, nguoiBan, giaHienTai, nguoiDatCao == null ? "(chua co)" : nguoiDatCao);
    }
}
