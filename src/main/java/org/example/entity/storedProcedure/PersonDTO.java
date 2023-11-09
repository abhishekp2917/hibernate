package org.example.entity.storedProcedure;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {

    private long id;
    private String name;
}
