package org.example.entity.basic;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name="BasicCourse")
public class Course {

    @Id
    @Column(name="CourseID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="CourseName")
    private String name;
}
