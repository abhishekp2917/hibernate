package org.example.entity.locking;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "LockingOrders")
public class Orders {

    @Id
    @Column(name = "orderID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String itemName;
    private double price;
    @Version
    private long version;
}
