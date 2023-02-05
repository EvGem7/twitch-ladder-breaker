package me.evgem.twitch.ladder_breaker

import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.runBlocking
import me.evgem.irk.client.IrkClient
import me.evgem.irk.client.joinChannel
import me.evgem.irk.client.model.message.PrivateMessage

fun main(args: Array<String>): Unit = runBlocking {
    val channelName = args.getOrElse(0) { return@runBlocking printUsage() }
    val nickname = args.getOrElse(1) { return@runBlocking printUsage() }
    val password = args.getOrElse(2) { return@runBlocking printUsage() }
    val server = IrkClient().connectServer(
        hostname = "irc.chat.twitch.tv",
        port = 6667,
        nickname = nickname,
        password = password
    )
    println("connected server ${server.welcomeMessage}")
    val channel = server.joinChannel(channelName)
    println("joined channel ${channel.name}")

    val ladderBreaker = LadderBreaker()
    channel
        .messages
        .filterIsInstance<PrivateMessage>()
        .collect { message ->
            ladderBreaker.getBreak(message.text)?.let {
                channel.sendMessage(it.breakingText)
                println("Broke a ladder from ${message.user}. The ladder consisted of ${it.ladderComponent}")
            }
        }
}

private fun printUsage() {
    println("Params: channelName nickname password")
}
