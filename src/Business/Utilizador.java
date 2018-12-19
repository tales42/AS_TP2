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


    /*
    * Construtores
    * */

    public Utilizador(){
        this.idUtilizador = 0;
        this.email="";
        this.password="";
        this.nome="";
    }

    public Utilizador(int idUtilizador, String email, String password, String nome){
        this.setIdUtilizador(idUtilizador);
        this.setEmail(email);
        this.setPassword(password);
        this.setNome(nome);
    }

    public Utilizador(Utilizador u){
        this.setIdUtilizador(u.getIdUtilizador());
        this.setEmail(u.getEmail());
        this.setPassword(u.getPassword());
        this.setNome(u.getNome());
    }


    /*
    * Getters e Setters
    * */

    public int getIdUtilizador() {
        return this.idUtilizador;
    }

    public void setIdUtilizador(int idUtilizador){
        this.idUtilizador = idUtilizador;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword() {
        /*
        Crypto crypto = new Crypto();
        final String iv = "0123456789012345";
        final String secretKey = "BetESS";
        return crypto.decrypt(this.password, iv, secretKey);
        */
        return this.password;
    }

    public void setPassword(String password) {
        /*
        Crypto crypto = new Crypto();
        final String iv = "0123456789012345";
        final String secretKey = "BetESS";
        this.password = crypto.encrypt(password, iv, secretKey);
        */
        this.password = password;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    /*
    * Clone, Equals e toString
    * */

    public Utilizador clone(){
        return new Utilizador(this);
    }

    public boolean equals(Object object){
        if(object==this) return true;

        if((object==null) || (object.getClass() != this.getClass())) return false;

        Utilizador utilizador = (Utilizador) object;
        if(utilizador.getIdUtilizador() == this.idUtilizador) return true;
        else return false;
    }


    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("----Utilizador----\n");
        s.append("ID : " + this.getIdUtilizador() + "\n");
        s.append("Email : " + this.getEmail() + "\n");
        s.append("Nome : " + this.getNome() + "\n");
        s.append("------------------\n");
        return s.toString();
    }
}
