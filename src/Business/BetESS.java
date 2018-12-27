package Business;

import java.io.Serializable;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import Exception.ApostadorRegistadoException;
import Exception.PasswordIncorretaException;
import Exception.UtilizadorInexistenteException;

/**
 * Created by luismp on 11/11/2018.
 */
public class BetESS implements Serializable {

    private Map<Integer,Utilizador> utilizadores;
    private Map<Integer, Desporto> desportos;
    private Map<Integer,Equipa> equipas;
    private Map<Integer,Evento> eventos;
    private Utilizador atual;


    /**
     * Construtor Vazio de um Objeto BetESS
     */
    public BetESS(){
        this.utilizadores = new HashMap<>();
        this.desportos = new HashMap<>();
        this.equipas = new HashMap<>();
        this.eventos = new HashMap<>();
        this.atual = null;
    }

    /**
     * Método que define quais os utilizadores de um sistema.
     * Só é usado em casos de teste para povoar o sistema.
     * @param utilizadores
     */
    public void setUtilizadores(Map<Integer, Utilizador> utilizadores) {
        this.utilizadores = utilizadores;
    }


    /**
     * Setter do Utilizador atual.
     * @param atual
     */
    public void setAtual(Utilizador atual){
        this.atual = atual;
    }

    /**
     * Método que regista um novo Apostador no Sistema.
     * Lança uma exceção caso o email utilizado já esteja registado no sistema
     * @param email
     * @param password
     * @param nome
     * @throws ApostadorRegistadoException
     */
    public void registarApostador(String email, String password, String nome) throws ApostadorRegistadoException {
        for(Utilizador utilizador : utilizadores.values()){
            if(utilizador.getEmail().equals(email)) throw new ApostadorRegistadoException(email);
        }
        Apostador apostador = new Apostador(utilizadores.size()+1,email,password,nome);
        utilizadores.put(apostador.getIdUtilizador(),apostador);
    }

    /**
     * Método que tenta autentica um utilizador no sistema.
     * Lança uma exceção caso a o utilizador não exista no sistema, ou caso a password esteja errada.
     * @param email
     * @param password
     * @throws PasswordIncorretaException
     * @throws UtilizadorInexistenteException
     */
    public void iniciarSessao(String email, String password) throws PasswordIncorretaException, UtilizadorInexistenteException {
        Utilizador atual = getUtilizador(email);
        if((getUtilizador(email).getIdUtilizador() == 0)) throw new UtilizadorInexistenteException();
        if(!atual.getPassword().equals(password)) throw new PasswordIncorretaException();
        else setAtual(atual);
    }

    /**
     * Método que termina a sessão do utilizador atual.
     */
    public void terminarSessao(){
        atual = null;
    }

    /**
     * Método que determina qual o utilizado que possui um email passado como parâmetro
     * @param email
     * @return Utilizador
     */
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

    /**
     * Getter dos Eventos do Sistema
     * @return Eventos
     */
    public Map<Integer,Evento> getEventos(){
        return eventos;
    }

    /**
     * Getter dos Utilizadores do sistema
     * @return Utilizadores
     */
    public Map<Integer,Utilizador> getUtilizadores(){
        return utilizadores;
    }

    /**
     * Getter do Utilizador atual
     * @return Utilizador Atual
     */
    public Utilizador getAtual(){
        return atual;
    }


    /**
     * Getter dos Desportos do sistema.
     * @return Desportos
     */
    public Map<Integer,Desporto> getDesportos(){
        return desportos;
    }

    /**
     * Getter das Equipas do Sistema.
     * @return Equipas
     */
    public Map<Integer,Equipa> getEquipas(){
        return equipas;
    }

    /**
     * Método que devolve uma coleção com os eventos que se encontram no estado aberto.
     * @return Eventos Abertos
     */
    public Map<Integer, Evento> getEventosAbertos(){
        Map<Integer,Evento> eventosAbertos = new HashMap<>();
        for(Evento e: eventos.values()){
            if(e.getEstado() == 'A') eventosAbertos.put(e.getIdEvento(),e);
        }
        return eventosAbertos;
    }

    /**
     * Método que regista um novo Evento no sistema.
     * @param idDesporto
     * @param idEquipa1
     * @param idEquipa2
     * @param data
     * @param hora
     * @param odds
     * @param localizacao
     */
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
        DetalhesEvento detalhesEvento = new DetalhesEvento(date,horaComeco,duracao,localizacao,eq1,eq2,desp);
        Evento evento = new Evento(eventos.size()+1, resultados, detalhesEvento);

        eventos.put(evento.getIdEvento(),evento);

    }

    public boolean isEventoAberto(int idEvento){
        return eventos.keySet().contains(idEvento);
    }

    public Equipa getEquipa(int idEquipa){
        return equipas.get(idEquipa);
    }

    public void alterarEstado(int idEvento){
        eventos.get(idEvento).alterarEstado(this);
    }

    public Evento getEvento(int idEvento){
        return eventos.get(idEvento);
    }

    public String getDesignacaoEquipa(int idEquipa){
        return equipas.get(idEquipa).getDesignacao();
    }

    /**
     * Método utilizado para povoar o sistema, caso não exista nenhum ficheiro de dados disponível.
     */
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
     *
     * todo: Generalidade Especulativa, saldoTOtal não é usado
     */

}
