package it.gagarin.reminderbot.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
data class BotConfig(
    @Value("\${telegram.bot.name}")
    var name: String,

    @Value("\${telegram.bot.token}")
    var token: String,

    @Value("\${telegram.bot.creator}")
    var creator: Long,

    @Value("\${telegram.bot.db.folder}")
    var dbFolder: String,
)