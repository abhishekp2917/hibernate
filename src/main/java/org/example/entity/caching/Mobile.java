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
@Entity(name = "CachingMobile")

// The @Cache annotation is used for entity-level caching in Hibernate. When you annotate an entity class or a class
// property with @Cache, you specify how Hibernate should cache instances of that entity class or collections of those
// entities.
// cache region is named as 'electronics'. A separate region named 'educationalInstitute' will be created and all
// the Mobile entity will be cached there providing better control over cached data
// Here we are using READ_WRITE concurrency strategy
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "electronics")
public class Mobile {

    @Id
    @Column(name = "mobileID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
}
