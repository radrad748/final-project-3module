package com.javarush.radik.controllers;

import com.javarush.radik.entity.DTO.UserDto;
import com.javarush.radik.entity.User;
import com.javarush.radik.services.ServiceUsers;
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

//перевод на следуйщий вопрос
@WebServlet(value = "/next-question")
public class NextQuestion extends HttpServlet {
    private final Logger log = LoggerFactory.getLogger(NextQuestion.class);
    private final ServiceUsers service = ServiceUsers.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("передаем следуйший вопрос");
        int numberQuestion = (int) req.getSession().getAttribute("numberQuestion");
        String selectedValueStr = req.getParameter("group");
        int answerUser = Integer.parseInt(selectedValueStr);
        int trueAnswer = (int) req.getSession().getAttribute("indexTrueAnswer");
        // если ответ верный плюсуем 1 к результату правельных ответов пользователя в данной игре
        if (answerUser == trueAnswer) userResultIncrement(req.getSession());
        //как доходит до последнего вопроса переводим на страницу с результатами
        //иначе, передаем через сейсию индекс следуйщего вопроса
        if (numberQuestion == 10) {
            bestResultUserChange(req.getSession());
            RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/jsp/finish.jsp");
            dispatcher.forward(req, resp);
        } else {
            numberQuestion++;
            req.getSession().setAttribute("numberQuestion", numberQuestion);
            RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/jsp/play.jsp");
            dispatcher.forward(req, resp);
        }
    }

    private void userResultIncrement(HttpSession session) {
        int countResult = (int) session.getAttribute("correctAnswers");
        countResult++;
        session.setAttribute("correctAnswers", countResult);
    }
    private void bestResultUserChange(HttpSession session) {
        //меняем dto и user +1 к числу сыгранных игр
        log.info("изменения статистики");
        UserDto dto = (UserDto) session.getAttribute("user");
        int count = dto.getCount();
        count++;
        dto.setCount(count);

        User user = service.byFindEmail(dto.getEmail());
        user.setCout(count);

        int correctAnswers = (int) session.getAttribute("correctAnswers");
        if (correctAnswers > dto.getBestResult()) {
            //если результат прошедшей игры самый лучший у пользователя то сохранить этот результат
            dto.setBestResult(correctAnswers);
            user.setBestResult(correctAnswers);
        }
        session.setAttribute("user", dto);
    }
}
