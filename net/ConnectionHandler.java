package melocotron.net;
import melocotron.auth.Authenticator;
import melocotron.net.protocol.ProtocolSpeaker;
import melocotron.resource.ResourceList;
import melocotron.resource.Resource;

public class ConnectionHandler implements Runnable {

    private Authenticator authenticator;
    private ProtocolSpeaker protocolSpeaker;
    private ResourceList resources;
    
    public ConnectionHandler(Socket client, ResourceList resources, Authenticator authenticator){
        this.protocolSpeaker = new ProtocolSpeaker(client);
        this.resources = resources;
        this.authenticator = authenticator;
    }

    public void start(){
        Thread thread = new Thread(this, "ConnectionHandler thread"); 
        thread.start();
    }

    /**
        Listen to new messages and answer them
    */
    public void run(){

    }

}
