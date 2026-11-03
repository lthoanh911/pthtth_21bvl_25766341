package THLab6.RMI.Bai5;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ChatClient extends UnicastRemoteObject implements ChatCallback {

    public ChatClient() throws RemoteException {
        super();
    }

    // Server goi ham nay de gui phan hoi
    @Override
    public void receiveMessage(String message) throws RemoteException {
        System.out.println("Server: " + message);
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.setProperty("java.rmi.server.hostname", "127.0.0.1"); // Client cung export doi tuong (callback)

            Chat chat = (Chat) Naming.lookup("rmi://localhost:1099/ChatObj");
            chat.registerClient(new ChatClient());

            System.out.println("Da ket noi server. Nhap tin nhan (go 'bye' de thoat):");

            while (true) {
                String msg = sc.nextLine();
                chat.sendMessage(msg);
                if (msg.equalsIgnoreCase("bye")) {
                    break;
                }
            }
            System.exit(0); // doi tuong callback van dang export nen phai thoat thu cong

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
