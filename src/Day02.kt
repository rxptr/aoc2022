enum class Hand(val score: Int) {

    ROCK(1), PAPER(2), SCISSORS(3);

    infix fun collide(other: Hand): Hand? {
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

        fun winnerSide(first: Hand, second: Hand): Outcome = when (first collide second) {
            first -> Outcome.First
            second -> Outcome.Second
            else -> Outcome.Draw
        }

    }
}

enum class Outcome {
    First, Second, Draw;

    companion object {
        fun fromInput(char: Char) : Outcome = when(char) {
            'X' -> First
            'Y' -> Draw
            'Z' -> Second
            else -> throw Exception("Invalid Input [$char]")
        }
    }
}

data class Move(val opponent: Hand, val player: Hand)
data class DesiredOutcome(val opponent: Hand, val outcome: Outcome)
class Game(moves: List<Move>) {
    private var opponentScore = 0
    var playerScore = 0
        private set

    private val winnerScore = 6
    private val drawScore = 3

    init {
        moves.forEach {
            val outcome = Hand.winnerSide(it.opponent, it.player)
            var opponentPoints = 0
            var playerPoints = 0
            when (outcome) {
                Outcome.First -> opponentPoints += winnerScore
                Outcome.Second -> playerPoints += winnerScore
                else -> {
                    opponentPoints += drawScore
                    playerPoints += drawScore
                }
            }
            opponentPoints += it.opponent.score
            playerPoints += it.player.score
            opponentScore += opponentPoints
            playerScore += playerPoints
        }
    }

}

fun main() {

    fun parsInputAsMoves(input: List<String>): List<Move> = input.map {
        val chars = it.toCharArray()
        Move(Hand.fromInput(chars[0]), Hand.fromInput(chars[2]))
    }

    fun parsInputAsDesiredOutcome(input: List<String>): List<DesiredOutcome> = input.map {
        val chars = it.toCharArray()
        DesiredOutcome(Hand.fromInput(chars[0]), Outcome.fromInput(chars[2]))
    }

    fun part1(input: List<String>): Int = Game(parsInputAsMoves(input)).playerScore

    fun part2(input: List<String>): Int {
        val moves = parsInputAsDesiredOutcome(input).map {
            Move(it.opponent, it.opponent expect it.outcome)
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