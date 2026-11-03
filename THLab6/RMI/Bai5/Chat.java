package THLab6.RMI.Bai5;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Chat extends Remote {
    // Client dang ky de server co the gui phan hoi nguoc lai
    void registerClient(ChatCallback client) throws RemoteException;

    void sendMessage(String message) throws RemoteException;
}
