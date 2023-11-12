package com.javarush.radik.services;

import com.javarush.radik.entity.User;
import com.javarush.radik.repositories.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.reflect.Whitebox;
import org.slf4j.Logger;

import javax.xml.crypto.Data;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceUsersTest {
    @Mock
    private Logger log;
    @Mock
    private Database repository;
    private ServiceUsers service;
    @BeforeEach
    void init() {
        service = ServiceUsers.getInstance();
        Whitebox.setInternalState(service, "database", repository);
        Whitebox.setInternalState(service, "log", log);
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
    //проверка вызываються ли внутри метода service.create() такие методы как repository.checkEmail с нужным параметром и repository.create()
    @ParameterizedTest
    @MethodSource("argsEmail")
    void verifyIfChallangeMethodsRepository_checkEmailAndRepository_Create(String email) {
        Mockito.when(repository.checkEmail(any(String.class))).thenReturn(false);
        User user = new User();
        user.setEmail(email);

        service.create(user);
        Mockito.verify(repository).checkEmail(email);
        Mockito.verify(repository).create(user);
    }

    //проверяем порядок вызовов методов внутри service.create(), при условии когда метод repository.checkEmail() false
    //порядок должен быть такой 1) repository.checkEmail(), 2) repository.create();
    @Test
    void verifyOrderChallangeMethodsInsideService_create() {
        Mockito.when(repository.checkEmail(any(String.class))).thenReturn(false);
        User user = new User();
        user.setEmail("some email");
        service.create(user);

        InOrder inOrder = inOrder(repository);

        inOrder.verify(repository).checkEmail(user.getEmail());
        inOrder.verify(repository).create(user);
    }

    //проверка когда проверяем если усть user с email в базе данных метод repository.checkEmail() и получаем tru
    //то метод repository.create() не должен вызываться, и проверяем чтоб 1 раз вызывался repository.checkEmail()
    //с аргументом user.getEmail()
    @Test
    void verifyWhenInsideService_createGetFalseInRepository_checkEmail() {
        Mockito.when(repository.checkEmail(any(String.class))).thenReturn(true);
        User user = new User();
        user.setEmail("some email");
        service.create(user);

        Mockito.verify(repository, times(1)).checkEmail(user.getEmail());
        Mockito.verify(repository, never()).create(user);
    }

    //проверка если при вызове метода service.byFindEmail() этот метод должен вызывать repository.byFindEmail()
    @Test
    void verifyMethodService_byFindEmailNeedToChallangeRepository_byFindEmail() {
        service.byFindEmail("some email");
        Mockito.verify(repository).byFindEmail("some email");
    }

    //если арг null то кидает исключение NullPointerException
    @Test
    void checkIfArgNullInMethodByFindEmail() {
        Mockito.when(repository.byFindEmail(null)).thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> service.byFindEmail(null));
    }

    //проверка когда в базе данных нет user по данному емаелу и возвращает null, так же service.byFindEmail()
    //так же должен вернуть null
    @ParameterizedTest
    @MethodSource("argsEmail")
    void checkWhenEmailNotExistInDataBase(String email) {
        Mockito.when(repository.byFindEmail(any(String.class))).thenReturn(null);
        User user = service.byFindEmail(email);
        assertNull(user);
    }

    //проверка если user существует в базе данных с данным email и если service.byFindEmail() корректно отрабатывает
    @ParameterizedTest
    @MethodSource("argsEmail")
    void checkWhenInDatabaseExistUserWithEmail(String email) {
        User user = new User();
        user.setEmail(email);
        Mockito.when(repository.byFindEmail(email)).thenReturn(user);

        User user1 = service.byFindEmail(email);
        assertNotNull(user1);
        assertEquals(email, user1.getEmail());
    }

    //проверка если service.delete() вызывает repository.delete() с нужным аргументом, только 1 вызов
    @Test
    void verifyIfMethodService_deleteChallangeRepository_delete() {
        User user = new User();
        service.delete(user);
        Mockito.verify(repository, times(1)).delete(user);
    }

    //проверка при арнументе null метод delete()
    @Test
    void checkIfArgNullInMethodDelete() {
        assertThrows(NullPointerException.class, () -> service.delete(null));
    }

    //проверка какой ответ возвращает repository.delete() такой ответ должен возвращать service.delete()
    @ParameterizedTest
    @ValueSource(booleans = { true, false, true })
    void checkAnswerMethodService_delete(boolean arg) {
        User user = new User();
        Mockito.when(repository.delete(user)).thenReturn(arg);

        boolean answer = service.delete(user);
        assertEquals(arg, answer);
    }

    //впроверка внутри метода service.update() должно вызываться один раз repository.update()
    @Test
    void verifyInMethodService_updateInInsideNeedChallangeRepository_update() {
        User user = new User();
        Mockito.when(repository.update(user)).thenReturn(user);
        service.update(user);
        Mockito.verify(repository, times(1)).update(user);
    }

    //когда аргумент null и когда user без id должно кидать исключение NullPointerException
    @Test
    void chekcWhenArgIsNullAndWhenUserDontHaveId() {
        assertThrows(NullPointerException.class, () -> service.update(null));
        assertThrows(NullPointerException.class, () -> service.update(new User()));
    }

    //проверка что метод service.update() возвращает старого user который ему передал база данных
    @Test
    void checkWhichObjectGiveMethodService_update() {
        User user = new User();
        Mockito.when(repository.update(user)).thenReturn(user);
        User user1 = service.update(user);
        assertEquals(user, user1);
    }

    //при вызове метода service.checkEmail(), должно вызваться 1 раз repository.checkEmail()
    @Test
    void verifyInMethodService_ChecmEmailInsideNeedToChallangeRepository_checkEmail() {
        service.checkEmail("some email");
        Mockito.verify(repository, times(1)).checkEmail("some email");
    }

    //в методе service.checkEmail() при аргументе null должно вернуть false
    @Test
    void whenArgIsNullInMethodService_checkEmail() {
        boolean answer = service.checkEmail(null);
        assertEquals(false, answer);
    }

    //проверка при вызове service.checkEmail() какой ответ возвращает база данных такой ответ должен вернуть метод от service
    @ParameterizedTest
    @ValueSource(booleans = { true, false, true })
    void checkAnswerFromMethodService_checkEmail(boolean arg) {
        Mockito.when(repository.checkEmail("some email")).thenReturn(arg);
        assertEquals(arg, service.checkEmail("some email"));
    }

    static Stream<String> argsEmail() {
        return Stream.of("один@mail.ru", "два@gmail.com",  "три@mail.ru");
    }
}