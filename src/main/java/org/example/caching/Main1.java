/*
    Code to demonstrate first level caching of entities
*/

package org.example.caching;

import org.example.entity.Laptop;
import org.example.util.SessionCreator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class Main1 {
    public static void main(String[] args) {

        // creating SessionFactory object to open multiple session
        // configuring Hibernate with config file which has configuration for first-level caching
        SessionFactory sessionFactory = SessionCreator.getSessionFactory(
                                    "caching\\hibernate-first-level-caching.cfg.xml",
                                            Laptop.class);

        // opening 1st session and transaction
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
        // 2nd level caching will only work if we have configured and enabled 'hibernate.cache.use_second_level_cache'
        // property in '.cfg.xml' file which we haven't done in this case due to which it will get data from DB
        Laptop laptop3 = (Laptop) session2.get(Laptop.class, (long)1);

        // committing and closing the 2nd session
        transaction2.commit();
        session2.close();
    }
}