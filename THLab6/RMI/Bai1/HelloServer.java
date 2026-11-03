package THLab6.RMI.Bai1;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class HelloServer {
    public static void main(String[] args) {
        try {
            Hello h = new HelloImpl();

            LocateRegistry.createRegistry(1099); // Khởi động rmiregistry ở port 1099
            Naming.rebind("rmi://localhost:1099/HelloObj", h);

            System.out.println("Hello Server da san sang!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
