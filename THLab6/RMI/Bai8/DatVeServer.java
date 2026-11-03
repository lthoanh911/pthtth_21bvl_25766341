package THLab6.RMI.Bai8;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class DatVeServer {
    public static void main(String[] args) {
        try {
            System.setProperty("java.rmi.server.hostname", "127.0.0.1"); // Phai dat truoc khi tao doi tuong

            DatVe dv = new DatVeImpl();

            LocateRegistry.createRegistry(1099);
            Naming.rebind("rmi://localhost:1099/DatVeObj", dv);

            System.out.println("Dat Ve Server da san sang! Chuyen bay: VN123 (100), VJ456 (50), QH789 (30)");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
