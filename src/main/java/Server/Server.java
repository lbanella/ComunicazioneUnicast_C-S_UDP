package Server;

import java.net.*;
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
public class Server {
    
    private byte[] bufferIN = new byte[1024];
    private byte[] bufferOUT = new byte[1024];
    private DatagramSocket dataServerSocket;
    private int porta;
    private InetAddress IPClient;
    private int portaClient;
    private DatagramPacket sendPacket;
    private DatagramPacket receivePacket;
    private String messaggioDaInviare;
    private String messaggioRicevuto;

    private Scanner scanner;
    public Server(int porta) {
        this.scanner=new Scanner(System.in);
        this.porta = porta; 
        receivePacket =  new DatagramPacket(bufferIN, bufferIN.length);
        
      try {
        System.out.println("Server in attesa di un messaggio");  
        dataServerSocket = new DatagramSocket(this.porta);  
         
        } 
        catch (BindException e) {
            System.out.println("Porta già in uso");
            e.printStackTrace();
        }
        
        catch (SocketException e) {
            System.out.println("Eccezzione Socket nella fase di avvio del server");
            e.printStackTrace();
        } 
        
    }
    
    public void comunica() {
        while(!dataServerSocket.isClosed()) {
            ricevi();
            invia();
        }
    }
    
    
    
    public void invia() {
        if(!dataServerSocket.isClosed()){
            try {
                System.out.println("Invia il tuo messaggio -- per chiudere la comunicazione scrivi : chiudi ");
                messaggioDaInviare = scanner.nextLine();
            
                bufferOUT = messaggioDaInviare.getBytes();
                
                sendPacket = new DatagramPacket(bufferOUT, bufferOUT.length, IPClient, portaClient);
                dataServerSocket.send(sendPacket);
                
                System.out.println("Messaggio inviato  : " + messaggioDaInviare);
                
                if(messaggioDaInviare.equalsIgnoreCase("chiudi")) {
                    System.out.println("Il Server ha deciso di chiudere la comunicazione");
                    chiudi();    
                }
                
            } catch (SocketException e) {
                System.out.println("Eccezzione Socket durante la risposta  del Server al Client");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("Eccezzione I/O");
                e.printStackTrace();
            } 
        }
    }
    
    public void ricevi() {
        if(!dataServerSocket.isClosed()){
            try {
                dataServerSocket.receive(receivePacket);
                    
                IPClient = receivePacket.getAddress();
                portaClient = receivePacket.getPort();
                    
                messaggioRicevuto = new String(receivePacket.getData());
                int lunghezza = receivePacket.getLength();
                messaggioRicevuto = messaggioRicevuto.substring(0, lunghezza);
                System.out.println("Messaggio ricevuto :" + messaggioRicevuto);
                    
                if(messaggioRicevuto.equalsIgnoreCase("chiudi ")) {
                    System.out.println("Il Server chiude la comunicazione con il client perchè esso ha deciso di interromperla");
                    chiudi();   
                }
                    
            } catch (SocketException e) {
                System.out.println("Eccezzione Socket durante la ricezione del messaggio inviato dal  Client verso il Server");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("Eccezzione I/O");
                e.printStackTrace();
            } 
        }
    }


    public void chiudi() {
       dataServerSocket.close();   
    }
    
}
