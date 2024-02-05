package it.gagarin.reminderbot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ReminderBotApplication

fun main(args: Array<String>) {
    runApplication<ReminderBotApplication>(*args)
}
