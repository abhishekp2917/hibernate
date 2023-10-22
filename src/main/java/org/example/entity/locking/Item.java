package org.example.entity.locking;

import lombok.*;
import org.hibernate.annotations.OptimisticLock;
import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity

public class Item {

    @Id
    @Column(name = "itemID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private int quantity;

    // including version field to keep track of entity version so that we can detect update conflicts
    @Version
    private long version;
}
