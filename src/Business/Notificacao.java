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


    public LocalDateTime getData() {
        return this.data;
    }

    public String getTexto() {
        return this.texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }


    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("----Notificação----\n");
        s.append("Timestamp : " + this.getData() + "\n");
        s.append(this.getTexto() + "\n");
        s.append("-------------------\n");
        return s.toString();
    }

    /**
     * Refactor:
     * Eliminados: clone(), equals(), construtor parametrizado e de cópia e setData
     */
}
