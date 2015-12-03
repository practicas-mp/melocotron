package melocotron.net.protocol;

public class Message {
    private int opCode;
    private String body;

    

    public Message(String rawMessage){
        this.parseRawMessage();
    }

    public Message(int opCode, String body){
        this.opCode = opCode;
        this.body = body;
    }

    private void parseRawMessage(String rawMessage){
        int i = -1;

        while(rawMessage.charAt(++i).isDigit());

        this.opCode = Integer.parseInt(rawMessage.substring(0, i));
        this.body = this.unescape(rawMessage.substring(i));
    }

    public String raw(){
        return this.opCode + this.escape(this.body);
    }

    private String escape(String source){
        return source.replace("\n", "\\n");
    }

    private String unescape(String source){
        return source.replace("\\n", "\n");
    }

    public int getOpCode(){
        return opCode;
    }

    public String getBody(){
        return body;
    }
}
