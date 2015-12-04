package melocotron.resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import melocotron.resource.Subresource;
import melocotron.resource.exceptions.*;
import static java.nio.file.FileVisitResult.*;


class SubresourceDiscoverer extends SimpleFileVisitor<Path> {

    private ArrayList<Path> subresources;

    public SubresourceDiscoverer(){
        this.subresources = new ArrayList<Path>();
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
        if (attr.isRegularFile() && file.toFile().canExecute()){
            this.subresources.add(file.toAbsolutePath());
        }

        return CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attr){
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        System.err.println(exc);
        return CONTINUE;
    }

    public ArrayList<Path> getSubresources(){
        return this.subresources;
    }
}


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
        Path root = Paths.get(this.resourcePath);
        SubresourceDiscoverer discoverer = new SubresourceDiscoverer();
        Files.walkFileTree(root, discoverer);
        ArrayList<Path> subresources = discoverer.getSubresources();

        Subresource sub;
        for(Path p: subresources){
            sub = new Subresource(p.getFileName().toString(), p.toString());
            this.subresources.put(p.getFileName().toString(), sub);
        }
    }

    public ArrayList<String> getSubresourceNames(){
        ArrayList<String> subresourceNames = new ArrayList<String>();

        for(String subresourceName: this.subresources.keySet()){
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
        Subresource sub = this.subresources.get(subresourceName);

        if(sub == null){
            throw new SubresourceNotFoundException(this.name, subresourceName);
        }

        return sub.access();
    }

}
