package tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


public class DigitServer {
	private static final int PORT = 5001;
	private static final String[] WORDS = { "không", "một", "hai", "ba", "bốn", "năm", "sáu", "bảy", "tám", "chín" };

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
		if (request.equals("QUIT"))
			return "OK BYE";

		if (request.length() == 1) {
			char c = request.charAt(0);
			if (c >= '0' && c <= '9') {
				return "OK " + WORDS[c - '0'];
			}
		}

		return "ERR INVALID_DIGIT";
	}

}