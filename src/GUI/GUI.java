package GUI;
import Business.*;
import Exception.*;

import java.io.*;
import java.util.*;


/**
 * Created by luismp on 11/11/2018.
 */
public class GUI {

    private static BetESS bet;

    private static final String filename = "data.bin";

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
     * Método que lê uma linha do teclado (System.in).
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
        return bet.getEventos().get(idEvento);
    }

    /**
     * Método que verifica se um dado evento está no estado aberto
     * @param idEvento
     * @return Bool
     */
    protected static boolean isEventoAberto(int idEvento){
        return bet.getEventosAbertos().keySet().contains(idEvento);
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
    protected static Equipa getEquipa(int idEquipa){
        return bet.getEquipas().get(idEquipa);
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
        bet.getEventos().get(idEvento).alterarEstado(bet);
    }

    /**
     * Método usado para carregar o sistema do ficheiro data.bin
     */
    protected static void loadFile() {
        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(filename));
            bet = (BetESS) is.readObject();
            is.close();
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
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(filename));
            os.writeObject(bet);
            os.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ocorreu um erro a guardar o ficheiro.");
        } catch (IOException e) {
            System.out.println("Ocorreu um erro a guardar o ficheiro.");
        }
    }


    /**
     * Método que verifica que uma dada String é um inteiro
     * @param s
     * @return Boolean
     */
    protected static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    /**
     * Método que verifica se uma dada String é um double
     * @param s
     * @return Boolean
     */
    protected static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }


    /**
     * Bad Smells:
     * -> Métodos Longos -> registarEventoForm(), novaApostaForm(), gerirSaldoHandler(), loggedInHandler(), loggedInScene(), registarForm(), showLoginForm()
     * -> Classe grande -> 25 métodos
     * -> Código repetido -> "prima 0 para sair" -> extract method
     * -> Message Chains -> cadeias de varíaveis
     */


    /**
     * REfactor:
     *          loggedInScene -> Extract MEthod x2
     *          mainPageForm -> Extract Method
     *          Substituir todos os scanners pela função readline + isInteger ou isDouble
     *          associarCartao -> Extract Method
     *          showLoginForm -> Extract MEthod
     *          registerForm -> Extract Method
     *          loggedInHandler -> Extract Method x2
     *          registarEVentoForm -> Extract Method x? -> Fazer nova classe
     *          novaApostaForm -> Extract Method x? -> Fazer nova classe
     *          handleEvents -> apagado
     *          showFormAposta -> apagado (estes ultimos dois estavam ligados um ao outro, mas não eram usados em qualquer outro lado)
     *
     *
     *          Classe GUI feita como superclasse de GUIMainPage e GUISaldo
     *                  Clase GUIMainPage feita como superclasse de GUIApostador e GUIEvento
     *
     *                 Métodos divididos de acordo com a sua função
     *                 Métodos que são usados por diversas classes, feitos protected
     */

}