package org.example.entity.storedProcedure;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "SPPerson")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "SPGetPersonInfo",
                procedureName = "GetPersonInfo",
                parameters = {
                        @StoredProcedureParameter(
                                mode = ParameterMode.IN,
                                type = Integer.class,
                                name = "personAge")
                },
                resultSetMappings = "PersonDTOMapping"
        )
})
@SqlResultSetMapping(
        name = "PersonDTOMapping",
        classes = @ConstructorResult(
                targetClass = PersonDTO.class,
                columns = {
                        @ColumnResult(name = "personID", type = Long.class),
                        @ColumnResult(name = "name", type = String.class),
                }
        )
)
public class Person {

    @Id
    @Column(name = "personID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int age;
}
