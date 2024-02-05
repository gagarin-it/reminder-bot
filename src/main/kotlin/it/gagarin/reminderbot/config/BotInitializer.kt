package it.gagarin.reminderbot.config

import it.gagarin.reminderbot.service.ReminderBot
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

@Configuration
class BotInitializer {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(ReminderBot::class.java)
    }

    @Bean
    fun telegramBotsApi(reminderBot: ReminderBot): TelegramBotsApi =
        TelegramBotsApi(DefaultBotSession::class.java).apply {
            try {
                registerBot(reminderBot)
                log.info("{} is registered", reminderBot.botUsername)
            } catch (e: TelegramApiException) {
                log.error(e.message)
            }
        }
}