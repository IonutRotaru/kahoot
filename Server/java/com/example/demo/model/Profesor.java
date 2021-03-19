package com.example.demo.model;

import java.io.Serializable;

public class Profesor implements Serializable {
    private Integer id;
    private String numetest;
    private Integer parolatest;

    public Profesor(){

    }

    public Profesor(Integer id, String numetest, Integer parolatest) {

        this.id = id;
        this.numetest = numetest;
        this.parolatest = parolatest;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumetest() {
        return numetest;
    }

    public void setNumetest(String numetest) {
        this.numetest = numetest;
    }

    public Integer getParolatest() {
        return parolatest;
    }

    public void setParolatest(Integer parolatest) {
        this.parolatest = parolatest;
    }
}
