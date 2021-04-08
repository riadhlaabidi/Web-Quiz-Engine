package com.example.engine.api;

import com.example.engine.exception.QuizNotFoundException;
import com.example.engine.model.Quiz;
import com.example.engine.model.Response;
import com.example.engine.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api")
public class Api {
    private final StorageService storage;

    @Autowired
    public Api(StorageService storage) {
        this.storage = storage;
    }

    @GetMapping(value = "/quizzes/{id}")
    public Quiz getQuiz(@PathVariable int id) {
        return storage.get(id).orElseThrow(QuizNotFoundException::new);
    }

    @GetMapping(value = "/quizzes")
    public Collection<Quiz> getQuizzes() {
        return storage.getQuizzes();
    }

    @PostMapping(value = "/quizzes", consumes = "application/json")
    public Quiz createQuiz(@RequestBody Quiz quiz) {
        storage.add(quiz);
        return quiz;
    }

    @PostMapping(value = "/quizzes/{id}/solve")
    public Response solveQuiz(@PathVariable int id, @RequestParam int answer) {
        return storage
                .get(id)
                .orElseThrow(QuizNotFoundException::new)
                .getAnswer() == answer
                ? new Response(true, "Congratulations, you're right!")
                : new Response(false, "Wrong answer! Please, try again.");
    }
}
