package org.example.entity.basic;

import lombok.*;
import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "BasicGuide")
public class Guide {

    @Id
    @Column(name = "guideID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "guide")
    private Set<Person> persons;
}
