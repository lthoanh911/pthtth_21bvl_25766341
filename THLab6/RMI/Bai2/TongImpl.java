package THLab6.RMI.Bai2;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class TongImpl extends UnicastRemoteObject implements Tong {

    public TongImpl() throws RemoteException {
        super();
    }

    @Override
    public int add(int a, int b) throws RemoteException {
        System.out.println("Client gui: a = " + a + ", b = " + b);
        return a + b;
    }
}
