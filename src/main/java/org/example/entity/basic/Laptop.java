package org.example.entity.basic;

import lombok.*;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity

// When @Cacheable annotation is applied to an entity class, Hibernate will enable caching for instances of that class.
// Cached entities can be retrieved from memory, which reduces the need to fetch them from the database repeatedly.
@Cacheable

// This specifies that the cache is to be used for read-only operations. This means that Hibernate will cache instances
// of the Laptop class when they are retrieved from the database, and subsequent read operations will use the cached data.
// However, any updates made to instances of Laptop won't automatically update the cache. This is suitable for scenarios
// where the data doesn't change frequently.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Laptop {

    @Id
    @Column(name = "laptopID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
}
