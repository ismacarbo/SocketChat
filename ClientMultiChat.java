/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author INTEL
 */
public class ClientMultiChat {

    private String indirizzo;
    private int porta;
    private String nome;
    private DataOutputStream output;
    private BufferedReader input, tastiera;
    private Socket socket;
    private String daInviare, ricevuta;

    public ClientMultiChat(String indirizzo, int porta) {
        try {
            this.indirizzo = indirizzo;
            this.porta = porta;
            tastiera = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Inserisci il tuo nome: ");
            this.nome = tastiera.readLine();
        } catch (IOException ex) {
            System.out.println("Errore: " + ex.getMessage());
        }
    }

    public void connetti() {
        try {
            socket = new Socket(indirizzo, porta);
            System.out.println("Connesso col server");
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new DataOutputStream(socket.getOutputStream());
            output.writeBytes(nome + "\n");
        } catch (IOException ex) {
            System.out.println("Errore: " + ex.getMessage());
        }
    }

    public void comunica() {
        boolean fine = false;
        while (!fine) {
            try {
                daInviare = tastiera.readLine();
                output.writeBytes(nome + ": " + daInviare + "\n");
            } catch (IOException ex) {
                System.out.println("Errore: " + ex.getMessage());
            }
        }
    }

    public void ascolta() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //creo un altro thread per ascoltare cosi non ho problemi di blocco della comunicazione
                while (socket.isConnected()) {
                    try {
                        ricevuta = input.readLine();
                        System.out.println(ricevuta);
                    } catch (IOException ex) {
                        System.out.println("Errore: " + ex.getMessage());
                    }
                }
            }
        }).start();
    }

}
