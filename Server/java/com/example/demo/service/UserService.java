package com.example.demo.service;

import com.example.demo.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface UserService {
   // String findEmail(int id);
    List<User> findBy(String field, Object value);
   // List<User> findAllUsers();
    Integer getMaxId();
    void saveUser(User user);
    Object returnValueUser(String field1,String field2, Object value);
    Object returnValueTest( Object value);
    String returnValueProfessor(String value);
    void insertTest(Test test);
    void insertProfesor(Profesor profesor);
    void insertIntrebare(Intrebare intrebare);
    void deleteTest(Object parolatest);
    List<Intrebare> findQuestionsBy(String field, Integer value);
    void updateTestPasswordAndName(String old, String newPass,String newName);
    void deleteIntrebare(String parola);
    Integer getProfessorId(Integer parolatest);
    void insertElev(Elev elev);
    Map<Integer, ArrayList<String>> returnQuestions(Integer parola);
    void updatePunctaj(String numeTest, Integer punctaj);
    boolean available(Integer id, Integer parolatest);
    List<Elev> myMarks(Integer id);
}

