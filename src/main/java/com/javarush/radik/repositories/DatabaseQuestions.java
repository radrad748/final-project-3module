package com.javarush.radik.repositories;

import com.javarush.radik.entity.Question;
import com.javarush.radik.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public class DatabaseQuestions implements Repository<Question>{

    private static final DatabaseQuestions instance = new DatabaseQuestions();
    private final Logger log = LoggerFactory.getLogger(DatabaseQuestions.class);
    private final AtomicLong questionId = new AtomicLong(System.currentTimeMillis());
    private final ConcurrentMap<Long, Question> questions = new ConcurrentHashMap<>();

    @Override
    public Question byFindId(long id) {
        Question question = questions.get(id);
        if (question == null) log.info("Запрос найти question по ID = {}, такого question нет в базе данных", id);
        else log.info("Запрос найти question по ID = {}, запрос успешно выполнен", id);
        return question;
    }

    @Override
    public void create(Question question) {
        long id = questionId.incrementAndGet();
        question.setId(id);
        questions.put(id, question);
        log.info("Запрос создать question: ID = {}, запрос успешно выполнен", id);
    }

    @Override
    public Question update(Question question) {
        Question oldQuestion = questions.replace(question.getId(), question);
        if (oldQuestion == null){
            log.info("Запрос изменить question: ID = {}, question по такому ID = {} нет в базе данных",
                    question.getId(), question.getId());
            return oldQuestion;
        } else {
            log.info("Запрос изменить question: ID = {} на question: ID = {}, запрос успешно выполнен",
                    question.getId(), question.getId());
            return oldQuestion;
        }
    }

    @Override
    public boolean delete(Question question) {
        Question questionDelete = questions.remove(question.getId());
        if (questionDelete == null) {
            log.info("Запрос удалить question: ID = {}, question по такому ID = {} нет в базе данных",
                    question.getId(), question.getId());
            return false;
        } else {
            log.info("Запрос удалить question: ID = {}, запрос успешно выполнен",
                    question.getId());
            return true;
        }
    }

    @Override
    public Collection<Question> getAll() {return questions.values();}

    public static DatabaseQuestions getInstance() {return instance;}

    private DatabaseQuestions(){
        Question q1 = new Question();
        q1.setId(1);
        q1.setQuestion("Какое Блюдо традиционно подаеться на десерт во время Нобелевского банкета?");
        List<String> answers1 = new ArrayList<>(Arrays.asList("мороженое", "брауни", "чизкейк", "шарлотка"));
        q1.setAnswers(answers1);
        q1.setPathImage("/images/question_image/q1.png");
        q1.setIndexTrueAnswer(0);
        q1.setTrueAnswer("Верно: ты супер!");
        q1.setFalseAnswer("Неверно: давай соберись)");

        Question q2 = new Question();
        q2.setId(2);
        q2.setQuestion("Если из всех букв 'ДОЧАВУКИН' составить слово, то к чему его можно отнести?");
        List<String> answers2 = new ArrayList<>(Arrays.asList("автомобили", "животные", "растения", "спортивные игры"));
        q2.setAnswers(answers2);
        q2.setPathImage("/images/question_image/q2.png");
        q2.setIndexTrueAnswer(2);
        q2.setTrueAnswer("Верно: из данных букв можно собрать слово 'Одуванчик'");
        q2.setFalseAnswer("Неверно: ну как ты так....");

        Question q3 = new Question();
        q3.setId(3);
        q3.setQuestion("Из чего строят гнезда совы?");
        List<String> answers3 = new ArrayList<>(Arrays.asList("из глины", "из веток", "из травы", "совы не строят гнезда"));
        q3.setAnswers(answers3);
        q3.setPathImage("/images/question_image/q3.png");
        q3.setIndexTrueAnswer(3);
        q3.setTrueAnswer("Верно: Красава;)");
        q3.setFalseAnswer("Неверно: думаю тебе стоит немного почитать о мире животных :D");

        Question q4 = new Question();
        q4.setId(4);
        q4.setQuestion("Как написать правильно?");
        List<String> answers4 = new ArrayList<>(Arrays.asList("потчивала яствами", "потчевала яствами", "потчевала явствами", "подчивала явствами"));
        q4.setAnswers(answers4);
        q4.setPathImage("/images/question_image/q4.png");
        q4.setIndexTrueAnswer(1);
        q4.setTrueAnswer("Верно: да ты гуру!!!");
        q4.setFalseAnswer("Неверно: ладно не волнуйся... сделаем вид что этого вопроса не было ;)");

        Question q5 = new Question();
        q5.setId(5);
        q5.setQuestion("Существуют ли растения - альбиносы?");
        List<String> answers5 = new ArrayList<>(Arrays.asList("да", "нет"));
        q5.setAnswers(answers5);
        q5.setPathImage("/images/question_image/q5.png");
        q5.setIndexTrueAnswer(0);
        q5.setTrueAnswer("Верно: откуда ты мог это знать?)");
        q5.setFalseAnswer("Неверно: теперь будешь это знать!");

        Question q6 = new Question();
        q6.setId(6);
        q6.setQuestion("Что делают с помидорами в завершении праздника Томатина в Испании?");
        List<String> answers6 = new ArrayList<>(Arrays.asList("помидорами кидаются", "помидоры сажают", "помидорами украшают здания", "дарят друг другу помидоры"));
        q6.setAnswers(answers6);
        q6.setPathImage("/images/question_image/q6.png");
        q6.setIndexTrueAnswer(0);
        q6.setTrueAnswer("Верно: Класс!");
        q6.setFalseAnswer("Неверно: Мда.. тут не дегкие вопросы, все по серьезному");

        Question q7 = new Question();
        q7.setId(7);
        q7.setQuestion("Что или кто такое 'Полудница'?");
        List<String> answers7 = new ArrayList<>(Arrays.asList("так называли обеденный перерыв", "русалка, которая поет в полдень", "дух, наказывающий тех, кто работает в полдень"));
        q7.setAnswers(answers7);
        q7.setPathImage("/images/question_image/q7.png");
        q7.setIndexTrueAnswer(2);
        q7.setTrueAnswer("Верно: Воу полегче..!");
        q7.setFalseAnswer("Неверно: Ну будешь знать.. ктстати хорошая отмазка чтоб не работать в полдень :D");

        Question q8 = new Question();
        q8.setId(8);
        q8.setQuestion("Когда день зимнего солнцестояния?");
        List<String> answers8 = new ArrayList<>(Arrays.asList("19 - 20 января", "1 - 2 декабря", "1 - 2 февраля", "21 - 22 декабря"));
        q8.setAnswers(answers8);
        q8.setPathImage("/images/question_image/q8.png");
        q8.setIndexTrueAnswer(3);
        q8.setTrueAnswer("Верно: Ай молодец!!");
        q8.setFalseAnswer("Неверно: Не ну ты серьезно..???");

        Question q9 = new Question();
        q9.setId(9);
        q9.setQuestion("Ютуб появился сравнительно недавно, в 2015 году. Скажите, а самый первый видеоролик, размещенный в нем, где был снят?");
        List<String> answers9 = new ArrayList<>(Arrays.asList("офис", "зоопарк", "стадион", "торговый центр"));
        q9.setAnswers(answers9);
        q9.setPathImage("/images/question_image/q9.png");
        q9.setIndexTrueAnswer(1);
        q9.setTrueAnswer("Верно: Тебе просто повезло...");
        q9.setFalseAnswer("Неверно: ты можешь лучше, я знаю!");

        Question q10 = new Question();
        q10.setId(10);
        q10.setQuestion("В каком городе не проходили игры чемпионата мира по футболу 2018?");
        List<String> answers10 = new ArrayList<>(Arrays.asList("Калининград", "Нижний Новгород", "Самара", "Саранск", "Ярославль"));
        q10.setAnswers(answers10);
        q10.setPathImage("/images/question_image/q10.png");
        q10.setIndexTrueAnswer(4);
        q10.setTrueAnswer("Верно: Дааа... ты лучший");
        q10.setFalseAnswer("Неверно: Да ладно.. такое не знать??");

        questions.put(q1.getId(), q1);
        questions.put(q2.getId(), q2);
        questions.put(q3.getId(), q3);
        questions.put(q4.getId(), q4);
        questions.put(q5.getId(), q5);
        questions.put(q6.getId(), q6);
        questions.put(q7.getId(), q7);
        questions.put(q8.getId(), q8);
        questions.put(q9.getId(), q9);
        questions.put(q10.getId(), q10);
    }

}
