package it.gagarin.reminderbot.model

import java.io.Serializable
import java.time.OffsetDateTime
import javax.persistence.*

@Entity
@Table(name = "reminder")
data class Reminder(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(name = "chatid")
    var chatId: Long = 0L,
    var text: String = "",

    @Column(name = "rundate")
    var runDate: OffsetDateTime = OffsetDateTime.now(),
) : Serializable