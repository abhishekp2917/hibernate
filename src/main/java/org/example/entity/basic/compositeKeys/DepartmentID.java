package org.example.entity.basic.compositeKeys;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DepartmentID implements Serializable {

    private long departmentID;
    private long regionID;
}
