import java.sql.*;
import java.util.Scanner;

// Bai 3: lam lai Bai 1 voi try-catch-finally nghiem ngat.
// Moi ham tu mo Connection/Statement/ResultSet va LUON dong chung trong finally (theo thu tu nguoc lai).
public class SanPhamFinally {
    static final String URL = "jdbc:sqlite:sanpham3.db";
    static final Scanner sc = new Scanner(System.in);

    // ---- cac ham dong tai nguyen an toan (bat loi khi dong) ----
    static void dong(ResultSet rs) {
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            System.out.println("Loi dong ResultSet: " + e.getMessage());
        }
    }

    static void dong(Statement st) {
        try {
            if (st != null)
                st.close();
        } catch (SQLException e) {
            System.out.println("Loi dong Statement: " + e.getMessage());
        }
    }

    static void dong(Connection c) {
        try {
            if (c != null)
                c.close();
        } catch (SQLException e) {
            System.out.println("Loi dong Connection: " + e.getMessage());
        }
    }

    static Connection moKetNoi() throws SQLException {
        return DriverManager.getConnection(URL);
    }

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

    static void inKetQua(ResultSet rs) throws SQLException {
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

    static void taoBang() {
        Connection conn = null;
        Statement st = null;
        try {
            conn = moKetNoi();
            st = conn.createStatement();
            st.executeUpdate("CREATE TABLE IF NOT EXISTS SanPham ("
                    + "maSP INTEGER PRIMARY KEY, tenSP TEXT NOT NULL, gia REAL, soluong INTEGER)");
        } catch (SQLException e) {
            System.out.println("Loi tao bang: " + e.getMessage());
        } finally {
            dong(st);
            dong(conn);
        }
    }

    // CREATE
    static void them() {
        int ma = docInt("Ma SP: ");
        System.out.print("Ten SP: ");
        String ten = sc.nextLine().trim();
        double gia = docDouble("Gia: ");
        int sl = docInt("So luong: ");

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = moKetNoi();
            ps = conn.prepareStatement("INSERT INTO SanPham(maSP, tenSP, gia, soluong) VALUES (?,?,?,?)");
            ps.setInt(1, ma);
            ps.setString(2, ten);
            ps.setDouble(3, gia);
            ps.setInt(4, sl);
            ps.executeUpdate();
            System.out.println("Da them san pham.");
        } catch (SQLException e) {
            if (e.getMessage() != null && e.getMessage().contains("PRIMARYKEY"))
                System.out.println("Ma SP " + ma + " da ton tai!");
            else
                System.out.println("Loi them: " + e.getMessage());
        } finally {
            dong(ps);
            dong(conn);
        }
    }

    // READ - tat ca
    static void lietKe() {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = moKetNoi();
            st = conn.createStatement();
            rs = st.executeQuery("SELECT * FROM SanPham");
            inKetQua(rs);
        } catch (SQLException e) {
            System.out.println("Loi doc: " + e.getMessage());
        } finally {
            dong(rs);
            dong(st);
            dong(conn);
        }
    }

    // READ - tim theo ten
    static void timKiem() {
        System.out.print("Nhap ten can tim: ");
        String tk = sc.nextLine().trim();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = moKetNoi();
            ps = conn.prepareStatement("SELECT * FROM SanPham WHERE tenSP LIKE ?");
            ps.setString(1, "%" + tk + "%");
            rs = ps.executeQuery();
            inKetQua(rs);
        } catch (SQLException e) {
            System.out.println("Loi tim kiem: " + e.getMessage());
        } finally {
            dong(rs);
            dong(ps);
            dong(conn);
        }
    }

    // UPDATE
    static void capNhat() {
        int ma = docInt("Ma SP can sua: ");
        double gia = docDouble("Gia moi: ");
        int sl = docInt("So luong moi: ");
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = moKetNoi();
            ps = conn.prepareStatement("UPDATE SanPham SET gia = ?, soluong = ? WHERE maSP = ?");
            ps.setDouble(1, gia);
            ps.setInt(2, sl);
            ps.setInt(3, ma);
            int n = ps.executeUpdate();
            System.out.println(n > 0 ? "Da cap nhat." : "Khong tim thay maSP = " + ma);
        } catch (SQLException e) {
            System.out.println("Loi cap nhat: " + e.getMessage());
        } finally {
            dong(ps);
            dong(conn);
        }
    }

    // DELETE
    static void xoa() {
        int ma = docInt("Ma SP can xoa: ");
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = moKetNoi();
            ps = conn.prepareStatement("DELETE FROM SanPham WHERE maSP = ?");
            ps.setInt(1, ma);
            int n = ps.executeUpdate();
            System.out.println(n > 0 ? "Da xoa." : "Khong tim thay maSP = " + ma);
        } catch (SQLException e) {
            System.out.println("Loi xoa: " + e.getMessage());
        } finally {
            dong(ps);
            dong(conn);
        }
    }

    // Co y gay loi SQL (bang khong ton tai) de chung minh finally van dong tai
    // nguyen
    static void thuGayLoi() {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = moKetNoi();
            st = conn.createStatement();
            rs = st.executeQuery("SELECT * FROM BangKhongTonTai");
            inKetQua(rs);
        } catch (SQLException e) {
            System.out.println("Bat duoc loi SQL: " + e.getMessage());
        } finally {
            System.out.println("[finally] Dang dong ResultSet, Statement, Connection...");
            dong(rs);
            dong(st);
            dong(conn);
            try {
                System.out.println("[finally] Connection da dong? " + (conn == null || conn.isClosed()));
            } catch (SQLException e) {
                System.out.println("Loi kiem tra: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("Khong tim thay driver: " + e.getMessage());
            return;
        }
        taoBang();
        while (true) {
            System.out.println("\n===== QUAN LY SAN PHAM (try-catch-finally) =====");
            System.out.println("1. Them san pham");
            System.out.println("2. Liet ke tat ca");
            System.out.println("3. Tim kiem theo ten");
            System.out.println("4. Cap nhat gia / so luong");
            System.out.println("5. Xoa san pham");
            System.out.println("6. Thu gay loi SQL (kiem tra finally)");
            System.out.println("0. Thoat");
            int chon = docInt("Chon: ");
            switch (chon) {
                case 1 -> them();
                case 2 -> lietKe();
                case 3 -> timKiem();
                case 4 -> capNhat();
                case 5 -> xoa();
                case 6 -> thuGayLoi();
                case 0 -> {
                    System.out.println("Tam biet!");
                    return;
                }
                default -> System.out.println("Lua chon khong hop le!");
            }
        }
    }
}
