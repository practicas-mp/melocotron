package melocotron;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import melocotron.auth.Authenticator;
import melocotron.net.ConnectionHandler;
import melocotron.resource.ResourceList;

public class Melocotron {
    private static int PORT = 1338;

    
    public static void main(String[] args){
        if (args.length < 1) {
            System.err.println("Error: falta el directorio donde buscar los recursos"); 
        } 

        String resourceListPath = args[0];

        ResourceList resourceList = new ResourceList(resourceListPath);
        Authenticator authenticator = new Authenticator();
		ServerSocket server;
		try {
			server = new ServerSocket(PORT);
		} catch (IOException e) {
			System.err.println("Hubo un error bindeando");
			System.err.println(e.toString());
			return;
		}

        while(true){
            try {
				Socket clientSocket = server.accept();
                ConnectionHandler handler = new ConnectionHandler(clientSocket, resourceList, authenticator);
                handler.start();
            } catch(IOException e){ 
                System.err.println("Error con el socket: abortando conexión");
                System.err.println(e.toString());
            }

        }

    }
}
