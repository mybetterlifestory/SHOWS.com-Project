package com.handson.basic.repo;

import com.handson.basic.model.Member;
import com.handson.basic.model.Show;
import org.springframework.data.repository.CrudRepository;

public interface ShowRepository extends CrudRepository<Show,Long> {

}
