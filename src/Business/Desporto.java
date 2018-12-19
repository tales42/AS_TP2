package Business;

import java.io.Serializable;

/**
 * Created by luismp on 11/11/2018.
 */
public class Desporto implements Serializable {
    private int idDesporto;
    private String designacao;

    /*
    * Construtores
    * */

    public Desporto(){
        this.idDesporto = 0;
        this.designacao = "";
    }

    public Desporto(int idDesporto, String designacao){
        this.idDesporto = idDesporto;
        this.designacao = designacao;
    }

    public Desporto(Desporto desporto){
        this.idDesporto = desporto.getIdDesporto();
        this.designacao = desporto.getDesignacao();
    }

    /*
    * Getters e Setters
    * */

    public int getIdDesporto() {
        return this.idDesporto;
    }

    public void setIdDesporto(int idDesporto) {
        this.idDesporto = idDesporto;
    }

    public String getDesignacao() {
        return this.designacao;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }

    /*
    * Clone, Equals e toString
    * */

    public Desporto clone(){
        return new Desporto(this);
    }

    public boolean equals(Object object){
        if(object==this) return true;

        if((object==null) || (object.getClass() != this.getClass())) return false;

        Desporto desporto = (Desporto) object;

        if(desporto.getIdDesporto() == this.getIdDesporto()) return true;
        else return false;
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("----Desporto----\n");
        s.append("ID : " + this.getIdDesporto() + "\n");
        s.append(this.getDesignacao()+"\n");
        s.append("------------------\n");
        return s.toString();
    }
}
