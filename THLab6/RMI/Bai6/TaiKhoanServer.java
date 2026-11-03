package THLab6.RMI.Bai6;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class TaiKhoanServer {
    public static void main(String[] args) {
        try {
            System.setProperty("java.rmi.server.hostname", "127.0.0.1"); // Phai dat truoc khi tao doi tuong

            TaiKhoan tk = new TaiKhoanImpl(1000000); // so du ban dau

            LocateRegistry.createRegistry(1099);
            Naming.rebind("rmi://localhost:1099/TaiKhoanObj", tk);

            System.out.println("Ngan Hang Server da san sang! So du ban dau: 1000000");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
