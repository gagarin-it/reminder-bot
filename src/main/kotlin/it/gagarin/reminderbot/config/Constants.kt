package it.gagarin.reminderbot.config

import java.time.format.DateTimeFormatter

object Constants {
    val DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy")
    const val START_COMMAND = "start"
    const val START_DESCRIPTION = "Start to use"
    const val START_REPLY = "Добро пожаловать | Welcome, %n%s"
    const val LANGUAGE_OPTION = "Выберете, пожалуйста, Ваш язык | Please, choose your language:"
    const val LANGUAGE_REPLY_RU = "Вы выбрали %s язык"
    const val LANGUAGE_REPLY_EN = "You have chosen %s language"
    const val LANGUAGE_CHOOSE_RU = "Русский"
    const val LANGUAGE_CHOOSE_EN = "English"
    const val TIMEZONE_OPTION_RU = "Выберете, пожалуйста, Ваш часовой пояс:"
    const val TIMEZONE_OPTION_EN = "Please, choose your time zone:"
    const val TIMEZONE_REPLY_RU = "Вы выбрали часовой пояс: %s %n%nДалее воспользуйтесь меню."
    const val TIMEZONE_REPLY_EN = "You have chosen time zone: %s %nUse a menu."
    const val TIMEZONE_HOWLAND_ISLAND = "-12:00"
    const val TIMEZONE_AMERICAN_SAMOA = "-11:00"
    const val TIMEZONE_ALASKA = "-10:00"
    const val TIMEZONE_MARQUESAS_ISLANDS = "-09:30"
    const val TIMEZONE_GAMBIER_ISLANDS = "-09:00"
    const val TIMEZONE_CALIFORNIA = "-08:00"
    const val TIMEZONE_ALBERTA = "-07:00"
    const val TIMEZONE_MANITOBA = "-06:00"
    const val TIMEZONE_CUBA = "-05:00"
    const val TIMEZONE_BARBADOS = "-04:00"
    const val TIMEZONE_NEWFOUNDLAND = "-03:30"
    const val TIMEZONE_ARGENTINA = "-03:00"
    const val TIMEZONE_FERNANDO_DE_NORONHA = "-02:00"
    const val TIMEZONE_CAPE_VERDE = "-01:00"
    const val TIMEZONE_UTC_TEXT = "UTC"
    const val TIMEZONE_UTC_VALUE = "Z"
    const val TIMEZONE_MONACO = "+01:00"
    const val TIMEZONE_EGYPT = "+02:00"
    const val TIMEZONE_MOSCOW = "+03:00"
    const val TIMEZONE_IRAN = "+03:30"
    const val TIMEZONE_DUBAI = "+04:00"
    const val TIMEZONE_KABUL = "+04:30"
    const val TIMEZONE_MALDIVES = "+05:00"
    const val TIMEZONE_SRI_LANKA = "+05:30"
    const val TIMEZONE_NEPAL = "+05:45"
    const val TIMEZONE_OMSK = "+06:00"
    const val TIMEZONE_MYANMAR = "+06:30"
    const val TIMEZONE_BANGKOK = "+07:00"
    const val TIMEZONE_CHINA = "+08:00"
    const val TIMEZONE_EUCLA = "+08:45"
    const val TIMEZONE_SOUTH_KOREA = "+09:00"
    const val TIMEZONE_ADELAIDE = "+09:30"
    const val TIMEZONE_SYDNEY = "+10:00"
    const val TIMEZONE_LORD_HOWE_ISLAND = "+10:30"
    const val TIMEZONE_MAGADAN = "+11:00"
    const val TIMEZONE_KAMCHATKA = "+12:00"
    const val TIMEZONE_CHATHAM_ISLANDS = "+12:45"
    const val TIMEZONE_SAMOA = "+13:00"
    const val TIMEZONE_LINE_ISLANDS = "+14:00"
    const val ADD_COMMAND = "add"
    const val ADD_DESCRIPTION = "Add a reminder"
    const val ADD_REPLY_RU = "Введите текст напоминания:"
    const val ADD_REPLY_EN = "Enter the reminder text:"
    const val ADD_OPTION_RUN_DATE_RU = "Введите дату и время уведомления в формате: HH:mm dd.MM.yyyy"
    const val ADD_OPTION_RUN_DATE_EN = "Enter the date and time of the notification in the format: HH:mm dd.MM.yyyy"
    const val ADD_REMINDER_RU = "Добавлено напоминание: %s %nДата и время уведомления: %s"
    const val ADD_REMINDER_EN = "Reminder added: %s %nDate and time of notification: %s"
    const val REMINDER_RU = "Вы просили напомнить '%s' в %s."
    const val REMINDER_EN = "You've asked to remind you about '%s' on %s."
    const val REMINDER_NAME_RU = "Напоминание"
    const val REMINDER_NAME_EN = "Reminder"
    const val REMINDER_DELETE_RU = "Напоминание удалено"
    const val REMINDER_DELETE_EN = "Reminder deleted"
    const val EDIT_COMMAND = "edit"
    const val EDIT_TEXT = "edit-text"
    const val EDIT_TEXT_RU = "Введите новый текст напоминания"
    const val EDIT_TEXT_EN = "Enter a new reminder text"
    const val EDIT_TEXT_REPLY_RU = "Изменён текст напоминания: %s%n"
    const val EDIT_TEXT_REPLY_EN = "The reminder text has been changed: %s%n"
    const val EDIT_RUN_DATE = "edit-run-date"
    const val EDIT_RUN_DATE_REPLY_RU = "Изменены дата и время уведомления: %s%n"
    const val EDIT_RUN_DATE_REPLY_EN = "Date and time of notification changed: %s%n"
    const val EDIT_RUN_DATE_RU = "Введите новое время напоминания (HH:mm dd.MM.yyyy):"
    const val EDIT_RUN_DATE_EN = "Enter a new reminder time (HH:mm dd.MM.yyyy):"
    const val SETTINGS_COMMAND = "settings"
    const val SETTINGS_DESCRIPTION = "Change user settings"
    const val SETTINGS_REPLY = "Запущено конфигурирование пользователя | User configuration started"
    const val HELP_COMMAND = "help"
    const val HELP_DESCRIPTION = "Open help"
    const val HELP_REPLY_RU = "Я могу помочь вам создавать и управлять напоминаниями. " +
            "Напоминания будут приходить вам через Telegram в указанное время%n%n" +
            "Вот полный список моих команд:%n" +
            "/start - начать использовать бота/переход в главное меню%n" +
            "/help - открыть справку%n" +
            "/settings - изменить настройки пользователя%n" +
            "/add - добавить напоминание%n" +
            "/list - список напоминаний%n"
    const val HELP_REPLY_EN = "I can help you create and manage reminders." +
            "Reminders will be sent to you via Telegram at the specified time%n%n" +
            "Here is the full list of my commands:%n" +
            "/start - start using the bot/go to the main menu%n" +
            "/help - open help%n" +
            "/settings - change user settings%n" +
            "/add - add a reminder%n" +
            "/list - list of reminders%n"
    const val LIST_COMMAND = "list"
    const val LIST_REPLY_RU = "Список напоминаний"
    const val LIST_REPLY_EN = "Reminder list"
    const val LIST_EDIT_REPLY_RU = "Выберите номер для редактирования"
    const val LIST_EDIT_REPLY_EN = "Select the number to edit"
}