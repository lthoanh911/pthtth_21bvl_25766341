package bench;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class BenchServer {
    static final int TCP_PORT = 5004;
    static final int UDP_PORT = 5005;

    public static void main(String[] args) {
        // tham số tuỳ chọn: tỉ lệ bỏ gói UDP, ví dụ 0.05 = 5%
        double dropRate = args.length > 0 ? Double.parseDouble(args[0]) : 0.0;

        new Thread(() -> runUdp(dropRate)).start();
        runTcp();
    }

    static void runTcp() {
        try (ServerSocket server = new ServerSocket(TCP_PORT)) {
            System.out.println("TCP echo listening on port " + TCP_PORT);

            while (true) {
                try (Socket socket = server.accept()) {
                    socket.setTcpNoDelay(true);
                    DataInputStream in = new DataInputStream(
                            new BufferedInputStream(socket.getInputStream()));
                    DataOutputStream out = new DataOutputStream(
                            new BufferedOutputStream(socket.getOutputStream()));

                    int size = in.readInt(); // client báo kích thước gói trước
                    byte[] buffer = new byte[size];
                    while (true) {
                        in.readFully(buffer);
                        out.write(buffer);
                        out.flush();
                    }
                } catch (EOFException e) {
                    // client đóng kết nối: kết thúc một lượt đo
                } catch (IOException e) {
                    System.err.println("TCP client error: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("TCP server error: " + e.getMessage());
        }
    }

    static void runUdp(double dropRate) {
        Random random = new Random();
        byte[] buffer = new byte[65507];

        try (DatagramSocket socket = new DatagramSocket(UDP_PORT)) {
            System.out.println("UDP echo listening on port " + UDP_PORT
                    + ", drop rate = " + dropRate);

            while (true) {
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                socket.receive(request);
                if (random.nextDouble() < dropRate)
                    continue; // giả lập mất gói

                socket.send(new DatagramPacket(request.getData(),
                        request.getLength(), request.getAddress(), request.getPort()));
            }
        } catch (IOException e) {
            System.err.println("UDP server error: " + e.getMessage());
        }
    }
}
