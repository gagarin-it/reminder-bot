package it.gagarin.reminderbot.service

import it.gagarin.reminderbot.config.Constants
import it.gagarin.reminderbot.model.Reminder
import it.gagarin.reminderbot.model.TgUser
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.telegram.abilitybots.api.bot.AbilityBot
import java.time.ZoneOffset

@Component
class ReminderScheduler @Autowired constructor(
    private val userService: IUserService,
    private val abilityBot: AbilityBot,
    private val reminderService: IReminderService
) {
    @Scheduled(cron = "\${cron.scheduler}")
    private fun remindAboutScheduled() {
        reminderService.findAllByRunDateEqualsNow { reminder: Reminder? -> sendRemainderMessage(reminder) }
        reminderService.deleteAllByRunDateBeforeDone()
    }

    private fun sendRemainderMessage(reminder: Reminder?) {
        val tgUser = reminder?.let { userService.findUser(it.chatId) }
        when (tgUser?.language) {
            Constants.LANGUAGE_CHOOSE_RU -> {
                val reminderMessage = getReminderMessage(tgUser, reminder, Constants.REMINDER_RU)
                abilityBot.silent().send(reminderMessage, reminder.chatId)
                log.info("Send '{}' to {}", reminderMessage, reminder.chatId)
            }
            Constants.LANGUAGE_CHOOSE_EN -> {
                val reminderMessage = getReminderMessage(tgUser, reminder, Constants.REMINDER_EN)
                abilityBot.silent().send(reminderMessage, reminder.chatId)
                log.info("Send '{}' to {}", reminderMessage, reminder.chatId)
            }
        }
    }

    private fun getReminderMessage(tgUser: TgUser?, reminder: Reminder?, notificationMessage: String): String {
        val zone = ZoneOffset.of(tgUser?.timeZone)
        return String.format(
            notificationMessage,
            reminder?.text, reminder?.runDate?.withOffsetSameInstant(zone)?.format(Constants.DATE_TIME_FORMATTER)
        )
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(ReminderScheduler::class.java)
    }
}