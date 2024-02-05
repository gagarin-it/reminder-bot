package it.gagarin.reminderbot.repository

import it.gagarin.reminderbot.model.TgUser
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<TgUser?, Long?>