package org.example.mapping;

import org.example.entity.mapping.oneToOne.Employee;
import org.example.entity.mapping.oneToOne.Laptop;
import org.example.util.SessionCreator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class Main1 {
    public static void main(String[] args) {

        // creating SessionFactory object to open multiple session
        SessionFactory sessionFactory = SessionCreator.getSessionFactory(
                "basic\\hibernate-mysql.cfg.xml",
                            Laptop.class,
                            Employee.class);

        // creating Laptop object
        Laptop laptop1 = Laptop.builder().name("MAC").build();
        Laptop laptop2 = Laptop.builder().name("Dell").build();
        Laptop laptop3 = Laptop.builder().name("HP").build();

        // creating Employee object and associating laptop with the respective employee
        Employee employee1 = Employee.builder()
                .name("Abhishek")
                .laptop(laptop1)
                .build();

        Employee employee2 = Employee.builder()
                .name("Ravi")
                .laptop(laptop2)
                .build();

        Employee employee3 = Employee.builder()
                .name("Saurabh")
                .laptop(laptop3)
                .build();

        // associating employee with the respective laptop
        laptop1.setEmployee(employee1);
        laptop2.setEmployee(employee2);
        laptop3.setEmployee(employee3);

        // creating session object and beginning the transaction
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        // persisting employee objects
        // since laptop reference is cascaded, by saving employee object laptop object will also get saved
        session.save(employee1);
        session.save(employee2);
        session.save(employee3);

        transaction.commit();

        // fetching employee data whose id is 1 and displaying the result
        Employee employee = (Employee) session.get(Employee.class, (long)1);
        displayEmployee(employee);

        // fetching laptop data whose id is 1 and displaying the result
        Laptop laptop = (Laptop) session.get(Laptop.class, (long)1);
        displayLaptop(laptop);

        // closing the current session
        session.close();
    }

    private static void displayEmployee(Employee employee) {
        System.out.println();
        System.out.println("Employee ID : " + employee.getId());
        System.out.println("Employee Name : " + employee.getName());
        System.out.println("Laptop : " + employee.getLaptop().getName());
    }

    private static void displayLaptop(Laptop laptop) {
        System.out.println();
        System.out.println("Laptop ID : " + laptop.getId());
        System.out.println("Laptop Name : " + laptop.getName());
        System.out.println("Employee : " + laptop.getEmployee().getName());
    }
}