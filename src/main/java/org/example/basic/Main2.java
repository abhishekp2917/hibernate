/*
    Code demonstrate state transition of entity across various hibernate states
*/

package org.example.basic;

import org.example.entity.basic.Student;
import org.example.util.SessionCreator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class Main2 {
    public static void main(String[] args) {

        // creating SessionFactory object to open multiple session
        SessionFactory sessionFactory = SessionCreator.getSessionFactory(
                "basic\\hibernate-mysql.cfg.xml",
                Student.class);


        // by opening the session, a new Persistence context will be created
        Session session1 = sessionFactory.openSession();
        Transaction transaction1 = session1.getTransaction();
        transaction1.begin();

        // creating student object which will be initially in the transient state
        Student student1 = Student.builder().name("Abhishek").age(22).build();

        // saving the student object will move the student entity to persistent state (Persistence context)
        // we can use saveOrUpdate() or merge() method also to persist the entity
        session1.save(student1);

        transaction1.commit();
        // on closing the session, all the entity present in the persistent state (Persistence context) will be moved to
        // detached state and the persistent context will be removed
        session1.close();


        // opening a new session will create a new persistent context
        Session session2 = sessionFactory.openSession();
        Transaction transaction2 = session2.getTransaction();
        transaction2.begin();

        // getting student object whose id is 1
        // this will create a new object in 2nd persistent context
        Student student2 = (Student) session2.get(Student.class, (long)1);

        // updating the name of the 1st student object which is in detached state
        // since this detached object is not being tracked by Persistent context, any change made to this object will have
        // no impact on DB until this object is not moved back to the persistent context
        student1.setName("Ravi");

        // detaching the 2nd student from the 2nd Persistent context
        session2.evict(student2);

        // merging the 1st student object (detached object) in 2nd Persistent context. Since there is no student object
        // of id 1 in 2nd Persistent context, hibernate will issue a select query to get that student entity from DB and
        // will merge detached entity with recently fetched entity
        session2.merge(student1);

        // committing the transaction and closing the 2nd session. Before closing the Persistent context hibernate will
        // compare all the persisted entity with the DB records and if there is any difference, hibernate will issue an
        // update query to synchronize the data
        transaction2.commit();
        session2.close();


        // opening a new session will create a new persistent context
        Session session3 = sessionFactory.openSession();
        Transaction transaction3 = session3.getTransaction();
        transaction3.begin();

        // updating the detached object will create a persisted copy of this object in persistent context
        // if there is already a same object of same id in persistent context, then calling update will give exception
        // as persistent context holds only unique entity (object)
        session3.update(student2);

        // committing and closing the session will update the student entity and then will move it to detached state
        transaction3.commit();
        session3.close();


        // opening a new session will create a new persistent context
        Session session4 = sessionFactory.openSession();
        Transaction transaction4 = session4.getTransaction();
        transaction4.begin();

        // getting student object of id 1 which will persist the object in Persistent context
        Student student3 = (Student) session4.get(Student.class, (long)1);

        // deleting the object will move it to removed state from Persistent context
        session4.delete(student3);

        // merging the removed object will bring the object back to Persistent context
        session4.persist(student3);

        transaction4.commit();
        session4.close();
    }
}