/*
    Code to demonstrate default fetching strategies of one-to-one, many-to-one/one-to-many and many-to-many
    associated entities
*/

package org.example.fetching;

import org.example.entity.fetching.Department;
import org.example.entity.fetching.Employee;
import org.example.entity.fetching.Manager;
import org.example.util.SessionCreator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main1 {
    public static void main(String[] args) {

        // creating SessionFactory object to open multiple session
        SessionFactory sessionFactory = SessionCreator.getSessionFactory(
                "basic\\hibernate-mysql.cfg.xml",
                        Employee.class,
                        Department.class,
                        Manager.class);


        // by opening the session, a new Persistence context will be created
        Session session1 = sessionFactory.openSession();
        Transaction transaction1 = session1.getTransaction();
        transaction1.begin();

        // creating employee objects and storing it into a list
        Employee employee1 = Employee.builder().name("Abhishek").build();
        Employee employee2 = Employee.builder().name("Ravi").build();
        List<Employee> employees1 = new ArrayList<>(Arrays.asList(employee1, employee2));

        // creating manager object
        Manager manager1 = Manager.builder().name("Saurabh").build();

        // creating department object and associating employees and manager with department object
        Department department1 = Department.builder().name("Engineering").build();
        department1.setEmployees(employees1);
//        department1.setManager(manager1);

        // associating department object with manager object
//        manager1.setDepartment(department1);

        // persisting department object which will persist associated entity as well, as cascading is enabled
        session1.save(department1);

        // committing and closing the session will persist the entity in DB
        transaction1.commit();
        session1.close();


        // opening a new session will create a new persistent context
        Session session2 = sessionFactory.openSession();
        Transaction transaction2 = session2.getTransaction();
        transaction2.begin();

        // fetching the department entity
        // since manager entity is having one-to-one association, it will be fetched eagerly
        // but employee collection is having one-to-many association, it will be fetched lazily instead a proxy object
        // will be associated with department object
        Department department2 = (Department) session2.get(Department.class, (long)1);

        // even at this point employee collection will not be fetched as we are just instantiating employee collection
        List<Employee> employees2 = department2.getEmployees();

        // on calling the size() method of employee collection, employee collection will be fetched as its data is being
        // used here
        employees2.size();

        transaction2.commit();
        session2.close();
    }
}