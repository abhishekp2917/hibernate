/*
    Code to demonstrate dynamically defining fetching strategy of an entity associated objects
*/

package org.example.fetching;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.entity.fetching.*;
import java.util.*;

public class Main3 {
    public static void main(String[] args) {

        // creating entity manager object which will create persistence context
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("my-persistence-unit");
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        // beginning the transaction
        entityManager1.getTransaction().begin();

        // creating employee objects and storing it into a list
        Employee employee1 = Employee.builder().name("Abhishek").build();
        Employee employee2 = Employee.builder().name("Ravi").build();
        List<Employee> employees1 = new ArrayList<>(Arrays.asList(employee1, employee2));

        // creating department object and associating employees with department object
        Department department1 = Department.builder().name("Engineering").build();
        department1.setEmployees(employees1);

        // persisting department object which will persist associated entity as well, as cascading is enabled
        entityManager1.persist(department1);

        // committing and closing the entity manager will persist the entity in DB
        entityManager1.getTransaction().commit();
        entityManager1.close();


        // creating new entity manager will create a new persistent context
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();
        // beginning the transaction
        entityManager1.getTransaction().begin();

        HashMap<String, Object> pros = new HashMap<>();
        pros.put("jakarta.persistence.fetchgraph", entityManager2.getEntityGraph("department.employees"));

        Department department2 = entityManager2.find(Department.class, (long)1, pros);

        entityManager2.getTransaction().commit();
        entityManager2.close();
    }
}

