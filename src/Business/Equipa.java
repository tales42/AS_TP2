package Business;

import java.io.Serializable;

/**
 * Created by luismp on 11/11/2018.
 */
public class Equipa implements Serializable {
    private int idEquipa;
    private String designacao;


    public Equipa(int idEquipa, String designacao){
        this.idEquipa = idEquipa;
        this.designacao = designacao;
    }

    public int getIdEquipa() {
        return this.idEquipa;
    }


    public String getDesignacao() {
        return this.designacao;
    }

    /**
     * Refactor:
     * Eliminados: clone(), equals() e toString(), construtor vazio e de cópia
     */
}
