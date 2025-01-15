package com.sd.stackoverflow.repository;

import com.sd.stackoverflow.entity.VoteAnswer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteAnswerRepository extends CrudRepository<VoteAnswer,Long> {
    VoteAnswer findByAnswer_AnswerIdAndUser_UserId(Long answerId, Long userId);
    List<VoteAnswer> findByAnswer_AnswerId(Long answerId);
    List<VoteAnswer> findByUser_UserId(Long userId);
}
