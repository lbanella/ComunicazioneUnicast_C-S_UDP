package Client;

/**
 *
 * @author Banella Lorenzo
 */
public class MainClient {

    /**
     * @param args the command line arguments
     */
     public static void main(String[] args) {
      Client client = new Client(4002); 
      System.out.println("-------------------------------");
      System.out.println("---------CLIENT ATTIVO---------");
      System.out.println("-------------------------------");
      client.connetti();
      client.comunica();
     
    }
    
}
