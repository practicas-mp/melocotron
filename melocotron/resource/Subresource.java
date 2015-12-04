package melocotron.resource;

import java.io.*;
import java.util.ArrayList;
import melocotron.resource.exceptions.SubresourceNotFoundException;
import melocotron.resource.exceptions.ResourceNotFoundException;

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
    public String access(){
        String chunk = null, output = "";

		Process proc;
		try {
        	proc = Runtime.getRuntime().exec(this.subresourcePath);
		} catch(IOException e){
			System.err.println("Error ejecutando archivo");
			System.err.println(e.toString());
			return "error ejecutando";
		}
        BufferedReader stdout = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        BufferedReader stderr = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

        output += "STDOUT\n";

		try {
			while ((chunk = stdout.readLine()) != null) {
				output += chunk;
			}

			output += "\nSTDERR\n";
			
			while ((chunk = stderr.readLine()) != null) {
				output += chunk;
			}
		} catch(IOException e){
			System.err.println("Error con la salida del archivo");
		}
        return output + "\n";
    }

}
