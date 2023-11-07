package org.example.entity.basic.inheritance;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Dog extends Animal {

    private String breed;
    @Override
    public String makeNoise() {
        return "Woof Woof";
    }
}
