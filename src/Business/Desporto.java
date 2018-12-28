package Business;

import java.io.Serializable;

/**
 * Created by luismp on 11/11/2018.
 */
public class Desporto implements Serializable {
    private int idDesporto;
    private String designacao;


    /**
     * Construtor parametrizado de um Desporto
     * @param idDesporto
     * @param designacao
     */
    public Desporto(int idDesporto, String designacao){
        this.idDesporto = idDesporto;
        this.designacao = designacao;
    }


    /**
     * Getter do identificador do desporto
     * @return idDesporto
     */
    public int getIdDesporto() {
        return idDesporto;
    }

    /**
     * Getter da designação do desporto
     * @return designação
     */
    public String getDesignacao() {
        return designacao;
    }

}
