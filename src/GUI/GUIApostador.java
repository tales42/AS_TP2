package GUI;

import Business.*;
import Exception.*;

/**
 * Created by luismp on 20/12/2018.
 */
public class GUIApostador extends GUIMainPage{

    /**
     * Método que recebe e trata os inputs da página após o login de um apostador
     * @return
     */
    protected static boolean apostadorLoggedInHandler() {
        String opcao = readLine();
        if (opcao.equals("1")) {
            showNotificacoes();
            confirmarContinuar();
            loggedInScene();
        } else if (opcao.equals("2")) {
            GUISaldo.gerirSaldoScene();
            GUISaldo.gerirSaldoHandler();
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
            terminarSessao();
            return false;
        } else {
            System.out.println("Input não reconhecido");
            confirmarContinuar();
            loggedInScene();
        }
        return true;
    }

    /**
     * Método que apresenta o formulário para registar uma nova Aposta
     * @see Aposta
     */
    private static void novaApostaForm() {
        Apostador atual = (Apostador) getAtual();
        System.out.println("---- Registar Nova Aposta ----");
        System.out.println("Indique em qual evento quer apostar");
        int idEvento = escolherIDEvento();
        if(idEvento == -1) {
            return;
        }
        Evento evento = getEvento(idEvento);
        printDetalhesEvento(evento);
        int idResultado = escolherResultado();
        Resultado resultado = evento.getResultadosPossiveis().get(idResultado);
        double quantia;
        quantia = escolherQuantia();
        tryRegistarAposta(atual, evento, resultado, quantia);
    }

    /**
     * Método que imprime no ecrã os detalhes de um evento
     * @param evento
     * @see Evento
     */
    private static void printDetalhesEvento(Evento evento) {
        System.out.println(evento);
        System.out.println("Indique qual o resultado que pretende apostar:\n" +
                "0 - Vitória de " + evento.getEquipa1().getDesignacao() + "- odd : " + evento.getResultadosPossiveis().get(0).getOdd() + "\n" +
                "1 - Empate - odd : " + evento.getResultadosPossiveis().get(1).getOdd() + "\n" +
                "2 - Vitória de " + evento.getEquipa2().getDesignacao() + "- odd : " + evento.getResultadosPossiveis().get(2).getOdd() + "\n");
    }

    /**
     * Método que tenta registar uma nova Aposta no sistema
     * @param atual
     * @param evento
     * @param resultado
     * @param quantia
     * @see Aposta
     */
    private static void tryRegistarAposta(Apostador atual, Evento evento, Resultado resultado, double quantia) {
        try {
            atual.registarAposta(evento, resultado, quantia);
        } catch (SaldoInsuficienteException e) {
            System.out.println("Saldo insuficiente. Aposta não registada");
            return;
        }
    }

    /**
     * Método que permite ao Apostador selecionar qual a quantia a apostar
     * @return Quantia
     */
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

    /**
     * Método que permite ao Apostador selecionar qual o Resultado em que quer apostar
     * @return Resultado
     */
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

    /**
     * Método que permite ao Apsotador selecionar qual o identificador do evento em que quer apostar
     * @return ID Evento
     */
    private static int escolherIDEvento() {
        int idEvento = 0;
        while (true) {
            String idev = readLine();
            if (isInteger(idev)) {
                idEvento = Integer.parseInt(idev);
                if (isEventoAberto(idEvento)) break;
                else {
                    System.out.println("Evento não disponível");
                    idEvento = -1;
                    break;
                }
            } else {
                System.out.println("Input não reconhecido");
                idEvento = -1;
                break;
            }
        }
        return idEvento;
    }

    /**
     * Método que imprime todos os resultados no ecrã
     */
    private static void showResultados() {
        System.out.println("---- Resultados ----");
        for (Evento e : getEventos().values()) {
            if (e.getEstado() == 'F') {
                System.out.println(e.toString());
            }
        }
        System.out.println("--------------------");

    }

    /**
     * Método que imprime no ecrã todas as Apostas que um Apostador registou
     */
    private static void showApostas() {
        Apostador atual = (Apostador) getAtual();
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

    /**
     * Método que imprime no ecrã todas as Notificações enviadas a um Apostador
     */
    private static void showNotificacoes() {
        Apostador atual = (Apostador) getAtual();
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

}
