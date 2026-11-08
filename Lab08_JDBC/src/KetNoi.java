import java.sql.*;

public class KetNoi {
    public static void main(String[] args) {
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:nhanvien.db")) {
                System.out.println("Ket noi SQLite thanh cong: " + conn.getMetaData().getDatabaseProductVersion());
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Khong tim thay driver: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Loi ket noi: " + e.getMessage());
        }
    }
}
