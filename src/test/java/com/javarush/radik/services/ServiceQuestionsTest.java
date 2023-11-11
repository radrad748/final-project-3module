package com.javarush.radik.services;

import com.javarush.radik.entity.Question;
import com.javarush.radik.entity.QuestionLevel;
import com.javarush.radik.repositories.DatabaseQuestions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ServiceQuestionsTest {
    @Mock
    private DatabaseQuestions mockDbQuestions;
    ServiceQuestions service;
    @BeforeEach
    void init() {
        service = ServiceQuestions.getInstance();
        Whitebox.setInternalState(service, "databaseQuestions", mockDbQuestions);
    }

    //проверка на кол-во обьекта question, метод должен вернуть 10
    @Test
    void checkNumberOfQuestions() {
        Mockito.when(mockDbQuestions.getAll()).thenReturn(getQuestions());
        List<Question> questions = service.getTenQuestionsEasy();

        assertEquals(10, questions.size());
    }
    //проверяем если метод getTenQuestionsEasy() возвращает question типа easy
    @Test
    void checkIfQuestionsTypeEasy() {
        Mockito.when(mockDbQuestions.getAll()).thenReturn(getQuestions());
        List<Question> questions = service.getTenQuestionsEasy();

        for(Question q : questions) {
            assertEquals(QuestionLevel.EASY, q.getLevel());
        }
    }
    //проверка на вызов метода getAll обьекта BD_questions внутри метода service.getTenQuestionsEasy()
    @Test
    void verifyBdQuestions_GetAllWhenChallengeService_GetTenQuestionsEasy() {
        Mockito.when(mockDbQuestions.getAll()).thenReturn(getQuestions());
        List<Question> questions = service.getTenQuestionsEasy();

        Mockito.verify(mockDbQuestions).getAll();
    }
    //проверяем если БД не имеет question типа easy то метод возвращает пустой лист и не null
    @Test
    void whenBdDontHaveTypeEasyThenReturnNotNullAndListEmpty() {
        Mockito.when(mockDbQuestions.getAll()).thenReturn(getTenQuestionsMedium());
        List<Question> questions = service.getTenQuestionsEasy();

        assertAll("проверка что questions не null и List empty",
                () -> assertEquals(0, questions.size()),
                () -> assertNotNull(questions)
        );
    }


    private List<Question> getTenQuestionsMedium() {
        List<Question> allQuestions = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Question q = new Question();
            q.setLevel(QuestionLevel.MEDIUM);
            q.setId(i);
            allQuestions.add(q);
        }
        return allQuestions;
    }
    private List<Question> getQuestions() {
        List<Question> allQuestions = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Question q = new Question();
            q.setLevel(QuestionLevel.MEDIUM);
            q.setId(i);
            allQuestions.add(q);
        }
        for (int i = 10; i < 20; i++) {
            Question q = new Question();
            q.setLevel(QuestionLevel.EASY);
            q.setId(i);
            allQuestions.add(q);
        }
        return allQuestions;
    }

}