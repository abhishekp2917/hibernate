/*
    Code to demonstrate why to provide own implementation for hashCode() and equals() method
*/

package org.example.basic;

import org.example.entity.basic.Guide;
import org.example.entity.basic.Person;
import org.example.util.SessionCreator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.HashSet;
import java.util.Set;

public class Main3 {
    public static void main(String[] args) {

        // creating SessionFactory object to open multiple session
        SessionFactory sessionFactory = SessionCreator.getSessionFactory(
                "basic\\hibernate-mysql.cfg.xml",
                        Person.class,
                        Guide.class);

        // by opening the session, a new Persistence context will be created
        Session session1 = sessionFactory.openSession();
        Transaction transaction1 = session1.getTransaction();
        transaction1.begin();

        // creating a Person object
        Person person1 = Person.builder().name("Abhishek").build();
        // creating a Guide object
        Guide guide1 = Guide.builder().name("Ravi").build();

        // associating guide entity with person entity
        person1.setGuide(guide1);

        // associating persons entity with guide entity
        HashSet<Person> persons = new HashSet<>();
        persons.add(person1);
        guide1.setPersons(persons);

        // persisting the guide entity, which will be cascade to persons entity
        session1.save(guide1);

        // committing and closing the session which will detach the person1 and guide1 object from persistent state to
        // detached state
        transaction1.commit();
        session1.close();

        // opening the second session, a new Persistence context will be created
        Session session2 = sessionFactory.openSession();
        Transaction transaction2 = session2.getTransaction();
        transaction2.begin();

        // fetching the guide entity which will be persisted in Persistent context
        Guide guide2 = (Guide) session2.get(Guide.class, (long)1);
        // getting set of persons from the guide entity which holds 1 person entity of id 1
        Set<Person> set = guide2.getPersons();

        // checking if the set contains person1 in it or not
        // even though person object in set and person1 are in different state i.e. persistent state and detached state,
        // since there hashCode are same and equals() method is implemented for Person object, both the object will be treated
        // as similar
        System.out.println(set.contains(person1));

        // checking if guide2 and guide1 are equal or not as both are in different state i.e. persistent state and detached state,
        // since we haven't provided implementation for hashCode() and equals() method, both of these object will be treated
        // as different
        System.out.println(guide1.equals(guide2));

        transaction2.commit();
        session2.close();
    }
}