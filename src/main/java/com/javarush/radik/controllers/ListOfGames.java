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

@WebServlet(value = "/list-of-game")
public class ListOfGames extends HttpServlet {
    private final Logger log = LoggerFactory.getLogger(ListOfGames.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("перевод на страниу по адресу /list-of-game");
        RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/jsp/list-of-game.jsp");
        dispatcher.forward(req, resp);
    }
}
