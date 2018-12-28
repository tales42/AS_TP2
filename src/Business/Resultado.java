package Business;

import java.io.Serializable;

/**
 * Created by luismp on 11/11/2018.
 */
public class Resultado implements Serializable {
    public String designacao;
    public double odd;

    /**
     * Construtor Vazio de um Resultado
     */
    public Resultado(){
        this.designacao = "";
        this.odd = 0;
    }

    /**
     * Construtor parametrizado de um Resultado
     * @param designacao
     * @param odd
     */
    public Resultado(String designacao, double odd){
        this.designacao = designacao;
        this.odd = odd;
    }

    /**
     * Getter da Designação de um Resultado
     * @return Designação
     */
    public String getDesignacao() {
        return designacao;
    }

    /**
     * Getter da Odd de um Resultado
     * @return Odd
     */
    public double getOdd() {
        return odd;
    }

    /**
     * Método equals
     * @param object
     * @return Boolean
     */
    public boolean equals(Object object){
        if(object == this) return true;

        if(object==null || object.getClass() != this.getClass()) return false;

        Resultado resultado = (Resultado) object;

        return resultado.getDesignacao().equals(this.getDesignacao()) && resultado.getOdd() == this.getOdd();
    }

    /**
     * toString
     * @return String
     */
    public String toString(){
        StringBuilder string = new StringBuilder();
        string
            .append("----Resultado----\nOdd : ")
            .append(this.getOdd())
            .append("\n")
            .append(this.getDesignacao())
            .append("\n-----------------\n");
        return string.toString();
    }
}
