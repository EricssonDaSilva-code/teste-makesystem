package org.makesystem.application;

import org.makesystem.model.dao.DaoFactory;
import org.makesystem.model.dao.PersonDao;
import org.makesystem.model.entities.Person;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class Program {
    public static void main(String[] args) throws ParseException {


        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
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
                        LocalDate birthDate = LocalDate.from(dtf.parse(array[2].trim()));
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
        }
        System.out.println("============================================================");
        System.out.println("Lista de registros importados: ");
        for (Person person1 : personSet) {
            personDao.insert(person1);
            System.out.println(person1);
        }
        System.out.println("============================================================");
        System.out.println();

        //média de idades
        int sum = 0;
        for (Person p : personSet) {
            sum += Period.between(p.getBirthDate(), LocalDate.now()).getYears();
        }
        sum = sum / personSet.size();

        // contando os números de São Paulo
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
        System.out.println("Média de idade: " + sum);
        System.out.println("Total de PJ: " + contadorPJ);
        System.out.println("Registros inválidos: " + contadorInvalidos);
        System.out.println("Registros repetidos: " + contadorRepetidos);
        System.out.println("Telefones de SP: " + contadorSP);
        System.out.println("============================================================");

    }
}
