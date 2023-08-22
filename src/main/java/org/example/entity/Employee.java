package org.example.entity;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee {

    @Id
    @Column(name = "employeeID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private double salary;

    // defining one-to-one relationship between Employee and Laptop entity
    // since it is one-to-one relationship, Laptop entity don't need to hold reference of Employee
    @OneToOne()
    @JoinColumn(name = "laptopID")
    private Laptop laptop;

    // defining many-to-one relationship between Employee and Department entity
    @ManyToOne()
    @JoinColumn(name = "departmentID")
    private Department department;

    // defining many-to-many relationship between Employee and Technology entity
    // moreover, Technology entity will take care of Join Table schema and data that need to be entered
    @ManyToMany(mappedBy = "employees", fetch = FetchType.LAZY)
    private List<Technology> technologies;
}
