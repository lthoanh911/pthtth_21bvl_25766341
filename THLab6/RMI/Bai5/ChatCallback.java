package THLab6.RMI.Bai5;

import java.rmi.Remote;
import java.rmi.RemoteException;

// Interface phia client: server goi phuong thuc nay de gui phan hoi
public interface ChatCallback extends Remote {
    void receiveMessage(String message) throws RemoteException;
}
