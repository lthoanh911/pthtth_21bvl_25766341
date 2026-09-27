package network;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

public class HostUriInspector {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java network.HostUriInspector <hostname> <uri>");
            System.out.println("Ví dụ: java network.HostUriInspector localhost \"https://example.com:8443/a/b?x=1#top\"");
            return;
        }

        inspectHost(args[0]);
        System.out.println();
        inspectUri(args[1]);
    }

    static void inspectHost(String hostname) {
        System.out.println("=== HOST: " + hostname + " ===");
        try {
            InetAddress[] addresses = InetAddress.getAllByName(hostname);
            for (InetAddress address : addresses) {
                String type = (address instanceof Inet4Address) ? "IPv4" : "IPv6";
                System.out.println("- IP: " + address.getHostAddress());
                System.out.println("  Loại: " + type);
                System.out.println("  Loopback: " + address.isLoopbackAddress());
                System.out.println("  Site local: " + address.isSiteLocalAddress());
            }
        } catch (UnknownHostException e) {
            System.err.println("Lỗi: không phân giải được host \"" + hostname + "\"");
        }
    }

    static void inspectUri(String text) {
        System.out.println("=== URI: " + text + " ===");
        try {
            URI uri = new URI(text);
            System.out.println("Scheme:   " + show(uri.getScheme()));
            System.out.println("Host:     " + show(uri.getHost()));
            System.out.println("Port:     " + (uri.getPort() == -1 ? "(không chỉ định)" : uri.getPort()));
            System.out.println("Path:     " + show(uri.getPath()));
            System.out.println("Query:    " + show(uri.getQuery()));
            System.out.println("Fragment: " + show(uri.getFragment()));
        } catch (URISyntaxException e) {
            System.err.println("Lỗi: URI sai cú pháp - " + e.getMessage());
        }
    }

    static String show(String value) {
        return (value == null || value.isEmpty()) ? "(không có)" : value;
    }
}
