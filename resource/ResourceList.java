package melocotron.resource;
import melocotron.resource.Resource;
import java.util.HashMap;

public class ResourceList {
    
    private String resourceListPath;

    // resourceName -> resource
    private HashMap<String, Resource> resources;

    public ResourceList(String resourceListPath){
        this.resourceListPath = resourceListPath;
        this.discoverResources();
    }

    /**
        Get folder names in resourceListPath and 
        add them as resources to resources
    */
    private void discoverResources(){}

    public HashMap<String, String> accessResource(String resourceName){}

    public String accessSubResource(String resourceName, String subresourceName) {}

}
