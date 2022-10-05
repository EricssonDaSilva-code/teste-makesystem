package org.makesystem;

import org.makesystem.entities.Person;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        Set<Person> personSet = new TreeSet<>();
        Person person = new Person();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String path = "C:\\Users\\dasil\\IdeaProjects\\teste-makesystem\\src\\main\\java\\org\\makesystem\\desafio_junior.csv";
        String outPath = "C:\\Users\\dasil\\IdeaProjects\\teste-makesystem\\src\\main\\java\\org\\makesystem\\desafio_junior3.txt";
        boolean teste;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "ISO-8859-1"))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] array = line.split(";");
                teste = person.testArray(array);
                if (!teste) {
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

}