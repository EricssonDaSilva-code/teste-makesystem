package org.makesystem.model.dao;

import org.makesystem.model.entities.Person;

import java.util.Set;

public interface PersonDao {

    void insert(Person obj);

    void update(Person obj);

    void deleteById(Integer id);


    Set<Person> findAll();

}
