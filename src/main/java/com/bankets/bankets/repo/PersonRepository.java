package com.bankets.bankets.repo;

import com.bankets.bankets.models.Person;
import org.springframework.data.repository.CrudRepository;
public interface PersonRepository extends CrudRepository<Person, Long> {
}
