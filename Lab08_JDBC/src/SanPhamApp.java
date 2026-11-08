import java.sql.*;
import java.util.Scanner;

// Bai 1: CRUD bang SanPham (maSP, tenSP, gia, soluong) co menu cho nguoi dung nhap
public class SanPhamApp {
    static final String URL = "jdbc:sqlite:sanpham.db";
    static final Scanner sc = new Scanner(System.in);

    static int docInt(String nhan) {
        while (true) {
            System.out.print(nhan);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Vui long nhap so nguyen!");
            }
        }
    }

    static double docDouble(String nhan) {
        while (true) {
            System.out.print(nhan);
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Vui long nhap so!");
            }
        }
    }

    static void hienThi(ResultSet rs) throws SQLException {
        System.out.println("maSP | tenSP | gia | soluong");
        int dem = 0;
        while (rs.next()) {
            System.out.printf("%d | %s | %.0f | %d%n",
                    rs.getInt("maSP"), rs.getString("tenSP"), rs.getDouble("gia"), rs.getInt("soluong"));
            dem++;
        }
        if (dem == 0)
            System.out.println("(khong co du lieu)");
    }

    // CREATE
    static void them(Connection conn) throws SQLException {
        int ma;
        while (true) {
            ma = docInt("Ma SP: ");
            try (PreparedStatement chk = conn.prepareStatement("SELECT 1 FROM SanPham WHERE maSP = ?")) {
                chk.setInt(1, ma);
                try (ResultSet r = chk.executeQuery()) {
                    if (!r.next())
                        break;
                }
            }
            System.out.println("Ma SP " + ma + " da ton tai, vui long nhap ma khac!");
        }
        System.out.print("Ten SP: ");
        String ten = sc.nextLine().trim();
        double gia = docDouble("Gia: ");
        int sl = docInt("So luong: ");
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO SanPham(maSP, tenSP, gia, soluong) VALUES (?,?,?,?)")) {
            ps.setInt(1, ma);
            ps.setString(2, ten);
            ps.setDouble(3, gia);
            ps.setInt(4, sl);
            ps.executeUpdate();
            System.out.println("Da them san pham.");
        }
    }

    // READ - tat ca
    static void lietKe(Connection conn) throws SQLException {
        try (Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM SanPham")) {
            hienThi(rs);
        }
    }

    // READ - tim theo ten
    static void timKiem(Connection conn) throws SQLException {
        System.out.print("Nhap ten can tim: ");
        String tk = sc.nextLine().trim();
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM SanPham WHERE tenSP LIKE ?")) {
            ps.setString(1, "%" + tk + "%");
            try (ResultSet rs = ps.executeQuery()) {
                hienThi(rs);
            }
        }
    }

    // UPDATE
    static void capNhat(Connection conn) throws SQLException {
        int ma = docInt("Ma SP can sua: ");
        double gia = docDouble("Gia moi: ");
        int sl = docInt("So luong moi: ");
        try (PreparedStatement ps = conn.prepareStatement(
                "UPDATE SanPham SET gia = ?, soluong = ? WHERE maSP = ?")) {
            ps.setDouble(1, gia);
            ps.setInt(2, sl);
            ps.setInt(3, ma);
            int n = ps.executeUpdate();
            System.out.println(n > 0 ? "Da cap nhat." : "Khong tim thay maSP = " + ma);
        }
    }

    // DELETE
    static void xoa(Connection conn) throws SQLException {
        int ma = docInt("Ma SP can xoa: ");
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM SanPham WHERE maSP = ?")) {
            ps.setInt(1, ma);
            int n = ps.executeUpdate();
            System.out.println(n > 0 ? "Da xoa." : "Khong tim thay maSP = " + ma);
        }
    }

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL);
                Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS SanPham ("
                    + "maSP INTEGER PRIMARY KEY, tenSP TEXT NOT NULL, gia REAL, soluong INTEGER)");

            while (true) {
                System.out.println("\n===== QUAN LY SAN PHAM =====");
                System.out.println("1. Them san pham");
                System.out.println("2. Liet ke tat ca");
                System.out.println("3. Tim kiem theo ten");
                System.out.println("4. Cap nhat gia / so luong");
                System.out.println("5. Xoa san pham");
                System.out.println("0. Thoat");
                int chon = docInt("Chon: ");
                try {
                    switch (chon) {
                        case 1 -> them(conn);
                        case 2 -> lietKe(conn);
                        case 3 -> timKiem(conn);
                        case 4 -> capNhat(conn);
                        case 5 -> xoa(conn);
                        case 0 -> {
                            System.out.println("Tam biet!");
                            return;
                        }
                        default -> System.out.println("Lua chon khong hop le!");
                    }
                } catch (SQLException e) {
                    System.out.println("Loi SQL: " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.out.println("Loi ket noi: " + e.getMessage());
        }
    }
}
