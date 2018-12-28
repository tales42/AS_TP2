package GUI;

import Business.Desporto;
import Business.Equipa;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Created by luismp on 20/12/2018.
 */
public class GUIEvento extends GUI{


    /**
     * Método que recebe e trata os inputs da página após o login de um administrador
     * @return
     */
    protected static boolean adminLoggedInHandler() {
        String opcao = readLine();
        if (opcao.equals("1")) {
            registarEventoForm();
            confirmarContinuar();
            GUIMainPage.loggedInScene();
        } else if (opcao.equals("2")) {
            showEvents();
            confirmarContinuar();
            GUIMainPage.loggedInScene();
        } else if (opcao.equals("3")) {
            closeEvent();
            confirmarContinuar();
            GUIMainPage.loggedInScene();
        } else if (opcao.equals("0")) {
            terminarSessao();
            return false;
        } else {
            System.out.println("Input não reconhecido. ");
            GUIMainPage.loggedInScene();
        }
        return true;
    }

    /**
     * Método que apresenta o formulário para registar um novo Evento
     */
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

    /**
     * Método que tenta registar um novo Evento no sistema
     * @param idDesporto
     * @param idEquipa1
     * @param idEquipa2
     * @param data
     * @param hora
     * @param odds
     * @param localizacao
     */
    private static void tryRegistarEvento(int idDesporto, int idEquipa1, int idEquipa2, String data, String hora, List<Double> odds, String localizacao) {
        try {
            registarEvento(idDesporto, idEquipa1, idEquipa2, data, hora, odds, localizacao);
            System.out.println("Evento Registado!");
        } catch (DateTimeParseException e) {
            System.out.println("Data e/ou Hora inválida. Registo Cancelado.");
            return;
        }
    }

    /**
     * Método que permite ao Administrador definir a localização de um EVento
     * @return Localização
     */
    private static String getLocalizacao() {
        System.out.println("Indique a localização do evento");
        String localizacao = readLine();
        return localizacao;
    }

    /**
     * Método que permite ao Administrador definir as odds de vitória da equipa 2
     * @param idEquipa2
     * @return Odd
     */
    private static double getOddVitoriaEquipa2(int idEquipa2) {
        System.out.println("Indique as odds de vitória da equipa 2 : ( " + getDesignacaoEquipa(idEquipa2) + ")");
        String odd = readLine();
        return Double.parseDouble(odd);
    }

    /**
     * Método que permite ao Administrador definir as odds de empate
     * @return Odd
     */
    private static double getOddEmpate() {
        System.out.println("Indique as odds de empate:");
        String odd = readLine();
        return Double.parseDouble(odd);
    }

    /**
     * Método que permite ao Administrador definir as odds de vitória da equipa 1
     * @param idEquipa1
     * @return Odd
     */
    private static double getOddVitoriaEquipa1(int idEquipa1) {
        System.out.println("Indique as odds de vitória da equipa 1 : ( " + getDesignacaoEquipa(idEquipa1) + ")");
        String odd = readLine();
        return Double.parseDouble(odd);
    }

    /**
     * Método que permite ao Administrador definir a hora do evento
     * @return Hora
     */
    private static String escolherHora() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Indique a hora do evento, no formato hh:mm");
        Pattern horaPattern = Pattern.compile("[0-9]{2}:[0-9]{2}");
        String hora = scanner.next(horaPattern);
        return hora;
    }

    /**
     * Método que permite ao Administrador definir a data do evento.
     * Lança uma exceção caso não respeite a seguinte expressão regular "[0-9]{2}-[0-9]{2}-[0-9]{4}"
     * @return Data
     * @throws InputMismatchException
     */
    private static String escolherData() throws InputMismatchException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Indique a data do evento, no formato dd-mm-yyyy");
        Pattern dataPattern = Pattern.compile("[0-9]{2}-[0-9]{2}-[0-9]{4}");
        String data = scanner.next(dataPattern);
        return data;
    }

    /**
     * Método que permite escolher a segunda equipa, sendo que já escolheu a primeira
     * @param idEquipa1
     * @return idEquipa2
     */
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

    /**
     * Método que permite escolher a primeira equipa
     * @return idEquipa1
     */
    private static int escolherEquipa1() {
        System.out.println("Seleciona a primeira equipa por identificador");
        String id1 = readLine();
        return Integer.parseInt(id1);
    }

    /**
     * Método que mostra quais as equipas disponíveis
     */
    private static void showEquipas() {
        System.out.println("> Equipas Disponíveis:");
        for (Equipa e : getEquipas()) {
            System.out.println("    > " + e.getIdEquipa() + " - " + e.getDesignacao());
        }
        System.out.println("-----");
    }

    /**
     * Método que permite escolher qual o desporto do evento
     * @return
     */
    private static int escolherDesporto() {
        System.out.println("> Desportos Disponíveis:");
        for (Desporto d : getDesportos()) {
            System.out.println("    > " + d.getIdDesporto() + " - " + d.getDesignacao());
        }
        System.out.println("------");
        System.out.println("Selecione o desporto por identificador");
        String iddesp = readLine();
        return Integer.parseInt(iddesp);
    }

}
