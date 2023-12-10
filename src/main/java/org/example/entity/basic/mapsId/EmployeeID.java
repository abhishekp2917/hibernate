package org.example.entity.basic.mapsId;

import lombok.*;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Embeddable
public class EmployeeID implements Serializable {

    private long departmentID;
    private long roleID;
}
