package Business;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
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
            designacao = detalhes.getEquipa1().getDesignacao();
        }
        if(equipa == 2){
            designacao = detalhes.getEquipa2().getDesignacao();
        }
        return designacao;
    }

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
            .append("--------------\n");
        if(detalhes.getEstado() == 'A'){
            string
                .append("Estado : Aberto\n----Resultados Possíveis----\n")
                .append("Vitória de " + detalhes.getEquipa1().getDesignacao() + " - " + getResultadosPossiveis().get(0).getOdd() + "\n")
                .append("Empate - "  + getResultadosPossiveis().get(1).getOdd() +"\n")
                .append("Vitória de " + detalhes.getEquipa2().getDesignacao() + " - " + getResultadosPossiveis().get(2).getOdd() + "\n");
        }
        else{
            string
                .append("Estado : Fechado \n")
                .append(resultadoFinal);
        }
        string.append("---------------------\n");
        return string.toString();
    }




    /**
     * Método que altera o estado de um Evento, notificando os apostadores e atribuindo os prémios
     * @param bet
     */
    public void alterarEstado(BetESS bet){
        if(detalhes.getEstado()=='A'){
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
            apostador.getNotificacoes().add(notificacao);
        }
    }

    private void notificaPerda (Notificacao notificacao) {
        StringBuilder string = new StringBuilder();
        string
                .append("Infelizmente, a sua aposta estava errada.\nBoa sorte para a próxima!\n");
        notificacao.setTexto(string.toString());
    }

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

    /**
     * Eliminado clone() e toString(), construtores vazio e de cópia, e quase todos os setters à exceção do setEstado
     */

}
