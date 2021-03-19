package com.example.demo.service;

import com.example.demo.data.*;
import com.example.demo.model.*;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    UserDAO userDAO = new UserDAO();
    TestDAO testDAO = new TestDAO();
    ProfesorDAO profesorDAO = new ProfesorDAO();
    IntrebareDAO intrebareDAO = new IntrebareDAO();
    ElevDAO elevDAO = new ElevDAO();

    public String findEmail(int id){
        List<User> user = userDAO.findBy("id",id);
        User u = user.get(0);
        return u.getEmail();
    }

    @Override
    public List<User> findBy(String field, Object value) {

        return userDAO.findBy(field,value);
    }

    //@Override
    public List<User> findAllUsers() {
        return userDAO.listAll();
    }

    @Override
    public Integer getMaxId() {
        return userDAO.maxId();
    }

    @Override
    public void saveUser(User user) {
        userDAO.insert(user);
    }

    @Override
    public Object returnValueUser(String field1, String field2, Object value) {

        List<User> users = userDAO.findBy(field2,value);
        if(users.isEmpty())
            return null;

        User u = users.get(0);

        if(field1.equals("id"))
            return u.getId();

        if(field1.equals("email"))
            return u.getEmail();

        if(field1.equals("statut"))
            return u.getStatut();

        if(field1.equals("password"))
            return u.getPassword();

        return null;
    }

    @Override
    public Object returnValueTest( Object value) {

        List<Test> tests = testDAO.findBy("parolatest",value);
        System.out.println(value);
        if(tests.isEmpty())
            return null;
        Test t = tests.get(0);
        return t.getParolatest();
    }

    @Override
    public String returnValueProfessor(String value) {

       List<Profesor> professors = profesorDAO.findBy("parolatest",value);
       if(professors.size() == 0)
       {
           return null;
       }
       return professors.get(0).getNumetest();
    }

    @Override
    public void insertTest(Test test) {
        testDAO.insert(test);
    }

    @Override
    public void insertProfesor(Profesor profesor) {
        profesorDAO.insert(profesor);
    }

    @Override
    public void insertIntrebare(Intrebare intrebare) {

            intrebareDAO.insert(intrebare);
    }

    @Override
    public void deleteTest(Object parolatest) {
        System.out.println(parolatest);

        testDAO.delete("parolatest",parolatest);
    }

    @Override
    public List<Intrebare> findQuestionsBy(String field, Integer value) {

        List<Intrebare> questions = intrebareDAO.getIntrebari(value);

        return questions;
    }

    @Override
    public void updateTestPasswordAndName(String old, String newPass, String newName) {
        testDAO.update("parolatest","parolatest",newPass,old);
        profesorDAO.update("numetest","parolatest",newName,newPass);
    }

    @Override
    public void deleteIntrebare(String parola) {
        intrebareDAO.delete("parolatest",parola);
    }

    @Override
    public Integer getProfessorId(Integer parolatest) {
       List<Profesor> professors= profesorDAO.findBy("parolatest",parolatest);
       if(professors.isEmpty()){

           return null;
       }
       return professors.get(0).getId();
    }

    @Override
    public void insertElev(Elev elev) {
        elevDAO.insert(elev);
    }

    @Override
    public Map<Integer, ArrayList<String>> returnQuestions(Integer parola) {
        Map<Integer, ArrayList<String>> questions = new HashMap<Integer, ArrayList<String>>();

        List<Intrebare> allQuestions = intrebareDAO.getIntrebari(parola);

        for(int i = 0; i<allQuestions.size(); i++){
            ArrayList<String> question = new ArrayList<String>();
            question.add(allQuestions.get(i).getTextintrebare());
            question.add(allQuestions.get(i).getRaspuns1());
            question.add(allQuestions.get(i).getRaspuns2());
            question.add(allQuestions.get(i).getRaspuns3());
            question.add(allQuestions.get(i).getRaspuns4());
            question.add(allQuestions.get(i).getRaspunscorect());
            question.add(allQuestions.get(i).getPunctaj()+"");

            questions.put(i+1, question);
        }



        return questions;
    }

    @Override
    public void updatePunctaj(String numeTest, Integer punctaj) {
        elevDAO.update("nota","numetest",punctaj, numeTest);

    }

    @Override
    public boolean available(Integer id, Integer parolatest) {
        List<Elev> elevi = elevDAO.findBy("parolatest",parolatest);
        for(int i=0; i<elevi.size(); i++){
            if(elevi.get(i).getId().equals(id)){
                return false;
            }
        }
        return true;
    }

    @Override
    public List<Elev> myMarks(Integer id) {
        List<Elev> elevi = elevDAO.findBy("id",id);
        return elevi;
    }


}
