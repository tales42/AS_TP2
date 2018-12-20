package Business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Exception.*;
/**
 * Created by luismp on 10/11/2018.
 */
public class Apostador extends Utilizador {
    private String cartaoAssociado;
    private double saldo;
    private Map<Integer,Aposta> apostas;
    private List<Notificacao> notificacoes;


    public Apostador(int idUtilizador, String email, String password, String nome){
        super(idUtilizador,email,password,nome);
        this.cartaoAssociado = "";
        this.saldo = 0;
        apostas = new HashMap<>();
        notificacoes = new ArrayList<>();
    }

    /**
     * Getter do cartão associado ao Apostador
     * @return cartão associado
     */
    public String getCartaoAssociado() {
        return cartaoAssociado;
    }

    /**
     * Setter do cartão associado ao Apostador
     * @param cartaoAssociado
     */
    public void setCartaoAssociado(String cartaoAssociado) {
        this.cartaoAssociado = cartaoAssociado;
    }

    /**
     * Getter do saldo de um Apostador
     * @return saldo
     */
    public double getSaldo() {
        return saldo;
    }

    /**
     * Setter do saldo de um Apostador
     * @param saldo
     */
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    /**
     * Getter de um Map de Apostas de um Apostador
     * @return Apostas
     */
    public Map<Integer, Aposta> getApostas() {
        return apostas;
    }

    /**
     * Getter de uma Lista com as Notificações de um Apostador
     * @return Notificações
     */
    public List<Notificacao> getNotificacoes() {
        return this.notificacoes;
    }



    /**
     * Método toString
     * @return String
     */
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("----Business.Apostador----\n");
        s.append("ID : " + this.getIdUtilizador() + "\n");
        s.append("Email : " + this.getEmail() + "\n");
        s.append("Nome : " + this.getNome() + "\n");
        s.append("Cartão Associado : " + this.getCartaoAssociado() + "\n");
        s.append("Saldo : " + this.getSaldo() + "€\n");
        s.append("------------------\n");
        return s.toString();
    }

    /**
     * Método que permite depositar uma quantia em ESSCoins a um Apostador
     * @param quantia
     */
    public void depositar(double quantia){
        double novoSaldo = this.getSaldo() + quantia;
        this.setSaldo(novoSaldo);
    }

    /**
     * Método que levanta uma quantia em ESSCoins de um Apostador.
     * Lança uma exceção, caso este não possua saldo suficiente
     * @param quantia
     * @throws SaldoInsuficienteException
     */
    public void levantar(double quantia) throws SaldoInsuficienteException {
        double novoSaldo = this.getSaldo() - quantia;
        if(novoSaldo < 0) throw new SaldoInsuficienteException(novoSaldo);
        else this.setSaldo(novoSaldo);
    }

    /**
     * Método que regista uma Aposta a um Apostador, lançando uma exceção caso este não possua saldo suficente.
     * @param evento
     * @param resultado
     * @param quantia
     * @throws SaldoInsuficienteException
     */
    public void registarAposta( Evento evento, Resultado resultado, double quantia) throws SaldoInsuficienteException{
        Aposta aposta = new Aposta(apostas.size()+1,quantia,resultado);
        levantar(quantia);
        apostas.put(aposta.getIdAposta(),aposta);
        evento.getApostas().put(getIdUtilizador(),aposta);
    }

    /**
     * Refactor:
     * Eliminados: equals(), clone(), construtor de cópia e construtor vazio, setNotificacoes(), setApostas(), associarCartao()
     */
}
