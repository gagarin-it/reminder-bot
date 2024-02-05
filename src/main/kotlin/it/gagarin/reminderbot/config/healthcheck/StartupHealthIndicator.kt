package it.gagarin.reminderbot.config.healthcheck

import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.stereotype.Component

@Component
class StartupHealthIndicator : HealthIndicator {
    override fun health(): Health {
        return Health.up().build()
    }
}