package THLab6.RMI.Bai9;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DauGiaImpl extends UnicastRemoteObject implements DauGia {

    private LinkedHashMap<String, SanPham> sanPhams = new LinkedHashMap<>(); // giu thu tu them vao
    private List<DauGiaCallback> clients = new CopyOnWriteArrayList<>();

    public DauGiaImpl() throws RemoteException {
        super();
    }

    private String chuanHoa(String name) {
        return name.trim().toLowerCase();
    }

    // Gui thong bao den tat ca client, client nao da tat thi xoa khoi danh sach
    private void thongBao(String message) {
        System.out.println(">> " + message);
        for (DauGiaCallback c : clients) {
            try {
                c.nhanThongBao(message);
            } catch (RemoteException e) {
                clients.remove(c);
            }
        }
    }

    @Override
    public void registerClient(DauGiaCallback client) throws RemoteException {
        clients.add(client);
        System.out.println("Co client moi ket noi. Tong so: " + clients.size());
    }

    @Override
    public boolean addProduct(String name, String seller, double startPrice) throws RemoteException {
        synchronized (this) {
            String key = chuanHoa(name);
            if (key.isEmpty() || startPrice <= 0 || sanPhams.containsKey(key)) {
                return false;
            }
            sanPhams.put(key, new SanPham(name.trim(), seller, startPrice));
        }
        // Thong bao ngoai khoi synchronized de khong khoa server khi goi ve client
        thongBao(String.format("[SAN PHAM MOI] %s dang ban '%s', gia khoi diem %,.0f", seller, name.trim(), startPrice));
        return true;
    }

    @Override
    public synchronized List<SanPham> getProducts() throws RemoteException {
        return new ArrayList<>(sanPhams.values());
    }

    @Override
    public boolean bid(String name, String bidder, double amount) throws RemoteException {
        String nguoiCu;
        String ten;
        synchronized (this) {
            SanPham sp = sanPhams.get(chuanHoa(name));
            if (sp == null || amount <= sp.getGiaHienTai()) {
                return false;
            }
            nguoiCu = sp.getNguoiDatCao();
            ten = sp.getTen();
            sp.datGia(bidder, amount);
        }
        String msg = String.format("[GIA MOI] %s dat %,.0f cho '%s'", bidder, amount, ten);
        if (nguoiCu != null && !nguoiCu.equals(bidder)) {
            msg += " (vuot gia cua " + nguoiCu + ")";
        }
        thongBao(msg);
        return true;
    }
}
