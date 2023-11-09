package org.example.entity.caching;

import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "CachingPerson")
public class Person {
    @Id
    @Column(name = "personID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    // specifying list of laptops to be cacheable due to which Hibernate will cache this collections but not the entire
    // Person entity
    @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    @OneToMany(mappedBy="person")
    private List<Laptop> laptops;
}
