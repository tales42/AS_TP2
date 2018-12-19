import Business.Aposta;
import Business.Resultado;

import static org.junit.Assert.*;

/**
 * Created by luismp on 17/12/2018.
 */
public class ApostaTest {
    @org.junit.Test
    public void calcularGanhos() throws Exception {
        Aposta aposta = new Aposta();
        aposta.setQuantia(100);

        Resultado resultadoFinal = new Resultado();
        resultadoFinal.setOdd(2.5);
        aposta.setResultado(resultadoFinal);

        assertEquals(aposta.calcularGanhos(), 100*2.5,0.000001);
    }

}