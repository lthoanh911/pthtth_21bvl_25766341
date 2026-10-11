package datetime;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;

public class DateTimeUdpServer {
    public static void main(String[] args) {
        byte[] buffer = new byte[1024];

        try (DatagramSocket socket = new DatagramSocket(DateTimeService.PORT)) {
            System.out.println("DateTime UDP server listening on port " + DateTimeService.PORT);

            while (true) {
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                socket.receive(request);
                String command = new String(request.getData(), request.getOffset(),
                        request.getLength(), StandardCharsets.UTF_8);

                // UDP không có phiên nên không có QUIT; mỗi datagram là một yêu cầu độc lập
                String response = DateTimeService.process(command);
                System.out.println("UDP " + request.getSocketAddress()
                        + " [" + command + "] -> " + response);

                byte[] data = response.getBytes(StandardCharsets.UTF_8);
                socket.send(new DatagramPacket(data, data.length,
                        request.getAddress(), request.getPort()));
            }
        } catch (IOException e) {
            System.err.println("UDP server error: " + e.getMessage());
        }
    }
}
