package com.javarush.radik.controllers;

import com.javarush.radik.entity.Question;
import com.javarush.radik.services.ServiceQuestions;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

//перевод страницы где начало игры
@WebServlet(value = "/play")
public class PlayPage extends HttpServlet {
    private final Logger log = LoggerFactory.getLogger(PlayPage.class);
    private final ServiceQuestions questions = ServiceQuestions.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Переход на страничку начало игры");
        List<Question> questionsList = questions.getTenQuestionsEasy();

        HttpSession session = req.getSession();
        session.setAttribute("questions", questionsList);
        session.setAttribute("numberQuestion", 1);
        session.setAttribute("correctAnswers", 0);


        RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/jsp/play.jsp");
        dispatcher.forward(req, resp);
    }
}
