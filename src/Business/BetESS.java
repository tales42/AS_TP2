package Business;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import Exception.*;

/**
 * Created by luismp on 11/11/2018.
 */
public class BetESS implements Serializable {
    private double saldoTotal;

    private Map<Integer,Utilizador> utilizadores;
    private Map<Integer, Desporto> desportos;
    private Map<Integer,Equipa> equipas;
    private Map<Integer,Evento> eventos;
    private Utilizador atual;


    /*
    * Construtores
    * */

    public BetESS(){
        this.saldoTotal = 0;
        this.utilizadores = new HashMap<>();
        this.desportos = new HashMap<>();
        this.equipas = new HashMap<>();
        this.eventos = new HashMap<>();
        this.atual = null;
    }

    /*
    * Métodos Business.BetESS
    * */

    public void setAtual(Utilizador atual){
        this.atual = atual;
    }

    public void registarApostador(String email, String password, String nome) throws ApostadorRegistadoException {
        for(Utilizador utilizador : utilizadores.values()){
            if(utilizador.getEmail().equals(email)) throw new ApostadorRegistadoException(email);
        }
        Apostador apostador = new Apostador(utilizadores.size()+1,email,password,nome);
        utilizadores.put(apostador.getIdUtilizador(),apostador);
    }

    public void iniciarSessao(String email, String password) throws PasswordIncorretaException, UtilizadorInexistenteException {
        Utilizador atual = getUtilizador(email);
        if((getUtilizador(email).getIdUtilizador() == 0)) throw new UtilizadorInexistenteException();
        if(!atual.getPassword().equals(password)) throw new PasswordIncorretaException();
        else setAtual(atual);
    }

    public void terminarSessao(){
        atual = null;
    }

    public Utilizador getUtilizador(String email){
        Utilizador ret = new Utilizador();
        for(Utilizador utilizador : utilizadores.values()){
            if(utilizador.getEmail().equals(email)){
                ret = utilizador;
                break;
            }
        }
        return ret;
    }

    public Map<Integer,Evento> getEventos(){
        return eventos;
    }

    public Map<Integer,Utilizador> getUtilizadores(){
        return utilizadores;
    }

    public Utilizador getAtual(){
        return atual;
    }

    public Map<Integer,Desporto> getDesportos(){
        return desportos;
    }

    public Map<Integer,Equipa> getEquipas(){
        return equipas;
    }

    public Map<Integer, Evento> getEventosAbertos(){
        Map<Integer,Evento> eventosAbertos = new HashMap<>();
        for(Evento e: eventos.values()){
            if(e.getEstado() == 'A') eventosAbertos.put(e.getIdEvento(),e);
        }
        return eventosAbertos;
    }

    public void registarEvento(int  idDesporto, int idEquipa1, int idEquipa2, String data, String hora, List<Double> odds, String localizacao){
        Desporto desp = desportos.get(idDesporto);
        Equipa eq1 = equipas.get(idEquipa1);
        Equipa eq2 = equipas.get(idEquipa2);


        DateTimeFormatter format = DateTimeFormatter.ofPattern("d-M-y");
        LocalDate date = LocalDate.parse(data,format);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("H:m");
        LocalTime horaComeco = LocalTime.parse(hora,dtf);

        Resultado vitoriaEquipa1 = new Resultado("Vitoria Equipa 1", (odds.get(0)));
        Resultado empate = new Resultado("Empate",(odds.get(1)));
        Resultado vitoriaEquipa2 = new Resultado("Vitoria Equipa 2",(odds.get(2)));
        List<Resultado> resultados = new ArrayList<>();
        resultados.add(vitoriaEquipa1);
        resultados.add(empate);
        resultados.add(vitoriaEquipa2);

        Duration duracao = Duration.ofMinutes(90);
        Evento evento = new Evento(eventos.size()+1, date, localizacao, horaComeco, duracao, eq1, eq2, desp, resultados);

        eventos.put(evento.getIdEvento(),evento);

    }

    public void initializeSystem(){
        Utilizador admin = new AdministradorDeEventos(getUtilizadores().size()+1,"admin","betess","Admin");
        getUtilizadores().put(admin.getIdUtilizador(),admin);
        Desporto futebol = new Desporto(1,"Futebol");
        desportos.put(futebol.getIdDesporto(),futebol);
        Desporto basquetebol = new Desporto(2,"Basquetebol");
        desportos.put(basquetebol.getIdDesporto(),basquetebol);
        Equipa slb = new Equipa(1,"Sport Lisboa e Benfica");
        equipas.put(slb.getIdEquipa(),slb);
        Equipa fcp = new Equipa(2, "Futebol Clube do Porto");
        equipas.put(fcp.getIdEquipa(),fcp);
    }

    /**
     * Refactor: eliminado getResultado()
     * Trocados construtores para ocuparem menos linhas
     */

}
