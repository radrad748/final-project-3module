package com.javarush.radik.controllers;

import com.javarush.radik.entity.User;
import com.javarush.radik.services.ServiceUsers;
import com.javarush.radik.util.Encoder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//при регистрации проходит проверка на валидность данных
@WebServlet(value = "/registration-check")
public class RegistrationCheck extends HttpServlet {

    private final Logger log = LoggerFactory.getLogger(RegistrationCheck.class);
    private final ServiceUsers service = ServiceUsers.getInstance();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Проверка данных при регистрации пользователя");

        JSONObject json = getJsonRequest(req);
        String name = json.getString("name");
        String email = json.getString("email");
        String password = json.getString("password");

        if (checkValidRequest(name, email, password)){
            if (service.checkEmail(email)) sendAnswerEmailExists(email, resp);
            else sendAnswerSuccessful(name, email, password, resp);
        } else {
            sendAnswerUnsuccessful(resp);
        }
    }
    //парсим тело запроса с данными на регистрацию
    private JSONObject getJsonRequest(HttpServletRequest req) throws IOException {
        log.info("Получаем Json обьект из тела запроса по адресу /registration-check");
        try (BufferedReader reader = req.getReader()) {
            StringBuilder jsonRequest = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonRequest.append(line);
            }
            return new JSONObject(jsonRequest.toString());
        } catch (Exception e) {
            log.error("При считывания тела запроса по адресу /registration-check в методе getJsonRequest(HttpServletRequest req) произошла ошибка");
            throw e;
        }
    }
    //проверка на то являеться ли строка адрес email
    private boolean isValidEmail(String email) {
        log.info("Проверка валидность email при регистрации");
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
    //проверка на валидность имени
    private boolean isValidName(String name) {
        log.info("Проверка валидность имени пользователя при регистрации");
        return name.trim().length() > 1;
    }
    //проверка являеться ли пароль надежным
    private boolean isValidPassword(String password) {
        log.info("Проверка валидность пароля при регистрации");
        // Проверка наличия хотя бы одной цифры
        if (!password.matches(".*\\d.*")) {
            return false;
        }

        // Проверка наличия хотя бы одного символа верхнего регистра
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }

        // Проверка наличия хотя бы одного символа нижнего регистра
        if (!password.matches(".*[a-z].*")) {
            return false;
        }

        return true;
    }
    //метод который вернем true или false если все данные валидны
    private boolean checkValidRequest(String name, String email, String password) {
        log.info("Проверка являеться ли валидными данные запроса на регистрацию");
        if (isValidEmail(email) && isValidName(name) && isValidPassword(password)) return true;
        else return false;
    }
    //если все данные валидны то регистрируем нового пользователя
    private void sendAnswerSuccessful(String name, String email, String password, HttpServletResponse resp) throws IOException {
        log.info("Успешная регистрация, создаем нового user в базу данных Email = {}, name = {}", email, name);
        service.create(new User(name, email, Encoder.encode(password)));
        //отправка ответа true что пользователь зарегестрирован и ссылку на страницу успеной регистрации
        try (PrintWriter out = resp.getWriter()){
            JSONObject json = new JSONObject();
            json.put("isValid", true);
            json.put("redirectUrl", "/registration-successful");
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            out.write(json.toString());
        } catch (Exception e) {
            log.error("При отправки ответа в методе sendAnswerSuccessful(String name, String email, String password, HttpServletResponse resp) " +
                    "по адресу /registration-check произошла ошибка");
            throw e;
        }
    }
    //отправка ответа что пользователь с таким email который пришел в запросе уже существует в базе
    private void sendAnswerEmailExists(String email, HttpServletResponse resp) throws IOException {
        log.info("При попытки регистрации, ответ орицательный, с таким email: {}, уже существует пользователь", email);
        //отправка ответа false так как такой email уже существует
        try (PrintWriter out = resp.getWriter()){
            JSONObject json = new JSONObject();
            json.put("isValid", false);
            json.put("response", "Email: " + email + " уже зарегестрирован");
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            out.write(json.toString());
        } catch (Exception e) {
            log.error("При отправки ответа в методе sendAnswerEmailExists(String email, HttpServletResponse resp) " +
                    "по адресу /registration-check произошла ошибка");
            throw e;
        }
    }
    //отправка ответа что данные пришли не валидные и регистрация не прошла успешно, ответ оправляем false
    private void sendAnswerUnsuccessful(HttpServletResponse resp) throws IOException {
        log.info("При попытки регистрации, ответ орицательный, пользователь ввел не валидные данные");

        try (PrintWriter out = resp.getWriter()){
            JSONObject json = new JSONObject();
            json.put("isValid", false);
            json.put("response", "Вы ввели некорректные данные");
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            out.write(json.toString());
        } catch (Exception e) {
            log.error("При отправки ответа в методе sendAnswerUnsuccessful(HttpServletResponse resp) " +
                    "по адресу /registration-check произошла ошибка");
            throw e;
        }
    }

}
