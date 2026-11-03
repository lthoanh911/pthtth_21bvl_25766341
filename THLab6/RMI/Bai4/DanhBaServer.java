package THLab6.RMI.Bai4;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class DanhBaServer {
    public static void main(String[] args) {
        try {
            System.setProperty("java.rmi.server.hostname", "127.0.0.1"); // Phai dat truoc khi tao doi tuong

            DanhBa db = new DanhBaImpl();

            LocateRegistry.createRegistry(1099);
            Naming.rebind("rmi://localhost:1099/DanhBaObj", db);

            System.out.println("Danh Ba Server da san sang!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
