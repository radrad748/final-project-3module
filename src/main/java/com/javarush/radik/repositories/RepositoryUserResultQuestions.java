package com.javarush.radik.repositories;

import com.javarush.radik.entity.Question;
import com.javarush.radik.entity.ResultQuestionsGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

//база данных с статистикой игрока по  играм в вопросы всех уровенй, 3 уровня easy, medium, hard, значит на каждого
//пользователя может быть по 3 обьекта с статистикой
public class RepositoryUserResultQuestions implements Repository<ResultQuestionsGame> {
    private static final RepositoryUserResultQuestions instance = new RepositoryUserResultQuestions();
    private final Logger log = LoggerFactory.getLogger(RepositoryUserResultQuestions.class);
    private final AtomicLong resultId = new AtomicLong(System.currentTimeMillis());
    private final ConcurrentMap<Long, ResultQuestionsGame> userResults = new ConcurrentHashMap<>();
    private RepositoryUserResultQuestions() {}
    @Override
    public ResultQuestionsGame byFindId(long id) {
        ResultQuestionsGame result = userResults.get(id);
        if (result == null) log.info("Запрос найти статистику по ID = {}, такой статистики нет в базе данных", id);
        else log.info("Запрос найти статистику по ID = {}, запрос успешно выполнен", id);
        return result;
    }

    @Override
    public void create(ResultQuestionsGame result) {
        long id = resultId.incrementAndGet();
        result.setId(id);
        userResults.put(id, result);
        log.info("Запрос создать статистику: ID = {} для user: ID = {}, запрос успешно выполнен", id, result.getUserId());
    }

    @Override
    public ResultQuestionsGame update(ResultQuestionsGame result) {
        ResultQuestionsGame oldResult = userResults.replace(result.getId(), result);
        if (oldResult == null) {
            log.info("Запрос изменить статистику: ID = {}, user: ID = {},  по такому ID статистика отсутствует",
                    result.getId(), result.getUserId());
            return oldResult;
        } else {
            log.info("Запрос изменить статистику: ID = {}, user: ID = {}, запрос успешно выполнен",
                    oldResult.getId(), result.getUserId());
            return oldResult;
        }
    }

    @Override
    public boolean delete(ResultQuestionsGame result) {
        ResultQuestionsGame deleteResult = userResults.remove(result.getId());
        if (deleteResult == null) {
            log.info("Запрос удалить статистику: ID = {}, user: ID = {}, статистики по такому ID нет в базе данных",
                    result.getId(), result.getUserId());
            return false;
        } else {
            log.info("Запрос удалить статистику: ID = {}, user: ID = {}, запрос успешно выполнен",
                    result.getId(), result.getUserId());
            return true;
        }
    }
    public void deleteAllWithUserID(long userId) {
        log.info("Удаление всей статистики пользователя по ID = {}", userId);
        userResults.forEach((key, value) -> {
            if (value != null && value.getUserId() == userId) {
                userResults.remove(key);
            }
        });
    }

    @Override
    public Collection<ResultQuestionsGame> getAll() {
        return userResults.values();
    }
    public int size() {
        int size = userResults.size();
        log.info("Запрос кол-во обьектов статистики в БД равна = {}", size);
        return size;
    }

    public static RepositoryUserResultQuestions getInstance() {return instance;}
}
