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
@Entity

// The @Cache annotation is used for entity-level caching in Hibernate. When you annotate an entity class or a class
// property with @Cache, you specify how Hibernate should cache instances of that entity class or collections of those
// entities.
// cache region is named as 'electronics'. A separate region named 'educationalInstitute' will be created and all
// the Laptop entity will be cached there providing better control over cached data
// Here we are using READ_ONLY concurrency strategy
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "electronics")
public class Laptop {

    @Id
    @Column(name = "laptopID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "personID")
    private Person person;

    // providing own implementation of toString cause if we don't provide own implementation, Person class toString will
    // call Laptop class toString method and since Laptop class also has reference of Person class, Laptop toString method
    // will call Person class toString method which will lead to endless loop cause StackoverflowError
    @Override
    public String toString() {
        return String.format("%d %s", this.id, this.name);
    }
}
