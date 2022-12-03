enum class Hand(val score: Int) {

    ROCK(1), PAPER(2), SCISSORS(3);

    infix fun draw(other: Hand): Hand? {
        if (beats[this] == other) return this
        if (beatenBy[this] == other) return other
        return null
    }

    infix fun expect(outcome: Outcome) : Hand {
        if (outcome == Outcome.First) return beats[this]!!
        if (outcome == Outcome.Second) return beatenBy[this]!!
        return this
    }

    companion object {

        val beats = mapOf(
            ROCK to SCISSORS,
            SCISSORS to PAPER,
            PAPER to ROCK
        )

        val beatenBy = mapOf(
            SCISSORS to ROCK,
            ROCK to PAPER,
            PAPER to SCISSORS
        )

        fun fromInput(char: Char): Hand = when (char) {
            'A', 'X' -> ROCK
            'B', 'Y' -> PAPER
            'C', 'Z' -> SCISSORS
            else -> throw Exception("Invalid Input [$char]")
        }

    }
}

enum class Outcome {
    First, Second, Tie;

    companion object {
        fun fromInput(char: Char) : Outcome = when(char) {
            'X' -> First
            'Y' -> Tie
            'Z' -> Second
            else -> throw Exception("Invalid Input [$char]")
        }
    }
}

data class DesiredOutcome(val hand: Hand, val outcome: Outcome)
class Game(moves: List<Pair<Hand, Hand>>) {
    private var opponentScore = 0
    var playerScore = 0
        private set

    private val winnerScore = 6
    private val tieScore = 3

    init {
        moves.forEach {
            val outcome = winnerSide(it.first, it.second)
            var opponentPoints = 0
            var playerPoints = 0
            when (outcome) {
                Outcome.First -> opponentPoints += winnerScore
                Outcome.Second -> playerPoints += winnerScore
                else -> {
                    opponentPoints += tieScore
                    playerPoints += tieScore
                }
            }
            opponentPoints += it.first.score
            playerPoints += it.second.score
            opponentScore += opponentPoints
            playerScore += playerPoints
        }
    }

    private fun winnerSide(first: Hand, second: Hand): Outcome = when (first draw second) {
        first -> Outcome.First
        second -> Outcome.Second
        else -> Outcome.Tie
    }

}

fun main() {

    fun parsInputAsPairOfHands(input: List<String>): List<Pair<Hand, Hand>> = input.map {
        val chars = it.toCharArray()
        Pair(Hand.fromInput(chars[0]), Hand.fromInput(chars[2]))
    }

    fun parsInputAsDesiredOutcome(input: List<String>): List<DesiredOutcome> = input.map {
        val chars = it.toCharArray()
        DesiredOutcome(Hand.fromInput(chars[0]), Outcome.fromInput(chars[2]))
    }

    fun part1(input: List<String>): Int = Game(parsInputAsPairOfHands(input)).playerScore

    fun part2(input: List<String>): Int {
        val moves = parsInputAsDesiredOutcome(input).map {
            Pair(it.hand, it.hand expect it.outcome)
        }
        return Game(moves).playerScore
    }

    val testInput = readInputLines("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInputLines("Day02")
    println(part1(input))
    println(part2(input))

}