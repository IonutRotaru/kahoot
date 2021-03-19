package com.example.demo.data;

import com.example.demo.model.Intrebare;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IntrebareDAO extends AbstractDAO<Intrebare> {


    public List<Intrebare> getIntrebari(Integer parola) {
        String query = "Select * from intrebare where parolatest = " + parola;
        Connection connection = null;
        List<Intrebare> intrebari = new ArrayList<>();
        ResultSet rs;
        try {
            connection = ConnectionClass.getConnection();//se face conexiunea la baza de date
            PreparedStatement statement = connection.prepareStatement(query); //executarea queryului
            rs = statement.executeQuery();
            intrebari = createList(rs);

        } catch (SQLException e) {

        }
        return intrebari;
    }

    private List<Intrebare> createList(ResultSet rs) {

        List<Intrebare> intrebari = new ArrayList<>();

        try {
            while (rs.next()) {
                Intrebare i = new Intrebare();

                i.setParolatest(rs.getInt("parolatest"));
                i.setNrintrebare(rs.getInt("nrintrebare"));
                i.setTextintrebare(rs.getString("textintrebare"));
                i.setRaspuns1(rs.getString("raspuns1"));
                i.setRaspuns2(rs.getString("raspuns2"));
                i.setRaspuns3(rs.getString("raspuns3"));
                i.setRaspuns4(rs.getString("raspuns4"));
                i.setRaspunscorect(rs.getString("raspunscorect"));

                i.setPunctaj(rs.getInt("punctaj"));
                intrebari.add(i);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println(intrebari);
        return intrebari;
    }
}
