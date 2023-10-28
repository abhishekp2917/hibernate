package org.example.mapping;

import org.example.entity.mapping.manyToMany.Course;
import org.example.entity.mapping.manyToMany.Student;
import org.example.util.SessionCreator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.ArrayList;
import java.util.List;

public class Main3 {
    public static void main(String[] args) {

        // creating SessionFactory object to open multiple session
        SessionFactory sessionFactory = SessionCreator.getSessionFactory(
                "basic\\hibernate-mysql.cfg.xml",
                            Student.class,
                            Course.class);

        // creating session object and beginning the transaction
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        // creating Student object
        Student student1 = Student.builder()
                .name("Abhishek")
                .build();

        Student student2 = Student.builder()
                .name("Ravi")
                .build();

        Student student3 = Student.builder()
                .name("Saurabh")
                .build();

        Student student4 = Student.builder()
                .name("Tushar")
                .build();

        // creating Course object
        Course course1 = Course.builder()
                .name("HTML")
                .build();

        Course course2 = Course.builder()
                .name("ReactJS")
                .build();

        Course course3 = Course.builder()
                .name("Java")
                .build();

        Course course4 = Course.builder()
                .name("NodeJS")
                .build();

        // associating Student with their respective list of Courses
        List<Course> frontendCourses = new ArrayList<>();
        frontendCourses.add(course1);
        frontendCourses.add(course2);
        student1.setCourses(frontendCourses);

        List<Course> backendCourses = new ArrayList<>();
        backendCourses.add(course3);
        backendCourses.add(course4);
        student2.setCourses(backendCourses);

        // associating Course with their respective list of Students
        List<Student> javaBatch = new ArrayList<>();
        javaBatch.add(student1);
        javaBatch.add(student2);
        course1.setStudents(javaBatch);

        List<Student> HTMLBatch = new ArrayList<>();
        HTMLBatch.add(student3);
        HTMLBatch.add(student4);
        course2.setStudents(HTMLBatch);

        // persisting student objects
        // since student reference is cascaded, by saving student object associated course objects will also get persisted
        session.save(student1);
        session.save(student2);
        session.save(student3);
        session.save(student4);

        transaction.commit();

        // fetching student data whose id is 1 and displaying the result
        Student student = (Student) session.get(Student.class, (long)3);
        displayStudent(student);

        // fetching course data whose id is 1 and displaying the result
        Course course = (Course) session.get(Course.class, (long)4);
        displayCourse(course);

        // closing the current session
        session.close();
    }

    private static void displayStudent(Student student) {
        System.out.println();
        System.out.println("Student ID : " + student.getId());
        System.out.println("Student Name : " + student.getName());
        System.out.print("Course : ");
        for(Course course : student.getCourses()) System.out.print(course.getName() + " ");
    }

    private static void displayCourse(Course course) {
        System.out.println();
        System.out.println("Course ID : " + course.getId());
        System.out.println("Course Name : " + course.getName());
        System.out.print("Student : ");
        for(Student student : course.getStudents()) System.out.print(student.getName() + " ");
    }
}