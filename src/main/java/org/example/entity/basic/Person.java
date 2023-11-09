package org.example.entity.basic;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "BasicPerson")
public class Person {

    @Id
    @Column(name = "personID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @ManyToOne()
    @JoinColumn(name = "guideID")
    private Guide guide;

    @Override
    public int hashCode() {
        return (int)this.id;
    }

    @Override
    public boolean equals(Object object) {
        if(!(object instanceof Person)) return false;
        Person person = (Person) object;
        return person.getId()==this.id;
    }
}
