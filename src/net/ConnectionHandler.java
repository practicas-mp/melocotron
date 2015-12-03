package melocotron.net;

import java.util.ArrayList;
import java.util.HashMap;
import melocotron.auth.Authenticator;
import melocotron.net.protocol.ProtocolSpeaker;
import melocotron.net.protocol.Message;
import melocotron.net.protocol.Codes.*;
import melocotron.resource.ResourceList;
import melocotron.resource.Resource;
import melocotron.resource.exceptions.ResourceNotFoundException;
import melocotron.resource.exceptions.SubresourceNotFoundException;

public class ConnectionHandler implements Runnable {

    private Authenticator authenticator;
    private ProtocolSpeaker protocolSpeaker;
    private ResourceList resources;
    private Boolean authenticated = false;
    
    public ConnectionHandler(Socket client, ResourceList resources, Authenticator authenticator) throws IOException {
        this.resources = resources;
        this.authenticator = authenticator;
        this.protocolSpeaker = new ProtocolSpeaker(client);
    }

    public void start(){
        Thread thread = new Thread(this, "ConnectionHandler thread"); 
        thread.start();
    }

    /**
        Listen to new messages and answer them
    */
    public void run(){
        Message input;
        try {
            input = protocolSpeaker.receive();
        } catch (IOException e){
            System.err.println("Hubo un error recibiendo un mensaje del socket");
            System.err.println(e.toString());
            return;
        }
        
        while (input.getOpCode() != OP_BYE){
            Message outputMessage;

            switch(input.getOpCode()){
                case OP_AUTH:
                    outputMessage = handleAuth(input);
                    break;
                case OP_LIST_RESOURCES:
                    outputMessage = handleListResources(input);
                    break;
                case OP_LIST_SUBRESOURCES:
                    outputMessage = handleListSubresources(input);
                    break;
                case OP_ACCESS_RESOURCE:
                    outputMessage = handleAccessResource(input);
                    break;
                case OP_ACCESS_SUBRESOURCE:
                    outputMessage = handleAccessSubresource(input);
                    break;
                default:
                    outputMessage = handleUnknownOperation(input);

            }

            try {
                protocolSpeaker.send(outputMessage);
            } catch(IOException e) {
                System.err.println("Hubo un error enviando un mensaje");
                System.err.println(e.toString());
                return;
            }
 
            try {
                input = protocolSpeaker.receive();
            } catch (IOException e){
                System.err.println("Hubo un error recibiendo un mensaje del socket");
                System.err.println(e.toString());
                return;
            }
        
        
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

        Message output = new Message(LIST_RESOURCES, join(resourceNames));

        return output;
    }


    private Message handleListSubresources(Message input){
        String resourceName = input.getBody();

        Message output;

        try {
            ArrayList<String> subresourceNames = resourceList.getSubresourceNames(resourceName);
            String flatSubresourceNames = join(subresourceNames);
            output = new Message(LIST_SUBRESOURCES, flatSubresourceNames);
        } catch(ResourceNotFoundException e){
            
            output = new Message(RESOURCE_NOT_FOUND, resourceName);
        }

        return output;
    }

    private Message handleAccessResource(Message input){
        Message output;

        String resourceName = input.getBody();

        try {
            HashMap<String, String> subresourceResults = resourceList.accessResource(resourceName);

            ArrayList<String> serializedResults = new ArrayList<String>();

            for(String subresourceName : subresourceResults.keySet() ){
                serializedResults.add(subresourceName + "/" + subresourceResults.get(subresourceName));
            }

            output = new Message(SHOW_RESOURCE, join(serializedResults));

        } catch(ResourceNotFoundException e) {
            output = new Message(RESOURCE_NOT_FOUND, resourceNAme);
        }


        return output;
    }

    private Message handleAccessSubresource(Message input){
        Message output; 

        String[] data = input.getBody().split(":");
        
        if (data.length != 2){
            // ToDo: handle
        }

        String resourceName = data[0];
        String subresourceName = data[1];

        try {
            String result = resourceList.accessSubresource(resourceName, subresourceName);
            output = new Message(SHOW_SUBRESOURCE, result);
        } catch (ResourceNotFoundException e) {
            output = new Message(RESOURCE_NOT_FOUND, resourceName);
        } catch (SubresourceNotFoundException e) {
            output = new Message(SUBRESOURCE_NOT_FOUND, subresourceName);
        }

        return output;

    }

    private Message handleUnknownOperation(Message input){
        return new Message(UNKNOWN_OPERATION);
    }



    private String join(ArrayList<String> names){
        String result = ""; 

        if (names.size() > 0){
            StringBuilder flatList = new StringBuilder();

            flatList.append(names[0]);

            for(int i = 1; i < names.size(); i++){
                flatList.append(":" + names.get(i));
            }

            result = new String(flatList);
        } 

        return result;
    }

}
