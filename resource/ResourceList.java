package melocotron.resource;
import melocotron.resource.Resource;
import melocotron.resource.exceptions.*;
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
    private void discoverResources(){
        File basedir = new File(this.resourceListPath);
        Iterator<File> resources = iterateFiles(basedir, TrueFileFilter.TRUE, null);
        Resource res;

        for(File f: this.resources){
            if(f.isDirectory()){
                res = new Resource(f.getName(), f.getAbsolutePath());
                this.resources.put(f.getName(), res);
            }
        }
    }

    public ArrayList<String> getResourceNames(){
        ArrayList<String> resourceNames = new ArrayList<String>();

        for(String resourceName : resources.keySet()){
            resourceNames.add(resourceName);
        }

        return resourceNames;
    }

    public ArrayList<String> getSubresourceNames(String resourceName){
        return this.resources.get(resourceName).getSubresourceNames();
    }

    public HashMap<String, String> accessResource(String resourceName){
        Resource res = this.resources.get(resourceName);

        if(res == null){
            throw new ResourceNotFoundException(resourceName);
        } 

        return res.access();
    }

    public String accessSubresource(String resourceName, String subresourceName) throws ResourceNotFoundException {  //, SubresourceNotFoundException {
        Resource res = this.resources.get(resourceName);

        if(res == null){
            throw new ResourceNotFoundException(resourceName);
        } 

        return res.accessSubresource(subresourceName);
    }

}
