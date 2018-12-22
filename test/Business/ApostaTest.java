package Business;

import static org.junit.Assert.*;

/**
 * Created by luismp on 21/12/2018.
 */
public class ApostaTest {
    @org.junit.Test
    public void calcularGanhos() throws Exception {

        Resultado resultadoFinal = new Resultado("Vitoria",2.5);
        Aposta aposta = new Aposta(1,100,resultadoFinal);
        assertEquals(aposta.calcularGanhos(), 100*2.5,0.000001);
    }


}