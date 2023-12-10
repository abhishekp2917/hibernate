/*
    Code to demonstrate how to use MapsId annotation
*/

package org.example.basic;

import org.example.entity.basic.mapsId.Department;
import org.example.entity.basic.mapsId.Employee;
import org.example.entity.basic.mapsId.EmployeeID;
import org.example.util.SessionCreator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class Main8 {
    public static void main(String[] args) {

        // creating SessionFactory object to open multiple session
        SessionFactory sessionFactory = SessionCreator.getSessionFactory(
                "basic\\hibernate-mysql.cfg.xml",
                        Department.class,
                        Employee.class);

        // by opening the session, a new Persistence context will be created
        Session session1 = sessionFactory.openSession();
        Transaction transaction1 = session1.getTransaction();
        transaction1.begin();

        // creating department objects
        Department department1 = Department.builder().name("Engineering").build();
        Department department2 = Department.builder().name("Product").build();

        // creating EmployeeID object as a composite primary key by passing department id to the constructor
        EmployeeID employeeID1 = new EmployeeID(department1.getId(), 1);
        EmployeeID employeeID2 = new EmployeeID(department2.getId(), 1);
        EmployeeID employeeID3 = new EmployeeID(department2.getId(), 2);

        // creating Employee objects
        Employee employee1 = Employee.builder().id(employeeID1).name("Abhishek").department(department1).build();
        Employee employee2 = Employee.builder().id(employeeID2).name("Saurabh").department(department2).build();
        Employee employee3 = Employee.builder().id(employeeID3).name("Ravi").department(department2).build();

        // persisting employee objects which will persist associated department objects as well
        session1.save(employee1);
        session1.save(employee2);
        session1.save(employee3);

        // committing and closing the session
        transaction1.commit();
        session1.close();

        // opening the second session, a new Persistence context will be created
        Session session2 = sessionFactory.openSession();
        Transaction transaction2 = session2.getTransaction();
        transaction2.begin();

        // fetching Employee object having composite primary key value as (departmentID : 1, roleID : 1)
        EmployeeID employeeID4 = new EmployeeID(1, 1);
        Employee employee4 = session2.get(Employee.class, employeeID4);
        System.out.println(employee4);

        // committing and closing the session
        transaction2.commit();
        session2.close();
    }
}