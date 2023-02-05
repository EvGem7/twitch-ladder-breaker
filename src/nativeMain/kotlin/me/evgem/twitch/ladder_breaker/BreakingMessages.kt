package me.evgem.twitch.ladder_breaker

import kotlin.random.Random
import kotlin.random.nextInt

class BreakingMessages {

    val messages: List<String> = listOf(
        "Ты строить, я ломать.",
        "Зачем ты это делаешь?",
        "Сломано.",
        "Увы.",
        "Нельзя сотворить здесь.",
        "Здесь тебе не Египет.",
        "Нет.",
        "Нельзя.",
        "Три раза построй, один раз сломай.",
        "Тебе нечем заняться?",
        "Бесполезно.",
        "Не стоило тебе этого делать...",
        "\uD83D\uDEAB TheIlluminati",
        "А вы знали, что постройка пирамид - верный признак шизофрении?",
    )

    private val notUsedMessages = ArrayList(messages)

    fun getRandomMessage(): String {
        if (notUsedMessages.isEmpty()) {
            notUsedMessages.addAll(messages)
        }
        val index = Random.nextInt(notUsedMessages.indices)
        return notUsedMessages.removeAt(index)
    }
}
