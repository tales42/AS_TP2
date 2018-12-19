import Business.Aposta;
import Business.Apostador;
import Business.Evento;
import Business.Resultado;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by luismp on 17/12/2018.
 */
public class ApostadorTest {
    @Test
    public void associarCartao() throws Exception {
        Apostador apostador = new Apostador();
        String cartao = "1111-1111-1111-1111";
        apostador.associarCartao(cartao);
        assertEquals(apostador.getCartaoAssociado(),cartao);
    }

    @Test
    public void depositar() throws Exception {
        Apostador apostador = new Apostador();
        apostador.depositar(100);
        assertEquals(apostador.getSaldo(),100,0.0000001);
    }

    @Test
    public void levantar() throws Exception {
        Apostador apostador = new Apostador();
        apostador.depositar(100);
        apostador.levantar(50);
        assertEquals(apostador.getSaldo(),50,0.0000001);
    }

    @Test
    public void registarAposta() throws Exception {
        Apostador apostador = new Apostador();
        apostador.setIdUtilizador(400);
        apostador.depositar(200);

        Resultado resultado = new Resultado();
        resultado.setDesignacao("Empate");
        resultado.setOdd(2);

        Evento evento = new Evento();

        apostador.registarAposta(evento,resultado,100);
        assertEquals(apostador.getApostas().size(),1,0.0000001);
        assertEquals(100,apostador.getSaldo(),0.0000001);
        assertEquals(evento.getApostas().size(),1,0.00000001);
        assertTrue(evento.getApostas().containsKey(apostador.getIdUtilizador()));
    }

}