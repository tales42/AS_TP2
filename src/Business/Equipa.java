package Business;

import java.io.Serializable;

/**
 * Created by luismp on 11/11/2018.
 */
public class Equipa implements Serializable {
    private int idEquipa;
    private String designacao;


    /**
     * Construtor parametrizado de uma Equipa
     * @param idEquipa
     * @param designacao
     */
    public Equipa(int idEquipa, String designacao){
        this.idEquipa = idEquipa;
        this.designacao = designacao;
    }

    /**
     * Getter do identificado da Equipa
     * @return idEquipa
     */
    public int getIdEquipa() {
        return this.idEquipa;
    }

    /**
     * Getter da designação de uma Equipa
     * @return designação
     */
    public String getDesignacao() {
        return this.designacao;
    }

    /**
     * Refactor:
     * Eliminados: clone(), equals() e toString(), construtor vazio e de cópia
     */
}
