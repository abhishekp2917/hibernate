/*
    Code to demonstrate @OrderBy annotation usage
*/

package org.example.basic;

import org.example.entity.basic.Employee;
import org.example.entity.basic.Department;
import org.example.util.SessionCreator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main4 {
    public static void main(String[] args) {

        // creating SessionFactory object to open multiple session
        SessionFactory sessionFactory = SessionCreator.getSessionFactory(
                "basic\\hibernate-mysql.cfg.xml",
                        Employee.class,
                        Department.class);

        // by opening the session, a new Persistence context will be created
        Session session1 = sessionFactory.openSession();
        Transaction transaction1 = session1.getTransaction();
        transaction1.begin();

        // creating a Department object
        Department department1 = Department.builder().name("Engineering").build();

        // creating a Employee objects
        Employee employee1 = Employee.builder().name("Abhishek").department(department1).build();
        Employee employee2 = Employee.builder().name("Abhishek").department(department1).build();
        Employee employee3 = Employee.builder().name("Ravi").department(department1).build();
        Employee employee4 = Employee.builder().name("Abhishek").department(department1).build();
        Employee employee5 = Employee.builder().name("Ravi").department(department1).build();

        // associating employees entity with department entity
        List<Employee> employees1 = new ArrayList<>(Arrays.asList(employee1, employee2, employee3, employee4, employee5));
        department1.setEmployees(employees1);

        // persisting the department entity, which will be cascade to employee entity
        session1.save(department1);

        // committing and closing the session
        transaction1.commit();
        session1.close();

        // opening the second session, a new Persistence context will be created
        Session session2 = sessionFactory.openSession();
        Transaction transaction2 = session2.getTransaction();
        transaction2.begin();

        // fetching the guide entity which will be persisted in Persistent context
        Department department2 = (Department) session2.get(Department.class, (long)1);
        List<Employee> employees2 = department2.getEmployees();

        // getting employees collection associated to department of id 1
        // since we have used @OrderBy annotation, employee details will be iterated in an order
        for(Employee employee : employees2) {
            System.out.println(employee.getId() + " " + employee.getName());
        }

        transaction2.commit();
        session2.close();
    }
}