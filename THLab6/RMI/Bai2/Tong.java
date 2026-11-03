package THLab6.RMI.Bai2;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Tong extends Remote {
    int add(int a, int b) throws RemoteException;
}
