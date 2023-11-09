package org.example.entity.caching;

import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "CachingSchool")

// The @Cache annotation is used for entity-level caching in Hibernate. When you annotate an entity class or a class
// property with @Cache, you specify how Hibernate should cache instances of that entity class or collections of those
// entities.
// cache region is named as 'educationalInstitute'. A separate region named 'educationalInstitute' will be created and all
// the School entity will be cached there providing better control over cached data
// Here we are using TRANSACTIONAL concurrency strategy
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "educationalInstitute")
public class School {
    @Id
    @Column(name = "schoolID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
}
