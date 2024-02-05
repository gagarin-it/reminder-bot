package it.gagarin.reminderbot.config

import com.google.common.collect.Lists
import it.gagarin.reminderbot.model.Reminder
import it.gagarin.reminderbot.service.IUserService
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import java.util.concurrent.atomic.AtomicInteger

@Component
class KeyboardFactory {
    companion object {
        lateinit var userService: IUserService

        fun forceReply(): ForceReplyKeyboard {
            val forceReplyKeyboard = ForceReplyKeyboard()
            forceReplyKeyboard.forceReply = true
            forceReplyKeyboard.selective = true
            return forceReplyKeyboard
        }

        fun languageButtons(): InlineKeyboardMarkup {
            val inlineKeyboard = InlineKeyboardMarkup()
            val rowsInline: MutableList<List<InlineKeyboardButton>> = ArrayList()
            val rowInline: MutableList<InlineKeyboardButton> = ArrayList()
            rowInline.add(
                InlineKeyboardButton.builder().text(Constants.LANGUAGE_CHOOSE_RU).callbackData(Constants.LANGUAGE_CHOOSE_RU)
                    .build()
            )
            rowInline.add(
                InlineKeyboardButton.builder().text(Constants.LANGUAGE_CHOOSE_EN).callbackData(Constants.LANGUAGE_CHOOSE_EN)
                    .build()
            )
            rowsInline.add(rowInline)
            inlineKeyboard.keyboard = rowsInline
            return inlineKeyboard
        }

        fun timezoneButtons(): InlineKeyboardMarkup {
            val inlineKeyboard = InlineKeyboardMarkup()
            val rowsInline: MutableList<List<InlineKeyboardButton>> = ArrayList()
            val rowInline1: MutableList<InlineKeyboardButton> = ArrayList()
            rowInline1.add(
                InlineKeyboardButton.builder().text(Constants.TIMEZONE_HOWLAND_ISLAND)
                    .callbackData(Constants.TIMEZONE_HOWLAND_ISLAND).build()
            )
            rowInline1.add(
                InlineKeyboardButton.builder().text(Constants.TIMEZONE_AMERICAN_SAMOA)
                    .callbackData(Constants.TIMEZONE_AMERICAN_SAMOA).build()
            )
            rowInline1.add(
                InlineKeyboardButton.builder().text(Constants.TIMEZONE_ALASKA).callbackData(Constants.TIMEZONE_ALASKA).build()
            )
            rowInline1.add(
                InlineKeyboardButton.builder().text(Constants.TIMEZONE_MARQUESAS_ISLANDS)
                    .callbackData(Constants.TIMEZONE_MARQUESAS_ISLANDS).build()
            )
            rowInline1.add(
                InlineKeyboardButton.builder().text(Constants.TIMEZONE_GAMBIER_ISLANDS)
                    .callbackData(Constants.TIMEZONE_GAMBIER_ISLANDS).build()
            )
            rowInline1.add(
                InlineKeyboardButton.builder().text(Constants.TIMEZONE_CALIFORNIA).callbackData(Constants.TIMEZONE_CALIFORNIA)
                    .build()
            )
            val rowInline2: MutableList<InlineKeyboardButton> = ArrayList()
            rowInline2.add(
                InlineKeyboardButton.builder().text(Constants.TIMEZONE_ALBERTA).callbackData(Constants.TIMEZONE_ALBERTA).build()
            )
            rowInline2.add(
                InlineKeyboardButton.builder().text(Constants.TIMEZONE_MANITOBA).callbackData(Constants.TIMEZONE_MANITOBA).build()
            )
            rowInline2.add(
                InlineKeyboardButton.builder().text(Constants.TIMEZONE_CUBA).callbackData(Constants.TIMEZONE_CUBA).build()
            )
            rowInline2.add(
                InlineKeyboardButton.builder().text(Constants.TIMEZONE_BARBADOS).callbackData(Constants.TIMEZONE_BARBADOS).build()
            )
            rowInline2.add(
                InlineKeyboardButton.builder().text(Constants.TIMEZONE_NEWFOUNDLAND).callbackData(Constants.TIMEZONE_NEWFOUNDLAND)
                    .build()
            )
            rowInline2.add(
                InlineKeyboardButton.builder().text(Constants.TIMEZONE_ARGENTINA).callbackData(Constants.TIMEZONE_ARGENTINA)
                    .build()
            )
            val rowInline3: MutableList<InlineKeyboardButton> = ArrayList()
            rowInline3.add(
                InlineKeyboardButton.builder().text(Constants.TIMEZONE_FERNANDO_DE_NORONHA)
                    .callbackData(Constants.TIMEZONE_FERNANDO_DE_NORONHA).build()
            )
            rowInline3.add(
                InlineKeyboardButton.builder().text(Constants.TIMEZONE_CAPE_VERDE).callbackData(Constants.TIMEZONE_CAPE_VERDE)
                    .build()
            )
            rowInline3.add(
                InlineKeyboardButton.builder().text(Constants.TIMEZONE_UTC_TEXT).callbackData(Constants.TIMEZONE_UTC_TEXT).build()
            )
            rowInline3.add(
                InlineKeyboardButton.builder().text(Constants.TIMEZONE_MONACO).callbackData(Constants.TIMEZONE_MONACO).build()
            )
            rowInline3.add(
                InlineKeyboardButton.builder().text(Constants.TIMEZONE_EGYPT).callbackData(Constants.TIMEZONE_EGYPT).build()
            )
            rowInline3.add(
                InlineKeyboardButton.builder().text(Constants.TIMEZONE_MOSCOW).callbackData(Constants.TIMEZONE_MOSCOW).build()
            )
            val rowInline4: MutableList<InlineKeyboardButton> = ArrayList()
            rowInline4.add(
                InlineKeyboardButton.builder().text(Constants.TIMEZONE_IRAN).callbackData(Constants.TIMEZONE_IRAN).build()
            )
            rowInline4.add(
                InlineKeyboardButton.builder().text(Constants.TIMEZONE_DUBAI).callbackData(Constants.TIMEZONE_DUBAI).build()
            )
            rowInline4.add(
                InlineKeyboardButton.builder().text(Constants.TIMEZONE_KABUL).callbackData(Constants.TIMEZONE_KABUL).build()
            )
            rowInline4.add(
                InlineKeyboardButton.builder().text(Constants.TIMEZONE_MALDIVES).callbackData(Constants.TIMEZONE_MALDIVES).build()
            )
            rowInline4.add(
                InlineKeyboardButton.builder().text(Constants.TIMEZONE_SRI_LANKA).callbackData(Constants.TIMEZONE_SRI_LANKA)
                    .build()
            )
            rowInline4.add(
                InlineKeyboardButton.builder().text(Constants.TIMEZONE_NEPAL).callbackData(Constants.TIMEZONE_NEPAL).build()
            )
            val rowInline5: MutableList<InlineKeyboardButton> = ArrayList()
            rowInline5.add(
                InlineKeyboardButton.builder().text(Constants.TIMEZONE_OMSK).callbackData(Constants.TIMEZONE_OMSK).build()
            )
            rowInline5.add(
                InlineKeyboardButton.builder().text(Constants.TIMEZONE_MYANMAR).callbackData(Constants.TIMEZONE_MYANMAR).build()
            )
            rowInline5.add(
                InlineKeyboardButton.builder().text(Constants.TIMEZONE_BANGKOK).callbackData(Constants.TIMEZONE_BANGKOK).build()
            )
            rowInline5.add(
                InlineKeyboardButton.builder().text(Constants.TIMEZONE_CHINA).callbackData(Constants.TIMEZONE_CHINA).build()
            )
            rowInline5.add(
                InlineKeyboardButton.builder().text(Constants.TIMEZONE_EUCLA).callbackData(Constants.TIMEZONE_EUCLA).build()
            )
            rowInline5.add(
                InlineKeyboardButton.builder().text(Constants.TIMEZONE_SOUTH_KOREA).callbackData(Constants.TIMEZONE_SOUTH_KOREA)
                    .build()
            )
            val rowInline6: MutableList<InlineKeyboardButton> = ArrayList()
            rowInline6.add(
                InlineKeyboardButton.builder().text(Constants.TIMEZONE_ADELAIDE).callbackData(Constants.TIMEZONE_ADELAIDE).build()
            )
            rowInline6.add(
                InlineKeyboardButton.builder().text(Constants.TIMEZONE_SYDNEY).callbackData(Constants.TIMEZONE_SYDNEY).build()
            )
            rowInline6.add(
                InlineKeyboardButton.builder().text(Constants.TIMEZONE_LORD_HOWE_ISLAND)
                    .callbackData(Constants.TIMEZONE_LORD_HOWE_ISLAND).build()
            )
            rowInline6.add(
                InlineKeyboardButton.builder().text(Constants.TIMEZONE_MAGADAN).callbackData(Constants.TIMEZONE_MAGADAN).build()
            )
            rowInline6.add(
                InlineKeyboardButton.builder().text(Constants.TIMEZONE_KAMCHATKA).callbackData(Constants.TIMEZONE_KAMCHATKA)
                    .build()
            )
            rowInline6.add(
                InlineKeyboardButton.builder().text(Constants.TIMEZONE_CHATHAM_ISLANDS)
                    .callbackData(Constants.TIMEZONE_CHATHAM_ISLANDS).build()
            )
            val rowInline7: MutableList<InlineKeyboardButton> = ArrayList()
            rowInline7.add(
                InlineKeyboardButton.builder().text(Constants.TIMEZONE_SAMOA).callbackData(Constants.TIMEZONE_SAMOA).build()
            )
            rowInline7.add(
                InlineKeyboardButton.builder().text(Constants.TIMEZONE_LINE_ISLANDS).callbackData(Constants.TIMEZONE_LINE_ISLANDS)
                    .build()
            )
            rowsInline.add(rowInline1)
            rowsInline.add(rowInline2)
            rowsInline.add(rowInline3)
            rowsInline.add(rowInline4)
            rowsInline.add(rowInline5)
            rowsInline.add(rowInline6)
            rowsInline.add(rowInline7)
            inlineKeyboard.keyboard = rowsInline
            return inlineKeyboard
        }

        fun listButtons(reminders: List<Reminder>): InlineKeyboardMarkup {
            val inlineKeyboard = InlineKeyboardMarkup()
            val rowInline: MutableList<InlineKeyboardButton> = ArrayList()
            val count = AtomicInteger(1)
            reminders.forEach { reminder: Reminder ->
                rowInline.add(
                    InlineKeyboardButton.builder()
                        .text(count.toString())
                        .callbackData(reminder.id.toString())
                        .build()
                )
                count.incrementAndGet()
            }
            val rowsInline: List<List<InlineKeyboardButton>> = ArrayList(Lists.partition(rowInline, 8))
            inlineKeyboard.keyboard = rowsInline
            return inlineKeyboard
        }

        fun reminderButtons(reminder: Reminder): InlineKeyboardMarkup {
            val inlineKeyboard = InlineKeyboardMarkup()
            val rowsInline: MutableList<List<InlineKeyboardButton>> = ArrayList()
            val rowInline: MutableList<InlineKeyboardButton> = ArrayList()
            val tgUser = userService.findUser(reminder.chatId)
            when (tgUser?.language) {
                Constants.LANGUAGE_CHOOSE_RU -> {
                    rowInline.add(
                        InlineKeyboardButton.builder()
                            .text("Изменить")
                            .callbackData("edit_" + reminder.id)
                            .build()
                    )
                    rowInline.add(
                        InlineKeyboardButton.builder()
                            .text("Удалить")
                            .callbackData("delete_" + reminder.id)
                            .build()
                    )
                }
                Constants.LANGUAGE_CHOOSE_EN -> {
                    rowInline.add(
                        InlineKeyboardButton.builder()
                            .text("Edit")
                            .callbackData("edit_" + reminder.id)
                            .build()
                    )
                    rowInline.add(
                        InlineKeyboardButton.builder()
                            .text("Delete")
                            .callbackData("delete_" + reminder.id)
                            .build()
                    )
                }
            }
            rowsInline.add(rowInline)
            inlineKeyboard.keyboard = rowsInline
            return inlineKeyboard
        }

        fun reminderEditButtons(reminder: Reminder?): InlineKeyboardMarkup {
            val inlineKeyboard = InlineKeyboardMarkup()
            val rowsInline: MutableList<List<InlineKeyboardButton>> = ArrayList()
            val rowInline: MutableList<InlineKeyboardButton> = ArrayList()
            val tgUser = reminder?.chatId?.let { userService.findUser(it) }
            when (tgUser?.language) {
                Constants.LANGUAGE_CHOOSE_RU -> {
                    rowInline.add(
                        InlineKeyboardButton.builder()
                            .text("Изменить текст")
                            .callbackData("edit-text_" + reminder.id.toString())
                            .build()
                    )
                    rowInline.add(
                        InlineKeyboardButton.builder()
                            .text("Изменить дату и время")
                            .callbackData("edit-run-date_" + reminder.id.toString())
                            .build()
                    )
                }
                Constants.LANGUAGE_CHOOSE_EN -> {
                    rowInline.add(
                        InlineKeyboardButton.builder()
                            .text("Edit text")
                            .callbackData("edit-text_" + reminder.id.toString())
                            .build()
                    )
                    rowInline.add(
                        InlineKeyboardButton.builder()
                            .text("Edit date and time")
                            .callbackData("edit-run-date_" + reminder.id.toString())
                            .build()
                    )
                }
            }
            rowsInline.add(rowInline)
            inlineKeyboard.keyboard = rowsInline
            return inlineKeyboard
        }

    }

}