package org.makesystem.model.dao;

import org.makesystem.db.DB;
import org.makesystem.model.dao.impl.PersonDaoJDBC;

public class DaoFactory {

    public static PersonDao createPersonDao() {
        return new PersonDaoJDBC(DB.getConnetion());
    }
}
