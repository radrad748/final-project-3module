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

//кнопка старт для начало игры, можно было обойтись без этой страницы но мне захотелось чтоб игра начиналась так
@WebServlet(value = "/start")
public class Start extends HttpServlet {
    private final Logger log = LoggerFactory.getLogger(Start.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("перевод на страниу начало игры по адресу /start");
        RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/jsp/head.jsp");
        dispatcher.forward(req, resp);
    }
}
