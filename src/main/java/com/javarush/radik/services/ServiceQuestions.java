package com.javarush.radik.services;

import com.javarush.radik.entity.Question;
import com.javarush.radik.repositories.DatabaseQuestions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ServiceQuestions {

    private static final ServiceQuestions instance = new ServiceQuestions();
    private final Logger log = LoggerFactory.getLogger(ServiceQuestions.class);
    private final DatabaseQuestions databaseQuestions = DatabaseQuestions.getInstance();

    public List<Question> getTenQuestions() {
        log.info("Получаем 10 вопросов для игры");
        List<Question> questions = new ArrayList<>();
        int count = 0;
        for (Question q : databaseQuestions.getAll()) {
            questions.add(q);
            count++;
            if (count == 10) break;
        }
        return questions;
    }

    public static ServiceQuestions getInstance() {return instance;}
}
