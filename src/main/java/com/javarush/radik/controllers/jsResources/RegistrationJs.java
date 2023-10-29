package com.javarush.radik.controllers.jsResources;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

//считываем js файл registration.js
@WebServlet(value = "/registration-js")
public class RegistrationJs extends HttpServlet {
    private final Logger log = LoggerFactory.getLogger(RegistrationJs.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Чтение registration.js");
        String path = "WEB-INF/js/registration.js";
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
            log.error("Произошла ошибка при чтение файла registration.js");
            throw  e;
        } finally {
            inputStream.close();
        }
    }
}
