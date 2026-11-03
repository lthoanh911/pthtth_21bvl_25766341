package THLab6.RMI.Bai9;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class DauGiaServer {
    public static void main(String[] args) {
        try {
            System.setProperty("java.rmi.server.hostname", "127.0.0.1"); // Phai dat truoc khi tao doi tuong

            DauGia dg = new DauGiaImpl();

            LocateRegistry.createRegistry(1099);
            Naming.rebind("rmi://localhost:1099/DauGiaObj", dg);

            System.out.println("Auction Server da san sang!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
