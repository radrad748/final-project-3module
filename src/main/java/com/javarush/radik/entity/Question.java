package com.javarush.radik.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.StandardToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class Question {
    private long id;
    private String question;
    private List<String> answers;
    private int indexTrueAnswer;
    private String pathImage;
    private String trueAnswer;
    private String falseAnswer;
    private QuestionLevel level;

    public Question(){}

    public Question(String question, List<String> answers, int indexTrueAnswer) {
        this.question = question;
        this.answers = answers;
        this.indexTrueAnswer = indexTrueAnswer;
    }

    public Question(String question, List<String> answers, int indexTrueAnswer, String pathImage) {
        this.question = question;
        this.answers = answers;
        this.indexTrueAnswer = indexTrueAnswer;
        this.pathImage = pathImage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public int getIndexTrueAnswer() {
        return indexTrueAnswer;
    }

    public void setIndexTrueAnswer(int indexTrueAnswer) {
        this.indexTrueAnswer = indexTrueAnswer;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    public String getTrueAnswer() {
        return trueAnswer;
    }

    public void setTrueAnswer(String trueAnswer) {
        this.trueAnswer = trueAnswer;
    }

    public String getFalseAnswer() {
        return falseAnswer;
    }

    public void setFalseAnswer(String falseAnswer) {
        this.falseAnswer = falseAnswer;
    }

    public QuestionLevel getLevel() {
        return level;
    }

    public void setLevel(QuestionLevel level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Question question1 = (Question) o;

        return new EqualsBuilder().append(id, question1.id).append(indexTrueAnswer, question1.indexTrueAnswer).append(question, question1.question).append(answers, question1.answers).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(question).append(answers).append(indexTrueAnswer).toHashCode();
    }

    @Override
    public String toString() {
        StandardToStringStyle style = new StandardToStringStyle();
        style.setUseClassName(false);
        style.setUseIdentityHashCode(false);
        style.setContentStart("This question's ");
        style.setContentEnd(".");
        style.setFieldSeparator("; ");
        style.setFieldNameValueSeparator(" is ");

        return ToStringBuilder.reflectionToString(this, style);
    }
}
