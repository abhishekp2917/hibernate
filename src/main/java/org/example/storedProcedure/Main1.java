/*
    Code to demonstrate how to execute named storedProcedure

    Use below MySQL query to create SP :

    ```
        USE temp;
        DELIMITER $$
        CREATE PROCEDURE GetDepartmentEmployeeInfo(
            IN departmentName VARCHAR(255),
            OUT employeeCount INT
        )
        BEGIN
            -- Declare a variable to store the department ID
            DECLARE depID INT;

            -- Find the department ID based on the department name
            SELECT departmentID INTO depID FROM spdepartment WHERE name = departmentName;

            -- Get the count of employees in the department
            SELECT COUNT(*) INTO employeeCount FROM spemployee WHERE departmentID = depID;

            -- Get the list of employees in the department
            SELECT * FROM spemployee WHERE departmentID = depID;
        END $$
        DELIMITER ;
    ```
*/

package org.example.storedProcedure;

import org.example.entity.storedProcedure.Department;
import org.example.entity.storedProcedure.Employee;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main1 {
    public static void main(String[] args) {

        // creating EntityManagerFactory object by specifying persistence unit name defined in 'Persistence.xml' file
        // from EntityManagerFactory object, create EntityManager which will create a Persistence context
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("mysql-persistence-unit");
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        // beginning the transaction
        entityManager1.getTransaction().begin();

        // first storing the data in employee and department table so that we would get result from SP

        // creating Department object
        Department department1 = Department.builder().name("Engineering").build();

        // creating employee objects and storing it into a list
        Employee employee1 = Employee.builder().name("Abhishek").department(department1).build();
        Employee employee2 = Employee.builder().name("Ravi").department(department1).build();
        List<Employee> employees1 = new ArrayList<>(Arrays.asList(employee1, employee2));

        // creating department object and associating employees with department object
        department1.setEmployees(employees1);

        // persisting department object which will persist associated entity as well, as cascading is enabled
        entityManager1.persist(department1);

        // committing and closing the entity manager will persist the entity in DB
        entityManager1.getTransaction().commit();
        entityManager1.close();

        // creating second EntityManager to execute SP
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();
        // beginning the transaction
        entityManager2.getTransaction().begin();

        // create StoredProcedureQuery object using entity manager in which pass the name of the SP query
        // don't pass the actual SP name instead pass the query name defined in @NamedStoredProcedureQuery annotation
        StoredProcedureQuery storedProcedure = entityManager2.createNamedStoredProcedureQuery("SPGetDepartmentEmployeeInfo");

        // set the input parameters
        storedProcedure.setParameter("depName", "Engineering");

        // execute the SP
        storedProcedure.execute();

        // use 'getOutputParameterValue()' to get the output parameter value
        int employeeCount = (int) storedProcedure.getOutputParameterValue("empCount");
        System.out.println(String.format("Total number of employee : %d", employeeCount));

        // use 'getResultList()' to get the list of record selected by the SP
        List<Employee> employees = storedProcedure.getResultList();
        for(Employee employee : employees) System.out.println(String.format("ID : %d Name : %s", employee.getId(), employee.getName()));

        // committing and closing the entity manager
        entityManager2.getTransaction().commit();
        entityManager2.close();
    }
}