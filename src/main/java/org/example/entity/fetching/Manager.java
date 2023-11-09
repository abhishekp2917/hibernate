package org.example.entity.fetching;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "FetchingManager")
public class Manager {

    @Id
    @Column(name = "managerID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @OneToOne()
    @JoinColumn(name = "departmentID")
    private Department department;
}
