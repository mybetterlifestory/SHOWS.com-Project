package com.handson.basic.repo;

import com.handson.basic.model.Member;
import com.handson.basic.model.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MemberRepository extends CrudRepository<Member,Long> {
//    List<Student> findAllBySatScoreGreaterThan(Integer satScore);

}
