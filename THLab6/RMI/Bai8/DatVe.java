package THLab6.RMI.Bai8;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DatVe extends Remote {
    boolean bookTicket(String flightNumber, int seats) throws RemoteException;

    int availableSeats(String flightNumber) throws RemoteException;

    boolean cancelBooking(String flightNumber, int seats) throws RemoteException;
}
