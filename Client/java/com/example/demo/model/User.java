package com.example.demo.model;


import java.io.Serializable;

//@Entity
public class User implements Serializable {

    //@Id
   // @GeneratedValue
    public Integer id=0;

    public String email;
    public String password;
    private String statut;

    public User(){

    }

    public User(Integer idUser,String email, String password, String statut){
        this.email = email;
        this.password=password;
        this.id=idUser;
        this.statut=statut;

    }

    public User(String email, String password){
        this.email = email;
        this.password=password;


    }

    public String getEmail(){
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer idUser) {
        this.id = idUser;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}
