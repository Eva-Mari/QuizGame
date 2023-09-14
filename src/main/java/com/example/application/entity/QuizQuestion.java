package com.example.application.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record QuizQuestion(String category, String id, String correctAnswer,
                           List<String> incorrectAnswers, Question question, String difficulty) {
}
