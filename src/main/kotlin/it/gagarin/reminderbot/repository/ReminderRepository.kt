package it.gagarin.reminderbot.repository

import it.gagarin.reminderbot.model.Reminder
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime

@Repository
interface ReminderRepository : CrudRepository<Reminder?, Long?> {
    fun findAllByChatId(chatId: Long?): List<Reminder>
    fun findAllByRunDateEquals(dateTime: OffsetDateTime?): List<Reminder?>?
    fun findAllByRunDateBefore(dateTime: OffsetDateTime?): List<Reminder?>?
}