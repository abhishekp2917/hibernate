/*
    Code to demonstrate how to work with DTO using Criteria API
*/

package org.example.criteriaAPI;

import org.example.entity.hql.Department;
import org.example.entity.hql.Employee;
import org.example.entity.hql.EmployeeDTO;
import org.example.util.SessionCreator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;

public class Main2 {
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
                                .department(department1).build();

        // persisting employee objects
        // since employee reference is cascaded, by saving employee object department objects will also get persisted
        session1.save(employee1);
        session1.save(employee2);
        session1.save(employee3);

        // committing and closing the session
        transaction1.commit();
        session1.close();

        // creating another session object and beginning the transaction
        Session session2 = sessionFactory.openSession();
        Transaction transaction2 = session2.beginTransaction();

        // creating CriteriaBuilder to create CriteriaQuery
        CriteriaBuilder criteriaBuilder = session2.getCriteriaBuilder();

        // creating CriteriaQuery of EmployeeDTO type as result type will be of EmployeeDTO
        CriteriaQuery<EmployeeDTO> criteriaQuery = criteriaBuilder.createQuery(EmployeeDTO.class);
        Root<Employee> root = criteriaQuery.from(Employee.class);

        // getting reference of various attributes of Employee entity
        Path<Long> id = root.get("id");
        Path<Long> firstName = root.get("firstName");
        Path<Long> lastName = root.get("lastName");
        Path<Long> department = root.get("department");

        // using construct() method to define how EmployeeDTO object should be instantiated and selecting the result
        criteriaQuery.select(
                criteriaBuilder.construct(
                        EmployeeDTO.class,
                        id, firstName, lastName, department)
        );

        // creating TypedQuery of EmployeeDTO type as this is what we have defined in CriteriaQuery
        TypedQuery<EmployeeDTO> query = session2.createQuery(criteriaQuery);
        List<EmployeeDTO> employees = query.getResultList();
        displayEmployee(employees);

        // committing and closing the session
        transaction2.commit();
        session2.close();
    }

    private static void displayEmployee(List<EmployeeDTO> employees) {
        System.out.println();
        for(EmployeeDTO employee : employees) System.out.println(employee);
        System.out.println();
    }
}