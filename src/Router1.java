import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Router1{
    public String routerID1 = "Router1";

    public static void main (String [] args) throws IOException{
         Scanner input = new Scanner (System.in);
         System.out.print ("Protocol version number: ");
         int protocolVersion = input.nextInt();

         System.out.print ("Update Interval: ");
         int updateInterval = input.nextInt();

         System.out.print ("Alive Interval: ");
         int aliveInterval = input.nextInt();

         System.out.print ("How many neighbors to be connected: ");
         int noOfNeighbors = input.nextInt();

        while (noOfNeighbors != 0) {
            String [] routerIDs = new String [noOfNeighbors];
            int [] linkCost = new int [noOfNeighbors];
            System.out.print("What is the router ID: ");
            routerIDs [noOfNeighbors - 1] = input.next();
            System.out.print ("Link cost between Router1 and " + routerIDs[noOfNeighbors - 1] + ": ");
            linkCost [noOfNeighbors - 1] = input.nextInt();
            noOfNeighbors--;
         }

        ServerSocket ser = new ServerSocket(9999);
        Socket sock = ser.accept();

        InputStream in = sock.getInputStream();
        OutputStream out = sock.getOutputStream();

        String localAddress = "127.0.0.1";
        int localPort = sock.getLocalPort();

        //System.out.println (localPort);

        byte buffer [] = new byte[1024];
        in.read(buffer);

        System.out.println (new String(buffer).trim());
        out.write("Connecting to Router 1".getBytes());

        sock.close();
    }
}