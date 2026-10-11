package tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ChatClient {
    public static void main(String[] args) throws Exception {
        String host = args.length > 0 ? args[0] : "localhost";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 5003;

        try (Socket socket = new Socket(host, port);
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream(), StandardCharsets.UTF_8));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(
                        socket.getOutputStream(), StandardCharsets.UTF_8), true);
                BufferedReader keyboard = new BufferedReader(
                        new InputStreamReader(System.in))) {

            Thread reader = new Thread(() -> {
                try {
                    String line;
                    while ((line = in.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    // socket đã đóng
                }
                System.out.println("Đã ngắt kết nối với server");
            });
            reader.setDaemon(true);
            reader.start();

            System.out.println("Lệnh: NICK <tên> | MSG <nội dung> | USERS | QUIT");
            String line;
            while ((line = keyboard.readLine()) != null) {
                out.println(line);
                if (line.trim().equalsIgnoreCase("QUIT"))
                    break;
            }
            reader.join(1000); // chờ in "OK BYE"
        }
    }
}
