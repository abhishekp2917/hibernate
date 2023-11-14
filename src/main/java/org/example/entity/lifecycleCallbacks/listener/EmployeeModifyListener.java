package org.example.entity.lifecycleCallbacks.listener;

import org.example.entity.lifecycleCallbacks.Employee;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

// listener class whose method will be called based on the annotation used
// it is reusable that means one can associate this listener to multiple entity
public class EmployeeModifyListener {

    // this method will be called just before persisting or updating the entity
    @PrePersist
    @PreUpdate
    private void modifyEntity(Object object) {
        if(object instanceof Employee) {
            Employee employee = (Employee) object;
            employee.setFirstName(String.format("%s %s", employee.getFirstName(), employee.getLastName()));
            employee.setLastUpdatedTimeStamp(LocalDateTime.now());
        }
    }
}
