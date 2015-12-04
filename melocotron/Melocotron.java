package melocotron;
import melocotron.resource.ResourceList;
import melocotron.net.ConnectionHandler;
import melocotron.auth.Authenticator;

public class Melocotron {
    private static int PORT = 1338;

    
    public static void main(String[] args){
        if (args.length < 1) {
            System.err.println("Error: falta el directorio donde buscar los recursos"); 
        } 

        String resourceListPath = args[0];

        ResourceList resourceList = new ResourceList(resourceListPath);
        Authenticator authenticator = new Authenticator();
        ServerSocket server = new ServerSocket(PORT);


        while(true){
            Socket clientSocket = server.accept();

            try {
                ConnectionHandler handler = new ConnectionHandler(clientSocket, resourceList, authenticator);
                handler.start();
            } catch(IOException e){ 
                System.err.println("Error con el socket: abortando conexión");
                System.err.println(e.toString());
            }

        }

    }
}
