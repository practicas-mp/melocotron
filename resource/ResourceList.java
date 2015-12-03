package melocotron.resource;
import melocotron.resource.Resource;
import java.util.HashMap;
import java.util.ArrayList;

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

    public ArrayList<String> getResourceNames(){
        ArrayList<String> resourceNames = new ArrayList<String>();

        for(String resourceName : resources.keySet()){
            resourceNames.add(resourceName);
        }

        return resourceNames;
    }

    public HashMap<String, String> accessResource(String resourceName){}

    public String accessSubResource(String resourceName, String subresourceName) {}

}
