package datetime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateTimeService {
    static final int PORT = 5010; // TCP và UDP dùng chung số cổng, không xung đột
    static final String MENU = "1. DATE   2. TIME   3. DATETIME   0. QUIT";

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH:mm:ss");

    static String process(String request) {
        LocalDateTime now = LocalDateTime.now(); // lấy một lần để ngày và giờ khớp nhau
        switch (request.trim().toUpperCase(Locale.ROOT)) {
            case "DATE":
                return "OK " + now.format(DATE_FMT);
            case "TIME":
                return "OK " + now.format(TIME_FMT);
            case "DATETIME":
                return "OK " + now.format(DATE_FMT) + " " + now.format(TIME_FMT);
            default:
                return "ERR UNKNOWN_COMMAND";
        }
    }

    // cho phép nhập số trên menu thay cho tên lệnh
    static String toCommand(String input) {
        switch (input.trim()) {
            case "1":
                return "DATE";
            case "2":
                return "TIME";
            case "3":
                return "DATETIME";
            case "0":
                return "QUIT";
            default:
                return input.trim();
        }
    }
}
