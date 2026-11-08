import java.sql.*;

// Bai 2: Transaction - chuyen hang ton kho tu san pham nay sang san pham khac.
// Hai lenh UPDATE (tru kho nguon, cong kho dich) phai cung thanh cong hoac cung bi huy.
public class GiaoDichApp {
    static final String URL = "jdbc:sqlite:giaodich.db";

    static void hienThi(Connection conn, String tieuDe) throws SQLException {
        System.out.println("-- " + tieuDe);
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT maSP, tenSP, soluong FROM SanPham ORDER BY maSP")) {
            while (rs.next()) {
                System.out.println("   " + rs.getInt("maSP") + " | " + rs.getString("tenSP")
                        + " | soluong = " + rs.getInt("soluong"));
            }
        }
    }

    // Chuyen soLuong tu san pham tuMa sang denMa trong 1 transaction
    static void chuyenHang(Connection conn, int tuMa, int denMa, int soLuong) {
        System.out.println("\n>>> Chuyen " + soLuong + " tu SP " + tuMa + " sang SP " + denMa);
        try {
            conn.setAutoCommit(false); // tat auto-commit: bat dau transaction

            try (PreparedStatement tru = conn.prepareStatement(
                         "UPDATE SanPham SET soluong = soluong - ? WHERE maSP = ?");
                 PreparedStatement cong = conn.prepareStatement(
                         "UPDATE SanPham SET soluong = soluong + ? WHERE maSP = ?")) {

                tru.setInt(1, soLuong);
                tru.setInt(2, tuMa);
                if (tru.executeUpdate() == 0) throw new SQLException("Khong co SP " + tuMa);

                // Kiem tra ton kho sau khi tru (khong cho am)
                try (PreparedStatement kt = conn.prepareStatement("SELECT soluong FROM SanPham WHERE maSP = ?")) {
                    kt.setInt(1, tuMa);
                    try (ResultSet rs = kt.executeQuery()) {
                        if (rs.next() && rs.getInt(1) < 0)
                            throw new SQLException("Khong du hang ton kho de chuyen");
                    }
                }

                cong.setInt(1, soLuong);
                cong.setInt(2, denMa);
                if (cong.executeUpdate() == 0) throw new SQLException("Khong co SP " + denMa);
            }

            conn.commit(); // tat ca thanh cong -> luu
            System.out.println("COMMIT: giao dich thanh cong.");
        } catch (SQLException e) {
            System.out.println("LOI: " + e.getMessage() + " -> ROLLBACK, huy toan bo thay doi.");
            try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException e) { }
        }
    }

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement st = conn.createStatement()) {

            // Du lieu ban dau (moi lan chay tao lai)
            st.executeUpdate("DROP TABLE IF EXISTS SanPham");
            st.executeUpdate("CREATE TABLE SanPham (maSP INTEGER PRIMARY KEY, tenSP TEXT NOT NULL, gia REAL, soluong INTEGER)");
            st.executeUpdate("INSERT INTO SanPham VALUES (1, 'Laptop', 15000000, 10)");
            st.executeUpdate("INSERT INTO SanPham VALUES (2, 'Chuot', 250000, 50)");
            hienThi(conn, "Ban dau");

            // Truong hop 1: thanh cong -> commit
            chuyenHang(conn, 2, 1, 20);
            hienThi(conn, "Sau giao dich 1 (ky vong: SP1 = 30, SP2 = 30)");

            // Truong hop 2: loi giua chung -> rollback (SP 99 khong ton tai, buoc tru kho da chay roi)
            chuyenHang(conn, 2, 99, 5);
            hienThi(conn, "Sau giao dich 2 (ky vong: khong doi, SP2 van = 30)");

            // Truong hop 3: vuot ton kho -> rollback
            chuyenHang(conn, 1, 2, 1000);
            hienThi(conn, "Sau giao dich 3 (ky vong: khong doi)");

        } catch (SQLException e) {
            System.out.println("Loi ket noi: " + e.getMessage());
        }
    }
}
