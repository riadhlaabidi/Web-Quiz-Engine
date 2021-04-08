package com.example.engine;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Api {

    @GetMapping(value = "/quiz")
    public Quiz getQuiz() {
        return new Quiz(
                "The Java Logo",
                "What is depicted on the Java logo?",
                new String[] {"Robot","Tea leaf","Cup of coffee","Bug"},
                2
        );
    }

    @PostMapping(value = "/quiz")
    public Response solveQuiz(@RequestParam final int answer) {
        return answer == getQuiz().getCorrectAnswer()
                ? new Response(true, "Congratulations, you're right!")
                : new Response(false, "Wrong answer! Please, try again.");
    }
}
