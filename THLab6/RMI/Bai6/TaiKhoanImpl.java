package THLab6.RMI.Bai6;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class TaiKhoanImpl extends UnicastRemoteObject implements TaiKhoan {

    private double soDu;

    public TaiKhoanImpl(double soDuBanDau) throws RemoteException {
        super();
        this.soDu = soDuBanDau;
    }

    @Override
    public synchronized double getBalance() throws RemoteException {
        System.out.println("Kiem tra so du: " + soDu);
        return soDu;
    }

    @Override
    public synchronized void deposit(double amount) throws RemoteException {
        if (amount <= 0) {
            return; // khong nhan so tien am hoac bang 0
        }
        soDu += amount;
        System.out.println("Gui " + amount + " -> So du: " + soDu);
    }

    @Override
    public synchronized boolean withdraw(double amount) throws RemoteException {
        if (amount <= 0 || amount > soDu) {
            System.out.println("Rut " + amount + " that bai (so du: " + soDu + ")");
            return false;
        }
        soDu -= amount;
        System.out.println("Rut " + amount + " -> So du: " + soDu);
        return true;
    }
}
