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
public class Cat extends Animal {

    private String gender;
    @Override
    public String makeNoise() {
        return "Meow Meow";
    }
}
