package org.example.entity.basic;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "BasicFriend")
public class Friend {

    @Id
    @Column(name = "friendID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @ElementCollection
    @CollectionTable(name = "friendNickname", joinColumns = @JoinColumn(name = "friendID"))
    @Column(name = "Nickname")
    private List<String> nicknames;
}
