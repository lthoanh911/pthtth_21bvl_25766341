package tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class DateTimeTcpServer {
	private static final int PORT = 5001;
	private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH:mm:ss");

	public static void main(String[] args) {
		try (ServerSocket server = new ServerSocket(PORT)) {
			System.out.println("TCP server listening on port " + PORT);

			while (true) {
				try (Socket socket = server.accept()) {
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
		try (BufferedReader in = new BufferedReader(
				new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
				PrintWriter out = new PrintWriter(
						new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true)) {
			String request;
			while ((request = in.readLine()) != null) {
				String response = process(request);
				System.out.println("Nhận: [" + request + "] -> Trả: " + response);
				out.println(response);
				if (request.equals("QUIT"))
					break;
			}
		}
	}

	static String process(String request) {
	    String cmd = request.trim().toUpperCase();
	    switch (cmd) {
	        case "DATE":     return "OK " + LocalDate.now().format(DATE_FMT);
	        case "TIME":     return "OK " + LocalTime.now().format(TIME_FMT);
	        case "DATETIME": return "OK " + LocalDateTime.now().format(DATE_FMT)
	                                + " " + LocalDateTime.now().format(TIME_FMT);
	        case "QUIT":     return "OK BYE";
	        default:         return "ERR UNKNOWN_COMMAND";
	    }
	}

}