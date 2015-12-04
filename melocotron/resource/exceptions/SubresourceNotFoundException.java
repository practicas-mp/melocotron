package melocotron.resource.exceptions;

public class SubresourceNotFoundException extends Exception {
    private String resourceName;
    private String subresourceName;

    public SubresourceNotFoundException(String resourceName, String subresourceName) {
        this.resourceName = resourceName;
        this.subresourceName = subresourceName;
    }

}
