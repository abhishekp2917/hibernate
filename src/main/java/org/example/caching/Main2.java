/*
    Code to demonstrate second level caching of entities having READ_ONLY concurrency strategy
*/

package org.example.caching;

import org.example.entity.caching.Laptop;
import org.example.entity.caching.Person;
import org.example.util.SessionCreator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class Main2 {
    public static void main(String[] args) {

        // creating SessionFactory object to open multiple session
        // configuring Hibernate with config file which has configuration for second-level caching
        SessionFactory sessionFactory = SessionCreator.getSessionFactory(
                                    "caching\\hibernate-second-level-caching.cfg.xml",
                                            Laptop.class,
                                            Person.class);
        try {
            // opening 1st session and beginning the transaction
            Session session1 = sessionFactory.openSession();
            Transaction transaction1 = session1.beginTransaction();

            // fetching Laptop data whose id is 1
            Laptop laptop1 = (Laptop) session1.get(Laptop.class, (long)1);
            System.out.println("Laptop details from first session from DB: " + laptop1);

            // fetching Laptop data with same id i.e. 1 in the same session
            // this time service will not go to DB, instead it will get the data from 1st level caching
            Laptop laptop2 = (Laptop) session1.get(Laptop.class, (long)1);
            System.out.println("Laptop details from first session from first level cache : " + laptop2);

            // updating the name of the Laptop of id 1 which will give exception as concurrency strategy is set to READ-ONLY
            // which doesn't allow any update to entity
            laptop2.setName(laptop2.getName()+ " Updated");
            System.out.println("Laptop details after update : " + laptop2);
            session1.update(laptop2);

            // committing and closing the 1st session
            transaction1.commit();
            session1.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            // opening 2nd session
            Session session2 = sessionFactory.openSession();
            Transaction transaction2 = session2.beginTransaction();

            // fetching Laptop data whose id is 1 similar to what we fetched from 1st session
            // since the data is not present in 1st level caching of this session, it will fetch the data from 2nd level caching
            // in this case we have enabled second level caching due to which Laptop data will be fetched from second level cache
            Laptop laptop3 = (Laptop) session2.get(Laptop.class, (long)1);
            System.out.println("Laptop details from second session from second level cache: " + laptop3);

            // committing and closing the 2nd session
            transaction2.commit();
            session2.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}