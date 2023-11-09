package org.example.entity.mapping.oneToOne;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "MappingOTOLaptop")
public class Laptop {
    @Id
    @Column(name = "laptopID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    @OneToOne(mappedBy = "laptop")
    private Employee employee;
}
