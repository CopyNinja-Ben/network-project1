import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Router2{
    public String routerID2 = "Router2";

    public static void main(String [] args) throws IOException{
        Scanner input = new Scanner (System.in);
        System.out.print ("Protocol version number: ");
        int protocolVersion = input.nextInt();

        System.out.print ("Update Interval: ");
        int updateInterval = input.nextInt();

        System.out.print ("Alive Interval: ");
        int aliveInterval = input.nextInt();

        Socket sock = new Socket("127.0.0.1", 9999);

        InputStream in = sock.getInputStream();
        OutputStream out = sock.getOutputStream();

        String localAddress = "127.0.0.1";
        int localPort = sock.getLocalPort();

        //System.out.println (localPort);

        out.write("Connecting to Router 2".getBytes());

        byte [] response = new byte[1024];
        in.read(response);
        System.out.println (new String(response).trim());

        sock.close();
    }
}
