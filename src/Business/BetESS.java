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


    public void setUtilizadores(Map<Integer, Utilizador> utilizadores) {
        this.utilizadores = utilizadores;
    }


    /*
    * Métodos Business.BetESS
    * */

    public void setAtual(Utilizador atual){
        this.atual = atual;
    }

    public void registarApostador(String email, String password, String nome) throws ApostadorRegistadoException {
        Apostador apostador = new Apostador();
        for(Utilizador utilizador : utilizadores.values()){
            if(utilizador.getEmail().equals(email)) throw new ApostadorRegistadoException(email);
        }
        int idUtilizador = utilizadores.size()+1;
        apostador.setIdUtilizador(idUtilizador);
        apostador.setEmail(email);
        apostador.setPassword(password);
        apostador.setNome(nome);
        apostador.setSaldo(0);
        utilizadores.put(apostador.getIdUtilizador(),apostador);
    }

    public void iniciarSessao(String email, String password) throws PasswordIncorretaException, UtilizadorInexistenteException {
        Utilizador atual = getUtilizador(email);
        if((getUtilizador(email).getIdUtilizador() == 0)) throw new UtilizadorInexistenteException();
        if(!atual.getPassword().equals(password)) throw new PasswordIncorretaException();
        else this.setAtual(atual);
    }

    public void terminarSessao(){
        this.atual = null;
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
        return this.eventos;
    }

    public List<Resultado> getResultado(){
        ArrayList<Resultado> resultados = new ArrayList<>();
        for(Evento e : this.getEventos().values()){
            if(e.getEstado() == 'F'){
                resultados.add(e.getResultadoFinal());
            }
        }
        return resultados;
    }

    public Map<Integer,Utilizador> getUtilizadores(){
        return this.utilizadores;
    }

    public Utilizador getAtual(){
        return this.atual;
    }

    public Map<Integer,Desporto> getDesportos(){
        return this.desportos;
    }

    public Map<Integer,Equipa> getEquipas(){
        return this.equipas;
    }

    public Map<Integer, Evento> getEventosAbertos(){
        Map<Integer,Evento> eventosAbertos = new HashMap<>();
        for(Evento e: this.eventos.values()){
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

        Evento novoEvento = new Evento();
        novoEvento.setIdEvento(eventos.size()+1);
        novoEvento.setDesporto(desp);
        novoEvento.setEquipa1(eq1);
        novoEvento.setEquipa2(eq2);
        novoEvento.setData(date);
        novoEvento.setHoraDeInicio(horaComeco);
        novoEvento.setEstado('A');
        novoEvento.setLocalizacao(localizacao);
        novoEvento.setDuracao(duracao);
        novoEvento.setResultadosPossiveis(resultados);

        eventos.put(novoEvento.getIdEvento(),novoEvento);

    }

    public void initializeSystem(){
        Utilizador admin = new AdministradorDeEventos();
        admin.setIdUtilizador(this.getUtilizadores().size()+1);
        admin.setEmail("admin");
        admin.setPassword("betess");
        admin.setNome("Admin");
        this.getUtilizadores().put(admin.getIdUtilizador(),admin);
        System.out.println(this.getUtilizador("admin").toString());


        Desporto futebol = new Desporto();
        futebol.setIdDesporto(1);
        futebol.setDesignacao("Futebol");

        this.desportos.put(futebol.getIdDesporto(),futebol);

        Desporto basquetebol = new Desporto();
        basquetebol.setIdDesporto(2);
        basquetebol.setDesignacao("Basquetebol");

        this.desportos.put(basquetebol.getIdDesporto(),basquetebol);


        Equipa slb = new Equipa();
        slb.setIdEquipa(1);
        slb.setDesignacao("Sport Lisboa e Benfica");
        this.equipas.put(slb.getIdEquipa(),slb);
        Equipa fcp = new Equipa();
        fcp.setIdEquipa(2);
        fcp.setDesignacao("Futebol Clube do Porto");
        this.equipas.put(fcp.getIdEquipa(),fcp);

    }

}
