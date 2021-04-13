package com.example.engine.persistence;

import com.example.engine.model.Quiz;
import org.springframework.data.repository.CrudRepository;


public interface QuizRepository extends CrudRepository<Quiz, Long> {

}
