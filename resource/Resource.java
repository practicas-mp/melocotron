package melocotron.resource;
import java.util.HashMap;
import melocotron.resource.Subresource;

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
    
    }

    /**
        Access subresources and return a map
        subresourceName <=> subresourceContent 
    */
    public HashMap<String, String> access(){}


    public String accessSubresource(String subresourceName){
    
    }

}
