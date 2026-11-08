import java.sql.*;

// Bai 5: truy van tong hop (SUM, AVG, MIN, MAX, COUNT + GROUP BY) tren bang NhanVien co cot luong
public class ThongKeApp {
    static final String URL = "jdbc:sqlite:thongke.db";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(URL);
            stmt = conn.createStatement();

            // Tao bang co cot luong, tao lai moi lan chay
            stmt.executeUpdate("DROP TABLE IF EXISTS NhanVien");
            stmt.executeUpdate("CREATE TABLE NhanVien ("
                    + "id INTEGER PRIMARY KEY, ten TEXT NOT NULL, chuc_vu TEXT, luong REAL)");

            // Them nhieu ban ghi trong 1 transaction bang PreparedStatement
            Object[][] data = {
                {1, "Nguyen Van A", "Giam doc", 30000000},
                {2, "Tran Thi B", "Ke toan", 15000000},
                {3, "Le Van C", "Lap trinh vien", 20000000},
                {4, "Pham Thi D", "Lap trinh vien", 22000000},
                {5, "Hoang Van E", "Ke toan", 13000000},
                {6, "Vo Thi F", "Lap trinh vien", 18000000},
                {7, "Do Van G", "Nhan su", 12000000}
            };
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement("INSERT INTO NhanVien VALUES (?,?,?,?)");
            try {
                for (Object[] r : data) {
                    ps.setInt(1, (Integer) r[0]);
                    ps.setString(2, (String) r[1]);
                    ps.setString(3, (String) r[2]);
                    ps.setDouble(4, ((Integer) r[3]).doubleValue());
                    ps.executeUpdate();
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                ps.close();
            }
            conn.setAutoCommit(true);

            System.out.println("=== Danh sach nhan vien ===");
            rs = stmt.executeQuery("SELECT * FROM NhanVien");
            while (rs.next())
                System.out.printf("%d | %-14s | %-15s | %,.0f%n",
                        rs.getInt("id"), rs.getString("ten"), rs.getString("chuc_vu"), rs.getDouble("luong"));
            rs.close();

            // SUM
            rs = stmt.executeQuery("SELECT SUM(luong) FROM NhanVien");
            if (rs.next()) System.out.printf("%nTong luong        : %,.0f%n", rs.getDouble(1));
            rs.close();

            // AVG
            rs = stmt.executeQuery("SELECT AVG(luong) FROM NhanVien");
            if (rs.next()) System.out.printf("Luong trung binh  : %,.0f%n", rs.getDouble(1));
            rs.close();

            // MAX (nhan vien luong cao nhat)
            rs = stmt.executeQuery("SELECT ten, luong FROM NhanVien WHERE luong = (SELECT MAX(luong) FROM NhanVien)");
            while (rs.next())
                System.out.printf("Luong cao nhat    : %s - %,.0f%n", rs.getString("ten"), rs.getDouble("luong"));
            rs.close();

            // MIN (nhan vien luong thap nhat)
            rs = stmt.executeQuery("SELECT ten, luong FROM NhanVien WHERE luong = (SELECT MIN(luong) FROM NhanVien)");
            while (rs.next())
                System.out.printf("Luong thap nhat   : %s - %,.0f%n", rs.getString("ten"), rs.getDouble("luong"));
            rs.close();

            // COUNT + GROUP BY
            System.out.println("\nSo nhan vien theo chuc vu:");
            rs = stmt.executeQuery("SELECT chuc_vu, COUNT(*) AS so_luong FROM NhanVien GROUP BY chuc_vu");
            while (rs.next())
                System.out.printf("   %-15s : %d%n", rs.getString("chuc_vu"), rs.getInt("so_luong"));

        } catch (ClassNotFoundException e) {
            System.out.println("Khong tim thay driver: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Loi SQL: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { }
            try { if (conn != null) conn.close(); } catch (SQLException e) { }
        }
    }
}
