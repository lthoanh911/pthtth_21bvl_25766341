package THLab6.RMI.Bai5;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ChatImpl extends UnicastRemoteObject implements Chat {

    private ChatCallback client; // chat 1-1 nen chi luu 1 client

    public ChatImpl() throws RemoteException {
        super();
    }

    @Override
    public synchronized void registerClient(ChatCallback client) throws RemoteException {
        this.client = client;
        System.out.println(">> Client da ket noi. Nhap tin nhan de phan hoi (go 'bye' de thoat).");
    }

    @Override
    public void sendMessage(String message) throws RemoteException {
        System.out.println("Client: " + message);
    }

    // Server gui phan hoi cho client qua callback
    public synchronized boolean reply(String message) {
        if (client == null) {
            return false;
        }
        try {
            client.receiveMessage(message);
            return true;
        } catch (RemoteException e) {
            System.out.println(">> Client da ngat ket noi.");
            client = null;
            return false;
        }
    }
}
