package org.example.entity.criteriaAPI;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    private long id;
    private String firstName;
    private String lastName;
    private Department department;

    @Override
    public String toString() {
        return String.format("[Id : %d First Name : %s Last Name : %s Department : %s]", id, firstName, lastName, department.getName());
    }
}
