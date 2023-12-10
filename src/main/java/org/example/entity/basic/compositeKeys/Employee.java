package org.example.entity.basic.compositeKeys;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "CompositeEmployee")
public class Employee {

    @Id
    @Column(name = "employeeID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name="department", referencedColumnName = "departmentID"),
            @JoinColumn(name="region", referencedColumnName = "regionID")
    })
    private Department department;

    @Override
    public String toString() {
        return String.format("[Id : %d Name : %s Department : [ %d %s %s ]]",
                id,
                name,
                department.getId().getDepartmentID(),
                department.getId().getRegionID(),
                department.getName());
    }
}
