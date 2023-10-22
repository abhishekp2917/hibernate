/*
    Code to demonstrate Optimistic locking
*/

package org.example.locking;

import org.example.entity.locking.Item;
import org.example.util.SessionCreator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class Main1 {
    public static void main(String[] args) {

        try {
            // creating SessionFactory object to open multiple session
            SessionFactory sessionFactory = SessionCreator.getSessionFactory(
                    "basic/hibernate-mysql.cfg.xml",
                    Item.class);

            // opening 1st session and transaction
            Session session1 = sessionFactory.openSession();
            Transaction transaction1 = session1.beginTransaction();

            // getting the item entity from DB of id 1 from first session (user)
            // version in DB : v1
            // version in entity : v1
            Item item1 = (Item) session1.get(Item.class, (long) 1);
            System.out.println(String.format("Item from first session : %s", item1));

            // decrementing the item quantity from the inventory by 1
            item1.setQuantity(item1.getQuantity()-1);

            // opening 2nd session which will also fetch the same item and will decrement the item quantity by 1
            Session session2 = sessionFactory.openSession();
            Transaction transaction2 = session2.beginTransaction();

            // getting the item entity from DB of id 1 from second session (user)
            // version in DB : v1
            // version in entity : v1
            Item item2 = (Item) session2.get(Item.class, (long) 1);
            System.out.println(String.format("Item from second session : %s", item2));

            // decrementing the item quantity from the inventory by 1 and updating the DB which will update the item version
            // version in DB : v1+1
            // version in entity item2 : v1+1
            System.out.println("Decrementing the item quantity from second session");
            item2.setQuantity(item2.getQuantity()-1);
            session2.update(item2);

            // committing the closing the second session
            transaction2.commit();
            session2.close();

            // updating the item from first session which was already updated by second session due to which there will be
            // version mismatch and an exception will be thrown
            // version in DB : v1+1
            // version in entity item1 : v1
            System.out.println("Decrementing the item quantity from first session");
            session1.update(item1);

            // committing and closing the 1st session
            transaction1.commit();
            session1.close();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}