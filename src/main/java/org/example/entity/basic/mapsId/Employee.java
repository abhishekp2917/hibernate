package org.example.entity.basic.mapsId;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "MapsIdEmployee")
public class Employee {

    @EmbeddedId
    @Column(name = "EmployeeID")
    private EmployeeID id;
    private String name;
    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("departmentID")
    private Department department;

    @Override
    public String toString() {
        return String.format("[Role: %d Name : %s Department : [ %d %s ]]",
                id.getRoleID(),
                name,
                department.getId(),
                department.getName());
    }
}
