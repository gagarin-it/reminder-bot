package it.gagarin.reminderbot.config

import it.gagarin.reminderbot.model.Reminder
import it.gagarin.reminderbot.model.TgUser
import it.gagarin.reminderbot.service.IReminderService
import it.gagarin.reminderbot.service.IUserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.telegram.abilitybots.api.sender.MessageSender
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import java.io.Serializable
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeParseException
import java.util.concurrent.atomic.AtomicInteger
import java.util.function.Function

class MessageFactory(private val sender: MessageSender, private val userService: IUserService, private val reminderService: IReminderService) {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(MessageFactory::class.java)
    }


    fun start(chatId: Long, user: User, usersInMemory: MutableMap<Long, TgUser?>) {
        try {
            sender.execute<Message, SendMessage>(
                SendMessage.builder()
                    .text(startReplyText(user))
                    .chatId(chatId)
                    .build()
            )
            createUserInMemory(chatId, usersInMemory)
            settings(chatId)
        } catch (e: TelegramApiException) {
            log.error(e.message)
        }
    }

    private fun startReplyText(user: User): String {
        val nameUser = parseNameUser(user)
        return String.format(Constants.START_REPLY, nameUser)
    }

    private fun parseNameUser(user: User): String {
        return if (user.firstName.isEmpty() && user.lastName.isEmpty()) {
            user.userName
        } else {
            String.format("%s %s", user.firstName, user.lastName)
        }
    }

    fun settings(chatId: Long) {
        try {
            sender.execute<Message, SendMessage>(
                SendMessage.builder()
                    .text(Constants.SETTINGS_REPLY)
                    .chatId(chatId)
                    .build()
            )
            languageConfiguration(chatId)
        } catch (e: TelegramApiException) {
            log.error(e.message)
        }
    }

    fun addReminder(chatId: Long?) {
        try {
            val tgUser: TgUser? = chatId?.let { userService.findUser(it) }
            when (tgUser?.language) {
                Constants.LANGUAGE_CHOOSE_RU -> sender.execute<Message, SendMessage>(
                    SendMessage.builder()
                        .text(Constants.ADD_REPLY_RU)
                        .chatId(chatId!!)
                        .replyMarkup(KeyboardFactory.forceReply())
                        .build()
                )
                Constants.LANGUAGE_CHOOSE_EN -> sender.execute<Message, SendMessage>(
                    SendMessage.builder()
                        .text(Constants.ADD_REPLY_EN)
                        .chatId(chatId!!)
                        .replyMarkup(KeyboardFactory.forceReply())
                        .build()
                )
            }
        } catch (e: TelegramApiException) {
            log.error(e.message)
        }
    }

    @kotlin.Throws(TelegramApiException::class)
    private fun languageConfiguration(chatId: Long) {
        sender.execute<Message, SendMessage>(
            SendMessage.builder()
                .text(Constants.LANGUAGE_OPTION)
                .chatId(chatId)
                .replyMarkup(KeyboardFactory.languageButtons())
                .build()
        )
    }

    @kotlin.Throws(TelegramApiException::class)
    private fun timezoneConfigurationRu(chatId: Long) {
        sender.execute<Message, SendMessage>(
            SendMessage.builder()
                .text(Constants.TIMEZONE_OPTION_RU)
                .chatId(chatId)
                .replyMarkup(KeyboardFactory.timezoneButtons())
                .build()
        )
    }

    @kotlin.Throws(TelegramApiException::class)
    private fun timezoneConfigurationEn(chatId: Long) {
        sender.execute<Message, SendMessage>(
            SendMessage.builder()
                .text(Constants.TIMEZONE_OPTION_EN)
                .chatId(chatId)
                .replyMarkup(KeyboardFactory.timezoneButtons())
                .build()
        )
    }

    fun replyToSettings(chatId: Long, messageIdCallback: Int, buttonId: String?, usersInMemory: MutableMap<Long, TgUser?>) {
        try {
            when (buttonId) {
                Constants.LANGUAGE_CHOOSE_RU -> {
                    replyToRuLanguage(chatId, messageIdCallback)
                    changeLanguageInMemory(chatId, Constants.LANGUAGE_CHOOSE_RU, usersInMemory)
                    timezoneConfigurationRu(chatId)
                }
                Constants.LANGUAGE_CHOOSE_EN -> {
                    replyToEnLanguage(chatId, messageIdCallback)
                    changeLanguageInMemory(chatId, Constants.LANGUAGE_CHOOSE_EN, usersInMemory)
                    timezoneConfigurationEn(chatId)
                }
                Constants.TIMEZONE_HOWLAND_ISLAND -> {
                    changeTimezoneInMemory(chatId, messageIdCallback, Constants.TIMEZONE_HOWLAND_ISLAND, usersInMemory)
                    saveUserToDb(chatId, usersInMemory)
                }
                Constants.TIMEZONE_AMERICAN_SAMOA -> {
                    changeTimezoneInMemory(chatId, messageIdCallback, Constants.TIMEZONE_AMERICAN_SAMOA, usersInMemory)
                    saveUserToDb(chatId, usersInMemory)
                }
                Constants.TIMEZONE_ALASKA -> {
                    changeTimezoneInMemory(chatId, messageIdCallback, Constants.TIMEZONE_ALASKA, usersInMemory)
                    saveUserToDb(chatId, usersInMemory)
                }
                Constants.TIMEZONE_MARQUESAS_ISLANDS -> {
                    changeTimezoneInMemory(chatId, messageIdCallback, Constants.TIMEZONE_MARQUESAS_ISLANDS, usersInMemory)
                    saveUserToDb(chatId, usersInMemory)
                }
                Constants.TIMEZONE_GAMBIER_ISLANDS -> {
                    changeTimezoneInMemory(chatId, messageIdCallback, Constants.TIMEZONE_GAMBIER_ISLANDS, usersInMemory)
                    saveUserToDb(chatId, usersInMemory)
                }
                Constants.TIMEZONE_CALIFORNIA -> {
                    changeTimezoneInMemory(chatId, messageIdCallback, Constants.TIMEZONE_CALIFORNIA, usersInMemory)
                    saveUserToDb(chatId, usersInMemory)
                }
                Constants.TIMEZONE_ALBERTA -> {
                    changeTimezoneInMemory(chatId, messageIdCallback, Constants.TIMEZONE_ALBERTA, usersInMemory)
                    saveUserToDb(chatId, usersInMemory)
                }
                Constants.TIMEZONE_MANITOBA -> {
                    changeTimezoneInMemory(chatId, messageIdCallback, Constants.TIMEZONE_MANITOBA, usersInMemory)
                    saveUserToDb(chatId, usersInMemory)
                }
                Constants.TIMEZONE_CUBA -> {
                    changeTimezoneInMemory(chatId, messageIdCallback, Constants.TIMEZONE_CUBA, usersInMemory)
                    saveUserToDb(chatId, usersInMemory)
                }
                Constants.TIMEZONE_BARBADOS -> {
                    changeTimezoneInMemory(chatId, messageIdCallback, Constants.TIMEZONE_BARBADOS, usersInMemory)
                    saveUserToDb(chatId, usersInMemory)
                }
                Constants.TIMEZONE_NEWFOUNDLAND -> {
                    changeTimezoneInMemory(chatId, messageIdCallback, Constants.TIMEZONE_NEWFOUNDLAND, usersInMemory)
                    saveUserToDb(chatId, usersInMemory)
                }
                Constants.TIMEZONE_ARGENTINA -> {
                    changeTimezoneInMemory(chatId, messageIdCallback, Constants.TIMEZONE_ARGENTINA, usersInMemory)
                    saveUserToDb(chatId, usersInMemory)
                }
                Constants.TIMEZONE_FERNANDO_DE_NORONHA -> {
                    changeTimezoneInMemory(chatId, messageIdCallback, Constants.TIMEZONE_FERNANDO_DE_NORONHA, usersInMemory)
                    saveUserToDb(chatId, usersInMemory)
                }
                Constants.TIMEZONE_CAPE_VERDE -> {
                    changeTimezoneInMemory(chatId, messageIdCallback, Constants.TIMEZONE_CAPE_VERDE, usersInMemory)
                    saveUserToDb(chatId, usersInMemory)
                }
                Constants.TIMEZONE_UTC_TEXT -> {
                    changeTimezoneInMemory(chatId, messageIdCallback, Constants.TIMEZONE_UTC_VALUE, usersInMemory)
                    saveUserToDb(chatId, usersInMemory)
                }
                Constants.TIMEZONE_MONACO -> {
                    changeTimezoneInMemory(chatId, messageIdCallback, Constants.TIMEZONE_MONACO, usersInMemory)
                    saveUserToDb(chatId, usersInMemory)
                }
                Constants.TIMEZONE_EGYPT -> {
                    changeTimezoneInMemory(chatId, messageIdCallback, Constants.TIMEZONE_EGYPT, usersInMemory)
                    saveUserToDb(chatId, usersInMemory)
                }
                Constants.TIMEZONE_MOSCOW -> {
                    changeTimezoneInMemory(chatId, messageIdCallback, Constants.TIMEZONE_MOSCOW, usersInMemory)
                    saveUserToDb(chatId, usersInMemory)
                }
                Constants.TIMEZONE_IRAN -> {
                    changeTimezoneInMemory(chatId, messageIdCallback, Constants.TIMEZONE_IRAN, usersInMemory)
                    saveUserToDb(chatId, usersInMemory)
                }
                Constants.TIMEZONE_DUBAI -> {
                    changeTimezoneInMemory(chatId, messageIdCallback, Constants.TIMEZONE_DUBAI, usersInMemory)
                    saveUserToDb(chatId, usersInMemory)
                }
                Constants.TIMEZONE_KABUL -> {
                    changeTimezoneInMemory(chatId, messageIdCallback, Constants.TIMEZONE_KABUL, usersInMemory)
                    saveUserToDb(chatId, usersInMemory)
                }
                Constants.TIMEZONE_MALDIVES -> {
                    changeTimezoneInMemory(chatId, messageIdCallback, Constants.TIMEZONE_MALDIVES, usersInMemory)
                    saveUserToDb(chatId, usersInMemory)
                }
                Constants.TIMEZONE_SRI_LANKA -> {
                    changeTimezoneInMemory(chatId, messageIdCallback, Constants.TIMEZONE_SRI_LANKA, usersInMemory)
                    saveUserToDb(chatId, usersInMemory)
                }
                Constants.TIMEZONE_NEPAL -> {
                    changeTimezoneInMemory(chatId, messageIdCallback, Constants.TIMEZONE_NEPAL, usersInMemory)
                    saveUserToDb(chatId, usersInMemory)
                }
                Constants.TIMEZONE_OMSK -> {
                    changeTimezoneInMemory(chatId, messageIdCallback, Constants.TIMEZONE_OMSK, usersInMemory)
                    saveUserToDb(chatId, usersInMemory)
                }
                Constants.TIMEZONE_MYANMAR -> {
                    changeTimezoneInMemory(chatId, messageIdCallback, Constants.TIMEZONE_MYANMAR, usersInMemory)
                    saveUserToDb(chatId, usersInMemory)
                }
                Constants.TIMEZONE_BANGKOK -> {
                    changeTimezoneInMemory(chatId, messageIdCallback, Constants.TIMEZONE_BANGKOK, usersInMemory)
                    saveUserToDb(chatId, usersInMemory)
                }
                Constants.TIMEZONE_CHINA -> {
                    changeTimezoneInMemory(chatId, messageIdCallback, Constants.TIMEZONE_CHINA, usersInMemory)
                    saveUserToDb(chatId, usersInMemory)
                }
                Constants.TIMEZONE_EUCLA -> {
                    changeTimezoneInMemory(chatId, messageIdCallback, Constants.TIMEZONE_EUCLA, usersInMemory)
                    saveUserToDb(chatId, usersInMemory)
                }
                Constants.TIMEZONE_SOUTH_KOREA -> {
                    changeTimezoneInMemory(chatId, messageIdCallback, Constants.TIMEZONE_SOUTH_KOREA, usersInMemory)
                    saveUserToDb(chatId, usersInMemory)
                }
                Constants.TIMEZONE_ADELAIDE -> {
                    changeTimezoneInMemory(chatId, messageIdCallback, Constants.TIMEZONE_ADELAIDE, usersInMemory)
                    saveUserToDb(chatId, usersInMemory)
                }
                Constants.TIMEZONE_SYDNEY -> {
                    changeTimezoneInMemory(chatId, messageIdCallback, Constants.TIMEZONE_SYDNEY, usersInMemory)
                    saveUserToDb(chatId, usersInMemory)
                }
                Constants.TIMEZONE_LORD_HOWE_ISLAND -> {
                    changeTimezoneInMemory(chatId, messageIdCallback, Constants.TIMEZONE_LORD_HOWE_ISLAND, usersInMemory)
                    saveUserToDb(chatId, usersInMemory)
                }
                Constants.TIMEZONE_MAGADAN -> {
                    changeTimezoneInMemory(chatId, messageIdCallback, Constants.TIMEZONE_MAGADAN, usersInMemory)
                    saveUserToDb(chatId, usersInMemory)
                }
                Constants.TIMEZONE_KAMCHATKA -> {
                    changeTimezoneInMemory(chatId, messageIdCallback, Constants.TIMEZONE_KAMCHATKA, usersInMemory)
                    saveUserToDb(chatId, usersInMemory)
                }
                Constants.TIMEZONE_CHATHAM_ISLANDS -> {
                    changeTimezoneInMemory(chatId, messageIdCallback, Constants.TIMEZONE_CHATHAM_ISLANDS, usersInMemory)
                    saveUserToDb(chatId, usersInMemory)
                }
                Constants.TIMEZONE_SAMOA -> {
                    changeTimezoneInMemory(chatId, messageIdCallback, Constants.TIMEZONE_SAMOA, usersInMemory)
                    saveUserToDb(chatId, usersInMemory)
                }
                Constants.TIMEZONE_LINE_ISLANDS -> {
                    changeTimezoneInMemory(chatId, messageIdCallback, Constants.TIMEZONE_LINE_ISLANDS, usersInMemory)
                    saveUserToDb(chatId, usersInMemory)
                }
            }
        } catch (ex: TelegramApiException) {
            log.error(ex.message)
        }
    }

    fun replyToReminders(chatId: Long, updMessage: Message, remindersInMemory: MutableMap<Long, Reminder?>) {
        try {
            when (updMessage.replyToMessage.text) {
                Constants.ADD_REPLY_RU -> {
                    createReminderInMemory(chatId, updMessage.text, remindersInMemory)
                    sender.execute<Message, SendMessage>(
                        SendMessage.builder()
                            .text(Constants.ADD_OPTION_RUN_DATE_RU)
                            .chatId(chatId)
                            .replyMarkup(KeyboardFactory.forceReply())
                            .build()
                    )
                }
                Constants.ADD_REPLY_EN -> {
                    createReminderInMemory(chatId, updMessage.text, remindersInMemory)
                    sender.execute<Message, SendMessage>(
                        SendMessage.builder()
                            .text(Constants.ADD_OPTION_RUN_DATE_EN)
                            .chatId(chatId)
                            .replyMarkup(KeyboardFactory.forceReply())
                            .build()
                    )
                }
                Constants.ADD_OPTION_RUN_DATE_RU -> {
                    changeRunDateReminderInMemory(chatId, updMessage.text, remindersInMemory)
                    val reminder = remindersInMemory[chatId]
                    sender.execute<Message, SendMessage>(
                        SendMessage.builder()
                            .text(
                                String.format(
                                    Constants.ADD_REMINDER_RU,
                                    reminder?.text,
                                    reminder?.runDate?.format(Constants.DATE_TIME_FORMATTER)
                                )
                            )
                            .chatId(chatId)
                            .build()
                    )
                }
                Constants.ADD_OPTION_RUN_DATE_EN -> {
                    changeRunDateReminderInMemory(chatId, updMessage.text, remindersInMemory)
                    val reminder = remindersInMemory[chatId]
                    sender.execute<Message, SendMessage>(
                        SendMessage.builder()
                            .text(
                                String.format(
                                    Constants.ADD_REMINDER_EN,
                                    reminder?.text,
                                    reminder?.runDate?.format(Constants.DATE_TIME_FORMATTER)
                                )
                            )
                            .chatId(chatId)
                            .build()
                    )
                }
            }
        } catch (ex: TelegramApiException) {
            log.error(ex.message)
        }
    }

    @kotlin.Throws(TelegramApiException::class)
    private fun replyToRuLanguage(chatId: Long, messageIdCallback: Int) {
        sender.execute<Serializable, EditMessageText>(
            EditMessageText.builder()
                .text(String.format(Constants.LANGUAGE_REPLY_RU, Constants.LANGUAGE_CHOOSE_RU))
                .chatId(chatId)
                .messageId(messageIdCallback)
                .build()
        )
    }

    @kotlin.Throws(TelegramApiException::class)
    private fun replyToEnLanguage(chatId: Long, messageIdCallback: Int) {
        sender.execute<Serializable, EditMessageText>(
            EditMessageText.builder()
                .text(String.format(Constants.LANGUAGE_REPLY_EN, Constants.LANGUAGE_CHOOSE_EN))
                .chatId(chatId)
                .messageId(messageIdCallback)
                .build()
        )
    }

    @kotlin.Throws(TelegramApiException::class)
    private fun replyToTimeZone(chatId: Long, messageIdCallback: Int, message: String, usersInMemory: Map<Long, TgUser?>) {
        when (usersInMemory[chatId]?.language) {
            Constants.LANGUAGE_CHOOSE_RU -> sender.execute<Serializable, EditMessageText>(
                EditMessageText.builder()
                    .text(String.format(Constants.TIMEZONE_REPLY_RU, message))
                    .chatId(chatId)
                    .messageId(messageIdCallback)
                    .build()
            )
            Constants.LANGUAGE_CHOOSE_EN -> sender.execute<Serializable, EditMessageText>(
                EditMessageText.builder()
                    .text(String.format(Constants.TIMEZONE_REPLY_EN, message))
                    .chatId(chatId)
                    .messageId(messageIdCallback)
                    .build()
            )
        }
    }

    fun replyToRemind(chatId: Long?, callbackQuery: CallbackQuery) {
        try {
            val tgUser: TgUser? = chatId?.let { userService.findUser(it) }
            val remindId: Long = callbackQuery.data.toLong()
            val reminder: Reminder? = reminderService.getReminder(remindId)
            when (tgUser?.language) {
                Constants.LANGUAGE_CHOOSE_RU -> sender.execute<Message, SendMessage>(
                    SendMessage.builder()
                        .text(String.format("%s:%n%s (%s)", Constants.REMINDER_NAME_RU, reminder?.text, reminder?.runDate))
                        .chatId(chatId!!)
                        .replyMarkup(reminder?.let { KeyboardFactory.reminderButtons(it) })
                        .build()
                )
                Constants.LANGUAGE_CHOOSE_EN -> sender.execute<Message, SendMessage>(
                    SendMessage.builder()
                        .text(String.format("%s:%n%s (%s)", Constants.REMINDER_NAME_EN, reminder?.text, reminder?.runDate))
                        .chatId(chatId!!)
                        .replyMarkup(reminder?.let { KeyboardFactory.reminderButtons(it) })
                        .build()
                )
            }
        } catch (ex: TelegramApiException) {
            log.error(ex.message)
        }
    }

    fun replyToDeleteRemind(chatId: Long?, callbackQuery: CallbackQuery) {
        try {
            val operation: String = callbackQuery.data.split("_").toTypedArray().get(0)
            val remindId: Long = callbackQuery.data.split("_").toTypedArray().get(1).toLong()
            val tgUser: TgUser? = chatId?.let { userService.findUser(it) }
            if (operation == "delete") {
                reminderService.deleteReminder(remindId)
                when (tgUser?.language) {
                    Constants.LANGUAGE_CHOOSE_RU -> sender.execute<Message, SendMessage>(
                        SendMessage.builder()
                            .text(Constants.REMINDER_DELETE_RU)
                            .chatId(chatId!!)
                            .build()
                    )
                    Constants.LANGUAGE_CHOOSE_EN -> sender.execute<Message, SendMessage>(
                        SendMessage.builder()
                            .text(Constants.REMINDER_DELETE_EN)
                            .chatId(chatId!!)
                            .build()
                    )

                }
            }
        } catch (ex: TelegramApiException) {
            log.error(ex.message)
        }
    }

    fun replyToEditRemind(chatId: Long?, callbackQuery: CallbackQuery) {
        try {
            val operation: String = callbackQuery.data.split("_").toTypedArray().get(0)
            val remindId: Long = callbackQuery.data.split("_").toTypedArray().get(1).toLong()
            val reminder: Reminder? = reminderService.getReminder(remindId)
            when (operation) {
                Constants.EDIT_COMMAND -> sender.execute<Message, SendMessage>(
                    SendMessage.builder()
                        .text(callbackQuery.message.text)
                        .chatId(chatId!!)
                        .replyMarkup(KeyboardFactory.reminderEditButtons(reminder))
                        .build()
                )
            }
        } catch (ex: TelegramApiException) {
            log.error(ex.message)
        }
    }

    private fun createUserInMemory(chatId: Long, usersInMemory: MutableMap<Long, TgUser?>) {
        val tgUser = TgUser()
        tgUser.id = chatId
        usersInMemory[chatId] = tgUser
    }

    private fun changeLanguageInMemory(chatId: Long, language: String, usersInMemory: MutableMap<Long, TgUser?>) {
        val user = usersInMemory[chatId]
        user?.language = language
        usersInMemory[chatId] = user
    }

    @kotlin.Throws(TelegramApiException::class)
    private fun changeTimezoneInMemory(chatId: Long, messageIdCallback: Int, timezone: String, usersInMemory: MutableMap<Long, TgUser?>) {
        val user = usersInMemory[chatId]
        user?.timeZone = timezone
        usersInMemory[chatId] = user
        replyToTimeZone(chatId, messageIdCallback, timezone, usersInMemory)
    }

    private fun createReminderInMemory(chatId: Long, text: String, remindersInMemory: MutableMap<Long, Reminder?>) {
        val reminder = Reminder()
        reminder.chatId = chatId
        reminder.text = text
        remindersInMemory[chatId] = reminder
    }

    @kotlin.Throws(TelegramApiException::class)
    private fun changeRunDateReminderInMemory(chatId: Long, strRunDate: String, remindersInMemory: MutableMap<Long, Reminder?>) {
        val tgUser: TgUser? = userService.findUser(chatId)
        try {
            val runDate = parseRunDate(chatId, strRunDate)
            val reminder = remindersInMemory[chatId]
            reminder?.runDate = runDate
            remindersInMemory[chatId] = reminder
            saveReminderToDb(chatId, remindersInMemory)
        } catch (ex: DateTimeParseException) {
            log.error("Incorrect format {}", strRunDate)
            sender.execute<Message, SendMessage>(
                SendMessage.builder()
                    .text(String.format("Incorrect format %s", strRunDate))
                    .chatId(chatId)
                    .build()
            )
            when (tgUser?.language) {
                Constants.LANGUAGE_CHOOSE_RU -> sender.execute<Message, SendMessage>(
                    SendMessage.builder()
                        .text(Constants.ADD_OPTION_RUN_DATE_RU)
                        .chatId(chatId)
                        .replyMarkup(KeyboardFactory.forceReply())
                        .build()
                )
                Constants.LANGUAGE_CHOOSE_EN -> sender.execute<Message, SendMessage>(
                    SendMessage.builder()
                        .text(Constants.ADD_OPTION_RUN_DATE_EN)
                        .chatId(chatId)
                        .replyMarkup(KeyboardFactory.forceReply())
                        .build()
                )
            }
        }
    }

    private fun parseRunDate(chatId: Long, strRunDate: String): OffsetDateTime {
        return try {
            val tgUser: TgUser? = userService.findUser(chatId)
            val zone = ZoneOffset.of(tgUser?.timeZone)
            LocalDateTime.parse(strRunDate, Constants.DATE_TIME_FORMATTER).atOffset(zone)
        } catch (ex: DateTimeParseException) {
            throw ex
        }
    }

    private fun saveUserToDb(chatId: Long, usersInMemory: Map<Long, TgUser?>) {
        try {
            val tgUser = usersInMemory[chatId]
            tgUser?.let { userService.saveUser(it) }
            log.info("Save user: {}", tgUser)
        } catch (ex: Exception) {
            log.error("Error saved user: {}", chatId)
        }
    }

    private fun saveReminderToDb(chatId: Long, remindersInMemory: Map<Long, Reminder?>) {
        try {
            val reminder = remindersInMemory[chatId]
            reminder?.let { reminderService.saveReminder(it) }
            log.info("Save reminder: {}", reminder)
        } catch (ex: Exception) {
            log.error("Error saved reminder of user: {}", chatId)
        }
    }

    fun help(chatId: Long?, user: User) {
        try {
            val tgUser: TgUser? = chatId?.let { userService.findUser(it) }
            when (tgUser?.language) {
                Constants.LANGUAGE_CHOOSE_RU -> sender.execute<Message, SendMessage>(
                    SendMessage.builder()
                        .text(String.format(Constants.HELP_REPLY_RU, user.firstName))
                        .chatId(chatId!!)
                        .build()
                )
                Constants.LANGUAGE_CHOOSE_EN -> sender.execute<Message, SendMessage>(
                    SendMessage.builder()
                        .text(String.format(Constants.HELP_REPLY_EN, user.firstName))
                        .chatId(chatId!!)
                        .build()
                )
            }
        } catch (e: TelegramApiException) {
            log.error(e.message)
        }
    }

    fun list(chatId: Long?) {
        try {
            val reminders: List<Reminder> = reminderService.findAllByChatId(chatId)
            val tgUser: TgUser? = chatId?.let { userService.findUser(it) }
            val zone = ZoneOffset.of(tgUser?.timeZone)
            val count = AtomicInteger(0)
            val remindsString = reminders.stream()
                .map(Function<Reminder, String> { el: Reminder ->
                    String.format(
                        "%s) %s (%s)%n", count.incrementAndGet(), el.text,
                        el.runDate.withOffsetSameInstant(zone).format(Constants.DATE_TIME_FORMATTER)
                    )
                })
                .reduce("") { obj: String, str: String -> obj + str }
            when (tgUser?.language) {
                Constants.LANGUAGE_CHOOSE_RU -> sender.execute<Message, SendMessage>(
                    SendMessage.builder()
                        .text(
                            String.format(
                                "%s:%n%s %n%s:",
                                Constants.LIST_REPLY_RU,
                                remindsString,
                                Constants.LIST_EDIT_REPLY_RU
                            )
                        )
                        .chatId(chatId!!)
                        .replyMarkup(KeyboardFactory.listButtons(reminders))
                        .build()
                )
                Constants.LANGUAGE_CHOOSE_EN -> sender.execute<Message, SendMessage>(
                    SendMessage.builder()
                        .text(
                            String.format(
                                "%s:%n%s %n%s:",
                                Constants.LIST_REPLY_EN,
                                remindsString,
                                Constants.LIST_EDIT_REPLY_EN
                            )
                        )
                        .chatId(chatId!!)
                        .replyMarkup(KeyboardFactory.listButtons(reminders))
                        .build()
                )
            }
        } catch (e: TelegramApiException) {
            log.error(e.message)
        }
    }
}