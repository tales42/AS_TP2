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
     * Construtor parametrizado de uma Aposta
     * @param idAposta
     * @param quantia
     * @param resultado
     */
    public Aposta(int idAposta, double quantia, Resultado resultado){
        this.idAposta = idAposta;
        this.quantia = quantia;
        this.resultado = resultado;
        ganhosPossiveis = calcularGanhos();
    }


    /**
     * Getter de idAposta
     * @return idAposta
     */
    public int getIdAposta() {
        return this.idAposta;
    }

    /**
     * Getter da quantia em ESSCoins
     * @return quantia
     */
    public double getQuantia() {
        return this.quantia;
    }

    /**
     * Getter dos ganhos possíveis de uma aposta.
     * @return ganhos possiveis
     */
    public double getGanhosPossiveis() {
        return this.ganhosPossiveis;
    }

    /**
     * Getter do Resultado apostado
     * @return Resultado
     */
    public Resultado getResultado() {
        return this.resultado;
    }


    /**
     * Método toString
     * @return String
     */
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("----Aposta----\n");
        s.append("Quantia : " + getQuantia() + "\n");
        s.append("Ganhos possíveis : " + getGanhosPossiveis() + "\n");
        s.append(resultado);
        return s.toString();
    }

    /**
     * Método que calcula quais os ganhos possíveis de uma aposta
     * @return ganhos possíveis
     */
    public double calcularGanhos(){
        return (quantia * resultado.getOdd());
    }

    /**
     * Refactor:
     * Eliminados: equals(), clone(), construtor vazio e construtor de cópia e setters
     */
}
