package Business;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by luismp on 10/11/2018.
 */
public class Notificacao implements Serializable {
    private LocalDateTime data;
    private String texto;

    /*
    * Construtores
    * */

    public Notificacao(){
        this.data = LocalDateTime.now();
        this.texto="";
    }

    public Notificacao(LocalDateTime data, String texto){
        this.data = data;
        this.texto = texto;
    }

    public Notificacao(Notificacao notificacao){
        this.data = notificacao.getData();
        this.texto = notificacao.getTexto();
    }

    /*
    * Getters e Setters
    * */

    public LocalDateTime getData() {
        return this.data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getTexto() {
        return this.texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    /*
    * Clone, Equals e toString
    * */

    public Notificacao clone(){
        return  new Notificacao(this);
    }

    public boolean equals(Object object){
        if(object == this) return true;

        if((object==null) || (object.getClass() != this.getClass())) return false;

        Notificacao notificacao = (Notificacao) object;

        if(notificacao.getData().equals(this.getData()) && notificacao.getTexto().equals(this.getTexto())) return true;
        else return false;
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("----Notificação----\n");
        s.append("Timestamp : " + this.getData() + "\n");
        s.append(this.getTexto() + "\n");
        s.append("-------------------\n");
        return s.toString();
    }


}
