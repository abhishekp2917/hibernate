package org.example;

import org.example.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.Arrays;

public class Main2 {
    public static void main(String[] args) {

        // creating Laptop object
        Laptop laptop1 = Laptop.builder().name("MAC").build();
        Laptop laptop2 = Laptop.builder().name("Dell").build();
        Laptop laptop3 = Laptop.builder().name("HP").build();

        // creating Department object
        // since foreign key will be hold by Employee table and not department, even if we add list of employees for a department
        // while inserting the data in DB, this list won't be considered during insertion
        Department department1 = Department.builder().name("Sales").build();
        Department department2 = Department.builder().name("Product").build();

        // creating Technology object
        Technology technology1 = Technology.builder().name("Java").build();
        Technology technology2 = Technology.builder().name("DotNet").build();

        // creating Employee object and even though we are adding list of technologies associated with a particular employee, this
        // data won't be considered while inserting data into DB
        Employee employee1 = Employee.builder()
                .name("Abhishek")
                .salary(10000.0)
                .laptop(laptop1)
                .department(department1)
                .technologies(new ArrayList<>(Arrays.asList(technology1, technology2)))
                .build();

        Employee employee2 = Employee.builder()
                .name("Ravi")
                .salary(12000.0)
                .laptop(laptop2)
                .department(department1)
                .technologies(new ArrayList<>(Arrays.asList(technology1)))
                .build();

        Employee employee3 = Employee.builder()
                .name("Saurabh")
                .salary(13000.0)
                .laptop(laptop3)
                .department(department2)
                .build();

        // since Join Table schema will be defined by Technology entity, adding list of employees mapped to a particular technology
        // while inserting the data in DB, this list will be considered during insertion
        technology1.setEmployees(new ArrayList<>(Arrays.asList(employee2, employee3)));
        technology2.setEmployees(new ArrayList<>(Arrays.asList(employee3)));

        // creating session object
        Session session = getSession("hibernate-mysql.cfg.xml");

        Transaction transaction = session.beginTransaction();

        session.save(laptop1);
        session.save(laptop2);
        session.save(laptop3);

        session.save(department1);
        session.save(department2);

        session.save(technology1);
        session.save(technology2);

        session.save(employee1);
        session.save(employee2);
        session.save(employee3);

        transaction.commit();

        // fetching employee data whose id is 1 and displaying the result
        Employee employeeResult = (Employee) session.get(Employee.class, (long)1);
        displayEmployee(employeeResult);
        
        // fetching technology data whose id is 1 and displaying the result
        Technology technologyResult = (Technology) session.get(Technology.class, (long)1);
        displayTechnology(technologyResult);

        // closing the current session
        session.close();
    }

    private static Session getSession(String configFile) {
        Configuration config = new Configuration().configure(configFile)
                .addAnnotatedClass(Employee.class)
                .addAnnotatedClass(Laptop.class)
                .addAnnotatedClass(Department.class)
                .addAnnotatedClass(Technology.class);
        ServiceRegistry registry = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
        SessionFactory sessionFactory = config.buildSessionFactory(registry);
        return sessionFactory.openSession();
    }

    private static void displayEmployee(Employee employee) {
        System.out.println();
        System.out.println("Employee ID : " + employee.getId());
        System.out.println("Employee Name : " + employee.getName());
        System.out.println("Employee Salary : " + employee.getSalary());
        System.out.println("Employee Laptop : " + employee.getLaptop().getName());
        System.out.println("Employee Department : " + employee.getDepartment().getName());
        System.out.print("Technologies on which employee work on : ");
        for(Technology technology : employee.getTechnologies()) System.out.print(technology.getName() + " ");
    }

    private static void displayTechnology(Technology technology) {
        System.out.println();
        System.out.println("Technology ID : " + technology.getId());
        System.out.println("Technology Name : " + technology.getName());
        System.out.print("List of employee work on this Technology : ");
        for(Employee employee : technology.getEmployees()) System.out.print(employee.getName() + " ");
    }
}