/*
    Code to demonstrate Inheritance mapping and polymorphic queries
*/

package org.example.basic;

import org.example.entity.basic.inheritance.*;
import org.example.util.SessionCreator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;

public class Main5 {
    public static void main(String[] args) {

        // creating SessionFactory object to open multiple session
        SessionFactory sessionFactory = SessionCreator.getSessionFactory(
                "basic\\hibernate-mysql.cfg.xml",
                        Animal.class,
                        Dog.class,
                        Cat.class);

        // by opening the session, a new Persistence context will be created
        Session session1 = sessionFactory.openSession();
        Transaction transaction1 = session1.getTransaction();
        transaction1.begin();

        // creating cat object
        Cat cat1 = new Cat();
        cat1.setId(1);
        cat1.setName("Xavier");
        cat1.setGender("Male");

        // creating dog object
        Dog dog1 = new Dog();
        dog1.setId(2);
        dog1.setName("Keto");
        dog1.setBreed("Golden Retriever");

        // persisting cat and dog object
        session1.save(cat1);
        session1.save(dog1);

        // committing and closing the session
        transaction1.commit();
        session1.close();

        // opening the second session, a new Persistence context will be created
        Session session2 = sessionFactory.openSession();
        Transaction transaction2 = session2.getTransaction();
        transaction2.begin();

        // fetching animals list from the DB. Since cat and dog both are of animal type, both cat and dog will be returned
        List<Animal> animals = session2.createQuery("SELECT animal FROM Animal animal").list();

        // accessing animal object and printing it
        for(Animal animal : animals) {
            System.out.println(animal.toString());
        }

        transaction2.commit();
        session2.close();
    }
}