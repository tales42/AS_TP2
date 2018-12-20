package GUI;

import Business.AdministradorDeEventos;
import Business.Apostador;
import Business.Utilizador;
import Exception.*;

/**
 * Created by luismp on 20/12/2018.
 */
public class GUIMainPage extends GUI {


    /**
     * Método que imprime no ecrã o menu principal
     */
    private static void showMainPage() {
        System.out.println("----- BET ESS -----");
        if (getAtual() != null) {
            System.out.println("Utilizador: " + getAtual().getNome());
        }
        System.out.println("1 - Efetuar Registo");
        System.out.println("2 - Iniciar Sessão");
        System.out.println("3 - Consultar Eventos");
        System.out.println("0 - Sair");
        System.out.println("-------------------");
    }

    /**
     * Método que mostra o menu principal e mantém o handler a correr
     */
    protected static void mainPageForm() {
        showMainPage();
        boolean handler = true;
        while (handler) {
            handler = mainPageHandler();
        }

    }

    /**
     * Handler do menu principal que recebe e trata os inputs do utilizador.
     * Retorna false quando o utilizador pretende sair do programa, parando o ciclo e a execução do programa
     * @return Booleano
     */
    private static boolean mainPageHandler() {
        String option = readLine();
        if (option.equals("1")) {
            registerForm();
            showMainPage();
        } else if (option.equals("2")) {
            showLoginForm();
            showMainPage();
        } else if (option.equals("3")) {
            GUIApostador.showEvents();
            showMainPage();
        } else if (option.equals("0")) {
            terminarSessao();
            saveFile();
            return false;
        } else {
            System.out.println("Input não reconhecido.");
        }
        return true;
    }

    /**
     * Método que imprime no ecrã o formulário de autenticação
     */
    private static void showLoginForm() {
        System.out.println("---- Iniciar Sessão ----");
        System.out.println("Email:");
        String email = readLine();
        System.out.println("Password:");
        String password = readLine();
        tryLogIn(email, password);

    }

    /**
     * Método que tenta autenticar um utilizado no sistema
     * @param email
     * @param password
     */
    private static void tryLogIn(String email, String password) {
        try {
            iniciarSessao(email, password);
            System.out.println("Sessão iniciada com sucesso.");
            confirmarContinuar();
            loggedInHandler();
        } catch (PasswordIncorretaException e) {
            System.out.println("Password Incorreta.");
            confirmarContinuar();
        } catch (UtilizadorInexistenteException e) {
            System.out.println("Utilizador Inexistente.");
            confirmarContinuar();
        }
    }

    /**
     * Método que imprime no ecrã o formulário de registo
     */
    private static void registerForm() {
        System.out.println("---- Efetuar Registo ----");
        System.out.println("Insira o seu email");
        String email = readLine();
        System.out.println("Insira a sua password");
        String password = readLine();
        System.out.println("Insira o seu nome");
        String nome = readLine();
        tryRegistarApostador(email, password, nome);
    }

    /**
     * Método que tenta registar um novo Apostador no sistema
     * @param email
     * @param password
     * @param nome
     */
    private static void tryRegistarApostador(String email, String password, String nome) {
        try {
            registarApostador(email, password, nome);
            System.out.println("Utilizador registado com sucesso.");
            confirmarContinuar();
        } catch (ApostadorRegistadoException e) {
            System.out.println("Utilizador já registado.");
            confirmarContinuar();
        }
    }

    /**
     * Método que imprime a página após a autenticação do utilizador, de acordo com qual o tipo de Utilizador autenticado
     * @see Utilizador
     */
    protected static void loggedInScene() {
        Utilizador atual = getAtual();
        if (atual instanceof AdministradorDeEventos) {
            adminLoggedInScene();
        } else if (atual instanceof Apostador) {
            apostadorLoggedInScene((Apostador) atual);
        }
    }

    /**
     * Método que imprime a página após autenticação de um administrador
     * @see AdministradorDeEventos
     */
    private static void adminLoggedInScene() {
        System.out.println("---- Administrador -----");
        System.out.println("1 - Registar Evento");
        System.out.println("2 - Consultar Eventos");
        System.out.println("3 - Fechar Evento");
        System.out.println("0 - Sair");
        System.out.println("-------------------------");
    }

    /**
     * Método que imprime a página após autenticação de um apostador, e apresenta os seus dados no ecrã
     * @param atual
     * @see Apostador
     */
    private static void apostadorLoggedInScene(Apostador atual) {
        System.out.println("---- Apostador -----");
        System.out.println("Nome: " + atual.getNome());
        System.out.println("Saldo: " + atual.getSaldo());
        System.out.println("Cartão: " + atual.getCartaoAssociado());
        System.out.println("--------------------");
        System.out.println("1 - Consultar Notificações ");
        System.out.println("2 - Gerir Saldo ");
        System.out.println("3 - Consultar Resultados ");
        System.out.println("4 - Consultar Eventos");
        System.out.println("5 - Consultar Apostas Realizadas");
        System.out.println("6 - Registar Aposta");
        System.out.println("0 - Sair");
        System.out.println("--------------------");
    }

    /**
     * Método que recebe e trata dos inputs da página após o login
     */
    private static void loggedInHandler() {
        loggedInScene();
        Utilizador atual = getAtual();
        boolean isLoggedIn = true;
        if (atual instanceof AdministradorDeEventos) {
            while (isLoggedIn) {
                isLoggedIn = GUIEvento.adminLoggedInHandler();
            }
        } else if (atual instanceof Apostador) {
            while (isLoggedIn) {
                isLoggedIn = GUIApostador.apostadorLoggedInHandler();
            }
        }
    }
}
