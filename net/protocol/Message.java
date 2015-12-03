package melocotron.net.protocol;

public class Message {
    private int opCode;
    private String body;

    

    public Message(String rawMessage){
    
    }

    private void parseRawMessage(String rawMessage){}

    public String raw(){}

    public int getOpCode(){
        return opCode;
    }

    public String getBody(){
        return body;
    }
}
