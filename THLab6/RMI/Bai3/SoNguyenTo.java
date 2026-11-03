package THLab6.RMI.Bai3;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SoNguyenTo extends Remote {
    boolean isPrime(int n) throws RemoteException;
}
