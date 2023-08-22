package org.example.entity;

import lombok.*;

import javax.persistence.Embeddable;

// declaring this class as Embeddable so it won't be treated as a separate table
// instead will be embedded in the entity (Table) which holds this object
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Embeddable
public class Address {

    private String street;
    private String city;
    private String state;
}
