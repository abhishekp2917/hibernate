/*
    Code to demonstrate query caching
*/

package org.example.caching;

import org.example.entity.caching.Laptop;
import org.example.entity.caching.Person;
import org.example.util.SessionCreator;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class Main6 {
    public static void main(String[] args) {

        // creating SessionFactory object to open multiple session
        // configuring Hibernate with config file which has configuration for second-level caching
        SessionFactory sessionFactory = SessionCreator.getSessionFactory(
                                    "caching\\hibernate-query-caching.cfg.xml",
                                        Laptop.class,
                                        Person.class);

        // opening 1st session and beginning the transaction
        Session session1 = sessionFactory.openSession();
        Transaction transaction1 = session1.beginTransaction();

        // creating a query to fetch laptop of id 1
        Query query1 = session1.createQuery("FROM Laptop WHERE id = :id");
        query1.setParameter("id", (long)1);
        // it is mandatory to set the query as cacheable else query caching won't work even if enabling query caching in
        // config file
        query1.setCacheable(true);

        // executing the query
        // this time it will go to DB and will fetch the data and will cache the results
        query1.list().forEach(System.out::println);

        // executing the same query from same session
        // this time as query cache is enabled, it will fetch the Laptop data from cache
        query1.list().forEach(System.out::println);

        // committing and closing the 1st session
        transaction1.commit();
        session1.close();

        // opening 2nd session
        Session session2 = sessionFactory.openSession();
        Transaction transaction2 = session2.beginTransaction();

        // creating another query object having same query as previous one
        Query query2 = session2.createQuery("FROM Laptop WHERE id = :id");
        query2.setParameter("id", (long)1);
        // to get leverage of query caching, we have to set each query object as cacheable
        query2.setCacheable(true);

        // executing the same query from another session
        // since we have enabled the query caching and query is also same, this time also it will get the Laptop data from
        // cache but as we haven't enabled caching for Person entity, it will get the Person data from DB
        query2.list().forEach(System.out::println);

        // committing and closing the 2nd session
        transaction2.commit();
        session2.close();
    }
}