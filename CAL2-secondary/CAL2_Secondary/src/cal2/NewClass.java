/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cal2;

import cal2.Exhibition;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dcc
 */
public class NewClass extends Thread {
        private Socket connection;
        private DataOutputStream output;
        private DataInputStream  input;
        private Exhibition goya;
        public NewClass(Socket connection, Exhibition goya)
        {
            this.goya=goya;
            this.connection=connection;
            start();
        }
    public void run()
    {
            try {
                //Open input-output channels
                input = new DataInputStream(connection.getInputStream());  
                output  = new DataOutputStream(connection.getOutputStream()); 
                //Read message from the client
                boolean mensaje = input.readBoolean();
                System.out.println("el mensaje ha sido leido: "+mensaje);
                goya.getQueueInside().setS(mensaje);
                goya.getQueueWaiting().setS(mensaje);
                if(!goya.getQueueInside().isClosed() || !goya.getQueueWaiting().isClosed())
                {   
                    goya.getQueueInside().signal();
                    goya.getQueueWaiting().signal();
                }
                //Close connection
                connection.close();       
            } catch (IOException ex) {
            } 
            
    }
}
