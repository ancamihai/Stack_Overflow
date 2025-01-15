package com.sd.stackoverflow.repository;

import com.sd.stackoverflow.entity.Answer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends CrudRepository<Answer,Long> {
    List<Answer> findByQuestion_QuestionId(Long questionId);
    List<Answer> findByUser_UserId(Long userId);

}
