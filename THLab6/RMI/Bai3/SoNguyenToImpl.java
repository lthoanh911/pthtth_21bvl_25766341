package THLab6.RMI.Bai3;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SoNguyenToImpl extends UnicastRemoteObject implements SoNguyenTo {

    public SoNguyenToImpl() throws RemoteException {
        super();
    }

    @Override
    public boolean isPrime(int n) throws RemoteException {
        System.out.println("Client gui: n = " + n);
        if (n < 2) {
            return false;
        }
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}
