package tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class CalcServer {
    private static final int PORT = 5002;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Calc server listening on port " + PORT);

            while (true) {
                try (Socket socket = server.accept()) {
                    System.out.println("Client: " + socket.getRemoteSocketAddress());
                    serve(socket);
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
                String response = process(request);
                System.out.println(request + " -> " + response);
                out.println(response);
            }
        }
    }

    static String process(String request) {
        String[] parts = request.trim().split("\\s+");
        if (parts.length != 4 || !parts[0].equalsIgnoreCase("CALC")) {
            return "ERR INVALID_FORMAT";
        }

        String op = parts[1];
        if (!op.equals("+") && !op.equals("-")
                && !op.equals("*") && !op.equals("/")) {
            return "ERR UNSUPPORTED_OPERATOR";
        }

        if (!isNumber(parts[2]) || !isNumber(parts[3])) {
            return "ERR INVALID_NUMBER";
        }
        double a = Double.parseDouble(parts[2]);
        double b = Double.parseDouble(parts[3]);

        double result;
        switch (op) {
            case "+":
                result = a + b;
                break;
            case "-":
                result = a - b;
                break;
            case "*":
                result = a * b;
                break;
            default:
                if (b == 0)
                    return "ERR DIVIDE_BY_ZERO";
                result = a / b;
        }
        return "OK " + format(result);
    }

    static boolean isNumber(String s) {
        return s.matches("-?\\d+(\\.\\d+)?");
    }

    // 300.0 -> "300", 3.5 -> "3.5"
    static String format(double value) {
        if (value == Math.rint(value) && Math.abs(value) < 1e15) {
            return String.valueOf((long) value);
        }
        return String.valueOf(value);
    }
}
