/*
    Code to demonstrate Criteria API
*/

package org.example.criteriaAPI;

import org.example.entity.hql.Department;
import org.example.entity.hql.Employee;
import org.example.util.SessionCreator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
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

        // getting an instance of CriteriaBuilder from which we will create various CriteriaQuery
        CriteriaBuilder criteriaBuilder = session2.getCriteriaBuilder();

        // creating CriteriaQuery which is used to specify the result type of the query, in this case it is Employee
        CriteriaQuery<Employee> criteriaQuery1 = criteriaBuilder.createQuery(Employee.class);

        // creating root object which will specify which entity we want to query
        Root<Employee> root1 = criteriaQuery1.from(Employee.class);

        // specifying what column/attribute we want to select, in this case we are selecting entire root entity i.e.
        // Employee entity
        criteriaQuery1.select(root1);

        // passing criteriaQuery to createQuery() method will create executable query
        // since for criteriaQuery we have specified the result type as Employee, the type parameter of TypedQuery should
        // also be of Employee type
        TypedQuery<Employee> query1 = session2.createQuery(criteriaQuery1);;

        // getting list of Employee by executing the query and displaying it
        List<Employee> employees1 = query1.getResultList();
        displayEmployee(employees1);

        /* ---------------------------------------------------------------------------------------------------------- */

        // creating CriteriaQuery whose result type will be Employee
        // and creating Root object which will fetch data from Employee entity
        CriteriaQuery<Employee> criteriaQuery2 = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> root2 = criteriaQuery2.from(Employee.class);

        // getting reference to the column/attribute 'firstName' using get() method by passing the attribute name
        // since this attribute is of String type, the type of Path object is also String
        Path<String> firstName1 = root2.get("firstName");

        // creating Predicate object to specify the where clause condition
        // in this case query will fetch only those Employee whose firstname is equal to "Ravi"
        Predicate predicate1 = criteriaBuilder.equal(firstName1, "Ravi");

        // passing the Predicate object to where() method and creating TypedQuery of Employee type
        // we can pass multiple Predicate object in case there is multiple where clause
        criteriaQuery2.where(predicate1);
        TypedQuery<Employee> query2 = session2.createQuery(criteriaQuery2);
        List<Employee> employees2 = query2.getResultList();
        displayEmployee(employees2);

        /* ---------------------------------------------------------------------------------------------------------- */

        // creating CriteriaQuery whose result type will be Employee
        // and creating Root object which will fetch data from Employee entity
        CriteriaQuery<Employee> criteriaQuery3 = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> root3 = criteriaQuery3.from(Employee.class);

        // getting reference to the column/attribute 'firstName'
        Path<String> firstName2 = root3.get("firstName");

        // declaring predicate for query which will fetch only those Employee whose firstname is like the "%i%" expression
        Predicate predicate2 = criteriaBuilder.like(firstName2, "%i%");

        // passing the Predicate object to where() method and creating TypedQuery of Employee type
        criteriaQuery3.where(predicate2);
        TypedQuery query3 = session2.createQuery(criteriaQuery3);
        List<Employee> employees3 = query3.getResultList();
        displayEmployee(employees3);

        /* ---------------------------------------------------------------------------------------------------------- */

        // creating CriteriaQuery whose result type will be Employee
        // and creating Root object which will fetch data from Employee entity
        CriteriaQuery<Employee> criteriaQuery4 = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> root4 = criteriaQuery4.from(Employee.class);

        // getting reference to the column/attribute 'age'
        Path<Integer> age1 = root4.get("age");

        // declaring predicate for query which will fetch only those Employee whose age is between 23 and 25
        Predicate predicate3 = criteriaBuilder.between(age1, 23, 25);

        // passing the Predicate object to where() method and creating TypedQuery of Employee type
        criteriaQuery4.where(predicate3);
        TypedQuery<Employee> query4 = session2.createQuery(criteriaQuery4);
        List<Employee> employees4 = query4.getResultList();
        displayEmployee(employees4);

        /* ---------------------------------------------------------------------------------------------------------- */

        // creating CriteriaQuery whose result type will be Employee
        // and creating Root object which will fetch data from Employee entity
        CriteriaQuery<Employee> criteriaQuery5 = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> root5 = criteriaQuery5.from(Employee.class);

        // getting reference to the column/attribute 'firstName' and 'age'
        Path<String> firstName3 = root5.get("firstName");
        Path<Integer> age2 = root4.get("age");

        // defining order in which result should be fetched
        // it will fetch employee in descending order of their name and then in ascending order of their age
        Order order1 = criteriaBuilder.desc(firstName3);
        Order order2 = criteriaBuilder.asc(age2);

        // passing the order condition to orderBy() method similar to passing Predicate object to where() method
        // and creating TypedQuery of Employee type
        criteriaQuery5.orderBy(order1, order2);
        TypedQuery<Employee> query5 = session2.createQuery(criteriaQuery5);
        List<Employee> employees5 = query5.getResultList();
        displayEmployee(employees5);

        /* ---------------------------------------------------------------------------------------------------------- */

        // creating CriteriaQuery whose result type will be Long
        // and creating Root object which will fetch data from Employee entity
        CriteriaQuery<Long> criteriaQuery6 = criteriaBuilder.createQuery(Long.class);
        Root<Employee> root6 = criteriaQuery6.from(Employee.class);

        // creating Expression object using count() method which will be selected
        // all the aggregate function return type will be an Expression
        Expression<Long> expression1 = criteriaBuilder.count(root6);

        // selecting the expression to select the count of employees
        criteriaQuery6.select(expression1);

        // creating TypedQuery of Long type as result is of Long type
        TypedQuery<Long> query6 = session2.createQuery(criteriaQuery6);
        long employeeCount = (long) query6.getSingleResult();
        System.out.println(String.format("Employee count : %d", employeeCount));

        /* ---------------------------------------------------------------------------------------------------------- */

        // creating CriteriaQuery whose result type will be Department
        // and creating Root object which will fetch data from Employee entity
        CriteriaQuery<Department> criteriaQuery7 = criteriaBuilder.createQuery(Department.class);
        Root<Employee> root7 = criteriaQuery7.from(Employee.class);

        // selecting department column of Employee and grouping them by department
        criteriaQuery7.select(root7.get("department")).groupBy(root7.get("department"));

        TypedQuery<Department> query7 = session2.createQuery(criteriaQuery7);
        List<Department> departments = query7.getResultList();
        displayDepartment(departments);

        /* ---------------------------------------------------------------------------------------------------------- */

        // creating CriteriaQuery whose result type will be Object[] as we will be selecting multiple entity
        // and creating Root object which will fetch data from Employee entity
        CriteriaQuery<Object[]> criteriaQuery8 = criteriaBuilder.createQuery(Object[].class);
        Root<Employee> root8 = criteriaQuery8.from(Employee.class);

        // using join() method to define the join strategy
        // since we are joining Employee and Department, Join type will be <Employee, Department>
        // in join() method first argument is foreign key column/attribute
        // the second argument will define the type of join in this case it is INNER
        Join<Employee, Department> innerJoin = root8.join("department", JoinType.INNER);

        // selecting employee and department object after defining the join strategy
        criteriaQuery8.multiselect(root8, innerJoin);

        // creating TypeQuery of Object[] type as we aren't selecting single entity but multiple entity
        TypedQuery<Object[]> query8 = session2.createQuery(criteriaQuery8);
        List<Object[]> objects1 = query8.getResultList();
        displayObjects(objects1);

        /* ---------------------------------------------------------------------------------------------------------- */

        // creating CriteriaQuery for sub query whose result type is Long as we want list of Department ID's
        CriteriaQuery<Long> subCriteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Department> root9 = subCriteriaQuery.from(Department.class);

        // getting reference of name and id attribute
        // name for creating predicate and id for selecting the result
        Path<String> name = root9.get("name");
        Path<Long> id = root9.get("id");

        // creating predicate for fetching ID's of department whose name contains 'i' in it
        Predicate predicate4 = criteriaBuilder.like(name, "%i%");
        subCriteriaQuery.select(id).where(predicate4);

        // creating CriteriaQuery for main query to fetch list of Employees based on the department ID'S which we will get
        // from sub query
        CriteriaQuery<Employee> mainCriteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> root10 = mainCriteriaQuery.from(Employee.class);

        // getting department attribute reference for creating Expression object
        Path<Long> department = root10.get("department");

        // Expression object which will be used in query to select those records whose department ID is in result of
        // sub query
        Expression expression2 = department.in(session2.createQuery(subCriteriaQuery).getResultList());

        // selecting Employee entity based on Expression provided and creating TypedQuery of Employee Type
        mainCriteriaQuery.select(root10).where(expression2);
        TypedQuery<Employee> query9 = session2.createQuery(mainCriteriaQuery);
        List<Employee> employees6 = query9.getResultList();
        displayEmployee(employees6);

        /* ---------------------------------------------------------------------------------------------------------- */

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