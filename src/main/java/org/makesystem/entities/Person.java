package org.makesystem.entities;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Objects;

public class Person implements Comparable<Person> {

    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private String name;
    private long document;
    private Date birthDate;
    private long phoneNumber;

    public Person() {

    }

    public Person(String name, long document, Date birthDate, long phoneNumber) {
        this.name = name;
        this.document = document;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDocument() {
        return document;
    }

    public void setDocument(long document) {
        this.document = document;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public static boolean testArray(String[] array) {
        String[] nameTeste = array[0].trim().split(" ");
        if (nameTeste.length < 2) {
            return false;
        }
        else {
            for (int i = 0; i < nameTeste.length; i ++) {
                if (nameTeste[i].replaceAll("[^a-zA-Z]*", "").length() <= 1) {
                    return false;
                }
            }
        }
        String documentTest = array[1].trim().replaceAll("[^0-9]*", "");
        if (!(documentTest.length() == 11 || documentTest.length() == 14)) {
            return false;
        }

        String phoneNumberTest = array[3].trim().replaceAll("[^0-9]*", "");
        if (phoneNumberTest.length() < 11) {
            return false;
        }

        String birthdateTest = array[2];
        String dateFormat = "dd/MM/yyyy";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat);
        try {
            LocalDate date = LocalDate.parse(birthdateTest, dateTimeFormatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }

    }

    @Override
    public String toString() {
        return name + ";" + document + ";" + birthDate + ";" + phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return document == person.document;
    }

    @Override
    public int hashCode() {
        return Objects.hash(document);
    }

    @Override
    public int compareTo(@NotNull Person other) {
        Integer document1 = (int) document;
        return document1.compareTo((int) other.document);
    }


}
