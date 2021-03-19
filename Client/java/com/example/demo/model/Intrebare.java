package com.example.demo.model;

import java.io.Serializable;

public class Intrebare implements Serializable {
    private Integer parolatest;
    private Integer nrintrebare;
    private String textintrebare;
    private String raspuns1;
    private String raspuns2;
    private String raspuns3;
    private String raspuns4;
    private String raspunscorect;
    private Integer punctaj;

    public Intrebare(){

    }

    public Intrebare(Integer parolatest, Integer nrintrebare, String textintrebare, String raspuns1, String raspuns2, String raspuns3, String raspuns4, String raspunscorect, Integer punctaj) {
        this.parolatest = parolatest;
        this.nrintrebare = nrintrebare;
        this.textintrebare = textintrebare;
        this.raspuns1 = raspuns1;
        this.raspuns2 = raspuns2;
        this.raspuns3 = raspuns3;
        this.raspuns4 = raspuns4;
        this.raspunscorect = raspunscorect;
        this.punctaj = punctaj;
    }

    public Integer getParolatest() {
        return parolatest;
    }

    public void setParolatest(Integer parolatest) {
        this.parolatest = parolatest;
    }

    public Integer getNrintrebare() {
        return nrintrebare;
    }

    public void setNrintrebare(Integer nrintrebare) {
        this.nrintrebare = nrintrebare;
    }

    public String getTextintrebare() {
        return textintrebare;
    }

    public void setTextintrebare(String textintrebare) {
        this.textintrebare = textintrebare;
    }

    public String getRaspuns1() {
        return raspuns1;
    }

    public void setRaspuns1(String raspuns1) {
        this.raspuns1 = raspuns1;
    }

    public String getRaspuns2() {
        return raspuns2;
    }

    public void setRaspuns2(String raspuns2) {
        this.raspuns2 = raspuns2;
    }

    public String getRaspuns3() {
        return raspuns3;
    }

    public void setRaspuns3(String raspuns3) {
        this.raspuns3 = raspuns3;
    }

    public String getRaspuns4() {
        return raspuns4;
    }

    public void setRaspuns4(String raspuns4) {
        this.raspuns4 = raspuns4;
    }

    public String getRaspunscorect() {
        return raspunscorect;
    }

    public void setRaspunscorect(String raspunscorect) {
        this.raspunscorect = raspunscorect;
    }

    public Integer getPunctaj() {
        return punctaj;
    }

    public void setPunctaj(Integer punctaj) {
        this.punctaj = punctaj;
    }

    @Override
    public String toString() {
        return "Intrebare{" +
                "parolatest=" + parolatest +
                ", nrintrebare=" + nrintrebare +
                ", textintrebare='" + textintrebare + '\'' +
                ", raspuns1='" + raspuns1 + '\'' +
                ", raspuns2='" + raspuns2 + '\'' +
                ", raspuns3='" + raspuns3 + '\'' +
                ", raspuns4='" + raspuns4 + '\'' +
                ", raspunscorect='" + raspunscorect + '\'' +
                ", punctaj=" + punctaj +
                '}';
    }
}
