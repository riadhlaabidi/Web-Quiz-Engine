package com.example.engine.persistence;

import com.example.engine.exception.QuizNotFoundException;
import com.example.engine.persistence.model.Quiz;
import com.example.engine.api.UserAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


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

    public boolean solve(long id, UserAnswer userAnswer) {
        return get(id).getAnswer().equals(userAnswer.getAnswer());
    }

    public Page<Quiz> getAllQuizzes(int pageNumber, int pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return quizRepository.findAll(paging);
    }
}
