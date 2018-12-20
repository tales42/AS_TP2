package Business;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * Created by luismp on 11/11/2018.
 */
public class Evento implements Serializable {
    private int idEvento;
    private LocalDate data;
    private String localizacao;
    private LocalTime horaDeInicio;
    private Duration duracao;
    private char estado;
    private Equipa equipa1;
    private Equipa equipa2;
    private Desporto desporto;
    private Resultado resultadoFinal;
    private List<Resultado> resultadosPossiveis;
    private Map<Integer,Aposta> apostas;

    /**
     * Construtor parametrizado de um Evento
     * @param idEvento
     * @param data
     * @param localizacao
     * @param horaDeInicio
     * @param duracao
     * @param equipa1
     * @param equipa2
     * @param desporto
     * @param resultadosPossiveis
     */
    public Evento(int idEvento, LocalDate data, String localizacao, LocalTime horaDeInicio, Duration duracao , Equipa equipa1, Equipa equipa2, Desporto desporto, List<Resultado> resultadosPossiveis){
        this.idEvento = idEvento;
        this.data = data;
        this.localizacao = localizacao;
        this.horaDeInicio = horaDeInicio;
        this.duracao = duracao;
        this.equipa1 = equipa1;
        this.equipa2 = equipa2;
        this.desporto = desporto;
        this.resultadosPossiveis = resultadosPossiveis;
        estado = 'A';
        resultadoFinal = new Resultado();
        apostas = new HashMap<>();
    }


    /**
     * Getter do identificador do Evento
     * @return idEvento
     */
    public int getIdEvento() {
        return idEvento;
    }

    /**
     * Getter da Data  do Evento
     * @return Data
     */
    public LocalDate getData() {
        return data;
    }

    /**
     * Getter da Localização  do Evento
     * @return Localização
     */
    public String getLocalizacao() {
        return localizacao;
    }

    /**
     * Getter da Hora de Início  do Evento
     * @return Hora de Início
     */
    public LocalTime getHoraDeInicio() {
        return horaDeInicio;
    }

    /**
     * Getter da Duração  do Evento
     * @return Duração
     */
    public Duration getDuracao() {
        return duracao;
    }

    /**
     * Getter do Estado do Evento
     * @return Estado
     */
    public char getEstado() {
        return estado;
    }

    /**
     * Setter do Estado do Evento
     * @param estado
     */
    public void setEstado(char estado) {
        this.estado = estado;
    }

    /**
     * Getter da Equipa 1  do Evento
     * @return Equipa 1
     */
    public Equipa getEquipa1() {
        return equipa1;
    }

    /**
     * Getter da Equipa 2  do Evento
     * @return Equipa 2
     */
    public Equipa getEquipa2() {
        return equipa2;
    }

    /**
     * Getter do Desporto do Evento
     * @return Desporto
     */
    public Desporto getDesporto() {
        return desporto;
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

    /**
     * toString
     * @return String
     */
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("----Evento----\n");
        s.append("ID : " + this.getIdEvento() + "\n");
        s.append("Data : " + this.getData().toString() + " | ");
        s.append("Hora de Início : " + this.getHoraDeInicio().toString() + " | ");
        s.append("Duração : " + this.getDuracao().toString() + "\n");
        s.append("Desporto : "+ this.getDesporto().getDesignacao() + "\n");
        s.append("Localização: " + this.getLocalizacao() + "\n");


        s.append("--------------\n");
        s.append(""+this.getEquipa1().getDesignacao() + " - " + this.getEquipa2().getDesignacao() + "\n");
        s.append("--------------\n");


        if(this.getEstado() == 'A'){
            s.append("Estado : Aberto \n");
            s.append("----Resultados Possíveis----\n");
            s.append("Vitória de " + this.getEquipa1().getDesignacao() + " - " + getResultadosPossiveis().get(0).getOdd() + "\n");
            s.append("Empate - "  + getResultadosPossiveis().get(1).getOdd() +"\n");
            s.append("Vitória de " + this.getEquipa2().getDesignacao() + " - " + getResultadosPossiveis().get(2).getOdd() + "\n");
        }
        else{
            s.append("Estado : Fechado \n");
            s.append(""+resultadoFinal.toString());
        }

        s.append("---------------------\n");
        return s.toString();
    }


    /**
     * Método que altera o estado de um Evento, notificando os apostadores e atribuindo os prémios
     * @param bet
     */
    public void alterarEstado(BetESS bet){
        if(getEstado()=='A'){
            setEstado('F');
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
        for(int idUtilizador : apostas.keySet()){
            Aposta aposta = apostas.get(idUtilizador);
            Notificacao notificacao = new Notificacao();
            Resultado resultadoAposta = aposta.getResultado();
            if(resultadoAposta.equals(this.getResultadoFinal())){
                StringBuilder s = new StringBuilder();
                s.append("Parabéns! \n");
                s.append("A sua aposta estava correta, ganhou " + aposta.getGanhosPossiveis() + "em ESSCoins!\n");
                notificacao.setTexto(s.toString());
            }
            else{
                StringBuilder s = new StringBuilder();
                s.append("Infelizmente, a sua aposta estava errada. \n");
                s.append("Boa sorte para a próxima!\n");
                notificacao.setTexto(s.toString());
            }

            Apostador apostador = (Apostador) utilizadores.get(idUtilizador);

            apostador.getNotificacoes().add(notificacao);
        }
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
