package org.makesystem.model.entities;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.Set;

public class Person implements Comparable<Person>, Serializable {

    public static final long serialVersionUID = 1L;

    private static DateTimeFormatter dtf= DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Integer id;
    private String name;
    private String document;
    private LocalDate birthDate;
    private String phoneNumber;

    public Person() {

    }

    public Person(String name, String document, LocalDate birthDate, String phoneNumber) {
        this.name = name;
        this.document = document;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
    }

    public Person(Integer id, String name, String document, LocalDate birthDate, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.document = document;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public static boolean nameTest(String name) {
        String[] nameTeste = name.trim().split(" ");
        if (nameTeste.length < 2) {
            return false;
        } else {
            for (int i = 0; i < nameTeste.length; i++) {
                if (nameTeste[i].replaceAll("[^a-zA-Z]*", "").length() <= 1) {
                    return false;
                }
            }
        }
        return true;
    }
    public static boolean phoneNumberTest(String phoneNumber) {
        String phoneNumberTest = phoneNumber.trim().replaceAll("[^0-9]*", "");
        if (phoneNumberTest.length() < 11) {
            return false;
        }
        return true;
    }
    public static boolean documentTest(String document) {
        String documentTest = document.trim().replaceAll("[^0-9]*", "");
        if (documentTest.length() == 11 || documentTest.length() == 14) {
            return true;
        }
        else {
            return false;
        }

    }
    public static boolean birthDayTest(String birthday) {
        String birthdateTest = birthday;
        String dateFormat = "dd/MM/yyyy";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat);
        try {
            LocalDate date = LocalDate.parse(birthdateTest, dateTimeFormatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    public static boolean objectTest(Set<Person> personSet, String document) {
        String documentTest = document.trim().replaceAll("[^0-9]*",  "");
        for (Person person : personSet) {
            if (person.document == documentTest) {
                return false;
            }

        }
        return true;
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
        return Objects.equals(document, person.document);
    }

    @Override
    public int hashCode() {
        return Objects.hash(document);
    }

    @Override
    public int compareTo(@NotNull Person o) {
        return 0;
    }
}
