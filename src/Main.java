import java.io.IOException;
import java.net.UnknownHostException;

public class Main {
	public static void main(String[] args) {
        String hostname = "192.168.0.100";
        int port = 3000;
        
        try {
            SocketClient client = new SocketClient(hostname, port);
            client.connect();
            client.operateConsoleAction();
            client.close();
        } catch (UnknownHostException ex) {
 
            System.out.println("Server not found: " + ex.getMessage());
 
        } catch (IOException ex) {
 
            System.out.println("I/O error: " + ex.getMessage());
        }
    }
}
