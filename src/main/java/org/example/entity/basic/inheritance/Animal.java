package org.example.entity.basic.inheritance;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Animal {

    @Id
    @Column(name = "animalID")
    private long id;
    private String name;
    public abstract String makeNoise();

    @Override
    public String toString() {
        return String.format("%s making %s noises", name, makeNoise());
    }
}
