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

@WebServlet(value = "/statistics")
public class StatisticsPage extends HttpServlet {
    private final Logger log = LoggerFactory.getLogger(FirstPage.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("перевод на страниу по адресу /statistics");
        RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/jsp/statistics.jsp");
        dispatcher.forward(req, resp);
    }
}
