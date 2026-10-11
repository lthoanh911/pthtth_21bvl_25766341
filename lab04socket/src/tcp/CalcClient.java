package tcp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class CalcClient {
    public static void main(String[] args) throws Exception {
        String host = args.length > 0 ? args[0] : "localhost";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 5002;

        try (Socket socket = new Socket(host, port);
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream(), StandardCharsets.UTF_8));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(
                        socket.getOutputStream(), StandardCharsets.UTF_8), true);
                BufferedReader keyboard = new BufferedReader(
                        new InputStreamReader(System.in))) {
            System.out.println("Nhập: CALC <+|-|*|/> <số1> <số2>   (QUIT để thoát)");

            String line;
            while (true) {
                System.out.print("> ");
                line = keyboard.readLine();
                if (line == null)
                    break;

                out.println(line);
                String response = in.readLine();
                if (response == null) {
                    System.out.println("Server đã đóng kết nối");
                    break;
                }
                System.out.println("Server: " + response);
                if (line.trim().equalsIgnoreCase("QUIT"))
                    break;
            }
        }
    }
}
