import java.sql.*;

public class NhanVienApp {
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            // B1: ket noi
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:nhanvien.db");

            // B2: tao Statement
            stmt = conn.createStatement();

            // B3.1: tao bang
            String sqlCreate = "CREATE TABLE IF NOT EXISTS NhanVien ("
                    + "id INTEGER PRIMARY KEY, "
                    + "ten TEXT NOT NULL, "
                    + "chuc_vu TEXT)";
            stmt.executeUpdate(sqlCreate);

            // B3.2: them du lieu trong mot transaction
            conn.setAutoCommit(false);
            stmt.executeUpdate(
                    "INSERT OR IGNORE INTO NhanVien(id, ten, chuc_vu) VALUES (1, 'Nguyen Van A', 'Giam doc')");
            stmt.executeUpdate("INSERT OR IGNORE INTO NhanVien(id, ten, chuc_vu) VALUES (2, 'Tran Thi B', 'Ke toan')");
            stmt.executeUpdate(
                    "INSERT OR IGNORE INTO NhanVien(id, ten, chuc_vu) VALUES (3, 'Le Van C', 'Lap trinh vien')");
            conn.commit();

            // B4: truy van va hien thi
            rs = stmt.executeQuery("SELECT id, ten, chuc_vu FROM NhanVien");
            System.out.println("ID | Ten | Chuc vu");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " | "
                        + rs.getString("ten") + " | "
                        + rs.getString("chuc_vu"));
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Khong tim thay driver: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Loi SQL: " + e.getMessage());
            try {
                if (conn != null)
                    conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            // B5: dong theo thu tu nguoc lai
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
            }
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
            }
        }
    }
}
