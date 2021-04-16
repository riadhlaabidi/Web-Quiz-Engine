package com.example.engine.persistence;

import com.example.engine.exception.QuizNotFoundException;
import com.example.engine.persistence.model.Quiz;
import com.example.engine.api.SolveResponse;
import com.example.engine.api.UserAnswer;
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

    public void delete(long id) {
        if (quizRepository.existsById(id)) {
            quizRepository.deleteById(id);
            return;
        }
        throw new QuizNotFoundException();
    }

    public List<Quiz> getQuizzes() {
        List<Quiz> allQuizzes = new ArrayList<>();
        quizRepository.findAll().forEach(allQuizzes::add);
        return allQuizzes;
    }

    public boolean solveQuiz(long id, UserAnswer userAnswer) {
        return get(id).getAnswer().equals(userAnswer.getAnswer());
    }
}
