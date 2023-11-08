package com.javarush.radik.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;

// тут мы принимаем ответ на вопрос пользователя и проверям если ответ верный, после отправляем результат
@WebServlet(value = "/accept-answer")
public class AcceptAnswer extends HttpServlet {
    private final Logger log = LoggerFactory.getLogger(AcceptAnswer.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("По адресу /accept-answer?selectedValue=NumberAnswer, пришел ответ и идет обработка ответа");
        String selectedValueStr = req.getParameter("selectedValue");
        int indexAnswerUser = Integer.parseInt(selectedValueStr);
        int indexRightAnswer = (int) req.getSession().getAttribute("indexTrueAnswer");
        // проверка если совпадает индекс ответа пользователя с индексом правельного ответа на вопрос
        if (indexAnswerUser == indexRightAnswer) {
            sendTrueAnswer(req.getSession(), resp);
        } else {
            sendWrongAnswer(req.getSession(), resp, indexAnswerUser);
        }
    }

    private void sendTrueAnswer(HttpSession session, HttpServletResponse resp) throws IOException {
        log.info("ответ пользователя является верным, отправляем json формат с фтрибутами 'answerIs', 'response', 'trueAnswer'");
        try (PrintWriter out = resp.getWriter()){
            JSONObject json = new JSONObject();
            json.put("answerIs", true);
            json.put("response", session.getAttribute("trueAnswer"));
            json.put("trueAnswer", session.getAttribute("indexTrueAnswer"));
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            out.write(json.toString());
        } catch (Exception e) {
            log.error("в методе sendTrueAnswer(HttpSession session, HttpServletResponse resp) при отправке ответа ошибка");
            throw e;
        }
    }

    private void sendWrongAnswer(HttpSession session, HttpServletResponse resp, int userAnswer) throws IOException {
        log.info("ответ пользователя является неверным, отправляем json формат с фтрибутами 'answerIs', 'response', 'indexTrueAnswer'");
        try (PrintWriter out = resp.getWriter()){
            JSONObject json = new JSONObject();
            json.put("answerIs", false);
            json.put("response", session.getAttribute("wrongAnswer"));
            json.put("trueAnswer", session.getAttribute("indexTrueAnswer"));
            json.put("wrongAnswer", userAnswer);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            out.write(json.toString());
        } catch (Exception e) {
            log.error("в методе sendWrongAnswer(HttpSession session, HttpServletResponse resp) при отправке ответа ошибка");
            throw e;
        }
    }
}
