package org.makesystem.model.entities;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class TextConverter {
    public static void main(String[] args) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Person person = new Person();
        String path = Paths.get("src/main/resources/").resolve("desafio_junior.csv").toAbsolutePath().toString();
        int contadorInvalidos = 0, contadorRepetidos = 0;

        List<Person> personList = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "ISO-8859-1"))) {

            String line = bufferedReader.readLine();
            System.out.println("Texto==============");
            System.out.println(line);

            while (line != null) {

                String[] array = line.split(";");
                if (line.startsWith("nome")) {
                    line = bufferedReader.readLine();
                } else {
                    String name = array[0].trim();
                    long document = Long.parseLong(array[1].replaceAll("[^0-9]*", ""));
                    LocalDate birthDate = LocalDate.from(dtf.parse(array[2].trim()));
                    long phoneNumber = Long.parseLong(array[3].trim().replaceAll("[^0-9]*", ""));
                    System.out.println(name + "; " + document + "; " + birthDate + "; " + phoneNumber);
                }
                line = bufferedReader.readLine();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println();
        System.out.println("========================");
        System.out.println("Objeto");
        for (Person p : personList) {
            System.out.println(p);
        }
    }

}
