/*
    Code to demonstrate how to store composite primary keys
*/

package org.example.basic;

import org.example.entity.basic.compositeKeys.Department;
import org.example.entity.basic.compositeKeys.DepartmentID;
import org.example.entity.basic.compositeKeys.Employee;
import org.example.util.SessionCreator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class Main7 {
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

        // creating department objects and using DepartmentID class for their IDs
        Department department1 = Department.builder()
                                    .departmentID(new DepartmentID(1, 1))
                                    .name("Engineering")
                                    .build();
        Department department2 = Department.builder()
                                    .departmentID(new DepartmentID(1, 2))
                                    .name("Engineering")
                                    .build();

        // creating Employee objects
        Employee employee1 = Employee.builder().name("Abhishek").department(department1).build();
        Employee employee2 = Employee.builder().name("Saurabh").department(department2).build();
        Employee employee3 = Employee.builder().name("Ravi").department(department1).build();

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

        // getting employee object of ID 1
        Employee employee4 = session2.get(Employee.class, (long) 1);
        System.out.println(employee4);

        // creating DepartmentID object as a composite primary key to fetch department object of specific id
        DepartmentID departmentID = new DepartmentID(1, 1);
        Department department3 = session2.get(Department.class, departmentID);
        System.out.println(department3);

        // committing and closing the session
        transaction2.commit();
        session2.close();
    }
}