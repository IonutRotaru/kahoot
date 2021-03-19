package com.example.demo;

import com.example.demo.model.*;
import com.example.demo.model.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class MyController {

    private User currentUser;
    private String currentPassword;
    private int punctaj;
    private Map<Integer, ArrayList<String>> intrebari;

    private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
    private UserService userService = context.getBean(UserService.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        return "index";

    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(Model model) {
        return "homeProfesor";

    }

    @RequestMapping(value = "/createaccount", method = RequestMethod.GET)
    public String createAccount(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }


    @RequestMapping(value = "/saveUser", method = RequestMethod.GET)
    public String saveUser(HttpServletRequest request, HttpServletResponse response, Model model) throws ServletException, IOException {

        String statut = request.getParameter("statut");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        List<User> users = userService.findBy("email", email);
        User user = new User();

        if (users.isEmpty()) {
            int id = userService.getMaxId();
            id++;
            user.setId(id);
            user.setEmail(email);
            user.setPassword(password);

            if (statut != null)
                user.setStatut("profesor");
            else
                user.setStatut("student");

            userService.saveUser(user);
            return "index";
        } else {

            return "registererror";
        }
    }

    @RequestMapping(value = "/Home", method = RequestMethod.GET)
    public String Home(HttpServletRequest request, HttpServletResponse response, Model model) throws ServletException, IOException {

        String email = request.getParameter("user");
        String password = request.getParameter("pass");


        List<User> users = userService.findBy("email", email);


        if (!users.isEmpty()) {
            Integer id = (Integer) userService.returnValueUser("id", "email", email);
            String statut = (String) userService.returnValueUser("statut", "email", email);
            String passwordFound = (String) userService.returnValueUser("password", "email", email);

            if (password.equals(passwordFound)) {
                currentUser = new User();
                currentUser.setStatut(statut);
                currentUser.setId(id);
                currentUser.setPassword(password);
                currentUser.setEmail(email);

                if (statut.equals("profesor")) {
                    model.addAttribute("currentEmail", currentUser.getEmail());
                    return "homeProfesor";
                } else {
                    model.addAttribute("currentEmail", currentUser.getEmail());
                    return "homeStudent";
                }
            } else {
                PrintWriter out = response.getWriter();
                out.println("Wrong password");

                return "index";
            }


        } else {
            PrintWriter out = response.getWriter();
            out.println("User not found");


        }
        return "index";


    }

    @RequestMapping(value = "/createtest", method = RequestMethod.GET)
    public String createTest(HttpServletRequest request, HttpServletResponse response, Model model) throws ServletException, IOException {

        return "createtest";
    }

    @RequestMapping(value = "/loadTest", method = RequestMethod.GET)
    public String loadTest(HttpServletRequest request, HttpServletResponse response, Model model) throws ServletException, IOException {

        String testPass = request.getParameter("quizPass");
        String testName = request.getParameter("quizName");

        String regex = "[+-]?[0-9]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(testPass);

        if (matcher.find() == false) {
            PrintWriter out = response.getWriter();
            out.println("Incorrect password");
            return "createtest";
        }

        String Q1 = request.getParameter("q1");
        String Q2 = request.getParameter("q2");
        String Q3 = request.getParameter("q3");
        String Q4 = request.getParameter("q4");
        String Q5 = request.getParameter("q5");

        String RaspunsCorect1 = request.getParameter("raspunsCorect1");
        String RaspunsCorect2 = request.getParameter("raspunsCorect2");
        String RaspunsCorect3 = request.getParameter("raspunsCorect3");
        String RaspunsCorect4 = request.getParameter("raspunsCorect4");
        String RaspunsCorect5 = request.getParameter("raspunsCorect5");

        String r11 = request.getParameter("r1");
        String r12 = request.getParameter("r2");
        String r13 = request.getParameter("r3");
        String r14 = request.getParameter("r4");

        String r21 = request.getParameter("r21");
        String r22 = request.getParameter("r22");
        String r23 = request.getParameter("r23");
        String r24 = request.getParameter("r24");

        String r31 = request.getParameter("r31");
        String r32 = request.getParameter("r32");
        String r33 = request.getParameter("r33");
        String r34 = request.getParameter("r34");

        String r41 = request.getParameter("r41");
        String r42 = request.getParameter("r42");
        String r43 = request.getParameter("r43");
        String r44 = request.getParameter("r44");

        String r51 = request.getParameter("r51");
        String r52 = request.getParameter("r52");
        String r53 = request.getParameter("r53");
        String r54 = request.getParameter("r54");

        String punctaj1 = request.getParameter("punctaj1");
        String punctaj2 = request.getParameter("punctaj2");
        String punctaj3 = request.getParameter("punctaj3");
        String punctaj4 = request.getParameter("punctaj4");
        String punctaj5 = request.getParameter("punctaj5");

        if (userService.returnValueTest(Integer.parseInt(testPass)) != null) {
            PrintWriter out = response.getWriter();
            out.println("A test with this password already exists");
            return "createtest";
        }

        Test test = new Test(Integer.parseInt(testPass));
        userService.insertTest(test);

        Profesor profesor = new Profesor(currentUser.getId(), testName, Integer.parseInt(testPass));
        userService.insertProfesor(profesor);

        Intrebare i1 = new Intrebare(Integer.parseInt(testPass), 1, Q1, r11, r12, r13, r14, RaspunsCorect1, Integer.parseInt(punctaj1));
        Intrebare i2 = new Intrebare(Integer.parseInt(testPass), 2, Q2, r21, r22, r23, r24, RaspunsCorect2, Integer.parseInt(punctaj2));
        Intrebare i3 = new Intrebare(Integer.parseInt(testPass), 3, Q3, r31, r32, r33, r34, RaspunsCorect3, Integer.parseInt(punctaj3));
        Intrebare i4 = new Intrebare(Integer.parseInt(testPass), 4, Q4, r41, r42, r43, r44, RaspunsCorect4, Integer.parseInt(punctaj4));
        Intrebare i5 = new Intrebare(Integer.parseInt(testPass), 5, Q5, r51, r52, r53, r54, RaspunsCorect5, Integer.parseInt(punctaj5));

        userService.insertIntrebare(i1);
        userService.insertIntrebare(i2);
        userService.insertIntrebare(i3);
        userService.insertIntrebare(i4);
        userService.insertIntrebare(i5);


        return "homeProfesor";
    }

    @RequestMapping(value = "/deletetest", method = RequestMethod.GET)
    public String deleteTest(HttpServletRequest request, HttpServletResponse response, Model model) throws ServletException, IOException {

        String parolatest = request.getParameter("testToDelete");


        String regex = "[+-]?[0-9]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(parolatest);

        if (matcher.find() == false) {
            PrintWriter out = response.getWriter();
            out.println("Incorrect password");
            return "homeProfesor";
        }

        if (userService.getProfessorId(Integer.parseInt(parolatest)) == null || !userService.getProfessorId(Integer.parseInt(parolatest)).equals(currentUser.getId())) {

            PrintWriter out = response.getWriter();
            out.println("Test not found");
            return "homeProfesor";
        }

        userService.deleteTest(Integer.parseInt(parolatest));
        PrintWriter out = response.getWriter();
        out.println("Test successfully deleted");
        return "homeProfesor";
    }

    @RequestMapping(value = "/updatetest", method = RequestMethod.GET)
    public String updateTest(HttpServletRequest request, HttpServletResponse response, Model model) throws ServletException, IOException {

        String parolatest = request.getParameter("testToUpdate");
        String numeTest = userService.returnValueProfessor(parolatest);

        currentPassword = parolatest;

        String regex = "[+-]?[0-9]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(parolatest);

        if (matcher.find() == false) {
            PrintWriter out = response.getWriter();
            out.println("Incorrect password");
            return "homeProfesor";
        }

        if (userService.getProfessorId(Integer.parseInt(parolatest)) == null || !userService.getProfessorId(Integer.parseInt(parolatest)).equals(currentUser.getId())) {

            PrintWriter out = response.getWriter();
            out.println("Test not found");
            return "homeProfesor";
        }

        if (userService.returnValueTest(Integer.parseInt(parolatest)) == null) {
            PrintWriter out = response.getWriter();
            out.println("Test not found");
            return "homeProfesor";
        }

        List<Intrebare> intrebari = userService.findQuestionsBy("parolatest", Integer.parseInt(parolatest));
        List<Object> intrebariObj = new ArrayList<Object>();


        for (int i = 0; i < intrebari.size(); i++) {
            Object o = (Object) intrebari.get(i);
            intrebariObj.add(o);
        }
        model.addAttribute("intrebare", intrebariObj.get(0));
        model.addAttribute("intrebare2", intrebariObj.get(1));
        model.addAttribute("intrebare3", intrebariObj.get(2));
        model.addAttribute("intrebare4", intrebariObj.get(3));
        model.addAttribute("intrebare5", intrebariObj.get(4));
        model.addAttribute("pass", Integer.parseInt(parolatest));
        model.addAttribute("numetest", numeTest);


        return "update";
    }

    @RequestMapping(value = "/updatequestions", method = RequestMethod.GET)
    public String updateQuestions(HttpServletRequest request, HttpServletResponse response, Model model) throws ServletException, IOException {

        String Q1 = request.getParameter("q1");
        String Q2 = request.getParameter("q2");
        String Q3 = request.getParameter("q3");
        String Q4 = request.getParameter("q4");
        String Q5 = request.getParameter("q5");

        String RaspunsCorect1 = request.getParameter("raspunsCorect1");
        String RaspunsCorect2 = request.getParameter("raspunsCorect2");
        String RaspunsCorect3 = request.getParameter("raspunsCorect3");
        String RaspunsCorect4 = request.getParameter("raspunsCorect4");
        String RaspunsCorect5 = request.getParameter("raspunsCorect5");

        String r11 = request.getParameter("r1");
        String r12 = request.getParameter("r2");
        String r13 = request.getParameter("r3");
        String r14 = request.getParameter("r4");

        String r21 = request.getParameter("r21");
        String r22 = request.getParameter("r22");
        String r23 = request.getParameter("r23");
        String r24 = request.getParameter("r24");

        String r31 = request.getParameter("r31");
        String r32 = request.getParameter("r32");
        String r33 = request.getParameter("r33");
        String r34 = request.getParameter("r34");

        String r41 = request.getParameter("r41");
        String r42 = request.getParameter("r42");
        String r43 = request.getParameter("r43");
        String r44 = request.getParameter("r44");

        String r51 = request.getParameter("r51");
        String r52 = request.getParameter("r52");
        String r53 = request.getParameter("r53");
        String r54 = request.getParameter("r54");

        String testPass = request.getParameter("quizPass");
        String testName = request.getParameter("quizName");

        String punctaj1 = request.getParameter("punctaj1");
        String punctaj2 = request.getParameter("punctaj2");
        String punctaj3 = request.getParameter("punctaj3");
        String punctaj4 = request.getParameter("punctaj4");
        String punctaj5 = request.getParameter("punctaj5");


        userService.updateTestPasswordAndName(currentPassword, testPass, testName);
        userService.deleteIntrebare(testPass);

        Intrebare i1 = new Intrebare(Integer.parseInt(testPass), 1, Q1, r11, r12, r13, r14, RaspunsCorect1, Integer.parseInt(punctaj1));
        Intrebare i2 = new Intrebare(Integer.parseInt(testPass), 2, Q2, r21, r22, r23, r24, RaspunsCorect2, Integer.parseInt(punctaj2));
        Intrebare i3 = new Intrebare(Integer.parseInt(testPass), 3, Q3, r31, r32, r33, r34, RaspunsCorect3, Integer.parseInt(punctaj3));
        Intrebare i4 = new Intrebare(Integer.parseInt(testPass), 4, Q4, r41, r42, r43, r44, RaspunsCorect4, Integer.parseInt(punctaj4));
        Intrebare i5 = new Intrebare(Integer.parseInt(testPass), 5, Q5, r51, r52, r53, r54, RaspunsCorect5, Integer.parseInt(punctaj5));


        userService.insertIntrebare(i1);
        userService.insertIntrebare(i2);
        userService.insertIntrebare(i3);
        userService.insertIntrebare(i4);
        userService.insertIntrebare(i5);

        return "homeProfesor";
    }


    @RequestMapping(value = "/rezolvaTest", method = RequestMethod.GET)
    public String rezolvaTest(HttpServletRequest request, HttpServletResponse response, Model model) throws ServletException, IOException {

        String parolatest = request.getParameter("ParolaTest");

        String regex = "[+-]?[0-9]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(parolatest);

        if (matcher.find() == false) {
            PrintWriter out = response.getWriter();
            out.println("Incorrect password");
            return "homeStudent";
        }

        if (userService.returnValueTest(Integer.parseInt(parolatest)) == null) {
            PrintWriter err = response.getWriter();
            err.println("Test not found");
            return "homeStudent";
        }


        currentPassword = parolatest;
        String numeTest = userService.returnValueProfessor(parolatest);

        if (userService.available(currentUser.getId(), Integer.parseInt(currentPassword)) == false) {
            PrintWriter err = response.getWriter();
            err.println("No more attempts are allowed");
            return "homeStudent";
        }

        punctaj = 0;
        intrebari = userService.returnQuestions(Integer.parseInt(currentPassword));
        Integer punctajMaxim = 0;

        for (int i = 1; i <= 5; i++) {
            punctajMaxim = punctajMaxim + Integer.parseInt(intrebari.get(i).get(6));
        }


        Elev elev = new Elev(currentUser.getId(), punctaj, numeTest, Integer.parseInt(currentPassword), punctajMaxim);
        userService.insertElev(elev);


        PrintWriter out = response.getWriter();

        response.setContentType("text/html");






        out.println("<html>\n" + "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"> <style>\n" + " p {\n" + "text-align: center;\n" + "            font-size: 60px;\n" + "            margin-top: 0px;\n" + "        }\n" + "    </style></head>\n" + "<body>");


        int timeout2 = 10;
        HttpSession sessionObj = request.getSession(true);
        out.println(
                "<html lang=\"en\" xmlns:th=\"http://www.thymeleaf.org\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <title>Final</title>\n" +
                        "</head>\n" +
                        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                        "<title>CreateTest</title>\n" +
                        "<style>\n" +
                        "    body {font-family: Arial, Helvetica, sans-serif;}\n" +
                        "    form {border: 0px solid #f1f1f1;}\n" +
                        "\n" +
                        "    input[type=text], input[type=password] {\n" +
                        "        width: 29%;\n" +
                        "        padding: 12px 20px;\n" +
                        "        margin: 8px 0;\n" +
                        "        display: inline-block;\n" +
                        "        border: 2px solid #ccc;\n" +
                        "        border-color: #363437;\n" +
                        "        box-sizing: border-box;\n" +
                        "    }\n" +
                        "\n" +
                        "    button {\n" +
                        "        background-color: #363437;\n" +
                        "        color: white;\n" +
                        "        padding: 14px 20px;\n" +
                        "        margin: 8px 0;\n" +
                        "        border: none;\n" +
                        "        cursor: pointer;\n" +
                        "        width: 10%;\n" +
                        "    }\n" +
                        "\n" +
                        "    button:hover {\n" +
                        "        opacity: 0.8;\n" +
                        "    }\n" +
                        "\n" +
                        "    .cancelbtn {\n" +
                        "        width: auto;\n" +
                        "        padding: 10px 18px;\n" +
                        "        background-color: #f44336;\n" +
                        "    }\n" +
                        "\n" +
                        "    .imgcontainer {\n" +
                        "        text-align: center;\n" +
                        "        margin: 16px 0 8px 0;\n" +
                        "    }\n" +
                        "\n" +
                        "    img.avatar {\n" +
                        "        width: 40%;\n" +
                        "        border-radius: 50%;\n" +
                        "    }\n" +
                        "\n" +
                        "    .container {\n" +
                        "        padding: 16px;\n" +
                        "    }\n" +
                        "\n" +
                        "    span.psw {\n" +
                        "        float: right;\n" +
                        "        padding-top: 16px;\n" +
                        "    }\n" +
                        "\n" +
                        "    /* Change styles for span and cancel button on extra small screens */\n" +
                        "    @media screen and (max-width: 300px) {\n" +
                        "        span.psw {\n" +
                        "            display: block;\n" +
                        "            float: none;\n" +
                        "        }\n" +
                        "        .cancelbtn {\n" +
                        "            width: 100%;\n" +
                        "        }\n" +
                        "    }\n" +
                        "</style>\n" +
                        "<body style=\"background-color:#43CDCD;\">\n" +

                        "<form align = \"right\" >\n" +
                        "    <div class=\"imgcontainer\">\n" +
                        "        <img src=\"kahoot_turcuaz.png\" alt=\"Avatar\" class=\"avatar\">\n" +
                        "    </div>\n" +
                        "</form>\n" +
                        "\n" + "<p id=\"demo\"> </p><script>\n" + "    // Set the date we're counting down to\n" + "\n" + "    var countDownDate = new Date().getTime();\n" + "\n" + "    // Update the count down every 1 second\n" + "    var x = setInterval(function() {\n" + "\n" + "        // Get today's date and time\n" + "        var now = new Date().getTime();\n" + "\n" + "        // Find the distance between now and the count down date\n" + "        var distance =  now - countDownDate;\n" + "\n" + "        // Time calculations for days, hours, minutes and seconds\n" + "\n" + "        var seconds = Math.floor((distance % (1000 * 60)) / 1000);\n" + "\n" + "        // Output the result in an element with id=\"demo\"\n" + "        document.getElementById(\"demo\").innerHTML =10 - seconds + \"s \";\n" + "\n" + "        // If the count down is over, write some text\n" + "        if (distance < 0) {\n" + "            clearInterval(x);\n" + "            document.getElementById(\"demo\").innerHTML = \"EXPIRED\";\n" + "        }\n" + "    }, 1000);\n" + "</script>"
                        +
                        "\n" +
                        "\n" +
                        "            <form action=\"Question2\" method=\"get\">\n" +
                        "                  <button type=\"submit\" value = \"next question\" >next question</button>\n" +
                        "            <h2>" + intrebari.get(1).get(0) + "<h2>\n" +
                        "            <input type=\"radio\" id=\"male\" name=\"q1\" value=\"unu\">\n" +
                        "                      <label for=\"male\"> " + intrebari.get(1).get(1) + "</label><br>\n" +
                        "                      <input type=\"radio\" id=\"female\" name=\"q1\" value=\"doi\">\n" +
                        "                      <label for=\"female\">" + intrebari.get(1).get(2) + "  </label><br>\n" +
                        "                      <input type=\"radio\" id=\"other\" name=\"q1\" value=\"trei\">\n" +
                        "                      <label for=\"other\">" + intrebari.get(1).get(3) + " </label><br>\n" +
                        "                      <input type=\"radio\" id=\"other\" name=\"q1\" value=\"patru\">\n" +
                        "                      <label for=\"other\"> " + intrebari.get(1).get(4) + "</label>\n" +
                        "\n" +

                        "</form>\n" +
                        "</body>\n" +
                        "</html>");


        response.setHeader("Refresh", timeout2 + "; Question2");



        out.close();

        return "Question1";


    }


    @RequestMapping(value = "/Question2", method = RequestMethod.GET)
    public String question2(HttpServletRequest request, HttpServletResponse response, Model model) throws ServletException, IOException {


        String raspuns = request.getParameter("q1");
        if (raspuns != null) {
            System.out.println("intrebare 1 " + intrebari.get(1).get(5));
            if (raspuns.equals("unu") && intrebari.get(1).get(1).equals(intrebari.get(1).get(5))) {

                punctaj = punctaj + Integer.parseInt(intrebari.get(1).get(6));
            }
            if (raspuns.equals("doi") && intrebari.get(1).get(2).equals(intrebari.get(1).get(5))) {

                punctaj = punctaj + Integer.parseInt(intrebari.get(1).get(6));
            }
            if (raspuns.equals("trei") && intrebari.get(1).get(3).equals(intrebari.get(1).get(5))) {

                punctaj = punctaj + Integer.parseInt(intrebari.get(1).get(6));
            }
            if (raspuns.equals("patru") && intrebari.get(1).get(4).equals(intrebari.get(1).get(5))) {

                punctaj = punctaj + Integer.parseInt(intrebari.get(1).get(6));
            }
        }

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        out.println("<html>\n" + "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"> <style>\n" + " p {\n" + "text-align: center;\n" + "            font-size: 60px;\n" + "            margin-top: 0px;\n" + "        }\n" + "    </style></head>\n" + "<body>");
        int timeout2 = 10;
        HttpSession sessionObj = request.getSession(true);
        out.println(
                "<html lang=\"en\" xmlns:th=\"http://www.thymeleaf.org\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <title>Final</title>\n" +
                        "</head>\n" +
                        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                        "<title>CreateTest</title>\n" +
                        "<style>\n" +
                        "    body {font-family: Arial, Helvetica, sans-serif;}\n" +
                        "    form {border: 0px solid #f1f1f1;}\n" +
                        "\n" +
                        "    input[type=text], input[type=password] {\n" +
                        "        width: 29%;\n" +
                        "        padding: 12px 20px;\n" +
                        "        margin: 8px 0;\n" +
                        "        display: inline-block;\n" +
                        "        border: 2px solid #ccc;\n" +
                        "        border-color: #363437;\n" +
                        "        box-sizing: border-box;\n" +
                        "    }\n" +
                        "\n" +
                        "    button {\n" +
                        "        background-color: #363437;\n" +
                        "        color: white;\n" +
                        "        padding: 14px 20px;\n" +
                        "        margin: 8px 0;\n" +
                        "        border: none;\n" +
                        "        cursor: pointer;\n" +
                        "        width: 10%;\n" +
                        "    }\n" +
                        "\n" +
                        "    button:hover {\n" +
                        "        opacity: 0.8;\n" +
                        "    }\n" +
                        "\n" +
                        "    .cancelbtn {\n" +
                        "        width: auto;\n" +
                        "        padding: 10px 18px;\n" +
                        "        background-color: #f44336;\n" +
                        "    }\n" +
                        "\n" +
                        "    .imgcontainer {\n" +
                        "        text-align: center;\n" +
                        "        margin: 16px 0 8px 0;\n" +
                        "    }\n" +
                        "\n" +
                        "    img.avatar {\n" +
                        "        width: 40%;\n" +
                        "        border-radius: 50%;\n" +
                        "    }\n" +
                        "\n" +
                        "    .container {\n" +
                        "        padding: 16px;\n" +
                        "    }\n" +
                        "\n" +
                        "    span.psw {\n" +
                        "        float: right;\n" +
                        "        padding-top: 16px;\n" +
                        "    }\n" +
                        "\n" +
                        "    /* Change styles for span and cancel button on extra small screens */\n" +
                        "    @media screen and (max-width: 300px) {\n" +
                        "        span.psw {\n" +
                        "            display: block;\n" +
                        "            float: none;\n" +
                        "        }\n" +
                        "        .cancelbtn {\n" +
                        "            width: 100%;\n" +
                        "        }\n" +
                        "    }\n" +
                        "</style>\n" +
                        "<body style=\"background-color:#43CDCD;\">\n" +

                        "<form align = \"right\" >\n" +
                        "    <div class=\"imgcontainer\">\n" +
                        "        <img src=\"kahoot_turcuaz.png\" alt=\"Avatar\" class=\"avatar\">\n" +
                        "    </div>\n" +
                        "</form>\n" +
                        "\n" + "<p id=\"demo\"> </p><script>\n" + "    // Set the date we're counting down to\n" + "\n" + "    var countDownDate = new Date().getTime();\n" + "\n" + "    // Update the count down every 1 second\n" + "    var x = setInterval(function() {\n" + "\n" + "        // Get today's date and time\n" + "        var now = new Date().getTime();\n" + "\n" + "        // Find the distance between now and the count down date\n" + "        var distance =  now - countDownDate;\n" + "\n" + "        // Time calculations for days, hours, minutes and seconds\n" + "\n" + "        var seconds = Math.floor((distance % (1000 * 60)) / 1000);\n" + "\n" + "        // Output the result in an element with id=\"demo\"\n" + "        document.getElementById(\"demo\").innerHTML =10 - seconds + \"s \";\n" + "\n" + "        // If the count down is over, write some text\n" + "        if (distance < 0) {\n" + "            clearInterval(x);\n" + "            document.getElementById(\"demo\").innerHTML = \"EXPIRED\";\n" + "        }\n" + "    }, 1000);\n" + "</script>"
                        +
                        "\n" +
                        "\n" +
                        "            <form action=\"Question3\" method=\"get\">\n" +
                        "                  <button type=\"submit\" value = \"next question\" >next question</button>\n" +
                        "            <h2>" + intrebari.get(2).get(0) + "<h2>\n" +
                        "            <input type=\"radio\" id=\"male\" name=\"q2\" value=\"unu\">\n" +
                        "                      <label for=\"male\"> " + intrebari.get(2).get(1) + "</label><br>\n" +
                        "                      <input type=\"radio\" id=\"female\" name=\"q2\" value=\"doi\">\n" +
                        "                      <label for=\"female\">" + intrebari.get(2).get(2) + "  </label><br>\n" +
                        "                      <input type=\"radio\" id=\"other\" name=\"q2\" value=\"trei\">\n" +
                        "                      <label for=\"other\">" + intrebari.get(2).get(3) + " </label><br>\n" +
                        "                      <input type=\"radio\" id=\"other\" name=\"q2\" value=\"patru\">\n" +
                        "                      <label for=\"other\"> " + intrebari.get(2).get(4) + "</label>\n" +
                        "\n" +

                        "</form>\n" +
                        "</body>\n" +
                        "</html>");

        response.setHeader("Refresh", timeout2 + "; /Question3");


        out.println("</body></html>");
        out.close();

        return "Question2";
    }


    @RequestMapping(value = "/Question3", method = RequestMethod.GET)
    public String question3(HttpServletRequest request, HttpServletResponse response, Model model) throws ServletException, IOException {


        String raspuns = request.getParameter("q2");
        if (raspuns != null) {

            if (raspuns.equals("unu") && intrebari.get(2).get(1).equals(intrebari.get(2).get(5))) {

                punctaj = punctaj + Integer.parseInt(intrebari.get(2).get(6));

            }
            if (raspuns.equals("doi") && intrebari.get(2).get(2).equals(intrebari.get(2).get(5))) {

                punctaj = punctaj + Integer.parseInt(intrebari.get(2).get(6));

            }
            if (raspuns.equals("trei") && intrebari.get(2).get(3).equals(intrebari.get(2).get(5))) {

                punctaj = punctaj + Integer.parseInt(intrebari.get(2).get(6));

            }
            if (raspuns.equals("patru") && intrebari.get(2).get(4).equals(intrebari.get(2).get(5))) {

                punctaj = punctaj + Integer.parseInt(intrebari.get(2).get(6));

            }
        }


        PrintWriter out = response.getWriter();

        response.setContentType("text/html");



        String docType = "<!DOCTYPE html>\n";
        out.println(docType + "<html>\n" + "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"> <style>\n" + " p {\n" + "text-align: center;\n" + "            font-size: 60px;\n" + "            margin-top: 0px;\n" + "        }\n" + "    </style></head>\n" + "<body>");

        int timeout2 = 10;
        HttpSession sessionObj = request.getSession(true);
        out.println(
                "<html lang=\"en\" xmlns:th=\"http://www.thymeleaf.org\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <title>Final</title>\n" +
                        "</head>\n" +
                        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                        "<title>CreateTest</title>\n" +
                        "<style>\n" +
                        "    body {font-family: Arial, Helvetica, sans-serif;}\n" +
                        "    form {border: 0px solid #f1f1f1;}\n" +
                        "\n" +
                        "    input[type=text], input[type=password] {\n" +
                        "        width: 29%;\n" +
                        "        padding: 12px 20px;\n" +
                        "        margin: 8px 0;\n" +
                        "        display: inline-block;\n" +
                        "        border: 2px solid #ccc;\n" +
                        "        border-color: #363437;\n" +
                        "        box-sizing: border-box;\n" +
                        "    }\n" +
                        "\n" +
                        "    button {\n" +
                        "        background-color: #363437;\n" +
                        "        color: white;\n" +
                        "        padding: 14px 20px;\n" +
                        "        margin: 8px 0;\n" +
                        "        border: none;\n" +
                        "        cursor: pointer;\n" +
                        "        width: 10%;\n" +
                        "    }\n" +
                        "\n" +
                        "    button:hover {\n" +
                        "        opacity: 0.8;\n" +
                        "    }\n" +
                        "\n" +
                        "    .cancelbtn {\n" +
                        "        width: auto;\n" +
                        "        padding: 10px 18px;\n" +
                        "        background-color: #f44336;\n" +
                        "    }\n" +
                        "\n" +
                        "    .imgcontainer {\n" +
                        "        text-align: center;\n" +
                        "        margin: 16px 0 8px 0;\n" +
                        "    }\n" +
                        "\n" +
                        "    img.avatar {\n" +
                        "        width: 40%;\n" +
                        "        border-radius: 50%;\n" +
                        "    }\n" +
                        "\n" +
                        "    .container {\n" +
                        "        padding: 16px;\n" +
                        "    }\n" +
                        "\n" +
                        "    span.psw {\n" +
                        "        float: right;\n" +
                        "        padding-top: 16px;\n" +
                        "    }\n" +
                        "\n" +
                        "    /* Change styles for span and cancel button on extra small screens */\n" +
                        "    @media screen and (max-width: 300px) {\n" +
                        "        span.psw {\n" +
                        "            display: block;\n" +
                        "            float: none;\n" +
                        "        }\n" +
                        "        .cancelbtn {\n" +
                        "            width: 100%;\n" +
                        "        }\n" +
                        "    }\n" +
                        "</style>\n" +
                        "<body style=\"background-color:#43CDCD;\">\n" +

                        "<form align = \"right\" >\n" +
                        "    <div class=\"imgcontainer\">\n" +
                        "        <img src=\"kahoot_turcuaz.png\" alt=\"Avatar\" class=\"avatar\">\n" +
                        "    </div>\n" +
                        "</form>\n" +
                        "\n" + "<p id=\"demo\"> </p><script>\n" + "    // Set the date we're counting down to\n" + "\n" + "    var countDownDate = new Date().getTime();\n" + "\n" + "    // Update the count down every 1 second\n" + "    var x = setInterval(function() {\n" + "\n" + "        // Get today's date and time\n" + "        var now = new Date().getTime();\n" + "\n" + "        // Find the distance between now and the count down date\n" + "        var distance =  now - countDownDate;\n" + "\n" + "        // Time calculations for days, hours, minutes and seconds\n" + "\n" + "        var seconds = Math.floor((distance % (1000 * 60)) / 1000);\n" + "\n" + "        // Output the result in an element with id=\"demo\"\n" + "        document.getElementById(\"demo\").innerHTML =10 - seconds + \"s \";\n" + "\n" + "        // If the count down is over, write some text\n" + "        if (distance < 0) {\n" + "            clearInterval(x);\n" + "            document.getElementById(\"demo\").innerHTML = \"EXPIRED\";\n" + "        }\n" + "    }, 1000);\n" + "</script>"
                        +
                        "\n" +
                        "\n" +
                        "            <form action=\"Question4\" method=\"get\">\n" +
                        "                  <button type=\"submit\" value = \"next question\" >next question</button>\n" +
                        "            <h2>" + intrebari.get(3).get(0) + "<h2>\n" +
                        "            <input type=\"radio\" id=\"male\" name=\"q3\" value=\"unu\">\n" +
                        "                      <label for=\"male\"> " + intrebari.get(3).get(1) + "</label><br>\n" +
                        "                      <input type=\"radio\" id=\"female\" name=\"q3\" value=\"doi\">\n" +
                        "                      <label for=\"female\">" + intrebari.get(3).get(2) + "  </label><br>\n" +
                        "                      <input type=\"radio\" id=\"other\" name=\"q3\" value=\"trei\">\n" +
                        "                      <label for=\"other\">" + intrebari.get(3).get(3) + " </label><br>\n" +
                        "                      <input type=\"radio\" id=\"other\" name=\"q3\" value=\"patru\">\n" +
                        "                      <label for=\"other\"> " + intrebari.get(3).get(4) + "</label>\n" +
                        "\n" +

                        "</form>\n" +
                        "</body>\n" +
                        "</html>");

        response.setHeader("Refresh", timeout2 + "; /Question4");

        out.println("</body></html>");
        out.close();

        return "Question3";
    }


    @RequestMapping(value = "/Question4", method = RequestMethod.GET)
    public String question4(HttpServletRequest request, HttpServletResponse response, Model model) throws ServletException, IOException {


        String raspuns = request.getParameter("q3");
        if (raspuns != null) {
            if (raspuns.equals("unu") && intrebari.get(3).get(1).equals(intrebari.get(3).get(5))) {

                punctaj = punctaj + Integer.parseInt(intrebari.get(3).get(6));

            }
            if (raspuns.equals("doi") && intrebari.get(3).get(2).equals(intrebari.get(3).get(5))) {

                punctaj = punctaj + Integer.parseInt(intrebari.get(3).get(6));

            }
            if (raspuns.equals("trei") && intrebari.get(3).get(3).equals(intrebari.get(3).get(5))) {

                punctaj = punctaj + Integer.parseInt(intrebari.get(3).get(6));

            }
            if (raspuns.equals("patru") && intrebari.get(3).get(4).equals(intrebari.get(3).get(5))) {

                punctaj = punctaj + Integer.parseInt(intrebari.get(3).get(6));

            }
        }

        PrintWriter out = response.getWriter();

        response.setContentType("text/html");

        String docType = "<!DOCTYPE html>\n";
        out.println(docType + "<html>\n" + "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"> <style>\n" + " p {\n" + "text-align: center;\n" + "            font-size: 60px;\n" + "            margin-top: 0px;\n" + "        }\n" + "    </style></head>\n" + "<body>");

        int timeout2 = 10;
        HttpSession sessionObj = request.getSession(true);
        out.println(
                "<html lang=\"en\" xmlns:th=\"http://www.thymeleaf.org\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <title>Final</title>\n" +
                        "</head>\n" +
                        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                        "<title>CreateTest</title>\n" +
                        "<style>\n" +
                        "    body {font-family: Arial, Helvetica, sans-serif;}\n" +
                        "    form {border: 0px solid #f1f1f1;}\n" +
                        "\n" +
                        "    input[type=text], input[type=password] {\n" +
                        "        width: 29%;\n" +
                        "        padding: 12px 20px;\n" +
                        "        margin: 8px 0;\n" +
                        "        display: inline-block;\n" +
                        "        border: 2px solid #ccc;\n" +
                        "        border-color: #363437;\n" +
                        "        box-sizing: border-box;\n" +
                        "    }\n" +
                        "\n" +
                        "    button {\n" +
                        "        background-color: #363437;\n" +
                        "        color: white;\n" +
                        "        padding: 14px 20px;\n" +
                        "        margin: 8px 0;\n" +
                        "        border: none;\n" +
                        "        cursor: pointer;\n" +
                        "        width: 10%;\n" +
                        "    }\n" +
                        "\n" +
                        "    button:hover {\n" +
                        "        opacity: 0.8;\n" +
                        "    }\n" +
                        "\n" +
                        "    .cancelbtn {\n" +
                        "        width: auto;\n" +
                        "        padding: 10px 18px;\n" +
                        "        background-color: #f44336;\n" +
                        "    }\n" +
                        "\n" +
                        "    .imgcontainer {\n" +
                        "        text-align: center;\n" +
                        "        margin: 16px 0 8px 0;\n" +
                        "    }\n" +
                        "\n" +
                        "    img.avatar {\n" +
                        "        width: 40%;\n" +
                        "        border-radius: 50%;\n" +
                        "    }\n" +
                        "\n" +
                        "    .container {\n" +
                        "        padding: 16px;\n" +
                        "    }\n" +
                        "\n" +
                        "    span.psw {\n" +
                        "        float: right;\n" +
                        "        padding-top: 16px;\n" +
                        "    }\n" +
                        "\n" +
                        "    /* Change styles for span and cancel button on extra small screens */\n" +
                        "    @media screen and (max-width: 300px) {\n" +
                        "        span.psw {\n" +
                        "            display: block;\n" +
                        "            float: none;\n" +
                        "        }\n" +
                        "        .cancelbtn {\n" +
                        "            width: 100%;\n" +
                        "        }\n" +
                        "    }\n" +
                        "</style>\n" +
                        "<body style=\"background-color:#43CDCD;\">\n" +

                        "<form align = \"right\" >\n" +
                        "    <div class=\"imgcontainer\">\n" +
                        "        <img src=\"kahoot_turcuaz.png\" alt=\"Avatar\" class=\"avatar\">\n" +
                        "    </div>\n" +
                        "</form>\n" +
                        "\n" + "<p id=\"demo\"> </p><script>\n" + "    // Set the date we're counting down to\n" + "\n" + "    var countDownDate = new Date().getTime();\n" + "\n" + "    // Update the count down every 1 second\n" + "    var x = setInterval(function() {\n" + "\n" + "        // Get today's date and time\n" + "        var now = new Date().getTime();\n" + "\n" + "        // Find the distance between now and the count down date\n" + "        var distance =  now - countDownDate;\n" + "\n" + "        // Time calculations for days, hours, minutes and seconds\n" + "\n" + "        var seconds = Math.floor((distance % (1000 * 60)) / 1000);\n" + "\n" + "        // Output the result in an element with id=\"demo\"\n" + "        document.getElementById(\"demo\").innerHTML =10 - seconds + \"s \";\n" + "\n" + "        // If the count down is over, write some text\n" + "        if (distance < 0) {\n" + "            clearInterval(x);\n" + "            document.getElementById(\"demo\").innerHTML = \"EXPIRED\";\n" + "        }\n" + "    }, 1000);\n" + "</script>"
                        +
                        "\n" +
                        "\n" +
                        "            <form action=\"Question5\" method=\"get\">\n" +
                        "                  <button type=\"submit\" value = \"next question\" >next question</button>\n" +
                        "            <h2>" + intrebari.get(4).get(0) + "<h2>\n" +
                        "            <input type=\"radio\" id=\"male\" name=\"q4\" value=\"unu\">\n" +
                        "                      <label for=\"male\"> " + intrebari.get(4).get(1) + "</label><br>\n" +
                        "                      <input type=\"radio\" id=\"female\" name=\"q4\" value=\"doi\">\n" +
                        "                      <label for=\"female\">" + intrebari.get(4).get(2) + "  </label><br>\n" +
                        "                      <input type=\"radio\" id=\"other\" name=\"q4\" value=\"trei\">\n" +
                        "                      <label for=\"other\">" + intrebari.get(4).get(3) + " </label><br>\n" +
                        "                      <input type=\"radio\" id=\"other\" name=\"q4\" value=\"patru\">\n" +
                        "                      <label for=\"other\"> " + intrebari.get(4).get(4) + "</label>\n" +
                        "\n" +
                        "</form>\n" +
                        "</body>\n" +
                        "</html>");

        response.setHeader("Refresh", timeout2 + "; /Question5");
        out.println("</body></html>");
        out.close();

        return "Question2";
    }


    @RequestMapping(value = "/Question5", method = RequestMethod.GET)
    public String question5(HttpServletRequest request, HttpServletResponse response, Model model) throws ServletException, IOException {


        String raspuns = request.getParameter("q4");
        if (raspuns != null) {
            if (raspuns.equals("unu") && intrebari.get(4).get(1).equals(intrebari.get(4).get(5))) {

                punctaj = punctaj + Integer.parseInt(intrebari.get(4).get(6));

            }
            if (raspuns.equals("doi") && intrebari.get(4).get(2).equals(intrebari.get(4).get(5))) {

                punctaj = punctaj + Integer.parseInt(intrebari.get(4).get(6));

            }
            if (raspuns.equals("trei") && intrebari.get(4).get(3).equals(intrebari.get(4).get(5))) {

                punctaj = punctaj + Integer.parseInt(intrebari.get(4).get(6));

            }
            if (raspuns.equals("patru") && intrebari.get(4).get(4).equals(intrebari.get(4).get(5))) {

                punctaj = punctaj + Integer.parseInt(intrebari.get(4).get(6));

            }
        }


        PrintWriter out = response.getWriter();

        response.setContentType("text/html");



        String docType = "<!DOCTYPE html>\n";
        out.println(docType + "<html>\n" + "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"> <style>\n" + " p {\n" + "text-align: center;\n" + "            font-size: 60px;\n" + "            margin-top: 0px;\n" + "        }\n" + "    </style></head>\n" + "<body>");



        int timeout2 = 10;
        HttpSession sessionObj = request.getSession(true);
        out.println(
                "<html lang=\"en\" xmlns:th=\"http://www.thymeleaf.org\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <title>Final</title>\n" +
                        "</head>\n" +
                        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                        "<title>CreateTest</title>\n" +
                        "<style>\n" +
                        "    body {font-family: Arial, Helvetica, sans-serif;}\n" +
                        "    form {border: 0px solid #f1f1f1;}\n" +
                        "\n" +
                        "    input[type=text], input[type=password] {\n" +
                        "        width: 29%;\n" +
                        "        padding: 12px 20px;\n" +
                        "        margin: 8px 0;\n" +
                        "        display: inline-block;\n" +
                        "        border: 2px solid #ccc;\n" +
                        "        border-color: #363437;\n" +
                        "        box-sizing: border-box;\n" +
                        "    }\n" +
                        "\n" +
                        "    button {\n" +
                        "        background-color: #363437;\n" +
                        "        color: white;\n" +
                        "        padding: 14px 20px;\n" +
                        "        margin: 8px 0;\n" +
                        "        border: none;\n" +
                        "        cursor: pointer;\n" +
                        "        width: 10%;\n" +
                        "    }\n" +
                        "\n" +
                        "    button:hover {\n" +
                        "        opacity: 0.8;\n" +
                        "    }\n" +
                        "\n" +
                        "    .cancelbtn {\n" +
                        "        width: auto;\n" +
                        "        padding: 10px 18px;\n" +
                        "        background-color: #f44336;\n" +
                        "    }\n" +
                        "\n" +
                        "    .imgcontainer {\n" +
                        "        text-align: center;\n" +
                        "        margin: 16px 0 8px 0;\n" +
                        "    }\n" +
                        "\n" +
                        "    img.avatar {\n" +
                        "        width: 40%;\n" +
                        "        border-radius: 50%;\n" +
                        "    }\n" +
                        "\n" +
                        "    .container {\n" +
                        "        padding: 16px;\n" +
                        "    }\n" +
                        "\n" +
                        "    span.psw {\n" +
                        "        float: right;\n" +
                        "        padding-top: 16px;\n" +
                        "    }\n" +
                        "\n" +
                        "    /* Change styles for span and cancel button on extra small screens */\n" +
                        "    @media screen and (max-width: 300px) {\n" +
                        "        span.psw {\n" +
                        "            display: block;\n" +
                        "            float: none;\n" +
                        "        }\n" +
                        "        .cancelbtn {\n" +
                        "            width: 100%;\n" +
                        "        }\n" +
                        "    }\n" +
                        "</style>\n" +
                        "<body style=\"background-color:#43CDCD;\">\n" +

                        "<form align = \"right\" >\n" +
                        "    <div class=\"imgcontainer\">\n" +
                        "        <img src=\"kahoot_turcuaz.png\" alt=\"Avatar\" class=\"avatar\">\n" +
                        "    </div>\n" +
                        "</form>\n" +
                        "\n" + "<p id=\"demo\"> </p><script>\n" + "    // Set the date we're counting down to\n" + "\n" + "    var countDownDate = new Date().getTime();\n" + "\n" + "    // Update the count down every 1 second\n" + "    var x = setInterval(function() {\n" + "\n" + "        // Get today's date and time\n" + "        var now = new Date().getTime();\n" + "\n" + "        // Find the distance between now and the count down date\n" + "        var distance =  now - countDownDate;\n" + "\n" + "        // Time calculations for days, hours, minutes and seconds\n" + "\n" + "        var seconds = Math.floor((distance % (1000 * 60)) / 1000);\n" + "\n" + "        // Output the result in an element with id=\"demo\"\n" + "        document.getElementById(\"demo\").innerHTML =10 - seconds + \"s \";\n" + "\n" + "        // If the count down is over, write some text\n" + "        if (distance < 0) {\n" + "            clearInterval(x);\n" + "            document.getElementById(\"demo\").innerHTML = \"EXPIRED\";\n" + "        }\n" + "    }, 1000);\n" + "</script>"
                        +
                        "\n" +
                        "\n" +
                        "            <form action=\"Final\" method=\"get\">\n" +
                        "                  <button type=\"submit\" value = \"next question\" >next question</button>\n" +
                        "            <h2>" + intrebari.get(5).get(0) + "<h2>\n" +
                        "            <input type=\"radio\" id=\"male\" name=\"q5\" value=\"unu\">\n" +
                        "                      <label for=\"male\"> " + intrebari.get(5).get(1) + "</label><br>\n" +
                        "                      <input type=\"radio\" id=\"female\" name=\"q5\" value=\"doi\">\n" +
                        "                      <label for=\"female\">" + intrebari.get(5).get(2) + "  </label><br>\n" +
                        "                      <input type=\"radio\" id=\"other\" name=\"q5\" value=\"trei\">\n" +
                        "                      <label for=\"other\">" + intrebari.get(5).get(3) + " </label><br>\n" +
                        "                      <input type=\"radio\" id=\"other\" name=\"q5\" value=\"patru\">\n" +
                        "                      <label for=\"other\"> " + intrebari.get(5).get(4) + "</label>\n" +
                        "\n" +
                        "</form>\n" +
                        "</body>\n" +
                        "</html>");

        response.setHeader("Refresh", timeout2 + "; /Final");
        out.println("</body></html>");
        out.close();

        return "Question2";
    }


    @RequestMapping(value = "/Final", method = RequestMethod.GET)
    public String finalTest(HttpServletRequest request, HttpServletResponse response, Model model) {
        String raspuns = request.getParameter("q5");

        if (raspuns != null) {
            if (raspuns.equals("unu") && intrebari.get(5).get(1).equals(intrebari.get(5).get(5))) {

                punctaj = punctaj + Integer.parseInt(intrebari.get(5).get(6));

            }
            if (raspuns.equals("doi") && intrebari.get(5).get(2).equals(intrebari.get(5).get(5))) {

                punctaj = punctaj + Integer.parseInt(intrebari.get(5).get(6));


            }
            if (raspuns.equals("trei") && intrebari.get(5).get(3).equals(intrebari.get(5).get(5))) {

                punctaj = punctaj + Integer.parseInt(intrebari.get(5).get(6));

            }
            if (raspuns.equals("patru") && intrebari.get(5).get(4).equals(intrebari.get(5).get(5))) {

                punctaj = punctaj + Integer.parseInt(intrebari.get(5).get(6));


            }
        }
        System.out.println("punctaj " + punctaj);
        String numeTest = userService.returnValueProfessor(currentPassword);
        userService.updatePunctaj(numeTest, punctaj);
        Integer puncte = new Integer(punctaj);
        model.addAttribute("punctaj", puncte);


        return "Final";
    }

    @RequestMapping(value = "/homeStudent", method = RequestMethod.GET)
    public String homeStudent(Model model) {
        model.addAttribute("currentEmail", currentUser.getEmail());
        return "homeStudent";
    }

    @RequestMapping(value = "/homeProfesor", method = RequestMethod.GET)
    public String homeProfesor(Model model) {
        model.addAttribute("currentEmail", currentUser.getEmail());
        return "homeProfesor";
    }


    @RequestMapping(value = "/veziNote", method = RequestMethod.GET)
    public String veziNote(Model model) {
        List<Elev> elevi = userService.myMarks(currentUser.getId());
        List<Object> eleviObj = new ArrayList<Object>();

        for (int i = 0; i < elevi.size(); i++) {
            Object obj = (Object) elevi.get(i);
            eleviObj.add(obj);
        }
        model.addAttribute("elevi", eleviObj);
        return "veziNote";
    }


}
