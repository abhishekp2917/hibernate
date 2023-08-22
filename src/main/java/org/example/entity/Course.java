package org.example.entity;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name="CS_Course")
public class Course {

    @Id
    @Column(name="CourseID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="CourseName")
    private String name;
}
