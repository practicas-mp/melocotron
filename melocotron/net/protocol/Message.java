package melocotron.net.protocol;

public class Message {
    private int opCode;
    private String body;

    

    public Message(String rawMessage){
        this.parseRawMessage(rawMessage);
    }

    public Message(int opCode, String body){
        this.opCode = opCode;
        this.body = body;
    }

	public Message(int opCode){
		this.opCode = opCode;
		this.body = "";
	}

    private void parseRawMessage(String rawMessage){
        int i = 0;

        Boolean goodMessage = rawMessage.length() > 0;
        System.out.println("Parsing: " + rawMessage);
		for(i = 0; i < rawMessage.length(); i++){
			if (Character.isDigit(rawMessage.charAt(i)) == false){
                goodMessage = i != 0;
				break;
            }
		}

        if (goodMessage){   
            this.opCode = Integer.parseInt(rawMessage.substring(0, i));
            this.body = this.unescape(rawMessage.substring(i));
        } else {
            this.opCode = -100; // Unknown operation
            this.body = "";
        }
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
