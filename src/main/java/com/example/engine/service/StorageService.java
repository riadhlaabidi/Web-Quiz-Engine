package com.example.engine.service;

import com.example.engine.model.Quiz;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class StorageService {

    private final Map<Integer, Quiz> quizzes = new HashMap<>();

    public void add(Quiz quiz) {
        quizzes.put(quiz.getId(), quiz);
    }

    public Optional<Quiz> get(int id) {
        return Optional.ofNullable(quizzes.get(id));
    }

    public Collection<Quiz> getQuizzes() {
        return quizzes.values();
    }
}
