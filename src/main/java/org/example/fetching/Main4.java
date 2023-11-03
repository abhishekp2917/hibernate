/*
    Code to demonstrate various fetch mode in Hibernate native API
*/

package org.example.fetching;

import org.example.entity.fetching.Department;
import org.example.entity.fetching.Employee;
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
                Department.class,
                Employee.class);


        // by opening the session, a new Persistence context will be created
        Session session1 = sessionFactory.openSession();
        Transaction transaction1 = session1.getTransaction();
        transaction1.begin();

        // creating employee objects and storing it into a list
        Employee employee1 = Employee.builder().name("Abhishek").build();
        Employee employee2 = Employee.builder().name("Ravi").build();
        Employee employee3 = Employee.builder().name("Saurabh").build();
        Employee employee4 = Employee.builder().name("Tushar").build();
        List<Employee> employees1 = new ArrayList<>(Arrays.asList(employee1, employee2));
        List<Employee> employees2 = new ArrayList<>(Arrays.asList(employee3, employee4));

        // creating department object and associating employees with department object
        Department department1 = Department.builder().name("Engineering").build();
        Department department2 = Department.builder().name("Product").build();
        department1.setEmployees(employees1);
        department2.setEmployees(employees2);

        // persisting department object which will persist associated entity as well, as cascading is enabled
        session1.save(department1);
        session1.save(department2);

        // committing and closing the session will persist the entity in DB
        transaction1.commit();
        session1.close();


        // opening a new session will create a new persistent context
        // session to demonstrate FetchMode.SELECT
        // Here we are fetching departments list which will execute one query and after that since we are fetching employees
        // list of each department which will issue select query for each department to get associated employees list
        // leading to N+1 select problem
        Session session2 = sessionFactory.openSession();
        Transaction transaction2 = session2.getTransaction();
        transaction2.begin();

        // getting department list
        List<Department> departments1 = session2.createQuery("select d from Department d").list();

        // getting each department employee list will result in execution of select query for individual department
        for(Department department : departments1) {
            System.out.println(department.getEmployees().size());
        }

        transaction2.commit();
        session2.close();


        // opening a new session will create a new persistent context
        // session to demonstrate FetchMode.SUBSELECT
        // Here we are fetching departments list which will execute one query and after that since we are fetching employees
        // list of each department which will issue select query for employees having subselect query to get the department
        // whose employees needs to be fetched which will result in 2 select query
        Session session3 = sessionFactory.openSession();
        Transaction transaction3 = session3.getTransaction();
        transaction3.begin();

        // getting department list
        List<Department> departments2 = session3.createQuery("select d from Department d").list();

        // getting each department employee list will result in execution of subselect query to get employees list of all
        // departments at once
        for(Department department : departments2) {
            System.out.println(department.getEmployees().size());
        }

        transaction3.commit();
        session3.close();


        // opening a new session will create a new persistent context
        // session to demonstrate FetchMode.JOIN
        // Here we are fetching department of id 1 and later on fetching its employee list. since Fetch mode is set to
        // JOIN, even though employees list is set to fetch lazily,it will be fetched eagerly
        Session session4 = sessionFactory.openSession();
        Transaction transaction4 = session4.getTransaction();
        transaction4.begin();

        // fetching department of id 1 will execute select query with outer join to get employee data also
        Department department3 = (Department)session4.get(Department.class, (long)1);

        // accessing property of employees list of department of id 1
        System.out.println(department3.getEmployees().size());

        transaction4.commit();
        session4.close();
    }
}

