package melocotron.resource;
import java.io.*;
import java.util.ArrayList;
import melocotron.resource.exceptions.*;

public class Subresource {
    
    private String name;
    private String subresourcePath;

    public Subresource(String name, String subresourcePath){
        this.name = name;
        this.subresourcePath = subresourcePath;
    }

    /**
        Execute subresource and return its output
    */
    public String access() throws IOException {
        String chunk = null, output = "";

        Process proc = Runtime.getRuntime().exec(this.subresourcePath);
        BufferedReader stdout = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        BufferedReader stderr = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

        output += "STDOUT\n";

        while ((chunk = stdout.readLine()) != null) {
            output += chunk;
        }

        output += "\nSTDERR\n";
        
        while ((chunk = stderr.readLine()) != null) {
            output += chunk;
        }

        return output + "\n";
    }

}
