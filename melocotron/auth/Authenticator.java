package melocotron.auth;

public class Authenticator{
    
    private String user = "bbb";
    private String pass = "ccc";

    public Boolean authenticate(String user, String pass){
		System.out.println("Pass " + pass + pass.length());
		System.out.println(this.pass.equals(pass));

		return this.user.equals(user) && this.pass.equals(pass);
	}
}
