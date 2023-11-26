/*
    Code to demonstrate how to persist collection of basic value types
*/

package org.example.basic;

import org.example.entity.basic.Friend;
import org.example.util.SessionCreator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.*;

public class Main6 {
    public static void main(String[] args) {

        // creating SessionFactory object to open multiple session
        SessionFactory sessionFactory = SessionCreator.getSessionFactory(
                "basic\\hibernate-mysql.cfg.xml",
                        Friend.class);

        // by opening the session, a new Persistence context will be created
        Session session1 = sessionFactory.openSession();
        Transaction transaction1 = session1.getTransaction();
        transaction1.begin();

        // creating a Friend object
        Friend friend1 = Friend.builder().name("Abhishek").build();
        Friend friend2 = Friend.builder().name("Christoper").build();

        List<String> friend1NickNames = new ArrayList<>(Arrays.asList("Abhi", "AB"));
        List<String> friend2NickNames = new ArrayList<>(Arrays.asList("Chris", "Christ"));

        // associating nickname collection with respective Friend object
        friend1.setNicknames(friend1NickNames);
        friend2.setNicknames(friend2NickNames);

        // persisting the Friend entities
        session1.save(friend1);
        session1.save(friend2);

        // committing and closing the session
        transaction1.commit();
        session1.close();

        // opening the second session, a new Persistence context will be created
        Session session2 = sessionFactory.openSession();
        Transaction transaction2 = session2.getTransaction();
        transaction2.begin();

        // fetching the Friend entity whose id is 1
        Friend friend3 = (Friend) session2.get(Friend.class, (long)1);

        // getting list of friend nicknames
        List<String> friend3NickNames = friend3.getNicknames();
        System.out.println(String.format("Name : %s NickName : %s", friend3.getName(), friend3NickNames));

        transaction2.commit();
        session2.close();
    }
}