package THLab6.RMI.Bai7;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class DienTichServer {
    public static void main(String[] args) {
        try {
            System.setProperty("java.rmi.server.hostname", "127.0.0.1"); // Phai dat truoc khi tao doi tuong

            DienTich dt = new DienTichImpl();

            LocateRegistry.createRegistry(1099);
            Naming.rebind("rmi://localhost:1099/DienTichObj", dt);

            System.out.println("Dien Tich Server da san sang!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
