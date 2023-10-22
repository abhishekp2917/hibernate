package org.example.basic;

import org.example.entity.basic.Department;
import org.example.entity.basic.Employee;
import org.example.entity.basic.Laptop;
import org.example.entity.basic.Technology;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class Main3 {
    public static void main(String[] args) {

        // creating SessionFactory object to open multiple session
        SessionFactory sessionFactory = getSessionFactory("basic/hibernate-mysql.cfg.xml");

        // opening 1st session
        Session session1 = sessionFactory.openSession();
        Transaction transaction1 = session1.beginTransaction();

        // fetching Laptop data whose id is 1
        Laptop laptop1 = (Laptop) session1.get(Laptop.class, (long)1);

        // fetching Laptop data with same id i.e. 1 in the same session
        // this time service will not go to DB, instead it will get the data from 1st level caching
        Laptop laptop2 = (Laptop) session1.get(Laptop.class, (long)1);

        // committing and closing the 1st session
        transaction1.commit();
        session1.close();

        // opening 2nd session
        Session session2 = sessionFactory.openSession();
        Transaction transaction2 = session2.beginTransaction();

        // fetching Laptop data whose id is 1 similar to what we fetched from 1st session
        // since the data is not present in 1st level caching of this session, it will fetch the data from 2nd level caching
        // 2nd level caching will only work if we have configured and enabled 'hibernate.cache.use_second_level_cache' property in '.cfg.xml' file
        Laptop laptop3 = (Laptop) session2.get(Laptop.class, (long)1);

        // committing and closing the 2nd session
        transaction2.commit();
        session2.close();
    }

    private static SessionFactory getSessionFactory(String configFile) {
        Configuration config = new Configuration().configure(configFile)
                .addAnnotatedClass(Employee.class)
                .addAnnotatedClass(Laptop.class)
                .addAnnotatedClass(Department.class)
                .addAnnotatedClass(Technology.class);
        ServiceRegistry registry = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
        SessionFactory sessionFactory = config.buildSessionFactory(registry);
        return sessionFactory;
    }
}