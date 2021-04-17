package com.example.engine.api;

import com.example.engine.persistence.QuizCompletionService;
import com.example.engine.persistence.QuizService;
import com.example.engine.persistence.UserService;
import com.example.engine.persistence.model.Quiz;
import com.example.engine.persistence.model.QuizCompletion;
import com.example.engine.persistence.model.User;
import com.example.engine.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api")
public class Api {

    private final UserService userService;
    private final QuizService quizService;
    private final QuizCompletionService quizCompletionService;

    @Autowired
    public Api(UserService userService, QuizService quizService,
               QuizCompletionService quizCompletionService) {
        this.userService = userService;
        this.quizService = quizService;
        this.quizCompletionService = quizCompletionService;
    }

    @PostMapping(value = "/register", consumes = "application/json")
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

    @GetMapping(value = "/quizzes")
    public Page<Quiz> getAllQuizzes(
            @Min(0) @RequestParam(defaultValue = "0") int page,
            @Min(10) @Max(30) @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        return quizService.getAllQuizzes(page, pageSize, sortBy);
    }

    @PostMapping(value = "/quizzes/{id}/solve", consumes = "application/json")
    public SolveResponse solveQuiz(@PathVariable long id,
                                   @RequestBody UserAnswer userAnswer,
                                   @AuthenticationPrincipal UserPrincipal userPrincipal) {

        if (quizService.solve(id, userAnswer)) {
            QuizCompletion quizCompletion = new QuizCompletion(
                    userPrincipal.user().getEmail(),
                    id,
                    LocalDateTime.now(),
                    userPrincipal.user(),
                    quizService.get(id)
            );
            quizCompletionService.add(quizCompletion);
            return new SolveResponse(true, SolveResponse.CORRECT_ANSWER);
        }
        return new SolveResponse(false, SolveResponse.WRONG_ANSWER);
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

    @GetMapping("/quizzes/completed")
    public Page<QuizCompletion> getUserCompletions(
            @Min(0) @RequestParam(defaultValue = "0") int page,
            @Min(10) @Max(30) @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "completionDate") String sortBy,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        return quizCompletionService.getCompletions(
                userPrincipal.user().getEmail(),
                page,
                pageSize,
                sortBy
        );
    }
}

