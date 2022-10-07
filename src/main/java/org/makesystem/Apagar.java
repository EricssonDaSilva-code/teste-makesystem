package org.makesystem;

import org.makesystem.model.dao.DaoFactory;
import org.makesystem.model.dao.PersonDao;
import org.makesystem.model.entities.Person;

import java.util.Set;

public class Apagar {
    public static void main(String[] args) {
        PersonDao personDao = DaoFactory.createPersonDao();
        Set<Person> personSetDB = personDao.findAll();

        for (int i = 1124; i <= 3000 ; i ++) {
            personDao.deleteById(i);
        }

    }
}
