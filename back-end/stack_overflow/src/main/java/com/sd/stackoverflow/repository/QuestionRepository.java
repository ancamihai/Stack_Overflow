package com.sd.stackoverflow.repository;

import com.sd.stackoverflow.entity.Question;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends CrudRepository<Question,Long> {
    List<Question> findAllByOrderByCreationDateTimeDesc();
    List<Question> findByTagsName(String tagName);
    List<Question> findByUser_UserId(Long userId);
    List<Question> findByUser_Username(String username);
    List<Question> findByTitleContainingIgnoreCase(String title);
}
