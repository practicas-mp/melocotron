package melocotron.auth;

public class Authenticator{
    
    private String user = "bbb";
    private String pass = "ccc";

    public Boolean authenticate(String user, String pass){
		return this.user.equals(user) && this.pass.equals(pass);
	}
}
