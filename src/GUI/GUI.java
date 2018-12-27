package GUI;
import Business.BetESS;
import Business.Desporto;
import Business.Equipa;
import Business.Evento;
import Business.Utilizador;
import Exception.ApostadorRegistadoException;
import Exception.PasswordIncorretaException;
import Exception.UtilizadorInexistenteException;

import java.io.*;
import java.util.*;


/**
 * Created by luismp on 11/11/2018.
 */
public class GUI {

    private static BetESS bet;

    private static final String FILENAME = "data.bin";

    public static void main(String[] args) {
        loadFile();
        GUIMainPage.mainPageForm();
    }

    /**
     * Método que tentar iniciar sessão com os parâmetros que lhe são passados.
     * Caso o utilizado não seja registado, ou a password seja incorreta, é enviada uma Exceção.
     * @param email
     * @param password
     * @throws UtilizadorInexistenteException
     * @throws PasswordIncorretaException
     */
    protected static void iniciarSessao(String email, String password) throws UtilizadorInexistenteException, PasswordIncorretaException{
        bet.iniciarSessao(email,password);
    }

    /**
     * Método que tenta registar um novo apostador.
     * Caso o email inserido já esteja registado no sistema, é enviada uma exceção.
     * @param email
     * @param password
     * @param nome
     * @throws ApostadorRegistadoException
     */
    protected static void registarApostador(String email, String password, String nome) throws ApostadorRegistadoException{
        bet.registarApostador(email,password,nome);
    }

    /**
     * Método que lê uma linha do teclado (System.in)
     * @return Linha lida do teclado
     */
    protected static String readLine(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    /**
     * Método que para a execução do programa, até ser inserido algo no teclado para continuar a execução.
     */
    protected static void confirmarContinuar() {
        System.out.println("Prima qualquer tecla para continuar");
        readLine();
    }

    /**
     * Método que mostra os eventos do sistema
     */
    protected static void showEvents() {
        Map<Integer, Evento> eventos = getEventos();
        System.out.println("---- Eventos ----");
        for (Evento e : eventos.values()) {
            System.out.println(e);
        }
    }

    /**
     * Método que termina a sessão no sistema
     */
    protected static void terminarSessao(){
        bet.terminarSessao();
    }

    /**
     * Método que retorna o utilizador atual
     * @return Utilizador atual
     */
    protected static Utilizador getAtual(){
        return bet.getAtual();
    }

    /**
     * Método que recebe um id de um evento e retorna o objeto associado a esse identificador.
     * @param idEvento
     * @return Evento
     */
    protected static Evento getEvento(int idEvento){
        return bet.getEvento(idEvento);
    }

    /**
     * Método que verifica se um dado evento está no estado aberto
     * @param idEvento
     * @return Bool
     */
    protected static boolean isEventoAberto(int idEvento){
        return bet.isEventoAberto(idEvento);
    }

    /**
     * Método que reotnra um Map com os eventos registados no sistema
     * @return Map de Evento
     */
    protected static Map<Integer,Evento> getEventos(){
        return bet.getEventos();
    }

    /**
     * Método que regista um novo evento.
     * @param idDesporto
     * @param idEquipa1
     * @param idEquipa2
     * @param data
     * @param hora
     * @param odds
     * @param localizacao
     */
    protected static void registarEvento(int idDesporto,int idEquipa1,int idEquipa2, String data, String hora, List<Double>odds, String localizacao){
        bet.registarEvento(idDesporto,idEquipa1,idEquipa2,data,hora,odds,localizacao);
    }

    /**
     * Método que recebe um id de uma equipa e retorna o objeto associado a esse identificador.
     * @param idEquipa
     * @return Equipa
     */
    protected static String getDesignacaoEquipa(int idEquipa){
        return bet.getDesignacaoEquipa(idEquipa);
    }

    /**
     * Método que retorna uma Coleção com as equipas do sistema.
     * @return
     */
    protected static Collection<Equipa> getEquipas(){
        return bet.getEquipas().values();
    }

    /**
     * Método que retorna uma Coleção com os desportos do sistema.
     * @return
     */
    protected static Collection<Desporto> getDesportos(){
        return bet.getDesportos().values();
    }

    /**
     * Método usado para fechar um evento
     */
    protected static void closeEvent() {
        System.out.println("Selecione qual o evento que quer fechar");
        Scanner scanner = new Scanner(System.in);
        int idEvento = scanner.nextInt();
        bet.alterarEstado(idEvento);
    }

    /**
     * Método usado para carregar o sistema do ficheiro data.bin
     */
    protected static void loadFile() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream( FILENAME ));
            bet = (BetESS) inputStream.readObject();
            inputStream.close();
        } catch (FileNotFoundException e) {
            bet = new BetESS();
            bet.initializeSystem();
        } catch (IOException e) {
            System.out.println("Ocorreu um erro na leitura do ficheiro.");
        } catch (ClassNotFoundException e) {
            System.out.println("Ocorreu um erro na leitura do ficheiro.");
        }
    }


    /**
     * Método usado para guardar o estado do sistema para o ficheiro data.bin
     */
    protected static void saveFile() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream( FILENAME ));
            outputStream.writeObject(bet);
            outputStream.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ocorreu um erro a guardar o ficheiro.");
        } catch (IOException e) {
            System.out.println("Ocorreu um erro a guardar o ficheiro.");
        }
    }


    /**
     * Método que verifica que uma dada String é um inteiro
     * @param string
     * @return Boolean
     */
    protected static boolean isInteger(String string) {
        try {
            Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    /**
     * Método que verifica se uma dada String é um double
     * @param string
     * @return Boolean
     */
    protected static boolean isDouble(String string) {
        try {
            Double.parseDouble(string);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

}