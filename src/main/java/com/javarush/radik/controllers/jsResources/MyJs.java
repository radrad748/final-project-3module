package com.javarush.radik.controllers.jsResources;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

//считываем js файл my-js.js

@WebServlet(value = "/my-js")
public class MyJs extends HttpServlet {
    private final Logger log = LoggerFactory.getLogger(MyJs.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Чтение my.js");
        String path = "WEB-INF/js/my-js.js";
        InputStream inputStream = getServletContext().getResourceAsStream(path);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                PrintWriter out = resp.getWriter()){
            if (inputStream != null) {
                String line;
                while ((line = reader.readLine()) != null) {
                    out.write(line);
                }
            }
            inputStream.close();
        } catch (Exception e){
            log.error("Произошла ошибка при чтение файла my.js");
            throw  e;
        } finally {
            inputStream.close();
        }
    }
}
