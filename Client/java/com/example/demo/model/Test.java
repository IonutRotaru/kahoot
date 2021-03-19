package com.example.demo.model;

import java.io.Serializable;

public class Test implements Serializable {

    private Integer parolatest;

    public Test(){

    }

    public Test(Integer parolatest) {
        this.parolatest = parolatest;
    }

    public Integer getParolatest() {
        return parolatest;
    }

    public void setParolatest(Integer parolatest) {
        this.parolatest = parolatest;
    }


}
