
package com.example.quiz;

public class QuizResult {
    private int score;
    private int total;

    public QuizResult(int score, int total) {
        this.score = score;
        this.total = total;
    }
    public int getScore() { return score; }
    public int getTotal() { return total; }
    public double getPercentage() { return (score * 100.0) / total; }
}
