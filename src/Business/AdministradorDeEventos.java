package Business;

/**
 * Created by luismp on 11/11/2018.
 */
public class AdministradorDeEventos extends Utilizador {

    /*
    * Constutores
    * */

    public AdministradorDeEventos(){
        super();
    }

    public AdministradorDeEventos(AdministradorDeEventos admin){
        super(admin.getIdUtilizador(),admin.getEmail(),admin.getPassword(),admin.getNome());
    }

    public AdministradorDeEventos(int idUtilizador, String email, String password, String nome){
        super(idUtilizador,email,password,nome);
    }

    /*
    * Clone, Equals e toString
    * */

    public AdministradorDeEventos clone(){
        return new AdministradorDeEventos(this);
    }

    public boolean equals(Object object){
        if(object==this) return true;

        if(object==null || object.getClass() != this.getClass()) return false;

        AdministradorDeEventos admin = (AdministradorDeEventos) object;

        return this.getIdUtilizador() == admin.getIdUtilizador();
    }

    @Override
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

    /*
    * Métodos Business.BetESS
    * */

    // Ligação entre admin e eventos, ou método passa para Business.BetESS ?
}
