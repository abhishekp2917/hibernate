/*
    Code to demonstrate various flushing modes
*/

package org.example.flushing;

import org.example.entity.flushing.Employee;
import org.example.util.SessionCreator;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import javax.persistence.Query;

public class Main1 {
    public static void main(String[] args) {

        // creating SessionFactory object to open multiple session
        SessionFactory sessionFactory = SessionCreator.getSessionFactory(
                "basic\\hibernate-mysql.cfg.xml",
                            Employee.class);

        // creating session objects and beginning the transaction
        Session session1 = sessionFactory.openSession();
        Transaction transaction1 = session1.beginTransaction();

        // creating Employee objects and associating department with the respective employee
        Employee employee1 = Employee.builder()
                                .name("Abhishek Pandey")
                                .age(22).build();
        Employee employee2 = Employee.builder()
                                .name("Ravi Singh")
                                .age(24).build();
        Employee employee3 = Employee.builder()
                                .name("Ravi Gupta")
                                .age(23).build();


        // persisting employee objects
        session1.save(employee1);
        session1.save(employee2);
        session1.save(employee3);

        // committing and closing the session
        // by default flush mode is Auto, so at the time of committing the transaction, hibernate will flush the changes
        transaction1.commit();
        session1.close();

        System.out.println("=================================== FlushMode.ALWAYS ===================================");

        // creating another session object and beginning the transaction
        Session session2 = sessionFactory.openSession();
        session2.setFlushMode(FlushMode.ALWAYS);
        Transaction transaction2 = session2.beginTransaction();

        // fetching employee data of id 1
        Employee employee4 = (Employee) session2.get(Employee.class, (long)1);

        // updating the name of the fetched data which is in persistent context
        employee4.setName("Tushar Singh");

        System.out.println(String.format("Employee after update : [Id : %d Name : %s]", employee4.getId(), employee4.getName()));

        // querying the employee data using HQL
        // since flush mode is set to ALWAYS, whenever a query is being executed, before executing it hibernate will flush
        // the update to DB
        Query query1 = session2.createQuery("FROM flushingEmployee e WHERE e.name=:name");
        query1.setParameter("name", "Tushar Singh");
        Employee employee5 = (Employee) query1.getSingleResult();

        System.out.println(String.format("Employee after HQL query : [Id : %d Name : %s]", employee5.getId(), employee5.getName()));

        // since employee entity has already been flushed and updated in DB, on committing the transaction, hibernate will
        // not perform flushing as object in persistent context is already in sync with DB.
        transaction2.commit();
        session2.close();

        System.out.println("=================================== FlushMode.COMMIT ===================================");

        // creating another session object and beginning the transaction
        Session session3 = sessionFactory.openSession();
        session3.setFlushMode(FlushMode.COMMIT);
        Transaction transaction3 = session3.beginTransaction();

        // fetching employee data of id 1
        Employee employee6 = (Employee) session3.get(Employee.class, (long)1);

        // updating the name of the fetched data which is in persistent context
        employee6.setName("Abhishek Pandey");

        System.out.println(String.format("Employee after update : [Id : %d Name : %s]", employee6.getId(), employee6.getName()));

        // querying the employee data using HQL
        // since fetch mode is set to COMMIT, hibernate will not flush the updated employee object to DB even on querying
        // using HQL
        Query query2 = session3.createQuery("FROM flushingEmployee e WHERE e.name=:name");
        query2.setParameter("name", "Tushar Singh");
        Employee employee7 = (Employee) query2.getSingleResult();

        System.out.println(String.format("Employee after HQL query : [Id : %d Name : %s]", employee7.getId(), employee7.getName()));

        // since fetch mode is set to COMMIT, employee object will be flushed and updated in DB at the time of commit
        transaction3.commit();
        session3.close();

        System.out.println("=================================== FlushMode.MANUAL ===================================");

        // creating another session object and beginning the transaction
        Session session4 = sessionFactory.openSession();
        session4.setFlushMode(FlushMode.MANUAL);
        Transaction transaction4 = session4.beginTransaction();

        // fetching employee data of id 1
        Employee employee8 = (Employee) session4.get(Employee.class, (long)1);

        // updating the name of the fetched data which is in persistent context
        employee8.setName("Saurabh Pathak");

        System.out.println(String.format("Employee after update : [Id : %d Name : %s]", employee8.getId(), employee8.getName()));

        // since fetch mode is set to MANUAL, hibernate will flush the updated employee object to DB only when we call
        // flush() method else not
        Query query3 = session4.createQuery("FROM flushingEmployee e WHERE e.name=:name");
        query3.setParameter("name", "Abhishek Pandey");
        Employee employee9 = (Employee) query3.getSingleResult();

        System.out.println(String.format("Employee after HQL query : [Id : %d Name : %s]", employee9.getId(), employee9.getName()));

        // since fetch mode is set to MANUAL, hibernate will not flush the update even if we are committing the transaction
        transaction4.commit();
        session4.close();
    }
}