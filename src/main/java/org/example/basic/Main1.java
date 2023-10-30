/*
    Code to demonstrate how to persist and fetch data using hibernate
*/

package org.example.basic;

import org.example.entity.basic.Address;
import org.example.entity.basic.Course;
import org.example.entity.basic.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main1 {
    public static void main(String[] args) {

        ArrayList<Student> students = new ArrayList<>();
        ArrayList<Course> courses = new ArrayList<>();

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of students data to be entered : ");

        int StudentCount = sc.nextInt();

        for(int i=1; i<=StudentCount; i++) {
            String name;
            int age;
            String street;
            String city;
            String state;
            int phoneCount;
            List<String> phoneNumbers = new ArrayList<>();

            System.out.println("Provide student " + i + " details : ");
            System.out.print("Name : ");
            name = sc.next();
            System.out.print("Age : ");
            age = sc.nextInt();
            System.out.print("Street : ");
            street = sc.next();
            System.out.print("City : ");
            city = sc.next();
            System.out.print("State : ");
            state = sc.next();
            System.out.print("Provide phone number count : ");
            phoneCount = sc.nextInt();

            for(int j=1; j<=phoneCount; j++) {
                System.out.print("Provide phone number " + i + " : ");
                phoneNumbers.add(sc.next());
            }

            // creating entity object whose data needs to be inserted in db
            Student student = Student.builder()
                    .name(name)
                    .age(age)
                    .address(new Address(street, city, state))
                    .phoneNumbers(phoneNumbers)
                    .build();

            students.add(student);
        }

        System.out.print("Enter number of courses data to be entered : ");

        int CourseCount = sc.nextInt();

        for(int i=1; i<=CourseCount; i++) {
            String name;

            System.out.println("Provide course " + i + " details : ");
            System.out.print("Name : ");
            name = sc.next();

            // creating entity object whose data needs to be inserted in db
            Course course = Course.builder()
                    .name(name)
                    .build();

            courses.add(course);
        }

        // creating Configuration object which will be used to create SessionFactory object which will eventually create a session object.
        // 'hibernate-mysql.cfg.xml' file holds all the configuration required to connect to the database
        // addAnnotatedClass is used to declare that 'Student' and 'Course' are entity classes
        Configuration config = new Configuration().configure("basic/hibernate-mysql.cfg.xml")
                                .addAnnotatedClass(Student.class)
                                .addAnnotatedClass(Course.class);
        ServiceRegistry registry = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();

        // using Configuration object to build SessionFactory
        // here we are passing 'ServiceRegistry' object as parameterless 'buildSessionFactory' is deprecated
        SessionFactory sessionFactory = config.buildSessionFactory(registry);

        // creating session from SessionFactory
        Session session = sessionFactory.openSession();

        // enclosing sql commands under transaction object will ensure ACID property of database
        // if we don't begin and commit the transaction, the data will not reflect into database
        Transaction transaction = session.beginTransaction();

        // finally saving the data

        for(Student student : students) session.save(student);
        for(Course course : courses) session.save(course);

        // committing the sql query
        transaction.commit();

        // fetching Student and Course data whose id is 1 in form of object
        Student student = (Student) session.get(Student.class, (long)1);
        Course course = (Course) session.get(Course.class, (long)1);

        // closing the current session
        session.close();

        // printing out the fetched student and course data
        System.out.println(student);
        System.out.println(course);
    }


}