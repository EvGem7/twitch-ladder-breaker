package me.evgem.twitch.ladder_breaker

class LadderBreaker {

    companion object {
        private const val MIN_LADDER_SIZE = 3
    }

    data class Break(
        val breakingText: String,
        val ladderComponent: String,
    )

    private val wordCounts = mutableListOf<Int>()
    private val breakingMessages = BreakingMessages()

    fun getBreak(message: String): Break? {
        val words = message.split(Regex("""\s+"""))
        if (words.isEmpty()) {
            reset()
            return null
        }
        if (!canBeLadderPart(words)) {
            reset()
            return null
        }
        wordCounts += words.size
        if (!isUpcomingLadder()) {
            reset(remainLast = true)
            return null
        }
        if (wordCounts.size >= MIN_LADDER_SIZE) {
            reset()
            return Break(breakingText = breakingMessages.getRandomMessage(), ladderComponent = words.first())
        }
        return null
    }

    private fun isUpcomingLadder(): Boolean {
        for (i in 1 until wordCounts.size) {
            val prev = wordCounts[i - 1]
            val cur = wordCounts[i]
            if (prev >= cur) {
                return false
            }
        }
        return true
    }

    private fun canBeLadderPart(words: List<String>): Boolean {
        return words.toHashSet().size == 1
    }

    private fun reset(remainLast: Boolean = false) {
        val last = if (remainLast) wordCounts.lastOrNull() else null
        wordCounts.clear()
        last?.let(wordCounts::add)
    }
}