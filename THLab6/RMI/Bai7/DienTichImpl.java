package THLab6.RMI.Bai7;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class DienTichImpl extends UnicastRemoteObject implements DienTich {

    public DienTichImpl() throws RemoteException {
        super();
    }

    @Override
    public double rectangleArea(double width, double height) throws RemoteException {
        System.out.println("Hinh chu nhat: " + width + " x " + height);
        return width * height;
    }

    @Override
    public double circleArea(double radius) throws RemoteException {
        System.out.println("Hinh tron: r = " + radius);
        return Math.PI * radius * radius;
    }

    @Override
    public double triangleArea(double base, double height) throws RemoteException {
        System.out.println("Tam giac: day = " + base + ", cao = " + height);
        return base * height / 2;
    }
}
