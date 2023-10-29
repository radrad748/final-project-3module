package com.javarush.radik.services;

import com.javarush.radik.entity.User;
import com.javarush.radik.repositories.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;

public class ServiceUsers {
    private static final ServiceUsers instance = new ServiceUsers();
    private ServiceUsers() {
    }

    private final Logger log = LoggerFactory.getLogger(ServiceUsers.class);
    private final Database database = Database.getInstance();



    public User byFindID(long id) {
        User user = database.byFindId(id);
        if (user == null) log.info("Запрос к базе данных найти user: ID = {}; запрос прошел успешно, ответ - такой user отсутствует", id);
        else log.info("Запрос к базе данных найти user: ID = {}; запрос прошел успешно, ответ - такой user существует - " +
                "ID = {}, name = {}, email = {}", id, id, user.getName(), user.getEmail());
        return user;
    }


    public boolean delete(User user) {
        boolean userDelete = database.delete(user);
        if (user == null) log.info("Запрос к базе данных удалить user: ID = {}, name = {}, email = {}; " +
                "запрос прошел успешно, ответ - такой user отсутствует", user.getId(), user.getName(), user.getEmail());
        else log.info("Запрос к базе данных удалить user: ID = {}, name = {}, email = {}; " +
                "user успешно удален из базы данных", user.getId(), user.getName(), user.getEmail());
        return userDelete;
    }


    public User update(User user) {
        User oldUser = database.update(user);
        if (user == null) log.info("Запрос к базе данных изменить user: ID = {}, name = {}, email = {}; " +
                "запрос прошел успешно, ответ - user по такому ID = {} отсутствует в базе данных", user.getId(), user.getName(), user.getEmail(), user.getId());
        else log.info("Запрос к базе данных изменить user: ID = {}, name = {}, email = {}; " +
                "база данных успешно произвела замену на user: ID = {}, name = {}, email = {}",
                user.getId(), user.getName(), user.getEmail(), oldUser.getId(), oldUser.getName(), oldUser.getEmail());
        return oldUser;
    }


    public boolean create(User user) {
        boolean checkUserEmail = checkEmail(user.getEmail());
        if (!checkUserEmail) {
            database.create(user);
                log.info("Запрос к базе данных создать user: name = {}, email = {}, запрос успешно выполнен, ответ - база данных добавила нового user",
                        user.getName(), user.getEmail());
            return true;
        } else {
            log.info("Запрос к базе данных создать user: name = {}, email = {}, запрос успешно выполнен, ответ - user с таким email = {} уже зарегистрирован",
                    user.getName(), user.getEmail(), user.getEmail());
            return false;
        }
    }

    public  User byFindEmail(String email) {
        User user =  database.biFindEmail(email);
        if (user == null) log.info("Запрос к базе данных поиск user по Email {},ответ от базы данных - такой user в базе данных отсутствует", email);
        else log.info("Запрос к базе данных поиск user по Email {},ответ от базы данных - поиск выполнен user: ID = {}, name = {}, email = {}", email, user.getId(), user.getName(), user.getEmail());
        return user;
    }


    public int size() {
        int size = database.size();
        log.info("запрос к базе данных получить кол-во users, ответ - {}", size);
        return size;
    }

    public static ServiceUsers getInstance() {
        return instance;
    }

    public boolean checkEmail(String email) {
        log.info("Проверка на наличие user в базе данных с таким email = {}", email);
        if (database.checkEmail(email)) {
            log.info("В базе данных зарегестрирован user с таким email = {}", email);
            return true;
        } else {
            log.info("С таким email = {} user в базе данных нет", email);
            return false;
        }
    }
}
