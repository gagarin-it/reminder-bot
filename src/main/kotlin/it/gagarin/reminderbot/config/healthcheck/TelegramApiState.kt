package it.gagarin.reminderbot.config.healthcheck

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.stereotype.Component
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL

@Component
class TelegramApiState : HealthIndicator {
    @Value("\${telegram.url}")
    lateinit var telegramUrl: String

    override fun health(): Health {
        return if (isConnect) {
            Health.up().build()
        } else {
            Health.down().build()
        }
    }

    val isConnect: Boolean
        get() = try {
            val telegramApiUrl = URL(telegramUrl)
            val telegramApiConnection = telegramApiUrl.openConnection() as HttpURLConnection
            telegramApiConnection.requestMethod = "GET"
            telegramApiConnection.connect()
            telegramApiConnection.responseCode == HttpURLConnection.HTTP_OK
        } catch (ex: Exception) {
            log.warn("TelegramApiUrl failed connection")
            false
        }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(TelegramApiState::class.java)
    }
}