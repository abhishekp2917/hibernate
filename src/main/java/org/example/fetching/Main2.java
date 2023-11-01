/*
    Code to demonstrate N+1 fetching problem
*/

package org.example.fetching;

import org.example.entity.fetching.Laptop;
import org.example.entity.fetching.Student;
import org.example.util.SessionCreator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;

public class Main2 {
    public static void main(String[] args) {

        // creating SessionFactory object to open multiple session
        SessionFactory sessionFactory = SessionCreator.getSessionFactory(
                "basic\\hibernate-mysql.cfg.xml",
                        Student.class,
                        Laptop.class);


        // by opening the session, a new Persistence context will be created
        Session session1 = sessionFactory.openSession();
        Transaction transaction1 = session1.getTransaction();
        transaction1.begin();

        // creating laptop objects
        Laptop laptop1 = Laptop.builder().name("HP").build();
        Laptop laptop2 = Laptop.builder().name("Dell").build();
        Laptop laptop3 = Laptop.builder().name("Asus").build();
        Laptop laptop4 = Laptop.builder().name("Apple").build();

        // creating student object and associating laptop with it
        Student student1 = Student.builder().name("Abhishek").laptop(laptop1).build();
        Student student2 = Student.builder().name("Ravi").laptop(laptop2).build();
        Student student3 = Student.builder().name("Saurabh").laptop(laptop3).build();
        Student student4 = Student.builder().name("Tushar").laptop(laptop4).build();

        // persisting student object which will persist associated entity as well, as cascading is enabled
        session1.save(student1);
        session1.save(student2);
        session1.save(student3);
        session1.save(student4);

        // committing and closing the session will persist the entity in DB
        transaction1.commit();
        session1.close();


        // opening a new session will create a new persistent context
        Session session2 = sessionFactory.openSession();
        Transaction transaction2 = session2.getTransaction();
        transaction2.begin();

        // fetching list of students which will issue a select query
        List<Student> students = session2.createQuery("FROM Student").list();

        // for each student, fetching it's associated laptop object
        // for each student, a separate select query will be issued leading to N+1 problem
        for(Student student : students) {
            System.out.println(student.getLaptop().getName());
        }

        transaction2.commit();
        session2.close();
    }
}