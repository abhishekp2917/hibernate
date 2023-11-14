package org.example.entity.fetching;

//import jakarta.persistence.NamedAttributeNode;
//import jakarta.persistence.NamedEntityGraph;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "FetchingDepartment")
//@NamedEntityGraph(name = "department.employees",
//        attributeNodes = @NamedAttributeNode("employees")
//)
public class Department {

    @Id
    @Column(name = "departmentID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @OneToOne(cascade = CascadeType.PERSIST, mappedBy = "department")
    private Manager manager;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "department")
    @Fetch(FetchMode.SELECT)
    private List<Employee> employees;
}
