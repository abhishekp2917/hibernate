package org.example.entity.criteriaAPI;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "criteriaEmployee")
public class Employee {

    @Id
    @Column(name = "employeeID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private int age;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "departmentID")
    private Department department;

    @Override
    public String toString() {
        return String.format("[Id : %d First Name : %s Last Name : %s Age : %d Department : %s]", id, firstName, lastName, age, department.getName());
    }
}
