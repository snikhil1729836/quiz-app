
package com.example.quiz;

import java.util.List;

public class Question {
    private String question;
    private List<String> options;
    private int correctIndex;

    public String getQuestion() { return question; }
    public List<String> getOptions() { return options; }
    public int getCorrectIndex() { return correctIndex; }
}
