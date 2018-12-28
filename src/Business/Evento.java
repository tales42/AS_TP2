package Business;

import java.io.Serializable;
import java.util.*;

/**
 * Created by luismp on 11/11/2018.
 */
public class Evento implements Serializable {
    private int idEvento;
    private Resultado resultadoFinal;
    private List<Resultado> resultadosPossiveis;
    private Map<Integer,Aposta> apostas;
    private DetalhesEvento detalhes;

    /**
     * Construtor parametrizado de um Evento
     * @param idEvento
     * @param resultadosPossiveis
     */
    public Evento(int idEvento, List<Resultado> resultadosPossiveis, DetalhesEvento detalhes){
        this.idEvento = idEvento;
        this.resultadosPossiveis = resultadosPossiveis;
        resultadoFinal = new Resultado();
        apostas = new HashMap<>();
        this.detalhes = detalhes;
    }


    /**
     * Getter do identificador do Evento
     * @return idEvento
     */
    public int getIdEvento() {
        return idEvento;
    }


    /**
     * Getter do Estado do Evento
     * @return Estado
     */
    public char getEstado(){
        return detalhes.getEstado();
    }

    /**
     * Setter do Resultado Final de um Evento
     * @param resultadoFinal
     */
    public void setResultadoFinal(Resultado resultadoFinal) {
        this.resultadoFinal = resultadoFinal;
    }

    /**
     * Getter do Resultado Final do Evento
     * @return
     */
    public Resultado getResultadoFinal() {
        return resultadoFinal;
    }

    /**
     * Getter da Lista de Resultados Possíveis  do Evento
     * @return Resultados Possíveis
     */
    public List<Resultado> getResultadosPossiveis() {
        return resultadosPossiveis;
    }

    /**
     * Getter do Map de Apostas  do Evento
     * @return Apostas
     */
    public Map<Integer, Aposta> getApostas() {
        return apostas;
    }

    public String getDesignacaoEquipa(int equipa){
        String designacao = "";
        if(equipa == 1){
            designacao = detalhes.getDesignacaoEquipa(1);
        }
        if(equipa == 2){
            designacao = detalhes.getDesignacaoEquipa(2);
        }
        return designacao;
    }

    /**
     * Método que devolve um dos resultados possíveis de acordo com o índice passado como parâmetro
     * @param resultado
     * @return Resultado
     */
    public Resultado getResultado(int resultado){
        return resultadosPossiveis.get(resultado);
    }

    /**
     * toString
     * @return String
     */
    public String toString(){
        StringBuilder string = new StringBuilder();
        string
            .append("----Evento----\n")
            .append("ID : " + getIdEvento() + "\n")
            .append(detalhes)
            .append("--------------\n")
            .append(builderEstado())
            .append("---------------------\n");
        return string.toString();
    }

    /**
     * Método que constrói a String relativa ao estado de um Evento
     * @return String
     */
    private String builderEstado() {
        StringBuilder string = new StringBuilder();
        if(detalhes.isAberto()){
            string
                .append("Estado : Aberto\n----Resultados Possíveis----\n")
                .append("Vitória de " + detalhes.getDesignacaoEquipa(1) + " - " + getOdd(0) + "\n")
                .append("Empate - "  + getOdd(1) +"\n")
                .append("Vitória de " + detalhes.getDesignacaoEquipa(2) + " - " + getOdd(2) + "\n");
        }
        else{
            string
                .append("Estado : Fechado \n")
                .append(resultadoFinal);
        }
        return string.toString();
    }


    /**
     * Método que devolve a odd de um dos resultados possíveis de acordo com o índice passado como parâmetro
     * @param resultado
     * @return Odd
     */
    public double getOdd(int resultado){
        double odd = 0;
        if(resultado == 0){
            odd = resultadosPossiveis.get(0).getOdd();
        }
        if(resultado == 1){
            odd = resultadosPossiveis.get(1).getOdd();
        }
        if(resultado == 2){
            odd = resultadosPossiveis.get(2).getOdd();
        }
        return odd;
    }


    /**
     * Método que altera o estado de um Evento, notificando os apostadores e atribuindo os prémios
     * @param bet
     */
    public void alterarEstado(BetESS bet){
        if(detalhes.isAberto()){
            detalhes.setEstado('F');
            Random random = new Random();
            int result = random.nextInt(2);
            setResultadoFinal(resultadosPossiveis.get(result));
            atribuiPremios(bet.getUtilizadores());
            notificarApostadores(bet.getUtilizadores());
        }
    }

    /**
     * Método que notifica todos os Apostadores sobre este evento do término deste e do Resultado Final
     * @param utilizadores
     */
    public void notificarApostadores(Map<Integer,Utilizador> utilizadores){

        Notificacao notificacao = new Notificacao();
        for(int idUtilizador : apostas.keySet()){

            Aposta aposta = apostas.get(idUtilizador);
            Resultado resultadoAposta = aposta.getResultado();

            if(resultadoAposta.equals(this.getResultadoFinal())){
                notificaGanho( aposta, notificacao );
            }
            else{
                notificaPerda( notificacao );
            }

            Apostador apostador = (Apostador) utilizadores.get(idUtilizador);
            apostador.addNotificacao(notificacao.clone());
        }
    }

    /**
     * Método que constrói a Notificação relativa à perda de uma aposta
     * @param notificacao
     */
    private void notificaPerda (Notificacao notificacao) {
        StringBuilder string = new StringBuilder();
        string
                .append("Infelizmente, a sua aposta estava errada.\nBoa sorte para a próxima!\n");
        notificacao.setTexto(string.toString());
    }

    /**
     * Método que constrói a Notificação relativa ao ganho de uma aposta
     * @param aposta
     * @param notificacao
     */
    private void notificaGanho (Aposta aposta, Notificacao notificacao) {
        StringBuilder string = new StringBuilder();
        string
                .append("Parabéns!\nA sua aposta estava correta, ganhou ")
                .append(aposta.getGanhosPossiveis())
                .append("em ESSCoins!\n");
        notificacao.setTexto(string.toString());
    }


    /**
     * Método que atribui os prémios aos utilizadores que venceram a Aposta
     * @param utilizadores
     */
    public void atribuiPremios(Map<Integer,Utilizador> utilizadores){
        for(int idUtilizador : apostas.keySet()){
            Aposta aposta = apostas.get(idUtilizador);
            Resultado resultadoAposta = aposta.getResultado();

            if(resultadoAposta.equals(this.getResultadoFinal())){
                Apostador apostador = (Apostador) utilizadores.get(idUtilizador);
                apostador.depositar(aposta.calcularGanhos());
            }
        }
    }

}
