package com.example.demo.data;

import com.example.demo.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO extends AbstractDAO<User>  {

    public int maxId() {//obtine id-ul maxim din tabela
        int id=0;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String query = queryMaxValue("id");
        try {
            connection = ConnectionClass.getConnection();//se face conexiunea
            statement = connection.prepareStatement(query);
            rs=statement.executeQuery();//se executa
            while(rs.next()) {
                id=rs.getInt("max(id)");//se returneaza valoarea de pe coloana idClient
                return id;
            }
        }catch(SQLException e) {

        }


        return id;//returneaza 0 daca nu exista niciun element in tabel

    }

}
