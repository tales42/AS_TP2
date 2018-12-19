package Business;

import java.io.Serializable;

/**
 * Created by luismp on 11/11/2018.
 */
public class Resultado implements Serializable {
    public String designacao;
    public double odd;

    /*
    * Getters e Setters
    * */

    public Resultado(){
        this.designacao = "";
        this.odd = 0;
    }

    public Resultado(String designacao, double odd){
        this.designacao = designacao;
        this.odd = odd;
    }

    public Resultado(Resultado resultado){
        this.designacao = resultado.getDesignacao();
        this.odd = resultado.getOdd();
    }

    /*
    * Construtores
    * */

    public String getDesignacao() {
        return this.designacao;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }

    public double getOdd() {
        return this.odd;
    }

    public void setOdd(double odd) {
        this.odd = odd;
    }

    /*
    * Clone, Equals e toString
    * */

    public Resultado clone(){
        return new Resultado(this);
    }

    public boolean equals(Object object){
        if(object == this) return true;

        if((object==null) || (object.getClass() != this.getClass())) return false;

        Resultado resultado = (Resultado) object;

        if((resultado.getDesignacao().equals(this.getDesignacao())) && (resultado.getOdd() == this.getOdd())) return true;
        else return false;
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("----Resultado----\n");
        s.append("Odd : "+ this.getOdd() + "\n");
        s.append(this.getDesignacao() + "\n");
        s.append("-----------------\n");
        return s.toString();
    }
}
