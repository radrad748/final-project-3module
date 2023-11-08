package com.javarush.radik.controllers;

import com.javarush.radik.entity.DTO.UserDto;
import com.javarush.radik.entity.User;
import com.javarush.radik.repositories.Database;
import com.javarush.radik.services.ServiceUsers;
import com.javarush.radik.util.Encoder;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//работа с sattings страницей, перевод на settings и изменение/удаление профиля
@WebServlet(value = "/settings")
public class SettingsPage extends HttpServlet {
    private final Logger log = LoggerFactory.getLogger(SettingsPage.class);
    private final ServiceUsers service = ServiceUsers.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("перевод на страниу по адресу /settings");
        RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/jsp/settings.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (checkIfValidRequest(req)) {
            sendSuccessfulAnswer(resp);
        } else {
            sendUnsuccessfulAnswer(resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        deleteUser(req.getSession());
    }

    private boolean checkIfValidRequest(HttpServletRequest req) throws IOException {
        JSONObject json = getJsonRequest(req);
        if (ifExistsParameter(json, "name")) {
            return updateUserName(json, req.getSession());
        }
        if (ifExistsParameter(json, "email")) {
            return updateUserEmail(json, req.getSession());
        }
        if (ifExistsParameter(json, "oldPassword") && ifExistsParameter(json, "newPassword")) {
            return updateUserPassword(json, req.getSession());
        }
        return false;
    }
    private void sendSuccessfulAnswer(HttpServletResponse resp) throws IOException {
        try (PrintWriter out = resp.getWriter()){
            JSONObject json = new JSONObject();
            json.put("isValid", true);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            out.write(json.toString());
        } catch (Exception e) {
            throw e;
        }
    }
    private void sendUnsuccessfulAnswer(HttpServletResponse resp) throws IOException {
        try (PrintWriter out = resp.getWriter()){
            JSONObject json = new JSONObject();
            json.put("isValid", false);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            out.write(json.toString());
        } catch (Exception e) {
            throw e;
        }
    }

    private boolean updateUserName(JSONObject json, HttpSession session) {
        String name = json.getString("name");
        if (!isValidName(name)) return false;

        UserDto dto = (UserDto) session.getAttribute("user");
        dto.setName(name);
        session.setAttribute("user", dto);

        User user = service.byFindEmail(dto.getEmail());
        user.setName(name);
        service.update(user);

        return true;
    }
    private boolean updateUserEmail(JSONObject json, HttpSession session) {
        String email = json.getString("email");
        if (!isValidEmail(email)) return false;

        if (service.checkEmail(email)) return false;

        UserDto dto = (UserDto) session.getAttribute("user");
        User user = service.byFindEmail(dto.getEmail());
        dto.setEmail(email);
        session.setAttribute("user", dto);
        user.setEmail(email);
        service.update(user);

        return true;
    }
    private boolean updateUserPassword(JSONObject json, HttpSession session) {
        String oldPassword = json.getString("oldPassword");
        String newPassword = json.getString("newPassword");
        if (!isValidPassword(newPassword)) return false;

        String oldPasswordEndoce = Encoder.encode(oldPassword);
        String newPasswordEncode = Encoder.encode(newPassword);

        UserDto dto = (UserDto) session.getAttribute("user");
        User user = service.byFindEmail(dto.getEmail());

        if (!user.getPassword().equals(oldPasswordEndoce)) return false;

        user.setPassword(newPasswordEncode);
        service.update(user);
        return true;
    }
    public boolean deleteUser(HttpSession session) {
        UserDto dto = (UserDto) session.getAttribute("user");
        User user = service.byFindEmail(dto.getEmail());
        session.invalidate();
        return service.delete(user);
    }

    private JSONObject getJsonRequest(HttpServletRequest req) throws IOException {
        try (BufferedReader reader = req.getReader()) {
            StringBuilder jsonRequest = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonRequest.append(line);
            }
            return new JSONObject(jsonRequest.toString());
        } catch (Exception e) {
            throw e;
        }
    }



    private boolean ifExistsParameter(JSONObject json, String parameter) {
        try {
            String str = json.getString(parameter);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
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
    private boolean isValidName(String name) {
        log.info("Проверка валидность имени пользователя при регистрации");
        if (name.trim().length() > 1 && name.trim().length() <= 30) return true;
        else return false;
    }
    private boolean isValidEmail(String email) {
        log.info("Проверка валидность email при регистрации");
        if (email.trim().length() > 30) return false;
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

}