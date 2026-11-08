import java.sql.*;

// Bai 4: PreparedStatement - tang cuong bao mat (chong SQL Injection) va hieu nang (bien dich 1 lan, dung lai nhieu lan)
public class PreparedDemo {
    static final String URL = "jdbc:sqlite:preparedemo.db";

    static void taoDuLieu(Connection conn) throws SQLException {
        try (Statement st = conn.createStatement()) {
            st.executeUpdate("DROP TABLE IF EXISTS SanPham");
            st.executeUpdate("CREATE TABLE SanPham (maSP INTEGER PRIMARY KEY, tenSP TEXT NOT NULL, gia REAL, soluong INTEGER)");
            st.executeUpdate("INSERT INTO SanPham VALUES (1, 'Laptop', 15000000, 10)");
            st.executeUpdate("INSERT INTO SanPham VALUES (2, 'Chuot', 250000, 50)");
            st.executeUpdate("INSERT INTO SanPham VALUES (3, 'Ban phim', 800000, 30)");
        }
    }

    static int demKetQua(ResultSet rs) throws SQLException {
        int n = 0;
        while (rs.next()) {
            System.out.println("   " + rs.getInt("maSP") + " | " + rs.getString("tenSP"));
            n++;
        }
        return n;
    }

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL)) {
            taoDuLieu(conn);

            // ===== PHAN 1: BAO MAT =====
            // Nguoi dung "nhap" chuoi tan cong nay vao o tim kiem ten san pham:
            String input = "xyz' OR '1'='1";
            System.out.println("Chuoi nguoi dung nhap: " + input);

            System.out.println("\n[1] Dung Statement (noi chuoi) -> BI SQL INJECTION:");
            String sqlNguyHiem = "SELECT * FROM SanPham WHERE tenSP = '" + input + "'";
            System.out.println("   SQL thuc te: " + sqlNguyHiem);
            try (Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery(sqlNguyHiem)) {
                System.out.println("   => Tra ve " + demKetQua(rs) + " dong (lo TOAN BO du lieu!)");
            }

            System.out.println("\n[2] Dung PreparedStatement (tham so ?) -> AN TOAN:");
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM SanPham WHERE tenSP = ?")) {
                ps.setString(1, input);
                try (ResultSet rs = ps.executeQuery()) {
                    System.out.println("   => Tra ve " + demKetQua(rs) + " dong (chuoi tan cong chi la ten thuong)");
                }
            }

            // ===== PHAN 2: HIEU NANG =====
            final int N = 20000;
            conn.createStatement().executeUpdate("DELETE FROM SanPham");
            conn.setAutoCommit(false);

            long t1 = System.nanoTime();
            try (Statement st = conn.createStatement()) {
                for (int i = 1; i <= N; i++)
                    st.executeUpdate("INSERT INTO SanPham VALUES (" + i + ", 'SP" + i + "', 1000, 1)");
            }
            conn.commit();
            long msStatement = (System.nanoTime() - t1) / 1_000_000;

            conn.createStatement().executeUpdate("DELETE FROM SanPham");
            conn.commit();

            long t2 = System.nanoTime();
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO SanPham VALUES (?,?,?,?)")) {
                for (int i = 1; i <= N; i++) {
                    ps.setInt(1, i);
                    ps.setString(2, "SP" + i);
                    ps.setDouble(3, 1000);
                    ps.setInt(4, 1);
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            conn.commit();
            long msPrepared = (System.nanoTime() - t2) / 1_000_000;

            System.out.println("\n[3] Chen " + N + " san pham:");
            System.out.println("   Statement            : " + msStatement + " ms");
            System.out.println("   PreparedStatement+batch: " + msPrepared + " ms");

        } catch (SQLException e) {
            System.out.println("Loi SQL: " + e.getMessage());
        }
    }
}
