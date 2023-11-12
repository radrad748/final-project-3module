package com.javarush.radik.repositories;

import com.javarush.radik.entity.Question;
import com.javarush.radik.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.reflect.Whitebox;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class DatabaseQuestionsTest {
    @Mock
    private Question questionForTestCreate;
    private DatabaseQuestions database;
    @Mock
    private Logger log;
    @Mock
    private AtomicLong questionId;
    @Mock
    private ConcurrentMap<Long, Question> questions;
    @BeforeEach
    void init() {
        database = DatabaseQuestions.getInstance();
        Whitebox.setInternalState(database, "log", log);
        Whitebox.setInternalState(database, "questionId", questionId);
        Whitebox.setInternalState(database, "questions", questions);
    }
    //проверка на арг null
    @Test
    void whenArgIsNull_biFindId() {
        Long id = null;
        assertThrows(NullPointerException.class, () -> database.byFindId(id));
    }

    //проверяем при вызове метода byFindId должно вызываться users.get() один раз с нужным аргументом
    @ParameterizedTest
    @ValueSource(longs = { 1, 2, 3 })
    void verifyRepository_get_InsideDatabase_byFindId(long id) {
        database.byFindId(id);
        Mockito.verify(questions, times(1)).get(id);
    }

    //есил нет question по id вернет null, если есть то должен вернуть по id question
    @Test
    void checkWhatReturnRepositoryWhenExistQuestionWithIdAndNotExist() {
        ConcurrentMap<Long, Question> obj = new ConcurrentHashMap<>();;
        Question question = new Question();
        obj.put(1L, question);
        Whitebox.setInternalState(database, "questions", obj);

        assertNull(database.byFindId(2));

        assertNotNull(database.byFindId(1));

        assertEquals(question, database.byFindId(1));
    }

    //проверка если вызывает нужные матоды врнутри create()
    @Test
    void verifyIfChallangeTheNecessaryMethodsInside() {
        Mockito.when(questionId.incrementAndGet()).thenReturn(1L);
        database.create(questionForTestCreate);

        Mockito.verify(questionId).incrementAndGet();
        Mockito.verify(questionForTestCreate).setId(1L);
        Mockito.verify(questions).put(1L, questionForTestCreate);
    }

    //проверка на арг null, выбрасывает исключение NullPointerException
    @Test
    void ifArgNullInMethodCreate() {
        assertThrows(NullPointerException.class, () -> database.create(null));
    }

    //впроверка внутри метода database.update() должно вызываться один раз questions.replace()
    @Test
    void verifyInMethodDatabase_updateInInsideNeedChallangeRepository_replace() {
        Question question = new Question();
        question.setId(1);
        Mockito.when(questions.replace(question.getId(), question)).thenReturn(question);
        database.update(question);
        Mockito.verify(questions, times(1)).replace(question.getId(), question);
    }

    //когда аргумент null должно кидать исключение NullPointerException
    @Test
    void chekcWhenArgIsNull() {
        assertThrows(NullPointerException.class, () -> database.update(null));
    }

    //проверка что метод database.update() возвращает старый question который ему передал база данных
    @Test
    void checkWhichObjectGiveMethodDatabase_update() {
        Question question = new Question();
        question.setId(1);
        Mockito.when(questions.replace(question.getId(), question)).thenReturn(question);
        Question question1 = database.update(question);
        assertEquals(question, question1);
    }

    //проверка если database.delete() вызывает questions.remove() с нужным аргументом, только 1 вызов
    @Test
    void verifyIfMethodDatabase_deleteChallangeRepository_remove() {
        Question question = new Question();
        question.setId(1);
        database.delete(question);
        Mockito.verify(questions, times(1)).remove(question.getId());
    }

    //проверка при арнументе null метод delete()
    @Test
    void checkIfArgNullInMethodDelete() {
        assertThrows(NullPointerException.class, () -> database.delete(null));
    }

    //когда question есть в БД ответ приходит true, если отсуствует то false
    @Test
    void checkAnswerMethodDatabase_delete() {
        Question question = new Question();
        question.setId(1);
        Mockito.when(questions.remove(question.getId())).thenReturn(question);

        boolean answer = database.delete(question);
        assertEquals(true, answer);

        Question question1 = new Question();
        question1.setId(1);
        Mockito.when(questions.remove(question1.getId())).thenReturn(null);

        boolean answer1 = database.delete(question1);
        assertEquals(false, answer1);
    }

    @Test
    void getAll() {
        database.getAll();
        Mockito.verify(questions).values();
    }
}