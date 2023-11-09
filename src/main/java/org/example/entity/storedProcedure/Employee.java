package org.example.entity.storedProcedure;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "SPEmployee")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "SPGetDepartmentEmployeeInfo",
                procedureName = "GetDepartmentEmployeeInfo",
                parameters = {
                        @StoredProcedureParameter(
                                mode = ParameterMode.IN,
                                type = String.class,
                                name = "depName"),
                        @StoredProcedureParameter(
                                mode = ParameterMode.OUT,
                                type = Integer.class,
                                name = "empCount")
                },
                resultClasses = Employee.class
        )
})
public class Employee {

    @Id
    @Column(name = "employeeID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @ManyToOne()
    @JoinColumn(name = "departmentID")
    private Department department;
}
