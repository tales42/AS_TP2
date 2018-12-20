package Business;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by luismp on 10/11/2018.
 */
public class Notificacao implements Serializable {
    private LocalDateTime data;
    private String texto;

    /**
     * Construtor Vazio de uma Notificação
     */
    public Notificacao(){
        this.data = LocalDateTime.now();
        this.texto="";
    }

    /**
     * Getter da Data de uma Notificação
     * @return Data
     */
    public LocalDateTime getData() {
        return data;
    }

    /**
     * Getter do Texto de uma Notificação
     * @return Texto
     */
    public String getTexto() {
        return texto;
    }

    /**
     * Setter do texto de uma Notificação
     * @param texto
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }

    /**
     * toString
     * @return String
     */
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
