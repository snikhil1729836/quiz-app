
package com.example.quiz;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class QuizService {

    @Value("${openai.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();
    private final Map<String, List<Question>> cache = new ConcurrentHashMap<>();

    public List<Question> generateQuestions(String topic) {
        if (cache.containsKey(topic)) return cache.get(topic);

        String prompt = "Generate exactly 10 multiple choice questions on topic: " + topic +
                ". Return strict JSON with fields question, options (4), correctIndex.";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> body = Map.of(
                "model", "gpt-4o-mini",
                "messages", List.of(Map.of("role", "user", "content", prompt))
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(
                "https://api.openai.com/v1/chat/completions", request, Map.class);

        try {
            String content = (String)((Map)((Map)((List)response.getBody()
                    .get("choices")).get(0)).get("message")).get("content");

            OpenAIResponse quiz = mapper.readValue(content, OpenAIResponse.class);
            cache.put(topic, quiz.questions);
            return quiz.questions;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public QuizResult evaluate(List<Question> questions, List<Integer> answers) {
        int score = 0;
        for (int i = 0; i < answers.size(); i++) {
            if (questions.get(i).getCorrectIndex() == answers.get(i)) score++;
        }
        return new QuizResult(score, questions.size());
    }
}
