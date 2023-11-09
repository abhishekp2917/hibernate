package org.example.entity.mapping.manyToMany;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "MappingMTMCourse")
public class Course {

    @Id
    @Column(name="CourseID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @ManyToMany()
    @JoinTable(
            name = "CourseStudents",
            joinColumns = @JoinColumn(name = "CourseID"),
            inverseJoinColumns = @JoinColumn(name = "StudentID")
    )
    private List<Student> students;
}
