package com.handson.basic.repo;

import com.handson.basic.model.Member;
//import com.handson.basic.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    MemberRepository repository;

//    public List<Student> getStudentWithSatHigherThan(Integer sat) {
//        return repository.findAllBySatScoreGreaterThan(sat);
//    }

    public Iterable<Member> all() {
        return repository.findAll();
    }

    public Optional<Member> findById(Long id) {
        return repository.findById(id);
    }


    public Member save(Member member) {
        return repository.save(member);
    }

    public void delete(Member member) {
        repository.delete(member);
    }

}
