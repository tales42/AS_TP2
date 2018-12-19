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


    public Aposta(int idAposta, double quantia, Resultado resultado){
        this.idAposta = idAposta;
        this.quantia = quantia;
        ganhosPossiveis = calcularGanhos();
        this.resultado = resultado;
    }


    public int getIdAposta() {
        return this.idAposta;
    }

    public double getQuantia() {
        return this.quantia;
    }

    public double getGanhosPossiveis() {
        return this.ganhosPossiveis;
    }

    public Resultado getResultado() {
        return this.resultado;
    }



    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("----Aposta----\n");
        s.append("Quantia : " + getQuantia() + "\n");
        s.append("Ganhos possíveis : " + getGanhosPossiveis() + "\n");
        s.append(resultado);
        return s.toString();
    }

    /*
    * Métodos Business.BetESS
    * */

    public double calcularGanhos(){
        return (quantia * resultado.getOdd());
    }

    /**
     * Refactor:
     * Eliminados: equals(), clone(), construtor vazio e construtor de cópia e setters
     */
}
