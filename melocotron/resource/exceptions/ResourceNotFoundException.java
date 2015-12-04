package melocotron.resource.exceptions;

public class ResourceNotFoundException extends Exception {

    private String resourceName;


    public ResourceNotFoundException(String resourceName){
        this.resourceName = resourceName;
    }
}
