package GUI;
import Business.*;
import Exception.*;

import java.io.*;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.regex.Pattern;


/**
 * Created by luismp on 11/11/2018.
 */
public class GUI {

    private static BetESS bet;

    private static final String filename = "data.bin";

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
        showMainPage();
        boolean handler = true;
        while (handler) {
            handler = mainPageHandler();
        }

    }

    private static boolean mainPageHandler() {
        String option = readLine();
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
            return false;
        } else {
            System.out.println("Input não reconhecido.");
        }
        return true;
    }

    private static void showLoginForm() {
        System.out.println("---- Iniciar Sessão ----");
        System.out.println("Email:");
        String email = readLine();
        System.out.println("Password:");
        String password = readLine();
        tryLogIn(email, password);

    }

    private static void tryLogIn(String email, String password) {
        try {
            bet.iniciarSessao(email, password);
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

    private static void tryRegistarApostador(String email, String password, String nome) {
        try {
            bet.registarApostador(email, password, nome);
            System.out.println("Utilizador registado com sucesso.");
            confirmarContinuar();
        } catch (ApostadorRegistadoException e) {
            System.out.println("Utilizador já registado.");
            confirmarContinuar();
        }
    }

    private static void loggedInScene() {
        Utilizador atual = bet.getAtual();
        if (atual instanceof AdministradorDeEventos) {
            adminLoggedInScene();
        } else if (atual instanceof Apostador) {
            apostadorLoggedInScene((Apostador) atual);
        }
    }

    private static void adminLoggedInScene() {
        System.out.println("---- Administrador -----");
        System.out.println("1 - Registar Evento");
        System.out.println("2 - Consultar Eventos");
        System.out.println("3 - Fechar Evento");
        System.out.println("0 - Sair");
        System.out.println("-------------------------");
    }

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

    private static void loggedInHandler() {
        loggedInScene();
        Utilizador atual = bet.getAtual();
        boolean isLoggedIn = true;
        if (atual instanceof AdministradorDeEventos) {
            while (isLoggedIn) {
                isLoggedIn = adminLoggedInHandler();
            }
        } else if (atual instanceof Apostador) {
            while (isLoggedIn) {
                isLoggedIn = apostadorLoggedInHandler();
            }
        }
    }

    private static boolean apostadorLoggedInHandler() {
        String opcao = readLine();
        if (opcao.equals("1")) {
            showNotificacoes();
            confirmarContinuar();
            loggedInScene();
        } else if (opcao.equals("2")) {
            gerirSaldoScene();
            gerirSaldoHandler();
            confirmarContinuar();
            loggedInScene();
        } else if (opcao.equals("3")) {
            showResultados();
            confirmarContinuar();
            loggedInScene();
        } else if (opcao.equals("4")) {
            showEvents();
            confirmarContinuar();
            loggedInScene();
        } else if (opcao.equals("5")) {
            showApostas();
            confirmarContinuar();
            loggedInScene();
        } else if (opcao.equals("6")) {
            novaApostaForm();
            confirmarContinuar();
            loggedInScene();
        } else if (opcao.equals("0")) {
            bet.terminarSessao();
            return true;
        } else {
            System.out.println("Input não reconhecido");
            confirmarContinuar();
            loggedInScene();
        }
        return false;
    }

    private static void confirmarContinuar() {
        System.out.println("Prima qualquer tecla para continuar");
        readLine();
    }

    private static boolean adminLoggedInHandler() {
        String opcao = readLine();
        if (opcao.equals("1")) {
            registarEventoForm();
            confirmarContinuar();
            loggedInScene();
        } else if (opcao.equals("2")) {
            showEvents();
            confirmarContinuar();
            loggedInScene();
        } else if (opcao.equals("3")) {
            closeEvent();
            confirmarContinuar();
            loggedInScene();
        } else if (opcao.equals("0")) {
            bet.terminarSessao();
            return false;
        } else {
            System.out.println("Input não reconhecido. ");
            loggedInScene();
        }
        return true;
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
        while (true) {
            String opcao = readLine();
            if (opcao.equals("1")) {
                associarCartao();
                confirmarContinuar();
                gerirSaldoScene();
            } else if (opcao.equals("2")) {
                depositarDinheiro();
                confirmarContinuar();
                gerirSaldoScene();
            } else if (opcao.equals("3")) {
                levantarDinheiro();
                confirmarContinuar();
                gerirSaldoScene();
            } else if (opcao.equals("0")) {
                break;
            } else {
                System.out.println("Input não reconhecido");
                confirmarContinuar();
                gerirSaldoScene();
            }
        }
    }

    private static void associarCartao() {
        Apostador atual = (Apostador) bet.getAtual();
        System.out.println("Insira o número do cartão, do tipo dddd-dddd-dddd-dddd");
        try {
            String novoCartao = validarCartao();
            atual.setCartaoAssociado(novoCartao);
        } catch (Exception e) {
            System.out.println("Cartão inválido");
            confirmarContinuar();
            return;
        }
    }

    private static String validarCartao() {
        Scanner scanner = new Scanner(System.in);
        Pattern mb = Pattern.compile("[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4}");
        return scanner.next(mb);
    }

    private static void depositarDinheiro() {
        Apostador atual = (Apostador) bet.getAtual();
        System.out.println("Qual a quantia que quer depositar?");
        String quantia = readLine();
        if (isDouble(quantia)) {
            double qt = Double.parseDouble(quantia);
            atual.depositar(qt);
            confirmarContinuar();
        } else {
            System.out.println("Valor inválido");
            confirmarContinuar();
            return;
        }
    }

    private static void levantarDinheiro() {
        Apostador atual = (Apostador) bet.getAtual();
        System.out.println("Qual a quantia que quer levantar?");
        String quantia = readLine();
        if (isDouble(quantia)) {
            double qt = Double.parseDouble(quantia);
            try {
                atual.levantar(qt);
                confirmarContinuar();
            } catch (SaldoInsuficienteException e) {
                System.out.println("Impossível levantar essa quantia");
                confirmarContinuar();
                return;
            }
        }
    }

    private static void novaApostaForm() {
        Apostador atual = (Apostador) bet.getAtual();
        System.out.println("---- Registar Nova Aposta ----");
        System.out.println("Indique em qual evento quer apostar");
        int idEvento = escolherIDEvento();
        Evento evento = bet.getEventos().get(idEvento);
        printDetalhesEvento(evento);
        int idResultado = escolherResultado();
        Resultado resultado = evento.getResultadosPossiveis().get(idResultado);
        double quantia;
        quantia = escolherQuantia();
        tryRegistarAposta(atual, evento, resultado, quantia);
    }

    private static void printDetalhesEvento(Evento evento) {
        System.out.println(evento);
        System.out.println("Indique qual o resultado que pretende apostar:\n" +
                "0 - Vitória de " + evento.getEquipa1().getDesignacao() + "- odd : " + evento.getResultadosPossiveis().get(0).getOdd() + "\n" +
                "1 - Empate - odd : " + evento.getResultadosPossiveis().get(1).getOdd() + "\n" +
                "2 - Vitória de " + evento.getEquipa2().getDesignacao() + "- odd : " + evento.getResultadosPossiveis().get(2).getOdd() + "\n");
    }

    private static void tryRegistarAposta(Apostador atual, Evento evento, Resultado resultado, double quantia) {
        try {
            atual.registarAposta(evento, resultado, quantia);
        } catch (SaldoInsuficienteException e) {
            System.out.println("Saldo insuficiente. Aposta não registada");
            return;
        }
    }

    private static double escolherQuantia() {
        System.out.println("Qual a quantia que quer apostar?");
        double quantia;
        while (true) {
            String qt = readLine();
            if (!isDouble(qt)) {
                System.out.println("Quantia inválida. Insira uma nova quantia");
            } else {
                quantia = Double.parseDouble(qt);
                break;
            }
        }
        return quantia;
    }

    private static int escolherResultado() {
        int idResultado = 0;
        while (true) {
            String idres = readLine();
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
        return idResultado;
    }

    private static int escolherIDEvento() {
        int idEvento = 0;
        while (true) {
            String idev = readLine();
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
        return idEvento;
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
        System.out.println("---- Registar Evento ----");
        int idDesporto = escolherDesporto();

        showEquipas();
        int idEquipa1 = escolherEquipa1();
        int idEquipa2 = escolherEquipa2(idEquipa1);
        String data;
        try {
            data = escolherData();
        } catch (InputMismatchException e) {
            System.out.println("Data Inválida. Registo cancelado");
            return;
        }
        String hora;
        try {
            hora = escolherHora();
        } catch (InputMismatchException e) {
            System.out.println("Hora Inválida. Registo cancelado");
            return;
        }

        double oddVitoria1 = getOddVitoriaEquipa1(idEquipa1);
        double oddEmpate = getOddEmpate();
        double oddVitoria2 = getOddVitoriaEquipa2(idEquipa2);
        List<Double> odds = new ArrayList<>();
        odds.add(oddVitoria1);
        odds.add(oddEmpate);
        odds.add(oddVitoria2);
        String localizacao = getLocalizacao();
        tryRegistarEvento(idDesporto, idEquipa1, idEquipa2, data, hora, odds, localizacao); // possível smell ?!?
    }

    private static void tryRegistarEvento(int idDesporto, int idEquipa1, int idEquipa2, String data, String hora, List<Double> odds, String localizacao) {
        try {
            bet.registarEvento(idDesporto, idEquipa1, idEquipa2, data, hora, odds, localizacao);
            System.out.println("Evento Registado!");
        } catch (DateTimeParseException e) {
            System.out.println("Data e/ou Hora inválida. Registo Cancelado.");
            return;
        }
    }

    private static String getLocalizacao() {
        System.out.println("Indique a localização do evento");
        return readLine();
    }

    private static double getOddVitoriaEquipa2(int idEquipa2) {
        System.out.println("Indique as odds de vitória da equipa 2 : ( " + bet.getEquipas().get(idEquipa2).getDesignacao() + ")");
        String odd = readLine();
        return Double.parseDouble(odd);
    }

    private static double getOddEmpate() {
        System.out.println("Indique as odds de empate:");
        String odd = readLine();
        return Double.parseDouble(odd);
    }

    private static double getOddVitoriaEquipa1(int idEquipa1) {
        System.out.println("Indique as odds de vitória da equipa 1 : ( " + bet.getEquipas().get(idEquipa1).getDesignacao() + ")");
        String odd = readLine();
        return Double.parseDouble(odd);
    }

    private static String escolherHora() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Indique a hora do evento, no formato hh:mm");
        Pattern horaPattern = Pattern.compile("[0-9]{2}:[0-9]{2}");
        String hora = scanner.next(horaPattern);
        return hora;
    }

    private static String escolherData() throws InputMismatchException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Indique a data do evento, no formato dd-mm-yyyy");
        Pattern dataPattern = Pattern.compile("[0-9]{2}-[0-9]{2}-[0-9]{4}");
        String data = scanner.next(dataPattern);
        return data;
    }

    private static int escolherEquipa2(int idEquipa1) {
        System.out.println("Selecione a segunda equipa por identificador");
        String id2;
        int idEquipa2;
        while (true) {
            id2 = readLine();
            idEquipa2 = Integer.parseInt(id2);
            if (idEquipa2 == idEquipa1) {
                System.out.println("Já escolheu essa equipa.");
            } else {
                break;
            }
        }
        return idEquipa2;
    }

    private static int escolherEquipa1() {
        System.out.println("Seleciona a primeira equipa por identificador");
        String id1 = readLine();
        return Integer.parseInt(id1);
    }

    private static void showEquipas() {
        System.out.println("> Equipas Disponíveis:");
        for (Equipa e : bet.getEquipas().values()) {
            System.out.println("    > " + e.getIdEquipa() + " - " + e.getDesignacao());
        }
        System.out.println("-----");
    }

    private static int escolherDesporto() {
        System.out.println("> Desportos Disponíveis:");
        for (Desporto d : bet.getDesportos().values()) {
            System.out.println("    > " + d.getIdDesporto() + " - " + d.getDesignacao());
        }
        System.out.println("------");
        System.out.println("Selecione o desporto por identificador");
        String iddesp = readLine();
        return Integer.parseInt(iddesp);
    }


    private static void showEvents() {
        Map<Integer, Evento> eventos = bet.getEventos();
        System.out.println("---- Eventos ----");
        for (Evento e : eventos.values()) {
            System.out.println(e);
        }
    }

    private static String readLine(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

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
     */

}