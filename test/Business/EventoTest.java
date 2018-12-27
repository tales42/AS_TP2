package Business;

import org.junit.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by luismp on 21/12/2018.
 */
public class EventoTest {
    @Test
    public void alterarEstado() throws Exception {
        Resultado vitoria1 = new Resultado("Vitória da Equipa 1", 2);
        Resultado empate = new Resultado("Empate", 1);
        Resultado vitoria2 = new Resultado("Vitória da Equipa 2", 3);

        List<Resultado> resultadoList = new ArrayList<>();
        resultadoList.add(vitoria1);
        resultadoList.add(empate);
        resultadoList.add(vitoria2);

        DetalhesEvento detalhesEvento = new DetalhesEvento(LocalDate.now(),LocalTime.now(),Duration.ofMinutes(90),"estádio", new Equipa(1,"SLB"), new Equipa(2,"FCP"),new Desporto(1,"Futebol") );
        Evento evento = new Evento(1,resultadoList,detalhesEvento);


        Apostador apostador1 = new Apostador(1,"Tales","Tales","Tales");
        apostador1.depositar(100);
        apostador1.setIdUtilizador(10);
        apostador1.registarAposta(evento,vitoria1,50);

        Apostador apostador2 = new Apostador(2,"Bomber","Bomber","Bomber");
        apostador2.depositar(100);
        apostador2.setIdUtilizador(20);
        apostador2.registarAposta(evento,empate,50);

        Apostador apostador3 = new Apostador(3,"Gigo","Gigo","Gigo");
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