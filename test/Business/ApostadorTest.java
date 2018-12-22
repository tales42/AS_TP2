package Business;

import org.junit.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by luismp on 21/12/2018.
 */
public class ApostadorTest {
    @Test
    public void associarCartao() throws Exception {
        Apostador apostador = new Apostador(1,"Tales","Tales","Tales");
        String cartao = "1111-1111-1111-1111";
        apostador.setCartaoAssociado(cartao);
        assertEquals(apostador.getCartaoAssociado(),cartao);
    }

    @Test
    public void depositar() throws Exception {
        Apostador apostador = new Apostador(1,"Tales","Tales","Tales");
        apostador.depositar(100);
        assertEquals(apostador.getSaldo(),100,0.0000001);
    }

    @Test
    public void levantar() throws Exception {
        Apostador apostador = new Apostador(1,"Tales","Tales","Tales");
        apostador.depositar(100);
        apostador.levantar(50);
        assertEquals(apostador.getSaldo(),50,0.0000001);
    }

    @Test
    public void registarAposta() throws Exception {
        Apostador apostador = new Apostador(400,"Tales","Tales","Tales");
        apostador.depositar(200);

        Resultado vitoria1 = new Resultado("Vitória da Equipa 1", 2);
        Resultado empate = new Resultado("Empate", 1);
        Resultado vitoria2 = new Resultado("Vitória da Equipa 2", 3);

        List<Resultado> resultadoList = new ArrayList<>();
        resultadoList.add(vitoria1);
        resultadoList.add(empate);
        resultadoList.add(vitoria2);

        Evento evento = new Evento(1, LocalDate.now(), "estádio", LocalTime.now(), Duration.ofMinutes(90), new Equipa(1,"SLB"), new Equipa(2,"FCP"), new Desporto(1,"Futebol"),resultadoList);

        apostador.registarAposta(evento,empate,100);
        assertEquals(apostador.getApostas().size(),1,0.0000001);
        assertEquals(100,apostador.getSaldo(),0.0000001);
        assertEquals(evento.getApostas().size(),1,0.00000001);
        assertTrue(evento.getApostas().containsKey(apostador.getIdUtilizador()));
    }
}