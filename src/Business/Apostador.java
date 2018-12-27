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


    /**
     * Construtor vazio
     */
    public Apostador(){
        cartaoAssociado = "";
        saldo=0;
        apostas = new HashMap<>();
        notificacoes = new ArrayList<>();
    }

    /**
     * Construtor parametrizado
     * @param idUtilizador
     * @param email
     * @param password
     * @param nome
     */
    public Apostador(int idUtilizador, String email, String password, String nome){
        super(idUtilizador,email,password,nome);
        this.setCartaoAssociado(cartaoAssociado);
        this.setSaldo(saldo);
        this.setApostas(new HashMap<>());
        this.setNotificacoes(new ArrayList<>());
    }


    /**
     * Getter cartão associado
     * @return Cartão associado
     */
    public String getCartaoAssociado() {
        return cartaoAssociado;
    }

    /**
     * Setter cartão associado
     * @param cartaoAssociado
     */
    public void setCartaoAssociado(String cartaoAssociado) {
        this.cartaoAssociado = cartaoAssociado;
    }

    /**
     * Getter Saldo
     * @return Saldo
     */
    public double getSaldo() {
        return saldo;
    }

    /**
     * Setter saldo
     * @param saldo
     */
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    /**
     * Getter apostas
     * @return Apostas
     */
    public Map<Integer, Aposta> getApostas() {
        return apostas;
    }

    /**
     * Setter apostas
     * @param apostas
     */
    public void setApostas(Map<Integer, Aposta> apostas) {
        this.apostas = apostas;
    }

    /**
     * Getter notificações
     * @return Notificações
     */
    public List<Notificacao> getNotificacoes() {
        return notificacoes;
    }

    /**
     * Setter notificações
     * @param notificacoes
     */
    public void setNotificacoes(List<Notificacao> notificacoes) {
        this.notificacoes = notificacoes;
    }

    /**
     * Clone
     * @return Apostador
     */
    public Apostador clone(){
        int idUtilizador = this.getIdUtilizador();
        String email = this.getEmail();
        String password = this.getPassword();
        String nome = this.getNome();
        return new Apostador(idUtilizador,email,password,nome);
    }

    /**
     * Equals
     * @param object
     * @return Boolean
     */
    public boolean equals(Object object){
        if(object == this) return true;

        if(object==null || object.getClass() != getClass()) return false;

        Apostador apostador = (Apostador) object;

        return apostador.getIdUtilizador() == getIdUtilizador();
    }


    /**
     * toString
     * @return String
     */
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


    /**
     * Método que deposita uma quantia em ESSCoins na conta de um Apostador
     * @param quantia
     */
    public void depositar(double quantia){
        double novoSaldo = getSaldo() + quantia;
        setSaldo(novoSaldo);
    }

    /**
     * Método que levanta uma quantia em ESSCoins da conta de um Apostador
     * Lança uma exceção caso não possua saldo suficiente
     * @param quantia
     * @throws SaldoInsuficienteException
     */
    public void levantar(double quantia) throws SaldoInsuficienteException {
        double novoSaldo = this.getSaldo() - quantia;
        if(novoSaldo < 0) throw new SaldoInsuficienteException(novoSaldo);
        else setSaldo(novoSaldo);
    }

    /**
     * Método que regista uma nova Aposta
     * Lança uma exceção caso o Apostador não possua saldo suficiente
     * @param evento
     * @param resultado
     * @param quantia
     * @throws SaldoInsuficienteException
     */
    public void registarAposta( Evento evento, Resultado resultado, double quantia) throws SaldoInsuficienteException{
        levantar(quantia);
        Aposta aposta = new Aposta();
        int idAposta = apostas.size()+1;
        aposta.setIdAposta(idAposta);
        aposta.setResultado(resultado);
        aposta.setQuantia(quantia);
        aposta.setGanhosPossiveis(aposta.calcularGanhos());
        apostas.put(idAposta,aposta);
        evento.getApostas().put(getIdUtilizador(),aposta);
    }

    public void addNotificacao(Notificacao notificacao){
        notificacoes.add(notificacao);
    }

}
