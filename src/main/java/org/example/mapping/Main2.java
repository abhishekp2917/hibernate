package org.example.mapping;

import org.example.entity.mapping.oneToMany.Department;
import org.example.entity.mapping.oneToMany.Employee;
import org.example.util.SessionCreator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class Main2 {
    public static void main(String[] args) {

        // creating SessionFactory object to open multiple session
        SessionFactory sessionFactory = SessionCreator.getSessionFactory(
                "basic\\hibernate-mysql.cfg.xml",
                            Department.class,
                            Employee.class);

        // creating session object and beginning the transaction
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        // creating department object
        Department department1 = Department.builder().name("Engineering").build();

        // creating Employee object and associating department with the respective employee
        Employee employee1 = Employee.builder()
                .name("Abhishek")
                .department(department1)
                .build();

        Employee employee2 = Employee.builder()
                .name("Ravi")
                .department(department1)
                .build();

        Employee employee3 = Employee.builder()
                .name("Saurabh")
                .department(department1)
                .build();

        // associating employees with their respective department
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);
        department1.setEmployees(employees);

        // persisting department objects
        // since department reference is cascaded, by saving department object employee objects will also get persisted
        session.save(department1);

        transaction.commit();

        // fetching employee data whose id is 1 and displaying the result
        Employee employee = (Employee) session.get(Employee.class, (long)1);
        displayEmployee(employee);

        // fetching department data whose id is 1 and displaying the result
        Department department = (Department) session.get(Department.class, (long)1);
        displayDepartment(department);

        // closing the current session
        session.close();
    }

    private static void displayEmployee(Employee employee) {
        System.out.println();
        System.out.println("Employee ID : " + employee.getId());
        System.out.println("Employee Name : " + employee.getName());
        System.out.println("Department : " + employee.getDepartment().getName());
    }

    private static void displayDepartment(Department department) {
        System.out.println();
        System.out.println("Department ID : " + department.getId());
        System.out.println("Department Name : " + department.getName());
        System.out.print("Employee : ");
        for(Employee employee : department.getEmployees()) System.out.print(employee.getName() + " ");
    }
}