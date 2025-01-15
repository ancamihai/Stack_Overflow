package com.sd.stackoverflow.repository;

import com.sd.stackoverflow.entity.VoteQuestion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteQuestionRepository extends CrudRepository<VoteQuestion,Long> {
    VoteQuestion findByQuestion_QuestionIdAndUser_UserId(Long questionId, Long userId);
    List<VoteQuestion> findByQuestion_QuestionId(Long questionId);
}
