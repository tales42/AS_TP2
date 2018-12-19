import Business.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by luismp on 17/12/2018.
 */
public class EventoTest {
    @Test
    public void alterarEstado() throws Exception {
        Evento evento = new Evento();
        Resultado vitoria1 = new Resultado("Vitória da Equipa 1", 2);
        Resultado empate = new Resultado("Empate", 1);
        Resultado vitoria2 = new Resultado("Vitória da Equipa 2", 3);

        List<Resultado> resultadoList = new ArrayList<>();
        resultadoList.add(vitoria1);
        resultadoList.add(empate);
        resultadoList.add(vitoria2);

        evento.setResultadosPossiveis(resultadoList);

        Apostador apostador1 = new Apostador();
        apostador1.depositar(100);
        apostador1.setIdUtilizador(10);
        apostador1.registarAposta(evento,vitoria1,50);

        Apostador apostador2 = new Apostador();
        apostador2.depositar(100);
        apostador2.setIdUtilizador(20);
        apostador2.registarAposta(evento,empate,50);

        Apostador apostador3 = new Apostador();
        apostador3.depositar(100);
        apostador3.setIdUtilizador(30);
        apostador3.registarAposta(evento,vitoria2,50);

        BetESS bet = new BetESS();
        HashMap<Integer, Utilizador> utilizadorHashMap = new HashMap<>();
        utilizadorHashMap.put(apostador1.getIdUtilizador(),apostador1);
        utilizadorHashMap.put(apostador2.getIdUtilizador(),apostador2);
        utilizadorHashMap.put(apostador3.getIdUtilizador(),apostador3);
        bet.setUtilizadores(utilizadorHashMap);

        evento.alterarEstado(bet);

        if(evento.getResultadoFinal().getDesignacao().equals(vitoria1.getDesignacao())){
            assertEquals(150,apostador1.getSaldo(),0.0000001);
        }
        else if(evento.getResultadoFinal().getDesignacao().equals(empate.getDesignacao())){
            assertEquals(100,apostador2.getSaldo(),0.0000001);
        }
        else if(evento.getResultadoFinal().getDesignacao().equals(vitoria2.getDesignacao())){
            assertEquals(200,apostador3.getSaldo(),0.0000001);
        }

        assertEquals(1,apostador1.getNotificacoes().size(),0.0000001);
        assertEquals(1,apostador2.getNotificacoes().size(),0.0000001);
        assertEquals(1,apostador3.getNotificacoes().size(),0.0000001);


    }

}