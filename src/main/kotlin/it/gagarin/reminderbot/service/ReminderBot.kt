package it.gagarin.reminderbot.service

import it.gagarin.reminderbot.config.BotConfig
import it.gagarin.reminderbot.config.Constants
import it.gagarin.reminderbot.config.KeyboardFactory
import it.gagarin.reminderbot.config.MessageFactory
import it.gagarin.reminderbot.model.Reminder
import it.gagarin.reminderbot.model.TgUser
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.telegram.abilitybots.api.bot.AbilityBot
import org.telegram.abilitybots.api.bot.BaseAbilityBot
import org.telegram.abilitybots.api.db.MapDBContext
import org.telegram.abilitybots.api.objects.*
import org.telegram.abilitybots.api.util.AbilityUtils
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeParseException
import java.util.concurrent.atomic.AtomicReference
import java.util.function.BiConsumer
import java.util.function.Predicate

@Component
class ReminderBot @Autowired constructor(
    private val botConfig: BotConfig,
    userService: IUserService,
    private val reminderService: IReminderService,
) :
    AbilityBot(
        botConfig.token, botConfig.name, MapDBContext.offlineInstance(String.format("%s\\%s", botConfig.dbFolder, botConfig.name))
    ) {
    lateinit var messageFactory: MessageFactory
    lateinit var usersInMemory: MutableMap<Long, TgUser?>
    lateinit var remindersInMemory: MutableMap<Long, Reminder?>
    lateinit var userService: IUserService

    companion object {
        private val log: Logger = LoggerFactory.getLogger(ReminderBot::class.java)

    }

    init {
        this.userService = userService
        messageFactory = MessageFactory(sender, userService, reminderService)
        usersInMemory = db.getMap("users")
        remindersInMemory = db.getMap("reminders")
    }


    override fun creatorId(): Long {
        return botConfig.creator
    }

    @Bean
    fun startCommand(): Ability {
        return Ability
            .builder()
            .name(Constants.START_COMMAND)
            .info(Constants.START_DESCRIPTION)
            .locality(Locality.USER)
            .input(0)
            .privacy(Privacy.PUBLIC)
            .action { ctx: MessageContext -> messageFactory.start(ctx.chatId(), ctx.user(), usersInMemory) }
            .setStatsEnabled(true)
            .build()
    }

    @Bean
    fun settingsCommand(): Ability {
        return Ability
            .builder()
            .name(Constants.SETTINGS_COMMAND)
            .info(Constants.SETTINGS_DESCRIPTION)
            .locality(Locality.USER)
            .input(0)
            .privacy(Privacy.PUBLIC)
            .action { ctx: MessageContext -> messageFactory.settings(ctx.chatId()) }
            .setStatsEnabled(true)
            .build()
    }

    @Bean
    fun addReminderCommand(): Ability {
        return Ability
            .builder()
            .name(Constants.ADD_COMMAND)
            .info(Constants.ADD_DESCRIPTION)
            .locality(Locality.USER)
            .input(0)
            .privacy(Privacy.PUBLIC)
            .action { ctx: MessageContext -> messageFactory.addReminder(ctx.chatId()) }
            .setStatsEnabled(true)
            .build()
    }

    @Bean
    fun helpCommand(): Ability {
        return Ability
            .builder()
            .name(Constants.HELP_COMMAND)
            .info(Constants.HELP_DESCRIPTION)
            .locality(Locality.USER)
            .input(0)
            .privacy(Privacy.PUBLIC)
            .action { ctx: MessageContext -> messageFactory.help(ctx.chatId(), ctx.user()) }
            .setStatsEnabled(true)
            .build()
    }

    @Bean
    fun listRemindersCommand(): Ability {
        return Ability
            .builder()
            .name(Constants.LIST_COMMAND)
            .info(Constants.LIST_REPLY_EN)
            .locality(Locality.USER)
            .input(0)
            .privacy(Privacy.PUBLIC)
            .action { ctx: MessageContext -> messageFactory.list(ctx.chatId()) }
            .setStatsEnabled(true)
            .build()
    }

    fun replyToRemind(): Reply {
        val action = BiConsumer { _: BaseAbilityBot?, upd: Update -> messageFactory.replyToRemind(AbilityUtils.getChatId(upd), upd.callbackQuery) }
        return Reply.of(action, Flag.CALLBACK_QUERY)
    }

    fun replyToDeleteRemind(): Reply {
        val action =
            BiConsumer { _: BaseAbilityBot?, upd: Update -> messageFactory.replyToDeleteRemind(AbilityUtils.getChatId(upd), upd.callbackQuery) }
        return Reply.of(action, Flag.CALLBACK_QUERY)
    }

    fun replyToLanguage(): Reply {
        val action = BiConsumer { _: BaseAbilityBot?, upd: Update ->
            messageFactory.replyToSettings(
                AbilityUtils.getChatId(upd),
                upd.callbackQuery.message.messageId, upd.callbackQuery.data,
                usersInMemory
            )
        }
        return Reply.of(action, Flag.CALLBACK_QUERY)
    }

    fun directionFlow(): ReplyFlow {
        val reminder = AtomicReference(Reminder())
        val saidText = Reply.of(
            { _: BaseAbilityBot?, upd: Update ->
                try {
                    val tgUser = userService.findUser(AbilityUtils.getChatId(upd))
                    val newMessage = upd.message.text
                    reminder.get().apply { text = newMessage }
                    reminderService.saveReminder(reminder.get()!!)
                    when (tgUser?.language) {
                        Constants.LANGUAGE_CHOOSE_RU -> sender.execute(
                            SendMessage.builder()
                                .text(String.format(Constants.EDIT_TEXT_REPLY_RU, newMessage))
                                .chatId(AbilityUtils.getChatId(upd))
                                .build()
                        )
                        Constants.LANGUAGE_CHOOSE_EN -> sender.execute(
                            SendMessage.builder()
                                .text(String.format(Constants.EDIT_TEXT_REPLY_EN, newMessage))
                                .chatId(AbilityUtils.getChatId(upd))
                                .build()
                        )
                    }
                    log.info("Update reminder: {}", reminder)
                } catch (e: TelegramApiException) {
                    e.printStackTrace()
                }
            },
            Flag.MESSAGE
        )
        val textFlow = ReplyFlow.builder(db)
            .action { _: BaseAbilityBot?, upd: Update? ->
                try {
                    val tgUser = userService.findUser(AbilityUtils.getChatId(upd))
                    when (tgUser?.language) {
                        Constants.LANGUAGE_CHOOSE_RU -> sender.execute(
                            SendMessage.builder()
                                .text(Constants.EDIT_TEXT_RU)
                                .chatId(AbilityUtils.getChatId(upd))
                                .build()
                        )
                        Constants.LANGUAGE_CHOOSE_EN -> sender.execute(
                            SendMessage.builder()
                                .text(Constants.EDIT_TEXT_EN)
                                .chatId(AbilityUtils.getChatId(upd))
                                .build()
                        )
                    }
                } catch (e: TelegramApiException) {
                    e.printStackTrace()
                }
            }
            .onlyIf(hasCallbackMessageWith(Constants.EDIT_TEXT))
            .next(saidText).build()
        val saidRunDate = Reply.of(
            { _: BaseAbilityBot?, upd: Update ->
                val strRunDate = upd.message.text
                try {
                    val newRunDate = parseRunDate(AbilityUtils.getChatId(upd), upd.message.text)
                    val tgUser = userService.findUser(AbilityUtils.getChatId(upd))
                    reminder.get().apply { runDate = newRunDate }
                    reminderService.saveReminder(reminder.get()!!)
                    when (tgUser?.language) {
                        Constants.LANGUAGE_CHOOSE_RU -> {
                            sender.execute(
                                SendMessage.builder()
                                    .text(
                                        String.format(
                                            Constants.EDIT_RUN_DATE_REPLY_RU,
                                            newRunDate.format(Constants.DATE_TIME_FORMATTER)
                                        )
                                    )
                                    .chatId(AbilityUtils.getChatId(upd))
                                    .build()
                            )
                            log.info("Update reminder: {}", reminder)
                        }
                        Constants.LANGUAGE_CHOOSE_EN -> {
                            sender.execute(
                                SendMessage.builder()
                                    .text(
                                        String.format(
                                            Constants.EDIT_RUN_DATE_REPLY_EN,
                                            newRunDate.format(Constants.DATE_TIME_FORMATTER)
                                        )
                                    )
                                    .chatId(AbilityUtils.getChatId(upd))
                                    .build()
                            )
                            log.info("Update reminder: {}", reminder)
                        }
                    }
                } catch (e: TelegramApiException) {
                    log.error("Incorrect format {}", strRunDate)
                    silent.execute(
                        SendMessage.builder()
                            .text(String.format("Некорректный формат %s, повторите ввод", strRunDate))
                            .chatId(AbilityUtils.getChatId(upd))
                            .build()
                    )
                }
            },
            Flag.MESSAGE
        )
        val runDateFlow = ReplyFlow.builder(db)
            .action { _: BaseAbilityBot?, upd: Update? ->
                try {
                    val tgUser = userService.findUser(AbilityUtils.getChatId(upd))
                    when (tgUser?.language) {
                        Constants.LANGUAGE_CHOOSE_RU -> sender.execute(
                            SendMessage.builder()
                                .text(Constants.EDIT_RUN_DATE_RU)
                                .chatId(AbilityUtils.getChatId(upd))
                                .build()
                        )
                        Constants.LANGUAGE_CHOOSE_EN -> sender.execute(
                            SendMessage.builder()
                                .text(Constants.EDIT_RUN_DATE_EN)
                                .chatId(AbilityUtils.getChatId(upd))
                                .build()
                        )
                    }
                } catch (e: TelegramApiException) {
                    e.printStackTrace()
                }
            }
            .onlyIf(hasCallbackMessageWith(Constants.EDIT_RUN_DATE))
            .next(saidRunDate).build()
        return ReplyFlow.builder(db)
            .action { bot: BaseAbilityBot?, upd: Update ->
                val remindId: Long = upd.callbackQuery.data.split("_").toTypedArray().get(1).toLong()
                reminder.set(reminderService.getReminder(remindId))
                try {
                    sender.execute(
                        SendMessage.builder()
                            .text(upd.callbackQuery.message.text)
                            .chatId(AbilityUtils.getChatId(upd))
                            .replyMarkup(KeyboardFactory.reminderEditButtons(reminder.get()))
                            .build()
                    )
                } catch (e: TelegramApiException) {
                    e.printStackTrace()
                }
            }
            .onlyIf(hasCallbackMessageWith("edit"))
            .next(textFlow)
            .next(runDateFlow)
            .build()
    }

    private fun hasCallbackMessageWith(msg: String): Predicate<Update> {
        return Predicate { upd: Update -> upd.callbackQuery.data.split("_").toTypedArray().get(0).equals(msg, ignoreCase = true) }
    }

    fun replyToReminder(): Reply {
        val action = BiConsumer { _: BaseAbilityBot?, upd: Update ->
            messageFactory.replyToReminders(
                AbilityUtils.getChatId(upd),
                upd.message,
                remindersInMemory
            )
        }
        return Reply.of(action, Flag.REPLY)
    }

    private fun parseRunDate(chatId: Long, strRunDate: String): OffsetDateTime {
        return try {
            val tgUser = userService.findUser(chatId)
            val zone = ZoneOffset.of(tgUser?.timeZone)
            LocalDateTime.parse(strRunDate, Constants.DATE_TIME_FORMATTER).atOffset(zone)
        } catch (ex: DateTimeParseException) {
            throw ex
        }
    }
}