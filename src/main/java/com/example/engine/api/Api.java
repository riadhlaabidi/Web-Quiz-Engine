package com.example.engine.api;

import com.example.engine.model.UserAnswer;
import com.example.engine.persistence.QuizService;
import com.example.engine.model.Quiz;
import com.example.engine.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class Api {

    private final QuizService quizService;

    @Autowired
    public Api(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping(value = "/quizzes", consumes = "application/json")
    public Quiz createQuiz(@Valid @RequestBody Quiz quiz) {
        return quizService.add(quiz);
    }

    @GetMapping(value = "/quizzes/{id}")
    public Quiz getQuiz(@PathVariable long id) {
        return quizService.get(id);
    }

    @GetMapping(value = "/quizzes")
    public List<Quiz> getQuizzes() {
        return quizService.getQuizzes();
    }

    @PostMapping(value = "/quizzes/{id}/solve")
    public Response solveQuiz(@PathVariable long id, @RequestBody UserAnswer userAnswer) {
        return quizService.solve(id, userAnswer);
    }
}
