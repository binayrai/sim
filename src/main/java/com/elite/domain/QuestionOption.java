package com.elite.domain;

import javax.persistence.*;

/**
 * Created by binaychap on 2/26/2016.
 */
@Entity
public class QuestionOption {
    private long id;
    private String name;
    private Question question;

    @Id
    @GeneratedValue
    @Column
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
