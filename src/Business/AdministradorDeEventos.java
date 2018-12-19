package Business;

/**
 * Created by luismp on 11/11/2018.
 */
public class AdministradorDeEventos extends Utilizador {
    private boolean estado;


    public AdministradorDeEventos(int idUtilizador, String email, String password, String nome){
        super(idUtilizador,email,password,nome);
        estado = true;
    }

    /**
     * Refactor:
     * Eliminados: construtor vazio e construtor de cópia (não utilizados) , equals(), clone(), toString(), getters e setters
     * Classe quase inútil ?
     */

}
