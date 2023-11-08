package com.javarush.radik.repositories;

import com.javarush.radik.entity.User;
import com.javarush.radik.services.ServiceUsers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;


public class Database implements Repository<User>{

    private static final Database instance = new Database();
    private final Logger log = LoggerFactory.getLogger(Database.class);
    private final AtomicLong userID = new AtomicLong(System.currentTimeMillis());
    private final ConcurrentMap<Long, User> users = new ConcurrentHashMap<>();

    private Database() {
    }
    public User byFindId(long id) {
        User user = users.get(id);
        if (user == null) log.info("Запрос найти user по ID = {}, такого user нет в базе данных", id);
        else log.info("Запрос найти user по ID = {}, запрос успешно выполнен", id);
        return user;
    }

    public boolean delete(User user) {
       User userDelete = users.remove(user.getId());
       if (userDelete == null) {
           log.info("Запрос удалить user: ID = {}, name = {}, email = {}, user по такому ID = {} нет в базе данных",
                   user.getId(), user.getName(), user.getEmail(), user.getId());
           return false;
       } else {
           log.info("Запрос удалить user: ID = {}, name = {}, email = {}, запрос успешно выполнен",
                   user.getId(), user.getName(), user.getEmail());
           return true;
       }
    }

    public User update(User user) {
        User oldUser = users.replace(user.getId(), user);
        if (oldUser == null){
            log.info("Запрос изменить user: ID = {}, name = {}, email = {}, user по такому ID = {} нет в базе данных",
                    user.getId(), user.getName(), user.getEmail(), user.getId());
            return oldUser;
        } else {
            log.info("Запрос изменить user: ID = {}, name = {}, email = {} на user: ID = {}, name = {}, email = {}, запрос успешно выполнен",
                    oldUser.getId(), oldUser.getName(), oldUser.getEmail(), user.getId(), user.getName(), user.getEmail());
            return oldUser;
        }
    }

    public void create(User user) {
        long id = userID.incrementAndGet();
        user.setId(id);
        users.put(id, user);
        log.info("Запрос создать user: ID = {}, name = {}, email = {}, запрос успешно выполнен", id, user.getName(), user.getEmail());
    }

    public User byFindEmail(String email) {
        User user = users.entrySet().stream()
                .filter(entry -> email.equals(entry.getValue().getEmail()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
        if (user == null) log.info("Запрос поиск user по Email {}, такой user в базе данных отсутствует", email);
        else log.info("Запрос поиск user по Email {}, поиск выполнен user: ID = {}, name = {}, email = {}", email, user.getId(), user.getName(), user.getEmail());
        return user;
    }

    public int size() {
        int size = users.size();
        log.info("Запрос кол-во users в БД равна = {}", size);
        return size;
    }

    public boolean checkEmail(String email) {
        log.info("База данных проверяет есть данный email = {} в базе", email);
        return users.values().stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }

    public Collection<User> getAll() {
        return users.values();
    }

    public static Database getInstance() {
        return instance;
    }

}
