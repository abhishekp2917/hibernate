/*
    Code to demonstrate HQL
*/

package org.example.hql;

import org.example.entity.hql.Department;
import org.example.entity.hql.Employee;
import org.example.util.SessionCreator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import javax.persistence.Query;
import java.util.List;

public class Main1 {
    public static void main(String[] args) {

        // creating SessionFactory object to open multiple session
        SessionFactory sessionFactory = SessionCreator.getSessionFactory(
                "basic\\hibernate-mysql.cfg.xml",
                            Department.class,
                            Employee.class);

        // creating session objects and beginning the transaction
        Session session1 = sessionFactory.openSession();
        Transaction transaction1 = session1.beginTransaction();

        // creating department object
        Department department1 = Department.builder().name("Engineering").build();
        Department department2 = Department.builder().name("Sales").build();
        Department department3 = Department.builder().name("Product").build();

        // creating Employee objects and associating department with the respective employee
        Employee employee1 = Employee.builder()
                                .firstName("Abhishek")
                                .lastName("Pandey")
                                .age(22)
                                .department(department1).build();
        Employee employee2 = Employee.builder()
                                .firstName("Ravi")
                                .lastName("Singh")
                                .age(24)
                                .department(department1).build();
        Employee employee3 = Employee.builder()
                                .firstName("Ravi")
                                .lastName("Gupta")
                                .age(23)
                                .department(department2).build();
        Employee employee4 = Employee.builder()
                                .firstName("Saurabh")
                                .lastName("Pathak")
                                .age(21)
                                .department(department2).build();
        Employee employee5 = Employee.builder()
                                .firstName("Tushar")
                                .lastName("Singh")
                                .age(22)
                                .department(department3).build();

        // persisting employee objects
        // since employee reference is cascaded, by saving employee object department objects will also get persisted
        session1.save(employee1);
        session1.save(employee2);
        session1.save(employee3);
        session1.save(employee4);
        session1.save(employee5);

        // committing and closing the session
        transaction1.commit();
        session1.close();

        // creating another session object and beginning the transaction
        Session session2 = sessionFactory.openSession();
        Transaction transaction2 = session2.beginTransaction();

        // HQL query to fetch the list of employees from hqlEmployee entity alias as 'e'
        // we have to match the entity name and not the class name. In this case class name is Employee while entity name
        // is hqlEmployee
        Query query1 = session2.createQuery("FROM hqlEmployee e");
        List<Employee> employees1 = query1.getResultList();
        displayEmployee(employees1);

        // HQL query to fetch the employee list based on WHERE clause
        // here ':firstName' is positional parameter which is passed later in the code
        Query query2 = session2.createQuery("FROM hqlEmployee e WHERE e.firstName=:firstName");
        query2.setParameter("firstName", "Ravi");
        List<Employee> employees2 = query2.getResultList();
        displayEmployee(employees2);

        // HQL query to fetch the employee list based on LIKE clause
        Query query3 = session2.createQuery("FROM hqlEmployee e WHERE e.firstName LIKE :firstName");
        query3.setParameter("firstName", "%i%");
        List<Employee> employees3 = query3.getResultList();
        displayEmployee(employees3);

        // HQL query to fetch the employee list whose age lies between a certain range
        // since positional parameter is not named instead it's numeric, while passing the parameter value, position index
        // must be used instead of name
        Query query4 = session2.createQuery("FROM hqlEmployee e WHERE e.age BETWEEN ?1 AND ?2");
        query4.setParameter(1, 23);
        query4.setParameter(2, 25);
        List<Employee> employees4 = query4.getResultList();
        displayEmployee(employees4);

        // HQL query to fetch employee list order by first name in descending order coupled with last name in ascending order
        Query query5 = session2.createQuery("FROM hqlEmployee e ORDER BY e.firstName DESC, e.lastName");
        List<Employee> employees5 = query5.getResultList();
        displayEmployee(employees5);

        // HQL query using aggregate function 'COUNT' which will result the count of employee
        // since it will return only one row of type long, we used getSingleResult() method
        Query query6 = session2.createQuery("SELECT COUNT(e) FROM hqlEmployee e");
        long employeeCount = (long) query6.getSingleResult();
        System.out.println(String.format("Employee count : %d", employeeCount));

        // HQL query using GROUP BY clause which will group all the department
        Query query7 = session2.createQuery("SELECT e.department FROM hqlEmployee e GROUP BY e.department");
        List<Department> departments = query7.getResultList();
        displayDepartment(departments);

        // HQL query to INNER join Employee and Department table
        // since select column is employee and department object, we will get object[] list of size 2 where index 0 will
        // have employee object and index 1 will have department object
        Query query8 = session2.createQuery("SELECT e, d FROM hqlEmployee e INNER JOIN e.department d");
        List<Object[]> objects1 = query8.getResultList();
        displayObjects(objects1);

        // HQL query to LEFT join Employee and Department table
        // It is to note that HQL doesn't support RIGHT join
        // since select column is employee and department object, we will get object[] list of size 2 where index 0 will
        // have employee object and index 1 will have department object
        Query query9 = session2.createQuery("SELECT e,d FROM hqlEmployee e LEFT JOIN e.department d");
        List<Object[]> objects2 = query9.getResultList();
        displayObjects(objects2);

        // HQL query to CROSS join Employee and Department table
        // since select column is employee and department object, we will get object[] list of size 2 where index 0 will
        // have employee object and index 1 will have department object
        Query query10 = session2.createQuery("SELECT e,d FROM hqlEmployee e, hqlDepartment d");
        List<Object[]> objects3 = query10.getResultList();
        displayObjects(objects3);

        // HQL query using subQuery listing out employees of department whose value depends on subQuery
        Query query11 = session2.createQuery("FROM hqlEmployee e WHERE e.department IN (SELECT d.id FROM hqlDepartment d WHERE d.name LIKE :name)");
        query11.setParameter("name", "%e%");
        List<Employee> employees6 = query11.getResultList();
        displayEmployee(employees6);

        // HQL query to list out employees with OFFSET and LIMIT
        // setFirstResult() is used to set the OFFSET and setMaxResults() for LIMIT
        Query query12 = session2.createQuery("FROM hqlEmployee");
        query12.setFirstResult(2);
        query12.setMaxResults(3);
        List<Employee> employees7 = query12.getResultList();
        displayEmployee(employees7);

        // committing and closing the session
        transaction2.commit();
        session2.close();
    }

    private static void displayEmployee(List<Employee> employees) {
        System.out.println();
        for(Employee employee : employees) System.out.println(employee);
        System.out.println();
    }

    private static void displayDepartment(List<Department> departments) {
        System.out.println();
        for(Department department : departments) System.out.println(department);
        System.out.println();
    }

    private static void displayObjects(List<Object[]> objects) {
        System.out.println();
        for(Object[] object : objects) {
            for(int i=0; i<object.length; i++) {
                System.out.print(object[i] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}