package it.gagarin.reminderbot.service

import it.gagarin.reminderbot.model.Reminder
import it.gagarin.reminderbot.repository.ReminderRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit
import java.util.function.Consumer

@Service
class ReminderService @Autowired constructor(private val reminderRepository: ReminderRepository) : IReminderService {
    override fun getReminder(reminderId: Long): Reminder? {
        val optional = reminderRepository.findById(reminderId)
        return optional.orElse(null)
    }

    override fun saveReminder(reminder: Reminder) {
        reminderRepository.save(reminder)
    }

    override fun deleteReminder(reminderId: Long) {
        reminderRepository.deleteById(reminderId)
    }

    override fun findAllByChatId(chatId: Long?): List<Reminder> {
        return reminderRepository.findAllByChatId(chatId)
    }

    override fun findAllByRunDateEqualsNow(consumer: Consumer<Reminder?>) {
        val dateTimeForSearch = OffsetDateTime.now().truncatedTo(ChronoUnit.MINUTES)
        val notificationToRemindNow = reminderRepository
            .findAllByRunDateEquals(dateTimeForSearch)
        if (notificationToRemindNow!!.isEmpty()) {
            log.info("Nothing found tasks for sent.")
        } else {
            log.info("Found {} notifications to remind. Sending reminders", notificationToRemindNow.size)
            for (notification in notificationToRemindNow) {
                consumer.accept(notification)
            }
            log.info("All tasks were sent to users.")
        }
    }

    override fun deleteAllByRunDateBeforeDone() {
        try {
            TimeUnit.SECONDS.sleep(10)
            val dateTimeForSearch = OffsetDateTime.now()
            val doneReminders = reminderRepository.findAllByRunDateBefore(dateTimeForSearch)
            if (doneReminders!!.isEmpty()) {
                log.info("Nothing found tasks for delete.")
            } else {
                log.info("Found {} done reminders to delete. Deleting reminders", doneReminders.size)
                reminderRepository.deleteAll(doneReminders)
                log.info("All done tasks were delete from DB.")
            }
        } catch (e: InterruptedException) {
            log.error("Error deleted reminders")
        }
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(ReminderService::class.java)
    }
}