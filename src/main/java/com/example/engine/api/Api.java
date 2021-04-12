package com.example.engine.api;

import com.example.engine.model.QuizAnswer;
import com.example.engine.service.StorageService;
import com.example.engine.exception.QuizNotFoundException;
import com.example.engine.model.Quiz;
import com.example.engine.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/api")
public class Api {

    private final StorageService storage;

    @Autowired
    public Api(StorageService storage) {
        this.storage = storage;
    }

    @PostMapping(value = "/quizzes", consumes = "application/json")
    public Quiz createQuiz(@Valid @RequestBody Quiz quiz) {
        storage.add(quiz);
        return quiz;
    }

    @GetMapping(value = "/quizzes/{id}")
    public Quiz getQuiz(@PathVariable int id) {
        return storage.get(id).orElseThrow(QuizNotFoundException::new);
    }

    @GetMapping(value = "/quizzes")
    public Collection<Quiz> getQuizzes() {
        return storage.getQuizzes();
    }

    @PostMapping(value = "/quizzes/{id}/solve")
    public Response solveQuiz(@PathVariable int id, @RequestBody QuizAnswer quizAnswer) {
        return storage
                .get(id)
                .orElseThrow(QuizNotFoundException::new)
                .getAnswer()
                .equals(quizAnswer.getAnswer())
                ? new Response(true, Response.CORRECT_ANSWER)
                : new Response(false, Response.WRONG_ANSWER);
    }
}