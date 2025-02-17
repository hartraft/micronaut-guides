package example.micronaut.domain

import grails.gorm.annotation.Entity
import org.grails.datastore.gorm.GormEntity
import io.micronaut.serde.annotation.Serdeable

@Serdeable
@Entity // <1>
class UserRole implements GormEntity<UserRole> { // <2>
    User user
    Role role

    static constraints = {
        user nullable: false
        role nullable: false
    }
}
