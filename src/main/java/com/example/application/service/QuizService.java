package com.example.application.service;

import com.example.application.entity.QuizQuestion;
import com.example.application.game.URLhandler;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class QuizService {

    private final RestTemplate restTemplate;

    private final URLhandler urLhandler;


    public QuizService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.urLhandler = new URLhandler();
    }

    public URLhandler getUrLhandler() {
        return urLhandler;
    }

    public List<QuizQuestion> getQuizQuestions() {
        try {
            ResponseEntity<List<QuizQuestion>> response = restTemplate.exchange(
                    urLhandler.getUpdatedUrl(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    }
            );
            return response.getBody();

        } catch (Exception e) {
            // Handle exceptions, e.g., network errors, API errors
            e.printStackTrace();
            return null;
        }
    }

    public QuizQuestion getQuizQuestion() {
        try {
            ResponseEntity<List<QuizQuestion>> response = restTemplate.exchange(
                    urLhandler.getUpdatedUrl(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    }
            );

            System.out.println("FROM GET QUIZ QUESTION "+response.getBody().get(0));
            return response.getBody().get(0);

        } catch (Exception e) {
            // Handle exceptions, e.g., network errors, API errors
            e.printStackTrace();
            return null;
        }
    }

    public static Boolean checkAnswer(String correctAnswer, String userAnswer) {
        return correctAnswer.equals(userAnswer);
    }
}
