package GUI;
import Business.*;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Scene;
import Exception.*;
import sun.security.krb5.internal.crypto.Des;

import java.io.*;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.regex.Pattern;


/**
 * Created by luismp on 11/11/2018.
 */
public class GUI {

    private static BetESS bet;

    private static final String filename = "../data.bin";

    public static void main(String[] args) {
        loadFile();
        mainPageForm();
    }


    private static void showMainPage() {
        System.out.println("----- BET ESS -----");
        if (bet.getAtual() != null) {
            System.out.println("Utilizador: " + bet.getAtual().getNome());
        }
        System.out.println("1 - Efetuar Registo");
        System.out.println("2 - Iniciar Sessão");
        System.out.println("3 - Consultar Eventos");
        System.out.println("0 - Sair");
        System.out.println("-------------------");
    }

    private static void mainPageForm() {
        Scanner scanner = new Scanner(System.in);

        showMainPage();

        while (true) {
            String option = scanner.nextLine();
            if (option.equals("1")) {
                registerForm();
                showMainPage();
            } else if (option.equals("2")) {
                showLoginForm();
                showMainPage();
            } else if (option.equals("3")) {
                showEvents();
                showMainPage();
            } else if (option.equals("0")) {
                bet.terminarSessao();
                saveFile();
                break;
            } else {
                System.out.println("Input não reconhecido.");
            }
        }

    }

    private static void showLoginForm() {
        Scanner scanner = new Scanner(System.in);

        System.out.flush();

        System.out.println("---- Iniciar Sessão ----");
        System.out.println("Email:");
        String email = scanner.nextLine();
        System.out.println("Password:");
        String password = scanner.nextLine();
        try {
            bet.iniciarSessao(email, password);
            System.out.println("Sessão iniciada com sucesso. Prima qualquer tecla para continuar");
            scanner.nextLine();
            loggedInHandler();
        } catch (PasswordIncorretaException e) {
            System.out.println("Password Incorreta. Prima qualquer tecla para continuar");
            scanner.nextLine();
        } catch (UtilizadorInexistenteException e) {
            System.out.println("Utilizador Inexistente. Prima qualquer tecla para continuar");
            scanner.nextLine();
        }

    }


    private static void registerForm() {
        Scanner scanner = new Scanner(System.in);

        System.out.flush();

        System.out.println("---- Efetuar Registo ----");
        System.out.println("Insira o seu email");
        String email = scanner.nextLine();
        System.out.println("Insira a sua password");
        String password = scanner.nextLine();
        System.out.println("Insira o seu nome");
        String nome = scanner.nextLine();
        try {
            bet.registarApostador(email, password, nome);
            System.out.println("Utilizador registado com sucesso. Prima qualquer tecla para continuar");
            scanner.nextLine();
            System.out.flush();
        } catch (ApostadorRegistadoException e) {
            System.out.println("Utilizador já registado. Prima qualquer tecla para continuar");
            scanner.nextLine();
            System.out.flush();
        }
    }

    private static void loggedInScene() {
        Utilizador atual = bet.getAtual();
        if (atual instanceof AdministradorDeEventos) {
            System.out.println("---- Administrador -----");
            System.out.println("1 - Registar Evento");
            System.out.println("2 - Consultar Eventos");
            System.out.println("3 - Fechar Evento");
            System.out.println("0 - Sair");
            System.out.println("-------------------------");
        } else if (atual instanceof Apostador) {
            Apostador apostador = (Apostador) atual;
            System.out.println("---- Apostador -----");
            System.out.println("Nome: " + apostador.getNome());
            System.out.println("Saldo: " + apostador.getSaldo());
            System.out.println("Cartão: " + apostador.getCartaoAssociado());
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
    }

    private static void loggedInHandler() {
        loggedInScene();
        Utilizador atual = bet.getAtual();
        Scanner scanner = new Scanner(System.in);
        if (atual instanceof AdministradorDeEventos) {
            while (true) {
                String opcao = scanner.nextLine();
                if (opcao.equals("1")) {
                    registarEventoForm();
                    System.out.println("Prima qualquer tecla para continuar");
                    scanner.nextLine();
                    loggedInScene();
                } else if (opcao.equals("2")) {
                    showEvents();
                    System.out.println("Prima qualquer tecla para continuar");
                    scanner.nextLine();
                    loggedInScene();
                } else if (opcao.equals("3")) {
                    closeEvent();
                    System.out.println("Prima qualquer tecla para continuar");
                    scanner.nextLine();
                    loggedInScene();
                } else if (opcao.equals("0")) {
                    bet.terminarSessao();
                    break;
                } else {
                    System.out.println("Input não reconhecido. ");
                    loggedInScene();
                }
            }
        } else if (atual instanceof Apostador) {
            while (true) {
                String opcao = scanner.nextLine();
                if (opcao.equals("1")) {
                    showNotificacoes();
                    System.out.println("Prima qualquer tecla para continuar");
                    scanner.nextLine();
                    loggedInScene();
                } else if (opcao.equals("2")) {
                    gerirSaldoScene();
                    gerirSaldoHandler();
                    System.out.println("Prima qualquer tecla para continuar");
                    scanner.nextLine();
                    loggedInScene();
                } else if (opcao.equals("3")) {
                    showResultados();
                    System.out.println("Prima qualquer tecla para continuar");
                    scanner.nextLine();
                    loggedInScene();
                } else if (opcao.equals("4")) {
                    showEvents();
                    System.out.println("Prima qualquer tecla para continuar");
                    scanner.nextLine();
                    loggedInScene();
                } else if (opcao.equals("5")) {
                    showApostas();
                    System.out.println("Prima qualquer tecla para continuar");
                    scanner.nextLine();
                    loggedInScene();
                } else if (opcao.equals("6")) {
                    novaApostaForm();
                    System.out.println("Prima qualquer tecla para continuar");
                    scanner.nextLine();
                    loggedInScene();
                } else if (opcao.equals("0")) {
                    bet.terminarSessao();
                    break;
                } else {
                    System.out.println("Input não reconhecido");
                    loggedInScene();
                }
            }
        }
    }

    private static void closeEvent() {
        System.out.println("Selecione qual o evento que quer fechar");
        Scanner scanner = new Scanner(System.in);
        int idEvento = scanner.nextInt();
        bet.getEventos().get(idEvento).alterarEstado(bet);
    }

    private static void gerirSaldoScene() {
        System.out.println("--- Gerir Saldo ---");
        System.out.println("1 - Associar Cartão");
        System.out.println("2 - Depositar Dinheiro");
        System.out.println("3 - Levantar Dinheiro");
        System.out.println("0 - Sair");
        System.out.println("-------------------");
    }

    private static void gerirSaldoHandler() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String opcao = scanner.nextLine();
            if (opcao.equals("1")) {
                associarCartao();
                System.out.println("Prima qualquer tecla para continuar");
                scanner.nextLine();
                gerirSaldoScene();
            } else if (opcao.equals("2")) {
                depositarDinheiro();
                System.out.println("Prima qualquer tecla para continuar");
                scanner.nextLine();
                gerirSaldoScene();
            } else if (opcao.equals("3")) {
                levantarDinheiro();
                System.out.println("Prima qualquer tecla para continuar");
                scanner.nextLine();
                gerirSaldoScene();
            } else if (opcao.equals("0")) {
                break;
            } else {
                System.out.println("Input não reconhecido");
                gerirSaldoScene();
            }
        }
    }

    private static void associarCartao() {
        Apostador atual = (Apostador) bet.getAtual();
        Scanner scanner = new Scanner(System.in);
        Pattern mb = Pattern.compile("[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4}");
        System.out.println("Insira o número do cartão, do tipo dddd-dddd-dddd-dddd");
        try {
            String novoCartao = scanner.next(mb);
            atual.setCartaoAssociado(novoCartao);
        } catch (Exception e) {
            System.out.println("Cartão inválido");
            return;
        }
    }

    private static void depositarDinheiro() {
        Apostador atual = (Apostador) bet.getAtual();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Qual a quantia que quer depositar?");
        String quantia = scanner.nextLine();
        if (isDouble(quantia)) {
            double qt = Double.parseDouble(quantia);
            atual.depositar(qt);
        } else {
            System.out.println("Valor inválido");
            return;
        }
    }

    private static void levantarDinheiro() {
        Apostador atual = (Apostador) bet.getAtual();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Qual a quantia que quer levantar?");
        String quantia = scanner.nextLine();
        if (isDouble(quantia)) {
            double qt = Double.parseDouble(quantia);
            try {
                atual.levantar(qt);
            } catch (SaldoInsuficienteException e) {
                System.out.println("Impossível levantar essa quantia");
                return;
            }
        }
    }

    private static void novaApostaForm() {
        Apostador atual = (Apostador) bet.getAtual();
        Scanner scanner = new Scanner(System.in);
        System.out.println("---- Registar Nova Aposta ----");
        System.out.println("Indique em qual evento quer apostar");
        int idEvento = 0;
        while (true) {
            String idev = scanner.nextLine();
            if (idev.equals("0")) {
                return;
            }
            if (isInteger(idev)) {
                idEvento = Integer.parseInt(idev);
                if (bet.getEventosAbertos().keySet().contains(idEvento)) break;
                else {
                    System.out.println("Evento não disponível");
                    System.out.println("Prima 0 para sair");
                }
            } else {
                System.out.println("Input não reconhecido");
                System.out.println("Prima 0 para sair");
            }
        }
        Evento evento = bet.getEventos().get(idEvento);
        System.out.println(evento.toString());
        System.out.println("Indique qual o resultado que pretende apostar:\n" +
                "0 - Vitória de " + evento.getEquipa1().getDesignacao() + "- odd : " + evento.getResultadosPossiveis().get(0).getOdd() + "\n" +
                "1 - Empate - odd : " + evento.getResultadosPossiveis().get(1).getOdd() + "\n" +
                "2 - Vitória de " + evento.getEquipa2().getDesignacao() + "- odd : " + evento.getResultadosPossiveis().get(2).getOdd() + "\n");
        int idResultado = 0;
        while (true) {
            String idres = scanner.nextLine();
            if (idres.equals("0")) {
                idResultado = 0;
                break;
            } else if (idres.equals("1")) {
                idResultado = 1;
                break;
            } else if (idres.equals("2")) {
                idResultado = 2;
                break;
            } else {
                System.out.println("Resultado inválido. Insira um resultado válido");
            }
        }
        Resultado resultado = evento.getResultadosPossiveis().get(idResultado);
        double quantia;
        System.out.println("Qual a quantia que quer apostar?");
        while (true) {
            String qt = scanner.nextLine();
            if (!isDouble(qt)) {
                System.out.println("Quantia inválida. Insira uma nova quantia");
            } else {
                quantia = Double.parseDouble(qt);
                break;
            }
        }
        try {
            atual.registarAposta(evento, resultado, quantia);
        } catch (SaldoInsuficienteException e) {
            System.out.println("Saldo insuficiente. Aposta não registada");
            return;
        }
    }

    private static void showResultados() {
        System.out.println("---- Resultados ----");
        for (Evento e : bet.getEventos().values()) {
            if (e.getEstado() == 'F') {
                System.out.println(e.toString());
            }
        }
        System.out.println("--------------------");

    }

    private static void showApostas() {
        Apostador atual = (Apostador) bet.getAtual();
        System.out.println("---- Apostas Realizadas ----");
        if (atual.getApostas().size() == 0) {
            System.out.println("Não há apostas");
        } else {
            for (Aposta a : atual.getApostas().values()) {
                System.out.println(a.toString());
            }
        }
        System.out.println("----------------------------");
    }

    private static void showNotificacoes() {
        Apostador atual = (Apostador) bet.getAtual();
        System.out.println("---- Notificações ----");
        if (atual.getNotificacoes().size() == 0) {
            System.out.println("Não há notificações");
        } else {
            for (Notificacao n : atual.getNotificacoes()) {
                System.out.println(n.toString());
            }
        }
        System.out.println("---------------------");
    }

    private static void registarEventoForm() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("---- Registar Evento ----");
        System.out.println("> Desportos Disponíveis:");
        for (Desporto d : bet.getDesportos().values()) {
            System.out.println("    > " + d.getIdDesporto() + " - " + d.getDesignacao());
        }
        System.out.println("------");
        System.out.println("Selecione o desporto por identificador");
        int idDesporto = scanner.nextInt();


        System.out.println("> Equipas Disponíveis:");
        for (Equipa e : bet.getEquipas().values()) {
            System.out.println("    > " + e.getIdEquipa() + " - " + e.getDesignacao());
        }
        System.out.println("-----");
        System.out.println("Seleciona a primeira equipa por identificador");
        int idEquipa1 = scanner.nextInt();
        System.out.println("Selecione a segunda equipa por identificador");
        int idEquipa2;
        while (true) {
            idEquipa2 = scanner.nextInt();
            if (idEquipa2 == idEquipa1) {
                System.out.println("Já escolheu essa equipa.");
            } else {
                break;
            }
        }

        System.out.println("Indique a data do evento, no formato dd-mm-yyyy");
        Pattern dataPattern = Pattern.compile("[0-9]{2}-[0-9]{2}-[0-9]{4}");
        String data = new String();
        try {
            data = scanner.next(dataPattern);
        } catch (InputMismatchException e) {
            System.out.println("Data Inválida. Registo cancelado");
            return;
        }

        System.out.println("Indique a hora do evento, no formato hh:mm");
        Pattern horaPattern = Pattern.compile("[0-9]{2}:[0-9]{2}");
        String hora = new String();
        try {
            hora = scanner.next(horaPattern);
        } catch (InputMismatchException e) {
            System.out.println("Hora Inválida. Registo cancelado");
            return;
        }

        System.out.println("Indique as odds de vitória da equipa 1 : ( "
                + bet.getEquipas().get(idEquipa1).getDesignacao() + ")");
        double oddVitoria1 = scanner.nextDouble();

        System.out.println("Indique as odds de empate:");
        double oddEmpate = scanner.nextDouble();

        System.out.println("Indique as odds de vitória da equipa 2 : ( "
                + bet.getEquipas().get(idEquipa2).getDesignacao() + ")");
        double oddVitoria2 = scanner.nextDouble();
        List<Double> odds = new ArrayList<>();
        odds.add(oddVitoria1);
        odds.add(oddEmpate);
        odds.add(oddVitoria2);

        System.out.println("Indique a localização do evento");
        String localizacao = scanner.nextLine();

        try {
            bet.registarEvento(idDesporto, idEquipa1, idEquipa2, data, hora, odds, localizacao);
        } catch (DateTimeParseException e) {
            System.out.println("Data e/ou Hora inválida. Registo Cancelado.");
            return;
        }

        System.out.println("Evento Registado!");
    }


    private static void showEvents() {
        Map<Integer, Evento> eventos = bet.getEventos();
        System.out.println("---- Eventos ----");
        for (Evento e : eventos.values()) {
            System.out.println(e.toString());
        }
    }

    private static void handleEvents() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int idEvento = scanner.nextInt();
            if (bet.getEventos().keySet().contains(idEvento)) {
                showFormAposta(bet.getEventos().get(idEvento));
                break;
            } else if (idEvento == 0) {
                break;
            } else {
                System.out.println("Inseriu um input inválido. Tente de novo.");
            }
        }
    }

    private static void showFormAposta(Evento evento) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("----------Registar Aposta--------");
        System.out.println("---Resultados Disponíveis---");
        for (Resultado r : evento.getResultadosPossiveis()) {
            int i = 1;
            System.out.println("Resultado " + i);
            System.out.println(r.toString());
            i++;
        }
        System.out.println("------------");
        System.out.println("Selecione um resultado");
        int res;
        while (true) {
            res = scanner.nextInt();
            if (res < 0 || res > 2) {
                System.out.println("Resultado inválido. Insira um resultado válido.");
            } else break;
        }
        Resultado resultado = evento.getResultadosPossiveis().get(res);

        Apostador apostador = (Apostador) bet.getAtual();
        System.out.println("Saldo disponível: " + apostador.getSaldo());
        System.out.println("Qual a quantia que quer apostar?");
        double quantia = scanner.nextDouble();

        try {
            apostador.registarAposta(evento, resultado, quantia);
            System.out.println("Aposta registada com sucesso");
            loggedInScene();
        } catch (SaldoInsuficienteException e) {
            System.out.println("Quantia insuficiente");
            loggedInScene();
        }

    }

    // TODO: Consultar notificações, resultados, apostas realizadas e gerir saldo

    private static void loadFile() {
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


    private static void saveFile() {
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

    private static boolean isInteger(String s) {
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

    private static boolean isDouble(String s) {
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
     *
     */

}