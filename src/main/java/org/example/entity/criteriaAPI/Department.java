package org.example.entity.criteriaAPI;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "criteriaDepartment")
public class Department {

    @Id
    @Column(name = "departmentID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @OneToMany(mappedBy = "department")
    private List<Employee> employees;

    @Override
    public String toString() {
        return String.format("[Id : %d Name : %s]", id, name);
    }
}
