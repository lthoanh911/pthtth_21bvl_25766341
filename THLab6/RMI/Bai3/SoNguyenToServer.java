package THLab6.RMI.Bai3;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class SoNguyenToServer {
    public static void main(String[] args) {
        try {
            SoNguyenTo s = new SoNguyenToImpl();

            LocateRegistry.createRegistry(1099);
            Naming.rebind("rmi://localhost:1099/SoNguyenToObj", s);

            System.out.println("So Nguyen To Server da san sang!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
