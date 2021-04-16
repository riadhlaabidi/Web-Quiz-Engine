package com.example.engine.api;

import com.example.engine.persistence.QuizService;
import com.example.engine.persistence.UserService;
import com.example.engine.persistence.model.Quiz;
import com.example.engine.persistence.model.User;
import com.example.engine.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class Api {

    private final UserService userService;
    private final QuizService quizService;

    @Autowired
    public Api(UserService userService, QuizService quizService) {
        this.userService = userService;
        this.quizService = quizService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<String> registerNewUser(@Valid @RequestBody User user) {
        userService.registerNewUser(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/quizzes", consumes = "application/json")
    public Quiz createQuiz(@Valid @RequestBody Quiz quiz,
                           @AuthenticationPrincipal UserPrincipal userPrincipal) {
        quiz.setUser(userPrincipal.user());
        return quizService.add(quiz);
    }

    @GetMapping(value = "/quizzes/{id}")
    public Quiz getQuiz(@PathVariable long id) {
        return quizService.get(id);
    }

    @DeleteMapping("/quizzes/{id}")
    public ResponseEntity<String> delete(@PathVariable long id,
                                         @AuthenticationPrincipal UserPrincipal userPrincipal) {
        if (quizService.get(id).getUser().getEmail()
                .equals(userPrincipal.user().getEmail())) {
            quizService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping(value = "/quizzes")
    public List<Quiz> getQuizzes() {
        return quizService.getQuizzes();
    }

    @PostMapping(value = "/quizzes/{id}/solve")
    public SolveResponse solveQuiz(@PathVariable long id, @RequestBody UserAnswer userAnswer) {
        return quizService.solveQuiz(id, userAnswer)
                ? new SolveResponse(true, SolveResponse.CORRECT_ANSWER)
                : new SolveResponse(false, SolveResponse.WRONG_ANSWER);
    }
}
