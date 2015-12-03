package melocotron.net;
import melocotron.auth.Authenticator;
import melocotron.net.protocol.ProtocolSpeaker;
import melocotron.net.protocol.Message;
import melocotron.net.protocol.Codes.*;
import melocotron.resource.ResourceList;
import melocotron.resource.Resource;

public class ConnectionHandler implements Runnable {

    private Authenticator authenticator;
    private ProtocolSpeaker protocolSpeaker;
    private ResourceList resources;
    private Boolean authenticated = false;
    
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
        Message inputMessage = protocolSpeaker.receive();

        while (inputMessage.getOpCode() != OP_BYE){
            Message outputMessage;

            switch(inputMessage.getOpCode()){
                case OP_AUTH:
                    outputMessage = handleAuth(inputMessage);
                    break;
                case OP_LIST_RESOURCES:
                    outputMessage = handleListResources(inputMessage);
                    break;
                case OP_LIST_SUBRESOURCES:
                    outputMessage = handleListSubresources(inputMessage);
                    break;
                case OP_ACCESS_RESOURCE:
                    outputMessage = handleAccessResource(inputMessage);
                    break;
                case OP_ACCESS_SUBRESOURCE:
                    outputMessage = handleAccessSubresource(inputMessage);
                    break;
                default:
                    outputMessage = handleUnknownOperation(inputMessage);

            }

            protocolSpeaker.send(outputMessage);
        }

        protocolSpeaker.close();

    }


    private Message handleAuth(Message input){
        String[] credentials = input.getBody().split(":");

        if (credentials.length != 2){
            return new Message(AUTH_INVALID);
        }

        String username = credentials[0];
        String password = credentials[1];

        Message output;

        if (authenticator.authenticate(username, password)){
            authenticated = true;
            output = new Message(AUTH_VALID);
        } else {
            authenticated = false;
            output = new Message(AUTH_INVALID);
        }

        return output;
    }

    private Message handleListResources(Message input){ 
        ArrayList<String> resourceNames = resourceList.getResourceNames();

        Message output = new Message(LIST_RESOURCES, flatResourceNameList(resourceNames));

        return output;
    }


    private Message handleListSubresources(Message input){
        

    }

    private Message handleAccessResource(Message input){


    }

    private Message handleAccessSubresource(Message input){


    }

    private Message handleUnknownOperation(Message input){


    }



    private String flatResourceNameList(ArrayList<String> resourceNames){
        String result = ""; 

        if (resourceNames.size() > 0){
            StringBuilder flatList = new StringBuilder();

            flatList.append(resourceNames[0]);

            for(int i = 1; i < resourceNames.size(); i++){
                flatList.append(":" + resourceNames.get(i));
            }

            result = new String(flatList);
        } 

        return result;
    }

}
