package THLab6.RMI.Bai9;

import java.rmi.Remote;
import java.rmi.RemoteException;

// Interface phia client: server goi de thong bao theo thoi gian thuc
public interface DauGiaCallback extends Remote {
    void nhanThongBao(String message) throws RemoteException;
}
