package org.makesystem.application;

import org.makesystem.model.dao.DaoFactory;
import org.makesystem.model.dao.PersonDao;
import org.makesystem.model.entities.Person;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class Program {
    public static void main(String[] args) throws ParseException {


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Person person = new Person();
        String path = "C:\\Users\\dasil\\IdeaProjects\\teste-makesystem\\src\\main\\java\\org\\makesystem\\desafio_junior.csv";
        int contadorInvalidos = 0, contadorRepetidos = 0;

        PersonDao personDao = DaoFactory.createPersonDao();

        Set<Person> personSet = personDao.findAll();
        Set<Person> personSetBr = new HashSet<>();

        int contadorPJ = 0;

        boolean t1, t2, t3, t4, t5;

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "ISO-8859-1"))) {

            String line = bufferedReader.readLine();

            while (line != null) {
                String[] array = line.split(";");
                t1 = Person.nameTest(array[0]);
                t2 = Person.documentTest(array[1]);
                t3 = Person.birthDayTest(array[2]);
                t4 = Person.phoneNumberTest(array[3]);

                if (!t1 || !t2 || !t3 || !t4) {
                    line = bufferedReader.readLine();
                    contadorInvalidos += 1;
                }
                else {
                    t5 = Person.objectTest(personSet, array[1]);
                    if (!t5) {
                        line = bufferedReader.readLine();
                    }
                    else {

                        String name = array[0].trim();
                        long document = Long.parseLong(array[1].trim().replaceAll("[^0-9]*", ""));
                        if (array[1].length() == 14) {
                            contadorPJ +=1;
                        }
                        Date birthDate = sdf.parse(array[2].trim());
                        long phoneNumber = Long.parseLong(array[3].trim().replaceAll("[^0-9]*", ""));

                        Person newPerson = new Person(name, document, birthDate, phoneNumber);

                        personSetBr.add(newPerson);
                        if (personSetBr.contains(newPerson)) {
                            contadorRepetidos += 1;
                        }
                        line = bufferedReader.readLine();
                    }
                }

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        System.out.println("============================================================");
        System.out.println("Lista de registros importados: ");
        for (Person person1 : personSetBr) {
            personDao.insert(person1);
            System.out.println(person1);
        }
        System.out.println("============================================================");
        System.out.println();

        int sum = 0;

        for (Person p : personSet) {
            long date = p.getBirthDate().getTime();
            long date2 = LocalDate.now().getYear();
            long idade = date2 - date;
            sum += idade;
        }

        int contadorSP = 0;
        for (Person p : personSet) {
            Long array = p.getPhoneNumber();
            String array2 = String.format(String.valueOf(array));
            int a1 = (int) array2.charAt(0);
            int a2 = (int) array2.charAt(1);
            boolean b = a1 == 1 && a2 == 1;
            if (b) {
                contadorSP += 1;
            }


        }


        System.out.println("============================================================");
        System.out.println("Média de idade: "  );
        System.out.println("Total de PJ: " + contadorPJ);
        System.out.println("Registros inválidos: " + contadorInvalidos);
        System.out.println("Registros repetidos: " + contadorRepetidos);
        System.out.println("Telefones de SP: " + contadorSP);
        System.out.println("============================================================");

    }
}
