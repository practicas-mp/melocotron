package melocotron.net.protocol;
import melocotron.net.protocol.Message;
import melocotron.auth.Authenticator;
import java.util.List;
import java.util.ArrayList;
import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ProtocolSpeaker {
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    
    public ProtocolSpeaker(Socket socket) throws IOException {
        this.socket = socket; 
        this.input = new DataInputStream(socket.getInputStream());
        this.output = new DataOutputStream(socket.getOutputStream());
    }

    public Message receive() throws IOException {
        ArrayList<Byte> bytesReceived = new ArrayList<Byte>();

		Byte received;

        do {
            received = input.readByte(); 
            bytesReceived.add(received);
        } while(received != '\n');

        String rawMessage = byteListToString(bytesReceived);
        return new Message(rawMessage);
    }

    public void send(Message message) throws IOException { 
        String rawMessage = message.raw(); 
        output.writeChars(rawMessage);
    }


    public void close(){
        socket.close(); 
    }

    private String byteListToString(List<Byte> bytes){
        byte[] bytearray = new byte[bytes.size()];
		for(int i = 0; i < bytes.size(); i++){
			bytearray[i] = bytes.get(i);
		}
        return new String(bytearray);
    }
}
