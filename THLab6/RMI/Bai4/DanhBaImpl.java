package THLab6.RMI.Bai4;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class DanhBaImpl extends UnicastRemoteObject implements DanhBa {

    private HashMap<String, String> danhBa = new HashMap<>();

    public DanhBaImpl() throws RemoteException {
        super();
    }

    // Bo khoang trang thua va khong phan biet hoa/thuong: " Oanh" == "oanh"
    private String chuanHoa(String name) {
        return name.trim().toLowerCase();
    }

    @Override
    public synchronized void addContact(String name, String phone) throws RemoteException {
        danhBa.put(chuanHoa(name), phone.trim());
        System.out.println("Them/sua: " + name + " - " + phone);
    }

    @Override
    public synchronized String findContact(String name) throws RemoteException {
        System.out.println("Tim: " + name);
        return danhBa.get(chuanHoa(name)); // tra ve null neu khong co
    }

    @Override
    public synchronized boolean deleteContact(String name) throws RemoteException {
        System.out.println("Xoa: " + name);
        return danhBa.remove(chuanHoa(name)) != null;
    }
}
