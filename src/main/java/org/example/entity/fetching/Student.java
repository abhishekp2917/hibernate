package org.example.entity.fetching;

import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "FetchingStudent")
public class Student {

    @Id
    @Column(name = "studentID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @BatchSize(size = 4)
    @JoinColumn(name = "laptopID")
    private Laptop laptop;
}
