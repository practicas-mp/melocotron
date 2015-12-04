package melocotron.resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import melocotron.resource.Resource;
import melocotron.resource.exceptions.*;
import static java.nio.file.FileVisitResult.*;


class ResourceDiscoverer extends SimpleFileVisitor<Path> {

    private ArrayList<Path> resources;

    public ResourceDiscoverer(){
        this.resources = new ArrayList<Path>();
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
        if (attr.isDirectory()){
            this.resources.add(file.toAbsolutePath());
        }

        return CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attr){
        return SKIP_SUBTREE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        System.err.println(exc);
        return CONTINUE;
    }

    public ArrayList<Path> getResources(){
        return this.resources;
    }
}


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
        Path root = Paths.get(this.resourceListPath);
        ResourceDiscoverer discoverer = new ResourceDiscoverer();
        Files.walkFileTree(root, discoverer);
        ArrayList<Path> resources = discoverer.getResources();

        Resource res;
        for(Path p: resources){
            res = new Resource(p.getFileName().toString(), p.toString());
            this.resources.put(p.getFileName().toString(), res);
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
