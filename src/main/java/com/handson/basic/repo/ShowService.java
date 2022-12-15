package com.handson.basic.repo;

import com.handson.basic.model.Member;
import com.handson.basic.model.Show;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShowService {

    @Autowired
    ShowRepository repository;

    public Iterable<Show> all() {
        return repository.findAll();
    }

    public Optional<Show> findById(Long id) {
        return repository.findById(id);
    }

    public Show save(Show show) {
        return repository.save(show);
    }

    public void delete(Show show) {
        repository.delete(show);
    }
}
