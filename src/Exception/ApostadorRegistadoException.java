package Exception;

/**
 * Created by luismp on 11/11/2018.
 */
public class ApostadorRegistadoException extends Exception {
    String email;

    public ApostadorRegistadoException(String email){
        this.email = email;
    }

    public String getEmail(){
        return this.email;
    }
}
