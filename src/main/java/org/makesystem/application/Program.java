package org.makesystem.application;

import org.makesystem.model.dao.DaoFactory;
import org.makesystem.model.dao.PersonDao;
import org.makesystem.model.entities.Person;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Program {
    public static void main(String[] args) throws ParseException {

        /*
        remova todos os dados da base e execute o projeto vera que dará erro na primeira execução.
        logica de contagem dos duplicados incorreta.
        falta contagem de CPFs validos.
        verifique com atenção a escrita da solicitação sobre os telefones de SP.


         */

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String path = Paths.get("src/main/resources/").resolve("desafio_junior.csv").toAbsolutePath().toString();
        int contadorInvalidos = 0;
        int contadorRepetidos = 0;

        PersonDao personDao = DaoFactory.createPersonDao();

        Set<Person> personSetDB = personDao.findAll();

        List<Person> personListR = new ArrayList<>();

        List<String> documentSet = new ArrayList<>();
        List<String> phoneSet = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path),
                "ISO-8859-1"))) {

            String line = bufferedReader.readLine();

            while (line != null) {
                String[] array = line.split(";");

                boolean t1 = Person.nameTest(array[0]);
                boolean t2 = Person.documentTest(array[1]);
                boolean t3 = Person.birthDayTest(array[2]);
                boolean t4 = Person.phoneNumberTest(array[3]);
                boolean t5 = Person.documentTest(array[1]);

                if (!t5) {
                    contadorRepetidos += 1;
                }

                if (!t1 || !t2 || !t3 || !t4) {
                    line = bufferedReader.readLine();
                    contadorInvalidos += 1;
                } else {

                    String name = array[0].trim();
                    String document = array[1].trim().replaceAll("[^0-9]*", "");
                    LocalDate birthDate = LocalDate.from(dtf.parse(array[2].trim()));
                    String phoneNumber = array[3].trim().replaceAll("[^0-9]*", "");

                    Person newPerson = new Person(name, document, birthDate, phoneNumber);

                    personListR.add(newPerson);

                    line = bufferedReader.readLine();
                }

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("============================================================");
        System.out.println("Lista de registros importados: ");
        int cont = 0;
        for (Person p : personListR) {
            if (personSetDB.add(p) != true) {
                cont += 1;
            } else {
                personDao.insert(p);
                System.out.println(p);
            }
        }
        if (cont == personListR.size()) {
            System.out.println("==== SEM REGISTROS A IMPORTAR ====");
        }


        System.out.println("============================================================");
        System.out.println();

        //média de idades
        int sum = 0;
        for (Person p : personSetDB) {
            sum += Period.between(p.getBirthDate(), LocalDate.now()).getYears();
        }
        sum = sum / personSetDB.size();

        //contador PF
        int contPF = 0;
        for (Person p : personSetDB) {
            String document = String.valueOf(p.getDocument());
            if (document.length() == 11) {
                contPF += 1;
            }
        }

        //Contador de PJ
        int contPJ = 0;
        for (Person p : personSetDB) {
            String document = String.valueOf(p.getDocument());
            if (document.length() == 14) {
                contPJ += 1;
            }
        }


        //Contador repetidos
        int contPe = 0;
        Set<Person> contP = new HashSet<>();
        for (Person p : personListR) {
            if (contP.add(p) == false) {
                contPe += 1;
            }
        }


        // contando os números de São Paulo
        int contadorSP = 0;
        for (Person p : personSetDB) {
            String phone = p.getPhoneNumber();
            String[] array = phone.split("");
            String firstNumber = array[0];
            String secondNumber = array[1];
            String[] ddd = {"11", "12", "13", "14", "15", "16", "17", "18", "19"};
            String ddd2 = firstNumber + secondNumber;
            for (int i = 0; i <= 8; i++) {
                if (Objects.equals(ddd2, ddd[i])) {
                    contadorSP += 1;
                }

            }

        }


        System.out.println("============================================================");
        System.out.println("Média de idade: " + sum);
        System.out.println("Total de PF: " + contPF);
        System.out.println("Total de PJ: " + contPJ);
        System.out.println("Registros inválidos: " + contadorInvalidos);
        System.out.println("Registros repetidos: " + contPe + "." + "Registros repetidos no total " + contadorRepetidos);
        System.out.println("Telefones de SP: " + contadorSP);
        System.out.println("============================================================");


        System.out.println("Lista integral");


    }


}