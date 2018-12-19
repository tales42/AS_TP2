package Exception;

/**
 * Created by luismp on 11/11/2018.
 */
public class SaldoInsuficienteException extends Exception {

    double quantia;

    public SaldoInsuficienteException(double quantia){
        this.quantia = quantia;
    }

    public double getQuantia(){
        return this.quantia;
    }


}
