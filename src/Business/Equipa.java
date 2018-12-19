package Business;

import java.io.Serializable;

/**
 * Created by luismp on 11/11/2018.
 */
public class Equipa implements Serializable {
    private int idEquipa;
    private String designacao;

    /*
    * Construtores
    * */

    public Equipa(){
        this.idEquipa = 0;
        this.designacao = "";
    }

    public Equipa(int idEquipa, String designacao){
        this.idEquipa = idEquipa;
        this.designacao = designacao;
    }

    public Equipa(Equipa equipa){
        this.idEquipa = equipa.getIdEquipa();
        this.designacao = equipa.getDesignacao();
    }

    /*
    * Getters e Setters
    * */

    public int getIdEquipa() {
        return this.idEquipa;
    }

    public void setIdEquipa(int idEquipa) {
        this.idEquipa = idEquipa;
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

    public Equipa clone(){
        return new Equipa(this);
    }

    public boolean equals(Object object){
        if(object==this) return true;

        if((object==null) || (object.getClass() != this.getClass())) return false;

        Equipa equipa = (Equipa) object;

        if(equipa.getIdEquipa() == this.getIdEquipa()) return true;
        else return false;
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("----Equipa----\n");
        s.append("ID : " + this.getIdEquipa() + "\n");
        s.append(this.getDesignacao()+"\n");
        s.append("------------------\n");
        return s.toString();
    }
}
