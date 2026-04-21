
package com.example.quiz;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import java.util.*;

@Controller
@SessionAttributes("questions")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/")
    public String home() { return "index"; }

    @PostMapping("/start")
    public String start(@RequestParam String topic, Model model) {
        model.addAttribute("questions", quizService.generateQuestions(topic));
        return "quiz";
    }

    @PostMapping("/submit")
    public String submit(@RequestParam Integer[] answers,
                         @ModelAttribute("questions") List<Question> questions,
                         Model model,
                         SessionStatus status) {
        model.addAttribute("result", quizService.evaluate(questions, List.of(answers)));
        status.setComplete();
        return "result";
    }
}
