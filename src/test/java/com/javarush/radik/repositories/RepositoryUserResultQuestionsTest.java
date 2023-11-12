package com.javarush.radik.repositories;

import com.javarush.radik.entity.QuestionLevel;
import com.javarush.radik.entity.ResultQuestionsGame;
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

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class RepositoryUserResultQuestionsTest {
    @Mock
    private ResultQuestionsGame resultForTestCreate;
    private RepositoryUserResultQuestions repository;
    @Mock
    private Logger log;
    @Mock
    private AtomicLong resultId;
    @Mock
    private ConcurrentMap<Long, ResultQuestionsGame> userResults;
    @BeforeEach
    void init() {
        repository = RepositoryUserResultQuestions.getInstance();
        Whitebox.setInternalState(repository, "log", log);
        Whitebox.setInternalState(repository, "resultId", resultId);
        Whitebox.setInternalState(repository, "userResults", userResults);
    }
    //проверка на арг null
    @Test
    void whenArgIsNull_biFindId() {
        Long id = null;
        assertThrows(NullPointerException.class, () -> repository.byFindId(id));
    }

    //проверяем при вызове метода byFindId должно вызываться userResults.get() один раз с нужным аргументом
    @ParameterizedTest
    @ValueSource(longs = { 1, 2, 3 })
    void verifyMethod_get_InsideRepository_byFindId(long id) {
        repository.byFindId(id);
        Mockito.verify(userResults, times(1)).get(id);
    }

    //если БД не содержит обьект по задоному id то он вовращает null, если есть то возвращает userResult по его id
    @Test
    void checkWhatReturnRepositoryWhenExistUserResultWithIdAndNotExist() {
        ConcurrentMap<Long, ResultQuestionsGame> obj = new ConcurrentHashMap<>();
        ResultQuestionsGame result = new ResultQuestionsGame(1, QuestionLevel.EASY);
        obj.put(1L, result);
        Whitebox.setInternalState(repository, "userResults", obj);

        ResultQuestionsGame resultNull = repository.byFindId(3);
        assertNull(resultNull);

        ResultQuestionsGame resultExist = repository.byFindId(1);
        assertNotNull(resultExist);

        assertEquals(result, repository.byFindId(1));
    }

    //проверка при вызове repository.size() внутри должен вызывать метод userResults.size()
    @Test
    void verifyMethod_sizeInsideDatabase_size() {
        repository.size();
        Mockito.verify(userResults, times(1)).size();
    }

    //метод repository.size() возвращает то число что и метод userResults.size() внутри метода repository.size()
    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3 })
    void size(int arg) {
        Mockito.when(userResults.size()).thenReturn(arg);

        assertEquals(arg, repository.size());
    }

    //проверка если вызывает нужные матоды врнутри create()
    @Test
    void verifyIfChallangeTheNecessaryMethodsInsideCreate() {
        Mockito.when(resultId.incrementAndGet()).thenReturn(1L);
        repository.create(resultForTestCreate);

        Mockito.verify(resultId).incrementAndGet();
        Mockito.verify(resultForTestCreate).setId(1L);
        Mockito.verify(userResults).put(1L, resultForTestCreate);
    }

    //проверка на арг null, выбрасывает исключение NullPointerException
    @Test
    void ifArgNullInMethodCreate() {
        assertThrows(NullPointerException.class, () -> repository.create(null));
    }

    //впроверка внутри метода repository.update() должно вызываться один раз userResult.replace()
    @Test
    void verifyInMethodUpdateInInsideNeedChallangeRepository_replace() {
        ResultQuestionsGame result = new ResultQuestionsGame(1, QuestionLevel.EASY);
        Mockito.when(userResults.replace(result.getId(), result)).thenReturn(result);
        repository.update(result);
        Mockito.verify(userResults, times(1)).replace(result.getId(), result);
    }

    //когда аргумент null должно кидать исключение NullPointerException
    @Test
    void chekcWhenArgIsNull() {
        assertThrows(NullPointerException.class, () -> repository.update(null));
    }

    //проверка что метод repository.update() возвращает старый result который ему передал база данных
    @Test
    void checkWhichObjectGiveMethodRepository_update() {
        ResultQuestionsGame result = new ResultQuestionsGame(1, QuestionLevel.EASY);
        Mockito.when(userResults.replace(result.getId(), result)).thenReturn(result);
        ResultQuestionsGame result1 = repository.update(result);
        assertEquals(result, result1);
    }

    //проверка если repository.delete() вызывает userResult.remove() с нужным аргументом, только 1 вызов
    @Test
    void verifyIfMethodDeleteChallangeRepository_remove() {
        ResultQuestionsGame result = new ResultQuestionsGame(1, QuestionLevel.EASY);
        repository.delete(result);
        Mockito.verify(userResults, times(1)).remove(result.getId());
    }

    //проверка при аргументе null метод delete()
    @Test
    void checkIfArgNullInMethodDelete() {
        assertThrows(NullPointerException.class, () -> repository.delete(null));
    }

    //когда result есть в БД ответ приходит true, если отсуствует то false
    @Test
    void checkAnswerMethodDatabase_delete() {
        ResultQuestionsGame result = new ResultQuestionsGame(1, QuestionLevel.EASY);
        Mockito.when(userResults.remove(result.getId())).thenReturn(result);

        boolean answer = repository.delete(result);
        assertEquals(true, answer);

        ResultQuestionsGame result1 = new ResultQuestionsGame(2, QuestionLevel.EASY);
        Mockito.when(userResults.remove(result1.getId())).thenReturn(null);

        boolean answer1 = repository.delete(result1);
        assertEquals(false, answer1);
    }

    //проверка если корректно работает метод deleteAllWithUserID()
    @Test
    void deleteAllWithUserID() {
        ConcurrentMap<Long, ResultQuestionsGame> obj = new ConcurrentHashMap<>();
        obj.put(1L, new ResultQuestionsGame(1, QuestionLevel.EASY));
        Whitebox.setInternalState(repository, "userResults", obj);

        assertEquals(1, repository.size());

        repository.deleteAllWithUserID(2);
        assertEquals(1, repository.size());

        repository.deleteAllWithUserID(1);
        assertEquals(0, repository.size());
    }

    @Test
    void getAll() {
        repository.getAll();
        Mockito.verify(userResults).values();
    }
}