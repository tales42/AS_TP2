package Business;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by luismp on 26/12/2018.
 */
public class DetalhesEvento {
    private LocalDate data;
    private String localizacao;
    private LocalTime horaDeInicio;
    private Duration duracao;
    private char estado;
    private Equipa equipa1;
    private Equipa equipa2;
    private Desporto desporto;


    /**
     * Construtor parametrizado
     * @param data
     * @param horaDeInicio
     * @param duracao
     * @param localizacao
     * @param equipa1
     * @param equipa2
     * @param desporto
     */
    public DetalhesEvento(LocalDate data, LocalTime horaDeInicio, Duration duracao,String localizacao, Equipa equipa1, Equipa equipa2, Desporto desporto ){
        this.data = data;
        this.localizacao = localizacao;
        this.horaDeInicio = horaDeInicio;
        this.duracao = duracao;
        this.equipa1 = equipa1;
        this.equipa2 = equipa2;
        this.desporto = desporto;
        estado = 'A';
    }



    /**
     * Getter da Data do Evento
     * @return Data
     */
    public LocalDate getData() {
        return data;
    }

    /**
     * Getter da Localização do Evento
     * @return Localização
     */
    public String getLocalizacao() {
        return localizacao;
    }

    /**
     * Getter da Hora de Início do Evento
     * @return Hora de Início
     */
    public LocalTime getHoraDeInicio() {
        return horaDeInicio;
    }

    /**
     * Getter da Duração  do Evento
     * @return Duração
     */
    public Duration getDuracao() {
        return duracao;
    }

    /**
     * Getter do Estado do Evento
     * @return Estado
     */
    public char getEstado() {
        return estado;
    }

    /**
     * Setter do Estado do Evento
     * @param estado
     */
    public void setEstado(char estado) {
        this.estado = estado;
    }

    /**
     * Getter da Equipa 1  do Evento
     * @return Equipa 1
     */
    public Equipa getEquipa1() {
        return equipa1;
    }

    /**
     * Getter da Equipa 2  do Evento
     * @return Equipa 2
     */
    public Equipa getEquipa2() {
        return equipa2;
    }

    /**
     * Getter do Desporto do Evento
     * @return Desporto
     */
    public Desporto getDesporto() {
        return desporto;
    }


}
