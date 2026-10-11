package datetime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class DateTimeTcpServer {
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(DateTimeService.PORT)) {
            System.out.println("DateTime TCP server listening on port " + DateTimeService.PORT);

            while (true) {
                try (Socket socket = server.accept()) {
                    System.out.println("Connected: " + socket.getRemoteSocketAddress());
                    serve(socket);
                    System.out.println("Disconnected: " + socket.getRemoteSocketAddress());
                } catch (IOException e) {
                    System.err.println("Lỗi phiên client: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Không mở được server: " + e.getMessage());
        }
    }

    static void serve(Socket socket) throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(
                socket.getInputStream(), StandardCharsets.UTF_8));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(
                        socket.getOutputStream(), StandardCharsets.UTF_8), true)) {
            String request;
            while ((request = in.readLine()) != null) {
                if (request.trim().equalsIgnoreCase("QUIT")) {
                    out.println("OK BYE");
                    break;
                }
                String response = DateTimeService.process(request);
                System.out.println("TCP [" + request + "] -> " + response);
                out.println(response);
            }
        }
    }
}
