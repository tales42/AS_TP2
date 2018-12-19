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


    public List<Notificacao> getNotificacoes() {
        return this.notificacoes;
    }



    @Override
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

    /*
    * Métodos Business.BetESS
    * */

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
