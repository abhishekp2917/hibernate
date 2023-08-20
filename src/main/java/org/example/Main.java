package org.example;

import org.example.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class Main {
    public static void main(String[] args) {

        // creating entity object whose data needs to be inserted in db
        Student student = new Student(6, "Ravi Singh", 22);

        // creating Configuration object which will be used to create SessionFactory object which will eventually create a session object.
        // 'hibernate.cfg.xml' file holds all the configuration required to connect to the database
        // addAnnotatedClass is used to declare that 'Student' is an entity class
        Configuration config = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class);
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
        session.save(student);

        // committing the sql query
        transaction.commit();
    }
}