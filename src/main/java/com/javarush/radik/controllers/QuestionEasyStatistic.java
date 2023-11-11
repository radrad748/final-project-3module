package com.javarush.radik.controllers;

import com.javarush.radik.entity.DTO.UserDto;
import com.javarush.radik.entity.QuestionLevel;
import com.javarush.radik.entity.ResultQuestionsGame;
import com.javarush.radik.services.ServiceUserResultQuestions;
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

@WebServlet(value = "/statistic-questions-easy")
public class QuestionEasyStatistic extends HttpServlet {
    private final Logger log = LoggerFactory.getLogger(QuestionEasyStatistic.class);
    private final ServiceUserResultQuestions results = ServiceUserResultQuestions.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Показать статистику игры - вопросы: легкий уровень, перевод на стриницу по адресу /statistic-questions-easy");
        HttpSession session = req.getSession();
        UserDto user = (UserDto) session.getAttribute("user");

        ResultQuestionsGame result = results.getUserResultLevelEasy(user.getId());
        if (result == null) {
            result = new ResultQuestionsGame(user.getId(), QuestionLevel.EASY);
            results.create(result);
            session.setAttribute("result", result);
        } else {
            session.setAttribute("result", result);
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/jsp/statistics.jsp");
        dispatcher.forward(req, resp);
    }
}
