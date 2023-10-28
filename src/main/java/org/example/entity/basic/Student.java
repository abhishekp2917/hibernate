package org.example.entity.basic;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Student {

    @Id
    @Column(name = "studentID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int age;

    // since this object is not an entity, we are defining this as @Embedded due to which whatever variables
    // this object holds will be embedded in student table
    @Embedded
    private Address address;

    @ElementCollection
    private List<String> phoneNumbers;
}
