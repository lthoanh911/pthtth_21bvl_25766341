package THLab6.RMI.Bai2;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class TongServer {
    public static void main(String[] args) {
        try {
            Tong t = new TongImpl();

            LocateRegistry.createRegistry(1099);
            Naming.rebind("rmi://localhost:1099/TongObj", t);

            System.out.println("Tong Server da san sang!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
