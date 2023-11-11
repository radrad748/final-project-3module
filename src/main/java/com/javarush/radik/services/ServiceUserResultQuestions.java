package com.javarush.radik.services;

import com.javarush.radik.entity.QuestionLevel;
import com.javarush.radik.entity.ResultQuestionsGame;
import com.javarush.radik.repositories.RepositoryUserResultQuestions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class ServiceUserResultQuestions {
    private static final ServiceUserResultQuestions instance = new ServiceUserResultQuestions();
    private final Logger log = LoggerFactory.getLogger(ServiceUserResultQuestions.class);
    private final RepositoryUserResultQuestions userResults = RepositoryUserResultQuestions.getInstance();
    private ServiceUserResultQuestions() {}

    public ResultQuestionsGame byFindId(long id) {
        log.info("Запрос к БД найти статистику по ID {}", id);
        return userResults.byFindId(id);
    }
    public int size() {
        return userResults.size();
    }
    public void create(ResultQuestionsGame result) {
        log.info("Запрос к БД создать статистику по ID = {}, для user: ID = {}", result.getId(), result.getUserId());
        userResults.create(result);
    }
    public ResultQuestionsGame update(ResultQuestionsGame result) {
        log.info("Запрос к БД изменить статистику по ID = {}, для user: ID = {}", result.getId(), result.getUserId());
        return userResults.update(result);
    }
    public boolean delete(ResultQuestionsGame result) {
        log.info("Запрос к БД удалить статистику по ID = {}, для user: ID = {}", result.getId(), result.getUserId());
        return userResults.delete(result);
    }
    public void deleteAllWithUserID(long userId) {
        userResults.deleteAllWithUserID(userId);
    }
    public ResultQuestionsGame getUserResultLevelEasy(long userId) {
        Collection<ResultQuestionsGame> results = getAll();
        ResultQuestionsGame result = results.stream()
                .filter(game -> game.getUserId() == userId && game.getLevel() == QuestionLevel.EASY)
                .findFirst()
                .orElse(null);
        return result;
    }
    public Collection<ResultQuestionsGame> getAll() {
        return userResults.getAll();
    }

    public static ServiceUserResultQuestions getInstance() {return instance;}
}
