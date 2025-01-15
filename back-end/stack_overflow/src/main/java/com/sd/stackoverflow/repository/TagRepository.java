package com.sd.stackoverflow.repository;

import com.sd.stackoverflow.entity.Tag;
import com.sd.stackoverflow.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {
    Tag findByName(String name);
}
