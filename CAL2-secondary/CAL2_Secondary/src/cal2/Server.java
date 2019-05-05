package cal2;
import java.io.*;
import java.net.*;
import cal2.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Server extends Thread{
    private int Puerto;
    private BufferedReader entrada;
    private boolean bound=false;
    private ServerSocket server;
    private Socket connection;
    private DataOutputStream output;
    private DataInputStream  input;
    private Exhibition goya;
    public Server(Main main, CountDownLatch parar) throws IOException {
        entrada=new BufferedReader(new InputStreamReader(System.in));
        InetAddress localhost = InetAddress.getLocalHost();
        System.out.println("System IP Address : " + (localhost.getHostAddress()).trim());
        System.out.println();
        goya=main.getGoya();
        Puerto=1024; //try to find automatically a non-privilege port
        do
        {
        try
        {
        server = new ServerSocket(Puerto);
        bound=true;
        }catch(java.net.SocketException e)
        {
            bound=false;
            Puerto++;
            if(Puerto>=49151)
            {
                System.out.println("there is no port available in the system, program terminated");
                System.exit(0);
            }
        }
        }while(!bound && Puerto<=49151); //if the bind process is correct or either no more tcp ports available in the system
        System.out.println("Socket port : " + Puerto);
        parar.countDown();
        start();
    }
    public void run()
    {
        try {
             // Create socket Port 5000
            System.out.println("Starting server...");
            Executor conjunto=new ThreadPoolExecutor(0,10, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue());
            while (true) {
                // Wait for a connection
                connection = server.accept();     
                NewClass thread=new NewClass(connection, goya); //create a new thread for each client created
                conjunto.execute(thread); //a maximum of 10 clents can connect to the server
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    public static void main(String args[]) throws IOException {
        Exhibition goya;
        Visitor v;
        CountDownLatch parar=new CountDownLatch(1);
        Main main = new Main();
        goya=new Exhibition(10,main.getjTextField1(),main.getjTextField2());
        main.setGoya(goya);
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                    main.setVisible(true);
            }
        });
        Server server = new Server(main, parar);
        try {
            parar.await(); //wait for the server to find the port and connect the socket to start.
        } catch (InterruptedException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(int i=1; i<=60; i++) v=new Visitor(i,goya);
    }
}
