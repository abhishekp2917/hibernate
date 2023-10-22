/*
    Code to demonstrate Pessimistic locking
*/

package org.example.locking;

import org.example.entity.locking.Orders;
import org.example.util.SessionCreator;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Scanner;

public class Main2 {
    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {

        try {
            // creating SessionFactory object to open multiple session
            SessionFactory sessionFactory = SessionCreator.getSessionFactory(
                    "basic/hibernate-mysql.cfg.xml",
                    Orders.class);

            // opening 1st session and transaction
            Session session = sessionFactory.openSession();
            Transaction transaction1 = session.beginTransaction();

            // getting the orders entity from DB of id 1 from first session (user)
            // explore various LockMode by changing LockMode in below statement
            Orders order = (Orders) session.get(Orders.class, (long) 1, LockMode.OPTIMISTIC_FORCE_INCREMENT);
            System.out.println(String.format("Order from first session : %s", order));

            // reading the order entity data of same id while a LockMode is applied to that data
            System.out.print("Do you want to read from another transaction?[YES/NO] : ");
            if(sc.next().equalsIgnoreCase("yes")) readFromAnotherTransaction(sessionFactory);

            // writing the order entity data of same id while a LockMode is applied to that data
            System.out.print("Do you want to write from another transaction?[YES/NO] : ");
            if(sc.next().equalsIgnoreCase("yes")) writeFromAnotherTransaction(sessionFactory);

            // getting order entity of id 1 after other transaction reads/updates the entity
            System.out.println("Again fetching order entity");
            Orders order1 = (Orders) session.get(Orders.class, (long) 1);

            // updating the item price after other transaction reads/updates the entity
            order1.setPrice(order1.getPrice()-100);
            session.update(order1);
            System.out.println(String.format("Order after update from other session : %s", order1));

            // committing and closing the 1st session
            transaction1.commit();
            session.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    private static void readFromAnotherTransaction(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        // getting the orders entity from DB of id 1
        Orders order = (Orders) session.get(Orders.class, (long) 1);
        System.out.println(String.format("Order from other session : %s", order));
        // committing and closing the session
        transaction.commit();
        session.close();
    }

    private static void writeFromAnotherTransaction(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        // getting the orders entity from DB of id 1
        Orders order = (Orders) session.get(Orders.class, (long) 1);
        System.out.println(String.format("Order from other session : %s", order));
        // updating the item price
        order.setPrice(order.getPrice()-100);
        session.update(order);
        System.out.println(String.format("Order after update from other session : %s", order));
        // committing and closing the session
        transaction.commit();
        session.close();
    }

}

