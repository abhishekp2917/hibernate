/*
    Code to demonstrate life cycle callbacks methods
*/

package org.example.lifecycleCallbacks;

import org.example.entity.lifecycleCallbacks.Employee;
import org.example.util.SessionCreator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class Main1 {
    public static void main(String[] args) {

        // creating SessionFactory object to open multiple session
        SessionFactory sessionFactory = SessionCreator.getSessionFactory(
                "basic\\hibernate-mysql.cfg.xml",
                        Employee.class);

        // by opening the session, a new Persistence context will be created
        Session session1 = sessionFactory.openSession();
        Transaction transaction1 = session1.getTransaction();
        transaction1.begin();

        // creating employee objects and storing it into a list
        Employee employee1 = Employee.builder().firstName("Abhishek").lastName("Pandey").build();
        Employee employee2 = Employee.builder().firstName("Ravi").lastName("Shukla").build();

        // persisting employee objects
        // PrePersist method will be called before persisting the entity and PostPersist method will be called
        // after persisting the entity in DB
        session1.persist(employee1);
        session1.persist(employee2);

        // committing and closing the session will persist the entity in DB
        transaction1.commit();
        session1.close();


        // opening a new session will create a new persistent context
        Session session2 = sessionFactory.openSession();
        Transaction transaction2 = session2.getTransaction();
        transaction2.begin();

        // PostLoad method will be called after loading the entity
        Employee employee3 = (Employee) session2.get(Employee.class, (long)1);
        System.out.println(String.format("%s %s %s Has removed? : %s",
                    employee3.getFirstName(),
                    employee3.getLastName(),
                    employee3.getLastUpdatedTimeStamp(),
                    employee3.getHasRemoved()));

        // PreUpdate method will be called before updating the entity and PostUpdate method will be called
        // after updating the entity in DB
        employee3.setFirstName("Saurabh");

        transaction2.commit();
        session2.close();


        // opening a new session will create a new persistent context
        Session session3 = sessionFactory.openSession();
        Transaction transaction3 = session3.getTransaction();
        transaction3.begin();

        // PostLoad method will be called after loading the entity
        Employee employee4 = (Employee) session3.get(Employee.class, (long)1);
        System.out.println(String.format("%s %s %s Has removed? : %s",
                employee4.getFirstName(),
                employee4.getLastName(),
                employee4.getLastUpdatedTimeStamp(),
                employee4.getHasRemoved()));

        // PreRemove method will be called before removing the entity and PostRemove method will be called
        // after removing the entity from DB
        session3.delete(employee4);

        transaction3.commit();
        session3.close();

        System.out.println(String.format("%s %s %s Has removed? : %s",
                employee4.getFirstName(),
                employee4.getLastName(),
                employee4.getLastUpdatedTimeStamp(),
                employee4.getHasRemoved()));
    }
}