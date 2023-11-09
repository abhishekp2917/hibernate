/*
    Code to demonstrate how to get SP result using DTO

    Use below MySQL query to create SP :

    ```
        USE temp;
        DELIMITER $$
        CREATE PROCEDURE GetPersonInfo(
            IN personAge INT
        )
        BEGIN

            -- Get the count of person whose age is greater than or equal to given age
            SELECT personID, name FROM spperson WHERE age >= personAge;

        END $$
        DELIMITER ;
    ```
*/

package org.example.storedProcedure;

import org.example.entity.storedProcedure.PersonDTO;
import org.example.entity.storedProcedure.Person;
import javax.persistence.*;
import java.util.List;

public class Main3 {
    public static void main(String[] args) {

        // creating EntityManagerFactory object by specifying persistence unit name defined in 'Persistence.xml' file
        // from EntityManagerFactory object, create EntityManager which will create a Persistence context
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("mysql-persistence-unit");
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        // beginning the transaction
        entityManager1.getTransaction().begin();

        // first storing the data in person table so that we could get result from SP
        // creating person objects and storing it into a list
        Person person1 = Person.builder().name("Abhishek").age(22).build();
        Person person2 = Person.builder().name("Ravi").age(23).build();
        Person person3 = Person.builder().name("Saurabh").age(21).build();
        Person person4 = Person.builder().name("Tarun").age(25).build();

        // persisting person object
        entityManager1.persist(person1);
        entityManager1.persist(person2);
        entityManager1.persist(person3);
        entityManager1.persist(person4);

        // committing and closing the entity manager will persist the entity in DB
        entityManager1.getTransaction().commit();
        entityManager1.close();

        // creating second EntityManager to execute SP
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();
        // beginning the transaction
        entityManager2.getTransaction().begin();

        // create StoredProcedureQuery object using entity manager in which pass the name of the SP query
        // don't pass the actual SP name instead pass the query name defined in @NamedStoredProcedureQuery annotation
        StoredProcedureQuery storedProcedure = entityManager2.createNamedStoredProcedureQuery("SPGetPersonInfo");

        // set the input parameter value
        storedProcedure.setParameter("personAge", 23);

        // execute the SP
        storedProcedure.execute();

        // use 'getResultList()' to get the list of record selected by the SP
        // it will return list of PersonDTO
        List<PersonDTO> persons = storedProcedure.getResultList();
        for(PersonDTO person : persons) {
            System.out.println(String.format("ID : %d Name : %s", person.getId(), person.getName()));
        }

        // committing and closing the entity manager
        entityManager2.getTransaction().commit();
        entityManager2.close();
    }
}