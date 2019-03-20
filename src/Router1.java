import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Router1{
    public String routerID1 = "Router1";
    public Queue<Message> lsa;
    public Queue<Message> keepAlive;
    public String[] routerIDs;
    public int[] linkCost;
    public int protocolVersion;
    public int updateInterval;
    public int aliveInterval;
    public Semaphore sendLock;

    public Router1() {
        this.sendLock = new Semaphore(1,true);
    }

    public void save(int port) {
        // save its ipname and port in a file
        // write-write to be dealed
        File routerPort = new File("routerPort.txt");
        try {
            if(!routerPort.exists()){
                routerPort.createNewFile();
            }
            Map<String, Integer> map = new HashMap<>();
            map.put("R1", port);
            FileOutputStream outputStream = new FileOutputStream("routerPort.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(map);
            outputStream.close();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void send(String r , int linkCost) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("routerPort.txt");
        ObjectInputStream ois = new ObjectInputStream(fis);
        Map<String,Integer> map = (Map<String, Integer>) ois.readObject();

        Socket socket = new Socket("localhost",map.get(r));
        ois.close();
        fis.close();

        sendThread sendthread = new sendThread(socket,this, r, linkCost);
        sendthread.start();
    }

    public void dispatcher(int port){

        // **** setup server socket ****
        final ServerSocket serverSock;
        try
        {
            serverSock = new ServerSocket(port);
            System.out.println("***** Router1 Is Up *****");
        }
        catch(Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace(System.err);
            return;
        }
        Thread lproducer = new lsaProducer();
        Thread lconsumer = new lsaConsumer();

        // **** listening to incoming messages ****
        while (true) {
            try {
                Socket socket = serverSock.accept();      //listen
                final ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                final ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) input.readObject();
                Message response;
                switch(message.getTitle()){
                    case "LSA":
                        lproducer.start();
//                        lsa.offer(message);
                        break;

                    case "Alive":
                        break;

                    case "beNeighborRequest":

                        break;

                    case "beNeighborConfirm":
                        break;

                    case "ACK":
                        //add one record to LSD
                        break;
                }



                ServerThread serverThread = new ServerThread(socket, this);
                serverThread.start();
            }
            catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                e.printStackTrace(System.err);
                return;
            }
        }
    }

    public void main(String[] args) {
        Router1 r1 = new Router1();

        Scanner input = new Scanner (System.in);
        System.out.println("Port: ");
        int port = input.nextInt();
        r1.save(port);

        System.out.print ("Protocol version number: ");
        this.protocolVersion = input.nextInt();

        System.out.print ("Update Interval: ");
        this.updateInterval = input.nextInt();

        System.out.print ("Alive Interval: ");
        this.aliveInterval = input.nextInt();

        System.out.print ("How many neighbors to be connected: ");
        int noOfNeighbors = input.nextInt();

        Map<String, Integer> tempMap = new HashMap<>();
        while (noOfNeighbors != 0) {
            routerIDs = new String [noOfNeighbors];
            linkCost = new int [noOfNeighbors];
            System.out.print("What is the router ID of neighbor: ");
            this.routerIDs [noOfNeighbors - 1] = input.next();
            System.out.print ("Link cost between Router1 and " + routerIDs[noOfNeighbors - 1] + ": ");
            this.linkCost [noOfNeighbors - 1] = input.nextInt();
//            tempMap.put(routerIDs[noOfNeighbors - 1] , linkCost[noOfNeighbors - 1]);
            try {
                r1.send(routerIDs[noOfNeighbors - 1],linkCost[noOfNeighbors - 1]);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            noOfNeighbors--;
        }

        r1.dispatcher(port);

        input.close();

//        ServerSocket ser = new ServerSocket(9999);
//
//        Socket sock = ser.accept();
//
//        InputStream in = sock.getInputStream();
//        OutputStream out = sock.getOutputStream();
//
//        String localAddress = "127.0.0.1";
//        int localPort = sock.getLocalPort();
//
//        //System.out.println (localPort);
//
//        byte buffer [] = new byte[1024];
//        in.read(buffer);
//
//        System.out.println (new String(buffer).trim());
//        out.write("Connecting to Router 1".getBytes());
//
//        sock.close();




        class lsaProducer extends Thread{
            private int maxSize = 5;

            public void run(){
                while (true)
                {
                    synchronized (queue) {
                        while (queue.size() == maxSize) {
                            try {
                                System.out .println("Queue is full, " + "Producer thread waiting for " + "consumer to take something from queue");
                                queue.wait();
                            } catch (Exception ex) {
                                ex.printStackTrace(); }
                        }
                        System.out.println("Producing value : " + i); queue.add(i);
                        queue.notify();
                    }
                }
            }
        }
    }
        }

        class lsaConsumer extends Thread{
            private int maxSize = 5;

            public void run(){
                while (true) {
                    synchronized (queue) {
                        while (queue.isEmpty()) {
                            System.out.println("Queue is empty," + "Consumer thread is waiting" + " for producer thread to put something in queue");
                            try {
                                queue.wait();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        System.out.println("Consuming value : " + queue.remove());
                        queue.notify();
                    }
                }
            }
        }
    }
}
