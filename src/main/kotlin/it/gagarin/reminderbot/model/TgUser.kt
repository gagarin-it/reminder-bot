package it.gagarin.reminderbot.model

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tguser")
data class TgUser(
    @Id
    var id: Long = 0L,

    @Column(name = "timezone")
    var timeZone: String = "",
    var language: String = "",
) : Serializable