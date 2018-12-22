package Business;

import java.io.Serializable;

/**
 * Created by luismp on 11/11/2018.
 */
public class Aposta implements Serializable {

    private int idAposta;
    private double quantia;
    private double ganhosPossiveis;
    private Resultado resultado;

    /*
    * Construtores
    * */

    public Aposta(){
        this.idAposta = 0;
        this.quantia = 0;
        this.ganhosPossiveis = 0;
        this.resultado = new Resultado();
    }

    public Aposta(Aposta aposta){
        this.setIdAposta(aposta.getIdAposta());
        this.setQuantia(aposta.getQuantia());
        this.setGanhosPossiveis(aposta.getGanhosPossiveis());
        this.setResultado(aposta.getResultado());
    }

    /*
    * Getters e Setters
    * */

    public int getIdAposta() {
        return this.idAposta;
    }

    public void setIdAposta(int idAposta) {
        this.idAposta = idAposta;
    }

    public double getQuantia() {
        return this.quantia;
    }

    public void setQuantia(double quantia) {
        this.quantia = quantia;
    }

    public double getGanhosPossiveis() {
        return this.ganhosPossiveis;
    }

    public void setGanhosPossiveis(double ganhosPossiveis) {
        this.ganhosPossiveis = ganhosPossiveis;
    }

    public Resultado getResultado() {
        return this.resultado;
    }

    public void setResultado(Resultado resultado) {
        this.resultado = resultado;
    }

    /*
    * Clone, Equals e toString
    * */

    public Aposta clone(){
        return new Aposta(this);
    }

    public boolean equals(Object object){
        if(object == this) return true;

        if(object==null || object.getClass() != this.getClass()) return false;

        Aposta aposta = (Aposta) object;

        return aposta.getIdAposta() == this.getIdAposta();
    }

    public String toString(){
        StringBuilder string = new StringBuilder();
        string
                .append("----Aposta----\nQuantia : ")
                .append(this.getQuantia())
                .append("\nGanhos possíveis : ")
                .append(this.getGanhosPossiveis())
                .append("\n")
                .append(resultado.toString());
        return string.toString();
    }

    /*
    * Métodos Business.BetESS
    * */

    public double calcularGanhos(){
        return quantia * resultado.getOdd();
    }
}
