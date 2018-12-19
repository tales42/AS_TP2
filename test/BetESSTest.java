import Business.BetESS;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by luismp on 17/12/2018.
 */
public class BetESSTest {
    @Test
    public void registarApostador() throws Exception {
        BetESS bet = new BetESS();
        bet.registarApostador("tales","tales","tales");
        bet.registarApostador("bit","bit","bit");
        assertTrue(bet.getUtilizadores().size()==2);
        assertTrue(bet.getUtilizador("tales").getIdUtilizador() == 1);
        assertTrue(bet.getUtilizador("bit").getIdUtilizador() == 2);
    }

    @Test
    public void iniciarSessao() throws Exception {
        BetESS bet  = new BetESS();
        bet.registarApostador("tales","tales","tales");
        bet.iniciarSessao("tales","tales");
        assertTrue(bet.getAtual().getEmail().equals("tales"));
    }

}