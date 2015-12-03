package melocotron.resource;
import java.util.HashMap;
import java.io.File;
import melocotron.resource.Subresource;
import melocotron.resource.exceptions.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

public class Resource {
    
    private String name;

    // subresourceName -> subresource
    private HashMap<String, Subresource> subresources;
    private String resourcePath;
    
    public Resource(String name, String resourcePath){
        this.name = name;
        this.resourcePath = resourcePath;

        this.discoverSubresources();
    }
    
    /**
        Get executable files in resourcePath and populate
        subResources arrayList
    */
    private void discoverSubresources(){
        File basedir = new File(this.resourcePath);
        Iterator<File> subresources = iterateFiles(basedir, TrueFileFilter.TRUE, TrueFileFilter.TRUE);
        Subresource sub;

        for(File f: this.subresources){
            if(f.canExecute()){
                sub = new Subresource(f.getName(), f.getAbsolutePath());
                this.subresources.put(f.getName(), sub);
            }
        }
    }

    public ArrayList<String> getSubresourceNames(){
        ArrayList<String> subresourceNames = new ArrayList<String>();

        for(String subresourceName: thus.subresources.keySet()){
            subresourceNames.add(subresourceName);
        }

        return subresourceNames;        
    }

    /**
        Access subresources and return a map
        subresourceName <=> subresourceContent 
    */
    public HashMap<String, String> access(){
        HashMap<String, String> result = new HashMap<String, String>();

        for(Map.Entry<String, Subresource> entry: this.subresources.entrySet()){
            result.put(entry.getKey(), entry.getValue().access());
        }

        return result;
    }


    public String accessSubresource(String subresourceName) throws SubresourceNotFoundException {
        Subresource sub = this.subresources.get(subresourceName)

        if(sub == null){
            throw new SubresourceNotFoundException(subresourceName);
        }

        return sub.access();
    }

}
