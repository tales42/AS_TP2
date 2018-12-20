package Business;

import java.io.Serializable;

/**
 * Created by luismp on 10/11/2018.
 */
public class Utilizador implements Serializable {
    private int idUtilizador;
    private String email;
    private String password;
    private String nome;

    /**
     * Construtor Vazio de um Utilizador
     */
    public Utilizador(){
        this.idUtilizador = 0;
        this.email="";
        this.password="";
        this.nome="";
    }

    /**
     * Construtor Parametrizado de um Utilizador
     * @param idUtilizador
     * @param email
     * @param password
     * @param nome
     */
    public Utilizador(int idUtilizador, String email, String password, String nome){
        this.setIdUtilizador(idUtilizador);
        this.setEmail(email);
        this.setPassword(password);
        this.setNome(nome);
    }


    /**
     * Getter do identificador do Utilizador
     * @return idUtilizador
     */
    public int getIdUtilizador() {
        return idUtilizador;
    }

    /**
     * Setter do identificador do Utilizador
     * @param idUtilizador
     */
    public void setIdUtilizador(int idUtilizador){
        this.idUtilizador = idUtilizador;
    }

    /**
     * Getter do Email do Utilizador
     * @return Email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter do Email do Utilizador
     * @param email
     */
    public void setEmail(String email){
        this.email = email;
    }

    /**
     * Getter da Password do Utilizador
     * @return Password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter da Password do Utilizador
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter do Nome do Utilizador
     * @return Nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Setter do Nome do Utilizador
     * @param nome
     */
    public void setNome(String nome){
        this.nome = nome;
    }

}
