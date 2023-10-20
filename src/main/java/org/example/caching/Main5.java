/*
    Code to demonstrate second level caching of entities having TRANSACTIONAL concurrency strategy
*/

package org.example.caching;

import org.example.entity.caching.School;
import org.example.util.SessionCreator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class Main5 {
    public static void main(String[] args) {

        // creating SessionFactory object to open multiple session
        // configuring Hibernate with config file which has configuration for second-level caching
        SessionFactory sessionFactory = SessionCreator.getSessionFactory(
                                    "caching\\hibernate-second-level-caching.cfg.xml",
                                        School.class);

        // opening 1st session and beginning the transaction
        Session session1 = sessionFactory.openSession();
        Transaction transaction1 = session1.beginTransaction();

        // fetching School data whose id is 1 which will store data in second level cache
        School school1 = (School) session1.get(School.class, (long)1);
        System.out.println("School details from first session from DB : " + school1);

        // committing and closing the 1st session
        transaction1.commit();
        session1.close();

        // opening 2nd session
        Session session2 = sessionFactory.openSession();
        Transaction transaction2 = session2.beginTransaction();

        // fetching School data whose id is 1 which we will get from second level cache
        School school2 = (School) session2.get(School.class, (long)1);
        System.out.println("School details from second session from second level cache : " + school2);

        // updating the name of the School of id 1
        school2.setName(school2.getName()+ " Updated");
        System.out.println("School details after update : " + school2);
        session2.update(school2);

        // opening 3rd session
        Session session3 = sessionFactory.openSession();
        Transaction transaction3 = session3.beginTransaction();

        // fetching School data whose id is 1,
        // although we have updated the name of school but since the concurrency strategy is set to Transactional, the
        // changes will not get reflected in cache until we commit the changes due to which this time we will get old data
        // since we have fetched the school data before committing the changes, the old data will be set in cache as an updated
        // one due to which even after committing the transaction, cache will not update
        School school3 = (School) session3.get(School.class, (long)1);
        System.out.println("School details from third session from second level cache : " + school3);

        // committing and closing the 2nd session
        transaction2.commit();
        session2.close();

        // committing and closing the 3rd session
        transaction3.commit();
        session3.close();
    }
}