package org.example.entity.lifecycleCallbacks;

import lombok.*;
import org.example.entity.lifecycleCallbacks.listener.EmployeeModifyListener;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity()
// defining entity listener class whose methods will be called during the lifecycle of Employee entity
@EntityListeners({EmployeeModifyListener.class})
public class Employee {

    @Id
    @Column(name = "employeeID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String firstName;
    @Transient
    private String lastName;
    private LocalDateTime lastUpdatedTimeStamp;
    @Transient
    private boolean hasRemoved;

    // this method will be called just after persisting, updating or loading the entity
    @PostLoad
    @PostUpdate
    @PostPersist
    private void splitName() {
        String[] tempArr = this.firstName.split(" ");
        this.firstName = tempArr[0];
        this.lastName = tempArr[1];
    }

    // this method will be called when entity has been removed from DB
    @PostRemove
    private void updateEntityStatus() {
        this.hasRemoved = true;
    }

    public boolean getHasRemoved() {
        return this.hasRemoved;
    }
}
