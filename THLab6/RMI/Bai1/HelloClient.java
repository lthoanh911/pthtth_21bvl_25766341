package THLab6.RMI.Bai1;

import java.rmi.Naming;

public class HelloClient {
    public static void main(String[] args) {
        try {
            Hello h = (Hello) Naming.lookup("rmi://localhost:1099/HelloObj");

            String kq = h.sayHello();
            System.out.println("Server tra ve: " + kq);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
