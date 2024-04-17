package Server;

/**
 *
 * @author Banella Lorenzo
 */
public class MainServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       Server server = new Server(4002);
       server.comunica();
      
    }
    
}
