package datetime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class DateTimeTcpClient {
    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";

        try (Socket socket = new Socket(host, DateTimeService.PORT);
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream(), StandardCharsets.UTF_8));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(
                        socket.getOutputStream(), StandardCharsets.UTF_8), true);
                BufferedReader keyboard = new BufferedReader(
                        new InputStreamReader(System.in))) {
            System.out.println("[TCP] " + DateTimeService.MENU);

            while (true) {
                System.out.print("> ");
                String input = keyboard.readLine();
                if (input == null)
                    break;

                String command = DateTimeService.toCommand(input);
                out.println(command);
                String response = in.readLine();
                if (response == null) {
                    System.out.println("Server đã đóng kết nối");
                    break;
                }
                System.out.println("Server: " + response);
                if (command.equalsIgnoreCase("QUIT"))
                    break;
            }
        } catch (ConnectException e) {
            System.err.println("Không kết nối được server: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Mất kết nối: " + e.getMessage());
        }
    }
}
