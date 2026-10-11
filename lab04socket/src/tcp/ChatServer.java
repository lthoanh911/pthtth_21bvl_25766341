package tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {
    private static final int PORT = 5003;
    private static final int MAX_CLIENTS = 20;

    // nickname -> luồng ghi tới client đó; ConcurrentHashMap an toàn đa luồng
    private static final Map<String, PrintWriter> clients = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(MAX_CLIENTS);

        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Chat server listening on port " + PORT);

            while (true) {
                Socket socket = server.accept();
                pool.submit(() -> handle(socket));
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        } finally {
            pool.shutdown();
        }
    }

    static void handle(Socket socket) {
        String address = String.valueOf(socket.getRemoteSocketAddress());
        String nick = null;
        System.out.println("Connected: " + address);

        try (socket;
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream(), StandardCharsets.UTF_8));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(
                        socket.getOutputStream(), StandardCharsets.UTF_8), true)) {
            out.println("OK WELCOME - gửi NICK <tên> để bắt đầu");

            String line;
            while ((line = in.readLine()) != null) {
                String[] parts = line.trim().split("\\s+", 2);
                String cmd = parts[0].toUpperCase(Locale.ROOT);
                String arg = parts.length > 1 ? parts[1].trim() : "";

                if (cmd.equals("QUIT")) {
                    out.println("OK BYE");
                    break;
                }

                if (nick == null) {
                    if (!cmd.equals("NICK")) {
                        out.println("ERR NICK_REQUIRED");
                    } else if (!arg.matches("[A-Za-z0-9_]{1,20}")) {
                        out.println("ERR INVALID_NICK");
                    } else if (clients.putIfAbsent(arg, out) != null) {
                        out.println("ERR NICK_TAKEN");
                    } else {
                        nick = arg;
                        out.println("OK NICK " + nick);
                        broadcast(nick, "INFO " + nick + " joined");
                        System.out.println(address + " is " + nick);
                    }
                    continue;
                }

                switch (cmd) {
                    case "MSG":
                        if (arg.isEmpty()) {
                            out.println("ERR EMPTY_MESSAGE");
                        } else {
                            broadcast(nick, "FROM " + nick + ": " + arg);
                            out.println("OK SENT");
                        }
                        break;
                    case "USERS":
                        out.println("OK USERS " + String.join(", ", clients.keySet()));
                        break;
                    case "NICK":
                        out.println("ERR ALREADY_REGISTERED");
                        break;
                    default:
                        out.println("ERR UNKNOWN_COMMAND");
                }
            }
        } catch (IOException e) {
            // client ngắt bất thường (tắt cửa sổ, Ctrl+C, mất mạng)
            System.err.println("Client " + address + " lỗi: " + e.getMessage());
        } finally {
            if (nick != null) {
                clients.remove(nick);
                broadcast(nick, "INFO " + nick + " left");
            }
            System.out.println("Disconnected: " + address
                    + (nick != null ? " (" + nick + ")" : ""));
        }
    }

    static void broadcast(String sender, String message) {
        for (Map.Entry<String, PrintWriter> e : clients.entrySet()) {
            if (!e.getKey().equals(sender)) {
                e.getValue().println(message);
            }
        }
    }
}
