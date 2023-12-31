package org.example.entity.basic.compositeKeys;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "CompositeDepartment")
public class Department {

    @EmbeddedId
    @Column(name = "departmentID")
    private DepartmentID id;
    private String name;
    @OneToMany(mappedBy = "department")
    private List<Employee> employees;
}
