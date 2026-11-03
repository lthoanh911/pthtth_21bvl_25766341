package THLab6.RMI.Bai4;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DanhBa extends Remote {
    void addContact(String name, String phone) throws RemoteException;

    String findContact(String name) throws RemoteException;

    boolean deleteContact(String name) throws RemoteException;
}
