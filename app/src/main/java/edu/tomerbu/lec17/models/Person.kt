package edu.tomerbu.lec17.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.UUID

//added a comment
@Entity(tableName = "people")
data class Person(
    val name: String,
    @PrimaryKey val personId: String = UUID.randomUUID().toString(),
)

//View
data class PersonWithDog(
    @Embedded
    val person: Person,

    @Relation(
        parentColumn = "personId",
        entityColumn = "dogId"
    )
    val dog: Dog?
)