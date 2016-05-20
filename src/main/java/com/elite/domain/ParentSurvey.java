package com.elite.domain;

import javax.persistence.*;

/**
 * Created by binaychap on 2/23/2016.
 */

@Entity
public class ParentSurvey {
    private long id;
    private Parent parent;
    private Question question;
    private QuestionOption option;
    private long questionHierarchy;

    @Id
    @GeneratedValue
    @Column
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @OneToOne
    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    @OneToOne
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @OneToOne
    public QuestionOption getOption() {
        return option;
    }

    public void setOption(QuestionOption option) {
        this.option = option;
    }

    public long getQuestionHierarchy() {
        return questionHierarchy;
    }

    public void setQuestionHierarchy(long questionHierarchy) {
        this.questionHierarchy = questionHierarchy;
    }
}
