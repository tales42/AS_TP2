package GUI;

import Business.Apostador;
import Business.BetESS;
import Exception.*;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Created by luismp on 20/12/2018.
 */
public class GUISaldo extends GUI {

    /**
     * Método que imprime no ecrã o menu de gestão do Saldo de um Apostador
     */
    protected static void gerirSaldoScene() {
        System.out.println("--- Gerir Saldo ---");
        System.out.println("1 - Associar Cartão");
        System.out.println("2 - Depositar Dinheiro");
        System.out.println("3 - Levantar Dinheiro");
        System.out.println("0 - Sair");
        System.out.println("-------------------");
    }

    /**
     * Método que recebe e trata os inputs relativos ao menu de gestão de saldo
     */
    protected static void gerirSaldoHandler() {
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

    /**
     * Método que permite um Apostador associar um cartão ao sistema
     */
    private static void associarCartao() {
        Apostador atual = (Apostador) getAtual();
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

    /**
     * Método que valida o cartão inserido baseado na forma
     * @return Cartão
     */
    private static String validarCartao() {
        Scanner scanner = new Scanner(System.in);
        Pattern mb = Pattern.compile("[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4}");
        return scanner.next(mb);
    }

    /**
     * Método que permite ao Apostador depositar dinheiro no sistema
     */
    private static void depositarDinheiro() {
        Apostador atual = (Apostador) getAtual();
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

    /**
     * Método que permite ao Apostador levantar dinheiro do sistema
     */
    private static void levantarDinheiro() {
        Apostador atual = (Apostador) getAtual();
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

}
