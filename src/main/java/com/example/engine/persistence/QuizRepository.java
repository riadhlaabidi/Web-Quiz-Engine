package com.example.engine.persistence;

import com.example.engine.persistence.model.Quiz;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends PagingAndSortingRepository<Quiz, Long> {

}
