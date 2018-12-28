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
     * Método clone()
     * @return
     */
    public Notificacao clone(){
        Notificacao notificacao = new Notificacao();
        notificacao.setTexto(texto);
        return notificacao;
    }

    /**
     * toString
     * @return String
     */
    public String toString(){
        StringBuilder string = new StringBuilder();
        string
                .append("----Notificação----\nTimestamp : ")
                .append(this.getData())
                .append("\n")
                .append(this.getTexto())
                .append("\n-------------------\n");
        return string.toString();
    }
}
