package org.example.entity.fetching;

import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Laptop {
    @Id
    @Column(name = "laptopID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    @BatchSize(size = 2)
    @OneToOne(mappedBy = "laptop")
    private Student student;
}
