package com.sd.stackoverflow.repository;

import com.sd.stackoverflow.entity.Salt;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaltRepository extends CrudRepository<Salt,Long> {

    Salt findByUser_UserId(Long userId);
}
