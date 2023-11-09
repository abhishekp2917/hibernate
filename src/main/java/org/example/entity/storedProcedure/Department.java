package org.example.entity.storedProcedure;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "SPDepartment")
public class Department {

    @Id
    @Column(name = "departmentID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "department")
    private List<Employee> employees;
}
