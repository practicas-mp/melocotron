package melocotron.net.protocol;

public class Codes {

    public static final int OP_AUTH = 10;
    public static final int OP_LIST_RESOURCES = 20;
    public static final int OP_LIST_SUBRESOURCES = 30;
    public static final int OP_ACCESS_RESOURCE = 40;
    public static final int OP_ACCESS_SUBRESOURCE = 50;
    public static final int OP_BYE = 60;
    public static final int AUTH_VALID = 110;
    public static final int AUTH_INVALID = 510;
    public static final int LIST_RESOURCES = 120;
    public static final int LIST_SUBRESOURCES = 130;
    public static final int RESOURCE_NOT_FOUND = 530;
    public static final int SHOW_RESOURCE = 140;
    public static final int SHOW_SUBRESOURCE = 150;
    public static final int SUBRESOURCE_NOT_FOUND = 550;
    public static final int UNKNOWN_OPERATION = 570;
    public static final int KTHX_BYE = 160;

}
