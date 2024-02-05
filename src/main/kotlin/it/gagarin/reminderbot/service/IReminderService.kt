package it.gagarin.reminderbot.service

import it.gagarin.reminderbot.model.Reminder
import java.util.function.Consumer

interface IReminderService {
    fun getReminder(reminderId: Long): Reminder?
    fun saveReminder(reminder: Reminder)
    fun deleteReminder(reminderId: Long)
    fun findAllByChatId(chatId: Long?): List<Reminder>
    fun findAllByRunDateEqualsNow(consumer: Consumer<Reminder?>)
    fun deleteAllByRunDateBeforeDone()
}