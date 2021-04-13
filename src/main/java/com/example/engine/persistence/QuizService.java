package com.example.engine.persistence;

import com.example.engine.exception.QuizNotFoundException;
import com.example.engine.model.Quiz;
import com.example.engine.model.Response;
import com.example.engine.model.UserAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {

    private final QuizRepository quizRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public Quiz add(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public Quiz get(long id) {
        return quizRepository
                .findById(id)
                .orElseThrow(QuizNotFoundException::new);
    }

    public List<Quiz> getQuizzes() {
        List<Quiz> allQuizzes = new ArrayList<>();
        quizRepository.findAll().forEach(allQuizzes::add);
        return allQuizzes;
    }

    public Response solve(long id, UserAnswer userAnswer) {
        return this
                .get(id)
                .getAnswer()
                .equals(userAnswer.getAnswer())
                ? new Response(true, Response.CORRECT_ANSWER)
                : new Response(false, Response.WRONG_ANSWER);
    }
}
