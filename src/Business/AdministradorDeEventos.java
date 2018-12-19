package Business;

/**
 * Created by luismp on 11/11/2018.
 */
public class AdministradorDeEventos extends Utilizador {
    private boolean estado;

    /*
    * Constutores
    * */

    public AdministradorDeEventos(){
        super();
        this.estado = true;
    }

    public AdministradorDeEventos(int idUtilizador, String email, String password, String nome, boolean estado){
        super(idUtilizador,email,password,nome);
        this.estado=estado;
    }

    public AdministradorDeEventos(AdministradorDeEventos a){
        super(a.getIdUtilizador(),a.getEmail(),a.getPassword(),a.getNome());
        this.setEstado(a.getEstado());
    }

    /*
    * Getters e Setters
    * */

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public boolean getEstado(){
        return this.estado;
    }

    /*
    * Clone, Equals e toString
    * */

    public AdministradorDeEventos clone(){
        return new AdministradorDeEventos(this);
    }

    public boolean equals(Object object){
        if(object==this) return true;

        if((object==null) || (object.getClass() != this.getClass())) return false;

        AdministradorDeEventos admin = (AdministradorDeEventos) object;

        if(this.getIdUtilizador() == admin.getIdUtilizador()) return true ;
        else return false;
    }

    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("----Administrador----\n");
        s.append("ID : " + this.getIdUtilizador() + "\n");
        s.append("Email : " + this.getEmail() + "\n");
        s.append("Nome : " + this.getNome() + "\n");
        s.append("------------------\n");
        return s.toString();
    }

    /*
    * Métodos Business.BetESS
    * */

    // Ligação entre admin e eventos, ou método passa para Business.BetESS ?
}
