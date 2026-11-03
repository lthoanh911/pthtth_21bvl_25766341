package THLab6.RMI.Bai8;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class DatVeImpl extends UnicastRemoteObject implements DatVe {

    private HashMap<String, Integer> tongGhe = new HashMap<>(); // so ghe toi da cua chuyen bay
    private HashMap<String, Integer> gheTrong = new HashMap<>(); // so ghe con trong

    public DatVeImpl() throws RemoteException {
        super();
        themChuyenBay("VN123", 100);
        themChuyenBay("VJ456", 50);
        themChuyenBay("QH789", 30);
    }

    private void themChuyenBay(String flightNumber, int soGhe) {
        tongGhe.put(flightNumber, soGhe);
        gheTrong.put(flightNumber, soGhe);
    }

    // " vn123" == "VN123"
    private String chuanHoa(String flightNumber) {
        return flightNumber.trim().toUpperCase();
    }

    @Override
    public synchronized boolean bookTicket(String flightNumber, int seats) throws RemoteException {
        String ma = chuanHoa(flightNumber);
        Integer trong = gheTrong.get(ma);
        if (trong == null || seats <= 0 || seats > trong) {
            System.out.println("Dat " + seats + " ghe " + ma + " that bai");
            return false;
        }
        gheTrong.put(ma, trong - seats);
        System.out.println("Dat " + seats + " ghe " + ma + " -> con trong: " + (trong - seats));
        return true;
    }

    @Override
    public synchronized int availableSeats(String flightNumber) throws RemoteException {
        String ma = chuanHoa(flightNumber);
        System.out.println("Kiem tra ghe trong: " + ma);
        Integer trong = gheTrong.get(ma);
        return trong == null ? -1 : trong; // -1: khong co chuyen bay
    }

    @Override
    public synchronized boolean cancelBooking(String flightNumber, int seats) throws RemoteException {
        String ma = chuanHoa(flightNumber);
        Integer trong = gheTrong.get(ma);
        // Khong cho huy nhieu hon so ghe da dat
        if (trong == null || seats <= 0 || trong + seats > tongGhe.get(ma)) {
            System.out.println("Huy " + seats + " ghe " + ma + " that bai");
            return false;
        }
        gheTrong.put(ma, trong + seats);
        System.out.println("Huy " + seats + " ghe " + ma + " -> con trong: " + (trong + seats));
        return true;
    }
}
