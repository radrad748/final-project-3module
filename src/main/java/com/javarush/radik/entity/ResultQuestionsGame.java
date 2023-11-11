package com.javarush.radik.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.StandardToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ResultQuestionsGame {
    private long id;
    private long userId;
    private int bestResult;
    private int count;
    private QuestionLevel level;

    public ResultQuestionsGame(long userId, QuestionLevel level) {
        this.userId = userId;
        this.level = level;
        this.bestResult = 0;
        this.count = 0;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getBestResult() {
        return bestResult;
    }

    public void setBestResult(int bestResult) {
        this.bestResult = bestResult;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public QuestionLevel getLevel() {
        return level;
    }

    public void setLevel(QuestionLevel level) {
        this.level = level;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        StandardToStringStyle style = new StandardToStringStyle();
        style.setUseClassName(false);
        style.setUseIdentityHashCode(false);
        style.setContentStart("this is the result of a user with an id=" + userId);
        style.setContentEnd(".");
        style.setFieldSeparator("; ");
        style.setFieldNameValueSeparator(" is ");

        return ToStringBuilder.reflectionToString(this, style);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ResultQuestionsGame that = (ResultQuestionsGame) o;

        return new EqualsBuilder().append(userId, that.userId).append(bestResult, that.bestResult).append(count, that.count).append(level, that.level).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(userId).append(bestResult).append(count).append(level).toHashCode();
    }
}
