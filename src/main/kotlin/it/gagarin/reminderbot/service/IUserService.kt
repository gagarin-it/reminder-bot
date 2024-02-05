package it.gagarin.reminderbot.service

import it.gagarin.reminderbot.model.TgUser

interface IUserService {
    fun saveUser(tgUser: TgUser)
    fun deleteUser(tgUserId: Long)
    fun findUser(tgUserId: Long): TgUser?
}