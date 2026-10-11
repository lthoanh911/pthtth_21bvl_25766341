package datetime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;

public class DateTimeUdpClient {
    public static void main(String[] args) throws IOException {
        String host = args.length > 0 ? args[0] : "localhost";
        InetAddress server = InetAddress.getByName(host);

        try (DatagramSocket socket = new DatagramSocket();
                BufferedReader keyboard = new BufferedReader(
                        new InputStreamReader(System.in))) {
            socket.setSoTimeout(3000);
            System.out.println("[UDP] " + DateTimeService.MENU);

            while (true) {
                System.out.print("> ");
                String input = keyboard.readLine();
                if (input == null)
                    break;

                String command = DateTimeService.toCommand(input);
                if (command.equalsIgnoreCase("QUIT"))
                    break; // thoát tại chỗ, không cần báo server

                byte[] data = command.getBytes(StandardCharsets.UTF_8);
                socket.send(new DatagramPacket(data, data.length,
                        server, DateTimeService.PORT));

                byte[] buffer = new byte[1024];
                DatagramPacket response = new DatagramPacket(buffer, buffer.length);
                try {
                    socket.receive(response);
                    System.out.println("Server: " + new String(response.getData(),
                            response.getOffset(), response.getLength(),
                            StandardCharsets.UTF_8));
                } catch (SocketTimeoutException e) {
                    System.out.println("Hết 3 giây chưa có phản hồi, thử lại sau");
                } catch (IOException e) {
                    System.out.println("Lỗi nhận phản hồi: " + e.getMessage());
                }
            }
        }
    }
}
