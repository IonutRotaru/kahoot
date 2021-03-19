package com.example.demo.model;

import java.io.Serializable;

public class Elev implements Serializable {

    private Integer id;
    private Integer nota;
    private String numetest;
    private Integer parolatest;
    private Integer punctajmaxim;

    public Elev(Integer id, Integer nota, String numetest, Integer parolatest, Integer punctajmaxim) {
        this.id = id;
        this.nota = nota;
        this.numetest = numetest;
        this.parolatest = parolatest;
        this.punctajmaxim = punctajmaxim;
    }

    public Elev(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
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

    public Integer getPunctajmaxim() {
        return punctajmaxim;
    }

    public void setPunctajmaxim(Integer punctajmaxim) {
        this.punctajmaxim = punctajmaxim;
    }

}
