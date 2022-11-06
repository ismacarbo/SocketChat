/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author INTEL
 */
public class ContenitoreClienti implements Runnable {
    private Socket socket;
    private DataOutputStream output;
    private BufferedReader input;
    private String nomeClient;
    public static ArrayList<ContenitoreClienti> clienti = new ArrayList<>();
    private String ricevuta;

    public ContenitoreClienti(Socket socket) {
        try {
            this.socket = socket;
            clienti.add(this);
            output = new DataOutputStream(this.socket.getOutputStream());
            input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.nomeClient = input.readLine();
            invia("SERVER: "+nomeClient+" si è connesso!");
        } catch (IOException ex) {
            System.out.println("Errore: " + ex.getMessage());
        }
    }

    public void comunica() {
        boolean fine = false;
        while (!fine) {
            try {
                ricevuta = input.readLine();
                System.out.println(ricevuta);
                invia(ricevuta); //invia a tutti i client
            } catch (IOException ex) {
                System.out.println("Errore: " + ex.getMessage());
            }

        }
    }

    public void rimuoviClient(){
        clienti.remove(this);
        invia("SERVER: " + nomeClient + " è uscito dalla chat!");
    }

    private void invia(String messaggio) {
        try {
            for(ContenitoreClienti cliente:clienti){
                if(!cliente.nomeClient.equals(nomeClient)){
                    cliente.output.writeBytes(messaggio + "\n");
                }
            }
        } catch (IOException ex) {
            System.out.println("Errore: " + ex.getMessage());
        }

    }

    @Override
    public void run() {
        comunica();
    }

}
