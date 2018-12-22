package Business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Exception.SaldoInsuficienteException;

/**
 * Created by luismp on 10/11/2018.
 */
public class Apostador extends Utilizador {
    private String cartaoAssociado;
    private double saldo;
    private Map<Integer,Aposta> apostas;
    private List<Notificacao> notificacoes;

    /*
    * Construtores
    * */

    public Apostador(){
        super();
        this.cartaoAssociado = "";
        this.saldo=0;
        this.apostas = new HashMap<>();
        this.notificacoes = new ArrayList<>();
    }

    public Apostador(int idUtilizador, String email, String password, String nome, String cartaoAssociado, double saldo){
        super(idUtilizador,email,password,nome);
        this.setCartaoAssociado(cartaoAssociado);
        this.setSaldo(saldo);
        this.setApostas(new HashMap<>());
        this.setNotificacoes(new ArrayList<>());
    }

    /*
    * Getters e Setters
    * */

    public String getCartaoAssociado() {
        return this.cartaoAssociado;
    }

    public void setCartaoAssociado(String cartaoAssociado) {
        this.cartaoAssociado = cartaoAssociado;
    }

    public double getSaldo() {
        return this.saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Map<Integer, Aposta> getApostas() {
        return this.apostas;
    }

    public void setApostas(Map<Integer, Aposta> apostas) {
        this.apostas = apostas;
    }

    public List<Notificacao> getNotificacoes() {
        return this.notificacoes;
    }

    public void setNotificacoes(List<Notificacao> notificacoes) {
        this.notificacoes = notificacoes;
    }

    /*
    * Clone, Equals e toString
    * */

    public Apostador clone(){
        int idUtilizador = this.getIdUtilizador();
        String email = this.getEmail();
        String password = this.getPassword();
        String nome = this.getNome();
        String cartaoAssociado = this.getCartaoAssociado();
        double saldo = this.getSaldo();
        return new Apostador(idUtilizador,email,password,nome,cartaoAssociado,saldo);
    }

    public boolean equals(Object object){
        if(object == this) return true;

        if(object==null || object.getClass() != this.getClass()) return false;

        Apostador apostador = (Apostador) object;

        return apostador.getIdUtilizador() == this.getIdUtilizador();
    }

    @Override
    public String toString(){
        StringBuilder string = new StringBuilder();
        string
            .append("----Business.Apostador----\nID : ")
            .append(this.getIdUtilizador())
            .append("\nEmail : ")
            .append(this.getEmail())
            .append("\nNome : ")
            .append(this.getNome())
            .append("\nCartão Associado : ")
            .append(this.getCartaoAssociado())
            .append("\nSaldo : ")
            .append(this.getSaldo())
            .append("€\n------------------\n");
        return string.toString();
    }

    /*
    * Métodos Business.BetESS
    * */

    public void associarCartao(String cartaoAssociado){
        this.setCartaoAssociado(cartaoAssociado);
    }

    public void depositar(double quantia){
        double novoSaldo = this.getSaldo() + quantia;
        this.setSaldo(novoSaldo);
    }

    public void levantar(double quantia) throws SaldoInsuficienteException {
        double novoSaldo = this.getSaldo() - quantia;
        if(novoSaldo < 0) throw new SaldoInsuficienteException(novoSaldo);
        else this.setSaldo(novoSaldo);
    }

    public void registarAposta( Evento evento, Resultado resultado, double quantia) throws SaldoInsuficienteException{
        this.levantar(quantia);
        Aposta aposta = new Aposta();
        int idAposta = apostas.size()+1;
        aposta.setIdAposta(idAposta);
        aposta.setResultado(resultado);
        aposta.setQuantia(quantia);
        aposta.setGanhosPossiveis(aposta.calcularGanhos());
        apostas.put(idAposta,aposta);
        evento.getApostas().put(this.getIdUtilizador(),aposta);
    }

}
