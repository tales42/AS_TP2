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


    /**
     * Construtor Vazio
     */
    public Aposta(){
        idAposta = 0;
        quantia = 0;
        ganhosPossiveis = 0;
        resultado = new Resultado();
    }

    /**
     * Construtor parametrizado
     * @param idAposta
     * @param quantia
     * @param resultado
     */
    public Aposta(int idAposta, double quantia, Resultado resultado){
        this.idAposta=idAposta;
        this.quantia=quantia;
        this.resultado=resultado;
    }

    /**
     * Construtor de cópia
     * @param aposta
     */
    public Aposta(Aposta aposta){
        setIdAposta(aposta.getIdAposta());
        setQuantia(aposta.getQuantia());
        setGanhosPossiveis(aposta.getGanhosPossiveis());
        setResultado(aposta.getResultado());
    }


    /**
     * Getter idAposta
     * @return idAposta
     */
    public int getIdAposta() {
        return idAposta;
    }


    /**
     * Setter idAposta
     * @param idAposta
     */
    public void setIdAposta(int idAposta) {
        this.idAposta = idAposta;
    }

    /**
     * Getter quantia
     * @return Quantia
     */
    public double getQuantia() {
        return quantia;
    }

    /**
     * Setter quantia
     * @param quantia
     */
    public void setQuantia(double quantia) {
        this.quantia = quantia;
    }

    /**
     * Getter ganhos possíveis
     * @return Ganhos possíveis
     */
    public double getGanhosPossiveis() {
        return ganhosPossiveis;
    }

    /**
     * Setter ganhos possíveis
     * @param ganhosPossiveis
     */
    public void setGanhosPossiveis(double ganhosPossiveis) {
        this.ganhosPossiveis = ganhosPossiveis;
    }

    /**
     * Getter Resultado
     * @return Resultado
     */
    public Resultado getResultado() {
        return resultado;
    }

    /**
     * Setter Resultado
     * @param resultado
     */
    public void setResultado(Resultado resultado) {
        this.resultado = resultado;
    }


    /**
     * Clone
     * @return Aposta
     */
    public Aposta clone(){
        return new Aposta(this);
    }

    /**
     * Equals
     * @param object
     * @return Boolean
     */
    public boolean equals(Object object){
        if(object == this) return true;

        if(object==null || object.getClass() != getClass()) return false;

        Aposta aposta = (Aposta) object;

        return aposta.getIdAposta() == getIdAposta();
    }

    /**
     * toString
     * @return String
     */
    public String toString(){
        StringBuilder string = new StringBuilder();
        string
                .append("----Aposta----\nQuantia : ")
                .append(getQuantia())
                .append("\nGanhos possíveis : ")
                .append(getGanhosPossiveis())
                .append("\n")
                .append(resultado);
        return string.toString();
    }


    /**
     * Método que calcula os ganhos possíveis de uma aposta
     * @return Ganhos possíveis
     */
    public double calcularGanhos(){
        return quantia * resultado.getOdd();
    }
}
