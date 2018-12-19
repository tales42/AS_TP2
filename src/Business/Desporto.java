package Business;

import java.io.Serializable;

/**
 * Created by luismp on 11/11/2018.
 */
public class Desporto implements Serializable {
    private int idDesporto;
    private String designacao;

    public Desporto(int idDesporto, String designacao){
        this.idDesporto = idDesporto;
        this.designacao = designacao;
    }


    public int getIdDesporto() {
        return idDesporto;
    }

    public String getDesignacao() {
        return designacao;
    }

    /**
     * Refactor:
     * Eliminados: clone(), equals() e toString(), construtor vazio e de cópia
     */
}
