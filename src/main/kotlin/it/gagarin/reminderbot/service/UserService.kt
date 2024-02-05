package it.gagarin.reminderbot.service

import it.gagarin.reminderbot.model.TgUser
import it.gagarin.reminderbot.repository.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService @Autowired constructor(private val userRepository: UserRepository) : IUserService {
    override fun saveUser(tgUser: TgUser) {
        userRepository.save(tgUser)
    }

    override fun deleteUser(tgUserId: Long) {
        userRepository.deleteById(tgUserId)
    }

    override fun findUser(tgUserId: Long): TgUser? {
        val optional = userRepository.findById(tgUserId)
        return optional.orElse(null)
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(UserService::class.java)
    }
}