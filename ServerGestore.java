/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author INTEL
 */
public class ServerGestore {
    private int porta;
    private ServerSocket serverSocket;

    

    public ServerGestore(int porta) {
        
        try {
            this.porta = porta;
            serverSocket=new ServerSocket(porta);
        } catch (IOException ex) {
            Logger.getLogger(ServerGestore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void attendi(){
        try {
            
            System.out.println("in esecuzione");
            while(true){
                
                Socket socket=serverSocket.accept();
                ContenitoreClienti c=new ContenitoreClienti(socket);
                System.out.println("NUOVO COLLEGAMENTO");
                Thread t1=new Thread(c);
                t1.start();
            }

        } catch (IOException ex) {
            System.out.println("Errore: " + ex.getMessage());
        }

    }
}
