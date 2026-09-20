public class TaiKhoan {
    private double soDu;

    public double getSoDu() {
        return soDu;
    }

    public void napTien(double soTien) {
        if (soTien <= 0) {
            throw new IllegalArgumentException();
        }
        soDu += soTien;
    }
}
