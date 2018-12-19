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

    /*
    * Construtores
    * */

    public Evento(){
        this.idEvento = 0;
        this.data = LocalDate.now();
        this.localizacao = "";
        this.horaDeInicio = LocalTime.now();
        this.duracao = Duration.ZERO;
        this.estado ='A';
        this.equipa1 = new Equipa();
        this.equipa2 = new Equipa();
        this.desporto = new Desporto();
        this.resultadoFinal = new Resultado();
        this.resultadosPossiveis = new ArrayList<>();
        this.apostas = new HashMap<>();
    }

    public Evento(int idEvento, LocalDate data, String localizacao, LocalTime horaDeInicio, Duration duracao , char estado, Equipa equipa1, Equipa equipa2, Desporto desporto, Resultado resultadoFinal, ArrayList<Resultado> resultadosPossiveis, HashMap<Integer,Aposta> apostas){
        this.idEvento = idEvento;
        this.data = data;
        this.localizacao = localizacao;
        this.horaDeInicio = horaDeInicio;
        this.duracao = duracao;
        this.estado = estado;
        this.equipa1 = equipa1;
        this.equipa2 = equipa2;
        this.desporto = desporto;
        this.resultadoFinal = resultadoFinal;
        this.resultadosPossiveis = resultadosPossiveis;
        this.apostas = apostas;
    }

    public Evento(Evento evento){
        this.idEvento = evento.getIdEvento();
        this.data = evento.getData();
        this.localizacao = evento.getLocalizacao();
        this.horaDeInicio = evento.getHoraDeInicio();
        this.duracao = evento.getDuracao();
        this.estado = evento.getEstado();
        this.equipa1 = evento.getEquipa1();
        this.equipa2 = evento.getEquipa2();
        this.desporto = evento.getDesporto();
        this.resultadoFinal = evento.getResultadoFinal();
        this.resultadosPossiveis = evento.getResultadosPossiveis();
        this.apostas = evento.getApostas();
    }

    /*
    * Getters e Setters
    * */

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public int getIdEvento() {
        return this.idEvento;
    }

    public LocalDate getData() {
        return this.data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getLocalizacao() {
        return this.localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public LocalTime getHoraDeInicio() {
        return this.horaDeInicio;
    }

    public void setHoraDeInicio(LocalTime horaDeInicio) {
        this.horaDeInicio = horaDeInicio;
    }

    public Duration getDuracao() {
        return this.duracao;
    }

    public void setDuracao(Duration duracao) {
        this.duracao = duracao;
    }

    public char getEstado() {
        return this.estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }

    public Equipa getEquipa1() {
        return this.equipa1;
    }

    public void setEquipa1(Equipa equipa1) {
        this.equipa1 = equipa1;
    }

    public Equipa getEquipa2() {
        return this.equipa2;
    }

    public void setEquipa2(Equipa equipa2) {
        this.equipa2 = equipa2;
    }

    public Desporto getDesporto() {
        return this.desporto;
    }

    public void setDesporto(Desporto desporto) {
        this.desporto = desporto;
    }

    public void setResultadoFinal(Resultado resultadoFinal) {
        this.resultadoFinal = resultadoFinal;
    }

    public Resultado getResultadoFinal() {
        return this.resultadoFinal;
    }

    public List<Resultado> getResultadosPossiveis() {
        return this.resultadosPossiveis;
    }

    public void setResultadosPossiveis(List<Resultado> resultadosPossiveis) {
        this.resultadosPossiveis = resultadosPossiveis;
    }

    public Map<Integer, Aposta> getApostas() {
        return this.apostas;
    }

    public void setApostas(Map<Integer, Aposta> apostas) {
        this.apostas = apostas;
    }

    /*
    * Clone, Equals e toString
    * */

    public Evento clone(){
        return new Evento(this);
    }

    public boolean equals(Object object){
        if(object==this) return true;

        if((object==null) | (object.getClass() != this.getClass())) return false;

        Evento evento = (Evento) object;

        if(evento.getIdEvento() == this.getIdEvento()) return true;
        else return false;
    }

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

    /*
    * Métodos BetESS
    * */

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

    public void notificarApostadores(Map<Integer,Utilizador> utilizadores){
        for(int idUtilizador : apostas.keySet()){
            Aposta aposta = apostas.get(idUtilizador);
            Notificacao notificacao = new Notificacao();
            notificacao.setData(LocalDateTime.now());
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
