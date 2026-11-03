package THLab6.RMI.Bai9;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface DauGia extends Remote {
    // Client dang ky de nhan thong bao khi co gia thau moi
    void registerClient(DauGiaCallback client) throws RemoteException;

    // Seller: them san pham (false neu ten da ton tai)
    boolean addProduct(String name, String seller, double startPrice) throws RemoteException;

    // Bidder: xem danh sach san pham
    List<SanPham> getProducts() throws RemoteException;

    // Bidder: dat gia, phai cao hon gia hien tai
    boolean bid(String name, String bidder, double amount) throws RemoteException;
}
