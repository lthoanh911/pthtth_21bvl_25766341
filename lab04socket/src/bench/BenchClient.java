package bench;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;

public class BenchClient {
    record Result(double ms, int received) {
    }

    public static void main(String[] args) throws Exception {
        int count = args.length > 0 ? Integer.parseInt(args[0]) : 1000;
        int size = args.length > 1 ? Integer.parseInt(args[1]) : 512;
        int runs = args.length > 2 ? Integer.parseInt(args[2]) : 5;
        int timeout = args.length > 3 ? Integer.parseInt(args[3]) : 200;
        String host = args.length > 4 ? args[4] : "localhost";

        if (size < 4 || size > 65507) {
            System.err.println("size phải trong khoảng 4..65507 byte");
            return;
        }

        System.out.printf("Java %s | %s %s | %d CPU%n",
                System.getProperty("java.version"), System.getProperty("os.name"),
                System.getProperty("os.version"),
                Runtime.getRuntime().availableProcessors());
        System.out.printf("host=%s count=%d size=%dB runs=%d udpTimeout=%dms%n%n",
                host, count, size, runs, timeout);

        // khởi động (warm-up) cho JIT, không tính vào kết quả
        tcpRun(host, 100, size);
        udpRun(host, 100, size, timeout);

        double[] tcpMs = new double[runs], udpMs = new double[runs];
        int[] tcpOk = new int[runs], udpOk = new int[runs];

        System.out.println("Lần |   TCP ms | TCP nhận |   UDP ms | UDP nhận");
        for (int r = 0; r < runs; r++) {
            Result t = tcpRun(host, count, size);
            Result u = udpRun(host, count, size, timeout);
            tcpMs[r] = t.ms();
            tcpOk[r] = t.received();
            udpMs[r] = u.ms();
            udpOk[r] = u.received();
            System.out.printf("%3d | %8.2f | %4d/%d | %8.2f | %4d/%d%n",
                    r + 1, t.ms(), t.received(), count, u.ms(), u.received(), count);
        }

        System.out.println();
        summary("TCP", tcpMs, tcpOk, count);
        summary("UDP", udpMs, udpOk, count);
    }

    static Result tcpRun(String host, int count, int size) throws IOException {
        byte[] data = new byte[size];
        byte[] reply = new byte[size];
        int received = 0;

        try (Socket socket = new Socket(host, BenchServer.TCP_PORT)) {
            socket.setTcpNoDelay(true);
            DataOutputStream out = new DataOutputStream(
                    new BufferedOutputStream(socket.getOutputStream()));
            DataInputStream in = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));
            out.writeInt(size);

            long start = System.nanoTime(); // không tính thời gian bắt tay kết nối
            for (int i = 0; i < count; i++) {
                ByteBuffer.wrap(data).putInt(0, i);
                out.write(data);
                out.flush();
                in.readFully(reply);
                received++;
            }
            return new Result((System.nanoTime() - start) / 1e6, received);
        }
    }

    static Result udpRun(String host, int count, int size, int timeout)
            throws IOException {
        InetAddress address = InetAddress.getByName(host);
        byte[] data = new byte[size];
        byte[] buffer = new byte[size];
        int received = 0;

        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(timeout);
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);

            long start = System.nanoTime();
            for (int i = 0; i < count; i++) {
                ByteBuffer.wrap(data).putInt(0, i);
                socket.send(new DatagramPacket(data, size, address,
                        BenchServer.UDP_PORT));
                if (waitFor(socket, reply, i))
                    received++;
            }
            return new Result((System.nanoTime() - start) / 1e6, received);
        }
    }

    // chờ đúng phản hồi số thứ tự seq, bỏ qua phản hồi đến muộn của gói trước
    static boolean waitFor(DatagramSocket socket, DatagramPacket reply, int seq)
            throws IOException {
        while (true) {
            try {
                reply.setLength(reply.getData().length);
                socket.receive(reply);
                if (ByteBuffer.wrap(reply.getData()).getInt(0) == seq)
                    return true;
            } catch (SocketTimeoutException e) {
                return false;
            }
        }
    }

    static void summary(String name, double[] ms, int[] ok, int count) {
        double sum = 0, min = Double.MAX_VALUE, max = 0;
        long totalOk = 0;
        for (int i = 0; i < ms.length; i++) {
            sum += ms[i];
            min = Math.min(min, ms[i]);
            max = Math.max(max, ms[i]);
            totalOk += ok[i];
        }
        System.out.printf("%s: TB %.2f ms (min %.2f, max %.2f) | nhận TB %.1f/%d%n",
                name, sum / ms.length, min, max, (double) totalOk / ms.length, count);
    }
}
