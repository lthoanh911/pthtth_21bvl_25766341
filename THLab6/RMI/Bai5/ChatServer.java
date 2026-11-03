package THLab6.RMI.Bai5;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

public class ChatServer {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.setProperty("java.rmi.server.hostname", "127.0.0.1"); // Phai dat truoc khi tao doi tuong

            ChatImpl chat = new ChatImpl();

            LocateRegistry.createRegistry(1099);
            Naming.rebind("rmi://localhost:1099/ChatObj", chat);

            System.out.println("Chat Server da san sang! Dang cho client...");

            while (true) {
                String msg = sc.nextLine();
                if (msg.equalsIgnoreCase("bye")) {
                    chat.reply("Server da thoat.");
                    break;
                }
                if (!chat.reply(msg)) {
                    System.out.println(">> Chua co client ket noi!");
                }
            }
            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
