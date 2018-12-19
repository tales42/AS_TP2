import Business.Aposta;
import Business.Resultado;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by luismp on 17/12/2018.
 */
public class ApostaTest {
    @org.junit.Test
    public void calcularGanhos() throws Exception {

        Resultado resultadoFinal = new Resultado("Vitoria",2.5);
        Aposta aposta = new Aposta(1,100,resultadoFinal);
        assertEquals(aposta.calcularGanhos(), 100*2.5,0.000001);
    }

}