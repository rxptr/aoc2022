fun main() {

    fun mapPriorities(chars: List<Char>) = chars.map {
        if (it.isLowerCase()) it.code - 96 else it.code - 38
    }

    fun part1(input: List<String>): Int {
        val pairs = input.map {
            val middle = it.length / 2
            Pair(it.substring(0, middle), it.substring(middle, it.length))
        }
        val duplicates = pairs.map {
            (it.first.toSet() intersect it.second.toSet()).first()
        }
        return mapPriorities(duplicates).sum()
    }

    fun part2(input: List<String>): Int {
        val groups = input.chunked(3)
        val common = groups.map { tuple ->
            tuple.map { it.toSet() }
        }.map {
            (it[0] intersect it[1] intersect it[2]).first()
        }
        return mapPriorities(common).sum()
    }

    val testInput = readInputLines("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInputLines("Day03")
    println(part1(input))
    println(part2(input))
}
