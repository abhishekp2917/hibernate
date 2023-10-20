/*
    Code to demonstrate second level caching of entities having NONSTRICT_READ_WRITE concurrency strategy
*/

package org.example.caching;

import org.example.entity.caching.College;
import org.example.util.SessionCreator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class Main4 {
    public static void main(String[] args) {

        // creating SessionFactory object to open multiple session
        // configuring Hibernate with config file which has configuration for second-level caching
        SessionFactory sessionFactory = SessionCreator.getSessionFactory(
                                    "caching\\hibernate-second-level-caching.cfg.xml",
                                        College.class);

        // opening 1st session and beginning the transaction
        Session session1 = sessionFactory.openSession();
        Transaction transaction1 = session1.beginTransaction();

        // fetching College data whose id is 1
        College college1 = (College) session1.get(College.class, (long)1);
        System.out.println("College details from first session from DB: " + college1);

        // updating the name of the College of id 1 which will invalidate the cache data due to which
        college1.setName(college1.getName()+ " Updated");
        System.out.println("College details after update : " + college1);
        session1.update(college1);

        // committing and closing the 1st session
        transaction1.commit();
        session1.close();

        // opening 2nd session
        Session session2 = sessionFactory.openSession();
        Transaction transaction2 = session2.beginTransaction();

        // fetching College data whose id is 1 similar to what we fetched from 1st session
        // as data was updated in first session, the cache data got invalidated due to which this time service will get
        // college data from DB and not from cache
        College college2 = (College) session2.get(College.class, (long)1);
        System.out.println("College details from second session from second level cache: " + college2);

        // committing and closing the 2nd session
        transaction2.commit();
        session2.close();
    }
}