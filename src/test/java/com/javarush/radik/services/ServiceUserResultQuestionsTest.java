package com.javarush.radik.services;

import com.javarush.radik.entity.QuestionLevel;
import com.javarush.radik.entity.ResultQuestionsGame;
import com.javarush.radik.repositories.RepositoryUserResultQuestions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.reflect.Whitebox;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ServiceUserResultQuestionsTest {
    @Mock
    private RepositoryUserResultQuestions repository;
    private ServiceUserResultQuestions service;
    @BeforeEach
    void init () {
        service = ServiceUserResultQuestions.getInstance();
        Whitebox.setInternalState(service, "userResults", repository);
    }

    //проверяем вызывает ли service метод repository.byFindId() с нужными параметрами
    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3 })
    void verifyRepository_byFindId_InsideService_byFindId(int id) {
        service.byFindId(id);
        Mockito.verify(repository).byFindId(id);
    }

    //при параметре null должно выкинуть исключение NullPointerException
    @Test
    void whenArgIsNull_biFindId() {
        Long id = null;
        assertThrows(NullPointerException.class, () -> service.byFindId(id));
    }

    //если БД не содержит обьект по задоному id то он вовращает null
    @Test
    void whenRepositoryReturnNullInsideMethodService_biFindId() {
        Mockito.when(repository.byFindId(any(long.class))).thenReturn(null);

        assertNull(service.byFindId(1));
    }

    //проверка при вызове service.size() внутри должен вызывать метод repository.size()
    @Test
    void verifyRepository_sizeInsideService_size() {
        service.size();
        Mockito.verify(repository).size();
    }

    //метод service.size() возвращает то число что и метод repository.size() внутри метода service.size()
    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3 })
    void size(int arg) {
        Mockito.when(repository.size()).thenReturn(arg);

        assertEquals(arg, service.size());
    }

    //проверка вызывает ли метод service.create() внутри себя метод repository.create()
    @Test
    void verifyRepository_createInsideService_create() {
        ResultQuestionsGame userResult = new ResultQuestionsGame(5, QuestionLevel.MEDIUM);
        service.create(userResult);
        Mockito.verify(repository).create(userResult);
    }

    //когда при методе crete() с аргуметом null выбрасывает исключение NullPointerException
    @Test
    void whenArgIsNull_create() {;
        assertThrows(NullPointerException.class, () -> service.create(null));
    }

    //проверка вызывает ли метод service.update() внутри себя метод repository.update()
    @Test
    void verifyRepository_updateInsideService_update() {
        ResultQuestionsGame userResult = new ResultQuestionsGame(5, QuestionLevel.MEDIUM);
        service.update(userResult);
        Mockito.verify(repository).update(userResult);
    }

    //когда при методе update() с аргуметом null выбрасывает исключение NullPointerException
    @Test
    void whenArgIsNull_update() {
        assertThrows(NullPointerException.class, () -> service.update(null));
    }

    //проверка вызывает ли метод service.delete() внутри себя метод repository.delete()
    @Test
    void verifyRepository_deleteInsideService_delte() {
        ResultQuestionsGame userResult = new ResultQuestionsGame(5, QuestionLevel.MEDIUM);
        service.delete(userResult);
        Mockito.verify(repository).delete(userResult);
    }

    //когда при методе delete() с аргуметом null выбрасывает исключение NullPointerException
    @Test
    void whenArgIsNull_delete() {
        assertThrows(NullPointerException.class, () -> service.delete(null));
    }

    //проверка какой результат возвращает service.delete()
    @ParameterizedTest
    @ValueSource(booleans = { true, false, true })
    void checkWhatReturnService_deleteWhenChallangeInsideRepository_delete(boolean arg) {
        ResultQuestionsGame result = new ResultQuestionsGame(5, QuestionLevel.MEDIUM);
        Mockito.when(repository.delete(result)).thenReturn(arg);

        assertEquals(arg, service.delete(result));
    }

    //проверка при вызове service.getAll() внутри должен вызывать метод repository.getAll()
    @Test
    void verifyRepository_getAllInsideService_getAll() {
        service.getAll();
        Mockito.verify(repository).getAll();
    }

    //проверка при вызове service.deleteAllWithUserID() внутри должен вызывать метод repository.deleteAllWithUserID()
    //и проверка что service.deleteAllWithUserID() вызывает метод с нужными параметрами метод repository.deleteAllWithUserID()
    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3 })
    void verifyRepository_deleteAllWithUserIDInsideService_deleteAllWithUserID(int id) {
        service.deleteAllWithUserID(id);
        Mockito.verify(repository).deleteAllWithUserID(id);
    }

    
}