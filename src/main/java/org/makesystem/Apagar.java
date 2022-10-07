package org.makesystem;

import org.makesystem.model.dao.DaoFactory;
import org.makesystem.model.dao.PersonDao;

import java.nio.charset.StandardCharsets;
import java.util.Set;

public class Apagar {
    public static void main(String[] args) {
        PersonDao personDao = DaoFactory.createPersonDao();
        for (int i = 0; i < 200; i ++) {
            personDao.deleteById(i);
        }
    }

}
