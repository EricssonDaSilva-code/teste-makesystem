package org.makesystem;

import org.makesystem.entities.Person;

import javax.swing.text.DateFormatter;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Set<Person> personSet = new TreeSet<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String path = "C:\\Users\\dasil\\IdeaProjects\\teste-makesystem\\src\\main\\java\\org\\makesystem\\desafio_junior_txt.txt";
        String outPath = "C:\\Users\\dasil\\IdeaProjects\\teste-makesystem\\src\\main\\java\\org\\makesystem\\desafio_junior3.txt";
        boolean r1, r2, r3, r4 ;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] array = line.split(";");
                r1 = testName(array[0]);
                r2 = testDocument(array[1]);
                r3 = testBirthDate(array[2]);
                r4 = testPhoneNumber(array[3]);
                if (!r1 || !r2 || !r3 || !r4) {
                    line = bufferedReader.readLine();
                }
                else {
                    String name = array[0].trim();
                    long document = Long.parseLong(array[1].trim().replaceAll("[^0-9]*",""));
                    Date birthDate = sdf.parse(array[2].trim());
                    long phoneNumber = Long.parseLong(array[3].trim().replaceAll("[^0-9]*", ""));

                    personSet.add(new Person(name, document, birthDate, phoneNumber));
                    line = bufferedReader.readLine();
                }

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        for (Person persona : personSet) {
            System.out.println(persona);
        }


        try (BufferedWriter brw = new BufferedWriter(new FileWriter(outPath))) {
            for (Person persona : personSet) {
                brw.write(String.valueOf(persona));
                brw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static Boolean testName(String name) {
        String[] nameTeste = name.trim().split(" ");
        boolean resposta = true;
        if (nameTeste.length < 2) {
            resposta = false;
        }
        else {
            for (int i = 0; i < nameTeste.length; i ++) {
                if (nameTeste[i].replaceAll("[^a-zA-Z]*", "").length() <= 1) {
                    resposta = false;
                }
            }
        }


        return resposta;
    }

    public static boolean testDocument(String document) {
        String documentTest = document.trim().replaceAll("[^0-9]*", "");
        boolean resposta = true;
        if (documentTest.length() == 11 || documentTest.length() == 14) {
            resposta = true;
        }
        else {
            resposta = false;
        }
        return resposta;
    }

    public static boolean testBirthDate(String birthDate) {
        String dateFormat = "dd/MM/yyyy";

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter
                .ofPattern(dateFormat);
        try {
            LocalDate date = LocalDate.parse(birthDate, dateTimeFormatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }

    }

    public static boolean testPhoneNumber(String phoneNumber) {
        String phoneNumberTest = phoneNumber.trim().replaceAll("[^0-9]*", "");
        if (phoneNumberTest.length() < 11) {
            return false;
        }
        return true;
    }

}