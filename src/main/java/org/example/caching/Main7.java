/*
    Code to demonstrate second level caching of collections of entities
*/

package org.example.caching;

import org.example.entity.caching.Laptop;
import org.example.entity.caching.Person;
import org.example.util.SessionCreator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class Main7 {
    public static void main(String[] args) {

        // creating SessionFactory object to open multiple session
        // configuring Hibernate with config file which has configuration for second-level caching
        SessionFactory sessionFactory = SessionCreator.getSessionFactory(
                                    "caching\\hibernate-second-level-caching.cfg.xml",
                                        Person.class,
                                        Laptop.class);

        // opening 1st session and beginning the transaction
        Session session1 = sessionFactory.openSession();
        Transaction transaction1 = session1.beginTransaction();

        // fetching Person data whose id is 1 which will store the Laptop list in cache as it is set to be cached in Person
        // entity. This Person entity won't be store in cache but it's collection of laptops
        Person person1 = (Person) session1.get(Person.class, (long)1);
        System.out.println("Person details from first session from DB : " + person1);

        // committing and closing the 1st session
        transaction1.commit();
        session1.close();

        // opening 2nd session
        Session session2 = sessionFactory.openSession();
        Transaction transaction2 = session2.beginTransaction();

        // fetching Person data whose id is 1
        // this time it will fetch person details from DB but the details of laptops collection will be fetched from cache
        Person person2 = (Person) session2.get(Person.class, (long)1);
        System.out.println("Person details from second session from DB : " + person2);

        // committing and closing the 2nd session
        transaction2.commit();
        session2.close();
    }
}