/*
    Code to demonstrate second level caching of entities having READ_WRITE concurrency strategy
*/

package org.example.caching;

import org.example.entity.caching.Mobile;
import org.example.util.SessionCreator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class Main3 {
    public static void main(String[] args) {

        // creating SessionFactory object to open multiple session
        // configuring Hibernate with config file which has configuration for second-level caching
        SessionFactory sessionFactory = SessionCreator.getSessionFactory(
                                    "caching\\hibernate-second-level-caching.cfg.xml",
                                            Mobile.class);

        // opening 1st session and beginning the transaction
        Session session1 = sessionFactory.openSession();
        Transaction transaction1 = session1.beginTransaction();

        // fetching Mobile data whose id is 1
        Mobile mobile1 = (Mobile) session1.get(Mobile.class, (long)1);
        System.out.println("Mobile details from first session from DB: " + mobile1);

        // updating the name of the Mobile of id 1 due to which other session will not be able to access the cache concurrently
        // this update will reflect in cache also due to which other sessions can access the cache as cache data is not
        // 'dirty'
        mobile1.setName(mobile1.getName()+ " Updated");
        System.out.println("Mobile details after update : " + mobile1);
        session1.update(mobile1);

        // committing and closing the 1st session
        transaction1.commit();
        session1.close();

        // opening 2nd session
        Session session2 = sessionFactory.openSession();
        Transaction transaction2 = session2.beginTransaction();

        // fetching Mobile data whose id is 1 similar to what we fetched from 1st session
        // since the data is not present in 1st level caching of this session, it will fetch the data from 2nd level caching
        // this time we will get updated data from cache as we are using READ_WRITE strategy which provides data consistency
        Mobile mobile2 = (Mobile) session2.get(Mobile.class, (long)1);
        System.out.println("Mobile details from second session from second level cache: " + mobile2);

        // committing and closing the 2nd session
        transaction2.commit();
        session2.close();
    }
}