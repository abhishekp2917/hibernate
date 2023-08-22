package org.example.entity;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Technology {

    @Id
    @Column(name="technologyID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    // defining many-to-many relationship with Employee
    // since in Employee entity, we have defined that relationship will be mapped by Technology entity, table schema will de defined by Technology entity
    // moreover, what data to be entered in the Join Table will be decided on the basis of list of Employee object holds by Technology class object and not on
    // the list of Technology object holds by Employee class object as Technology entity will define the schema of Join Table
    @ManyToMany()
    @JoinTable(
            name = "EmployeeTechnology",
            joinColumns = @JoinColumn(name = "technologyID"),
            inverseJoinColumns = @JoinColumn(name = "employeeID")
    )
    private List<Employee> employees;
}
