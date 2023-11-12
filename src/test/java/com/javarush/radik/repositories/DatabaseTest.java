package com.javarush.radik.repositories;

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
class DatabaseTest {
    @Mock
    User userForTestCreate;
    @Mock
    private Logger log;
    @Mock
    private AtomicLong userID;
    @Mock
    private ConcurrentMap<Long, User> users;
    private Database database;
    @BeforeEach
    void init() {
        database = Database.getInstance();
        Whitebox.setInternalState(database, "log", log);
        Whitebox.setInternalState(database, "userID", userID);
        Whitebox.setInternalState(database, "users", users);
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
        Mockito.verify(users, times(1)).get(id);
    }
    //если БД не содержит обьект по задоному id то он вовращает null, если есть то возвращает user по его id
    @Test
    void checkWhatReturnRepositoryWhenExistUserWithIdAndNotExist() {
        ConcurrentMap<Long, User> obj = new ConcurrentHashMap<>();
        User user = new User();
        user.setId(1);
        user.setEmail("some email");
        obj.put(1L, user);
        Whitebox.setInternalState(database, "users", obj);

        User userNull = database.byFindId(3);
        assertNull(userNull);

        User userExist = database.byFindId(1);
        assertNotNull(userExist);

        assertEquals(user, database.byFindId(1));
    }

    //проверка при вызове database.size() внутри должен вызывать метод users.size()
    @Test
    void verifyRepository_sizeInsideDatabase_size() {
        database.size();
        Mockito.verify(users, times(1)).size();
    }
    //метод database.size() возвращает то число что и метод users.size() внутри метода database.size()
    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3 })
    void size(int arg) {
        Mockito.when(users.size()).thenReturn(arg);

        assertEquals(arg, database.size());
    }

    //проверка если database.delete() вызывает users.remove() с нужным аргументом, только 1 вызов
    @Test
    void verifyIfMethodDatabase_deleteChallangeRepository_remove() {
        User user = new User();
        user.setId(1);
        database.delete(user);
        Mockito.verify(users, times(1)).remove(user.getId());
    }

    //проверка при аргументе null метод delete()
    @Test
    void checkIfArgNullInMethodDelete() {
        assertThrows(NullPointerException.class, () -> database.delete(null));
    }

    //когда user есть в БД ответ приходит true, если отсуствует то false
    @Test
    void checkAnswerMethodDatabase_delete() {
        User user = new User();
        user.setId(1);
        Mockito.when(users.remove(user.getId())).thenReturn(user);

        boolean answer = database.delete(user);
        assertEquals(true, answer);

        User user1 = new User();
        user.setId(2);
        Mockito.when(users.remove(user1.getId())).thenReturn(null);

        boolean answer1 = database.delete(user1);
        assertEquals(false, answer1);
    }

    //впроверка внутри метода database.update() должно вызываться один раз users.replace()
    @Test
    void verifyInMethodDatabase_updateInInsideNeedChallangeRepository_replace() {
        User user = new User();
        user.setId(1);
        Mockito.when(users.replace(user.getId(), user)).thenReturn(user);
        database.update(user);
        Mockito.verify(users, times(1)).replace(user.getId(), user);
    }

    //когда аргумент null должно кидать исключение NullPointerException
    @Test
    void chekcWhenArgIsNull() {
        assertThrows(NullPointerException.class, () -> database.update(null));
    }

    //проверка что метод database.update() возвращает старого user который ему передал база данных
    @Test
    void checkWhichObjectGiveMethodDatabase_update() {
        User user = new User();
        user.setId(1);
        Mockito.when(users.replace(user.getId(), user)).thenReturn(user);
        User user1 = database.update(user);
        assertEquals(user, user1);
    }
    //проверка если вызывает нужные матоды врнутри create()
    @Test
    void verifyIfChallangeTheNecessaryMethodsInside() {
        Mockito.when(userID.incrementAndGet()).thenReturn(1L);
        database.create(userForTestCreate);

        Mockito.verify(userID).incrementAndGet();
        Mockito.verify(userForTestCreate).setId(1L);
        Mockito.verify(users).put(1L, userForTestCreate);
    }
    //проверка на арг null, выбрасывает исключение NullPointerException
    @Test
    void ifArgNullInMethodCreate() {
        assertThrows(NullPointerException.class, () -> database.create(null));
    }

    //метод byFindEmail если арг null то кидает исключение NullPointerException
    @Test
    void whenArgNullInMethodByFindEmail() {
        assertThrows(NullPointerException.class, () -> database.byFindEmail(null));
    }

    //метод byFindEmail() если нет с таким email user то вернет null,
    //если совпадает email который есть в БД должен вернуть этого user
    @Test
    void whenNotExistUserWithEmailAndExist() {
        Map<Long, User> obj = new HashMap<>();
        User user = new User();
        user.setId(1);
        user.setEmail("some email");
        obj.put(1L, user);
        Mockito.when(users.entrySet()).thenReturn(obj.entrySet());

        User user1 = database.byFindEmail("email");
        assertNull(user1);

        User user2 = database.byFindEmail("some email");
        assertNotNull(user2);
        assertEquals(user, user2);
    }
    //проверяем метод checkEmail при аргументе null, не совраденине email, и когда находит email в базе данных
    @Test
    void whenArgIsNullAndWhenExistAndNotExistEmail() {
        Map<Long, User> obj = new HashMap<>();
        User user = new User();
        user.setId(1);
        user.setEmail("some email");
        obj.put(1L, user);
        Mockito.when(users.values()).thenReturn(obj.values());

        assertEquals(false, database.checkEmail(null));
        assertEquals(false, database.checkEmail("email"));
        assertEquals(true, database.checkEmail("some email"));
    }

    @Test
    void getAll() {
        database.getAll();
        Mockito.verify(users).values();
    }


}