package com.javarush.radik.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

// переход на первую страницу после как пользователь успешно залогинелся
@WebServlet(value = "/first-page")
public class FirstPage extends HttpServlet {
    private final Logger log = LoggerFactory.getLogger(FirstPage.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Переходим на страницу старта после как пользователь успешного залогинелся");
        RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/jsp/head.jsp");
        dispatcher.forward(req, resp);
    }

}
