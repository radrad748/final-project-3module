package com.javarush.radik.controllers.jsResources;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

// Сервлет для чтение js файлов
@WebServlet(value = "/all-js")
public class JsResource extends HttpServlet {
    private final Logger log = LoggerFactory.getLogger(JsResource.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Идет чтение js файла");
        ServletContext context = req.getServletContext();
        String path = (String) context.getAttribute("pathJs");

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
            log.error("Произошла ошибка при чтение файла js файла");
            throw  e;
        } finally {
            inputStream.close();
        }
    }
}
