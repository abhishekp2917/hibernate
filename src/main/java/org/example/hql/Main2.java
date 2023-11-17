/*
    Code to demonstrate how to execute named query
*/

package org.example.hql;

import org.example.entity.hql.Department;
import org.example.entity.hql.Employee;
import org.example.util.SessionCreator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import javax.persistence.Query;
import java.util.List;

public class Main2 {
    public static void main(String[] args) {

        // creating SessionFactory object to open multiple session
        SessionFactory sessionFactory = SessionCreator.getSessionFactory(
                "basic\\hibernate-mysql.cfg.xml",
                            Department.class,
                            Employee.class);

        // creating session objects and beginning the transaction
        Session session1 = sessionFactory.openSession();
        Transaction transaction1 = session1.beginTransaction();

        // creating department object
        Department department1 = Department.builder().name("Engineering").build();

        // creating Employee objects and associating department with the respective employee
        Employee employee1 = Employee.builder()
                                .firstName("Abhishek")
                                .lastName("Pandey")
                                .age(22)
                                .department(department1).build();
        Employee employee2 = Employee.builder()
                                .firstName("Ravi")
                                .lastName("Singh")
                                .age(24)
                                .department(department1).build();
        Employee employee3 = Employee.builder()
                                .firstName("Ravi")
                                .lastName("Gupta")
                                .age(23)
                                .department(department1).build();

        // persisting employee objects
        // since employee reference is cascaded, by saving employee object department objects will also get persisted
        session1.save(employee1);
        session1.save(employee2);
        session1.save(employee3);

        // committing and closing the session
        transaction1.commit();
        session1.close();

        // creating another session object and beginning the transaction
        Session session2 = sessionFactory.openSession();
        Transaction transaction2 = session2.beginTransaction();

        // creating named query by passing the query name 'findEmployeeByFirstName' and passing the parameter
        Query query = session2.createNamedQuery("findEmployeeByFirstName", Employee.class);
        query.setParameter("firstName", "Ravi");
        List<Employee> employees = query.getResultList();
        displayEmployee(employees);

        // committing and closing the session
        transaction2.commit();
        session2.close();
    }

    private static void displayEmployee(List<Employee> employees) {
        System.out.println();
        for(Employee employee : employees) System.out.println(employee);
        System.out.println();
    }
}