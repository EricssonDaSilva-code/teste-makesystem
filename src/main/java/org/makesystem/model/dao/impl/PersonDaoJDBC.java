package org.makesystem.model.dao.impl;

import org.makesystem.db.DB;
import org.makesystem.db.DbException;
import org.makesystem.model.dao.PersonDao;
import org.makesystem.model.entities.Person;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class PersonDaoJDBC implements PersonDao {

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private Connection connection;

    public PersonDaoJDBC(Connection connetion) {
        this.connection = connetion;
    }

    @Override
    public void insert(Person obj) {
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(
                    "INSERT INTO tbl_persons "
                            + "(name_person, document_person, birthdate, phonenumber)"
                            + "VALUES"
                            + "(?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getName());
            st.setLong(2, obj.getDocument());
            st.setDate(3, java.sql.Date.valueOf(obj.getBirthDate()));
            st.setLong(4, obj.getPhoneNumber());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    System.out.println("Done! Id = " + id);
                }
                DB.CloseResultSet(rs);
            } else {
                throw new DbException("Unexpected error! No rows affected!");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.CloseStament(st);
        }
    }

    @Override
    public void update(Person obj) {
        PreparedStatement st = null;

        try {
            st = connection.prepareStatement(
                    "UPDATE tbl_person "
                            + "SET name_person = ?, document_person = ?, birthdate = ?, phonenumber = ? "
                            + "WHERE document_person = ?");

            st.setString(1, obj.getName());
            st.setLong(2, obj.getDocument());
            st.setDate(3, java.sql.Date.valueOf(obj.getBirthDate()));
            st.setLong(4, obj.getPhoneNumber());
            st.setLong(6, obj.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.CloseStament(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;

        try {
            st = connection.prepareStatement("DELETE FROM tbl_persons WHERE id = ?");

            st.setInt(1, id);

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.CloseStament(st);
        }

    }


    @Override
    public Set<Person> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = connection.prepareStatement(
                    "SELECT * FROM tbl_persons");

            rs = st.executeQuery();

            Set<Person> personSet = new HashSet<>();
            while (rs.next()) {
                Person obj = new Person();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name_person"));
                obj.setDocument(rs.getLong("document_person"));
                obj.setBirthDate(rs.getDate("birthdate").toLocalDate());
                obj.setPhoneNumber(rs.getLong("phonenumber"));
                personSet.add(obj);
            }
            return personSet;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.CloseStament(st);
            DB.CloseResultSet(rs);
        }
    }
}
