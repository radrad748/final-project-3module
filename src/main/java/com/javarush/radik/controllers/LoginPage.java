package com.javarush.radik.controllers;

import com.javarush.radik.entity.DTO.UserDto;
import com.javarush.radik.services.ServiceUsers;
import com.javarush.radik.util.MapperDto;
import jakarta.servlet.annotation.WebServlet;
import com.javarush.radik.util.Encoder;
import com.javarush.radik.entity.User;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

//после как пользователь вводит данные для авторизации идет запрос на этот сервлет и он проверяет валидность данных и если действительно
//заренестрирован такой пользователь
@WebServlet(value = "/login")
public class LoginPage extends HttpServlet {
    private final Logger log = LoggerFactory.getLogger(LoginPage.class);
    private final ServiceUsers service = ServiceUsers.getInstance();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("Запрос авторизации");
        HttpSession session = req.getSession(true);

        JSONObject jsonObject = getJsonRequest(req);

        String email = jsonObject.getString("email");
        String password = jsonObject.getString("password");

        if (checkUser(email, password, session)) successfulLogin(resp);
        else unsuccessfulLogin(resp);
    }

    // проверяем email i пароль
    private boolean checkUser(String email, String password, HttpSession session) {
        if(email == null || password == null) {
            log.info("Введённый пользователем email/password равено null");
            return false;
        }
        User user = service.byFindEmail(email);
        log.info("Проверка на наличие user в базе данных с таким Email = {}, так же проверка на совпадение пароля", email);
        if (user == null){
            log.info("В базе данных нет user с таким Email = {}", email);
            return false;
        }
        String passwordEncode = Encoder.encode(password);
        if (!(user.getPassword().equals(passwordEncode))){
            log.info("пароль не совпадает");
            return false;
        } else{
            UserDto dto = MapperDto.getUserDto(user);
            session.setAttribute("user", dto);
            return true;
        }
    }

    //парсим тело запроса в json обьект
    private JSONObject getJsonRequest(HttpServletRequest req) throws IOException {
        log.info("Получаем Json обьект из тела запроса по адресу /login");
        try (BufferedReader reader = req.getReader()) {
            StringBuilder jsonRequest = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonRequest.append(line);
            }
            return new JSONObject(jsonRequest.toString());
        } catch (Exception e) {
            log.error("При считывания тела запроса по адресу /login в методе getJsonRequest(HttpServletRequest req) произошла ошибка");
            throw e;
        }
    }
   //успешная авторизация и перевод на первую страницу игры
    private void successfulLogin(HttpServletResponse resp) throws IOException {
        log.info("User успешно авторизоваться, отправка redirect на list of games page");
        try (PrintWriter out = resp.getWriter()) {
            //отправляем ответ true и ссылку
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("isValid", true);
            jsonResponse.put("redirectUrl", "/first-page");
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            out.write(jsonResponse.toString());
        } catch (Exception e) {
            log.error("При отправки redirect на first page в методе successfulLogin(HttpServletResponse resp) произошла ошибка");
            throw e;
        }
    }
   // неверные введеные данные пользователем, отправляет ответ false
    private void unsuccessfulLogin(HttpServletResponse resp) throws IOException {
        log.info("User ввел неверный логин или пароль");
        try (PrintWriter out = resp.getWriter()) {
            // отправляем ответ false
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("isValid", false);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            out.write(jsonResponse.toString());
        } catch (Exception e) {
            log.error("При отправки ответа на неудачную авторизацию в методе unsuccessfulLogin(HttpServletResponse resp) произогшла ошибка");
            throw e;
        }
    }

}
