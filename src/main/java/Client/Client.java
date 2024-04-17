package Client;

import java.io.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

/**
 *
 * @author Banella Lorenzo
 */


public class Client {
   
    private int porta;
    private InetAddress indirizzoServer;
    private DatagramSocket dataClientSocket;
    private DatagramPacket sendPacket;
    private DatagramPacket receivePacket;
    private byte[]  bufferIN = new byte[1024];
    private byte[]  bufferOUT = new byte[1024];
    private String  messaggioDaInviare;
    private String  messaggioRicevuto;
    private Scanner scanner;
  
    
    public Client(int porta) {
        this.scanner = new Scanner(System.in);
        this.porta = porta; 
    }
    
    public void comunica() {
        while(!dataClientSocket.isClosed()) {
            invia();
            ricevi();
        }
    }
    
    public void connetti() {
        try {
        this.indirizzoServer= InetAddress.getLocalHost();
        System.out.println("Il Client ha trovato l'Indirizzo del Server ");
        dataClientSocket = new DatagramSocket();
        

        } catch (SocketException e) {
        System.out.println("Eccezzione Socket nel connettersi al server");
        e.printStackTrace();
        } catch (IOException e) {
        System.out.println("Errore generico nell'avvio del server");
        e.printStackTrace();
        }
    }

    public void invia(){
        if(!dataClientSocket.isClosed()){
            try {
                System.out.println("Invia il tuo messaggio -- per chiudere la comunicazione scrivi : chiudi ");
                String messaggioDaInviare = scanner.nextLine();
                bufferOUT = messaggioDaInviare.getBytes();
                sendPacket = new DatagramPacket(bufferOUT, bufferOUT.length, indirizzoServer, porta);
                dataClientSocket.send(sendPacket);
                System.out.println("Messaggio inviato  : " + messaggioDaInviare);
                
                if(messaggioDaInviare.equalsIgnoreCase("chiudi")) {
                    System.out.println("Il client ha deciso di chiudere la comunicazione");
                    
                    chiudi();    
                }
                
            } catch (SocketException e) {
                System.out.println("Eccezzione Socket durante la risposta  del Client al Server");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("Eccezzione I/O");
            }
        }

    }
    
    public void ricevi() {
        if(!dataClientSocket.isClosed()){
            try {
                receivePacket =  new DatagramPacket(bufferIN, bufferIN.length);
                dataClientSocket.receive(receivePacket);
            messaggioRicevuto = new String(receivePacket.getData());
                
                int lunghezza = receivePacket.getLength();
                messaggioRicevuto = messaggioRicevuto.substring(0, lunghezza);
                System.out.println("Messaggio ricevuto :" + messaggioRicevuto);

                if(messaggioRicevuto.equalsIgnoreCase("FINE")) {
                    System.out.println("Il client ha deciso di chiudere ,perchè il server ha deciso di interrompere la comunicazione");
                    chiudi();    
                }
                
            } catch (SocketException e) {
                System.out.println("Eccezzione Socket durante la ricezione del messaggio inviato dal Server verso il Client");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("Eccezzione I/O");
            }
        }
    }
    
    public void chiudi() {
       dataClientSocket.close();   
    }
    
}
