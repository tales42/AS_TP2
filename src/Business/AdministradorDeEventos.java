package Business;

/**
 * Created by luismp on 11/11/2018.
 */
public class AdministradorDeEventos extends Utilizador {

    /**
     * Construtor Vazio
     */
    public AdministradorDeEventos(){}

    /**
     * Construtor de cópia
     * @param admin
     */
    public AdministradorDeEventos(AdministradorDeEventos admin){
        super(admin.getIdUtilizador(),admin.getEmail(),admin.getPassword(),admin.getNome());
    }

    /**
     * Construtor parametrizado
     * @param idUtilizador
     * @param email
     * @param password
     * @param nome
     */
    public AdministradorDeEventos(int idUtilizador, String email, String password, String nome){
        super(idUtilizador,email,password,nome);
    }


    /**
     * Clone
     * @return AdministradorDeEventos
     */
    public AdministradorDeEventos clone(){
        return new AdministradorDeEventos(this);
    }

    /**
     * Equals
     * @param object
     * @return Boolean
     */
    public boolean equals(Object object){
        if(object==this) return true;

        if(object==null || object.getClass() != getClass()) return false;

        AdministradorDeEventos admin = (AdministradorDeEventos) object;

        return getIdUtilizador() == admin.getIdUtilizador();
    }

    /**
     * toString
     * @return String
     */
    public String toString(){
        StringBuilder string = new StringBuilder();
        string
            .append("----Administrador----\nID : ")
            .append(this.getIdUtilizador())
            .append("\nEmail : ")
            .append(this.getEmail())
            .append("\nNome : ")
            .append( this.getNome() )
            .append("\n------------------\n");
        return string.toString();
    }

}
