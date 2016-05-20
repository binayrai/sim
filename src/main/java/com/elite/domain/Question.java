package com.elite.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by binaychap on 2/23/2016.
 */
@Entity
public class Question {
    private long id;
    private String name;
    private List<QuestionOption> questionOptions;

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


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "question", cascade = CascadeType.PERSIST)
    public List<QuestionOption> getQuestionOptions() {
        return questionOptions;
    }

    public void setQuestionOptions(List<QuestionOption> questionOptions) {
        this.questionOptions = questionOptions;
    }

    public void addOption(QuestionOption option) {
        if (this.questionOptions == null) {
            this.questionOptions = new ArrayList<>();
        }
        this.questionOptions.add(option);
        option.setQuestion(this);
    }
}
