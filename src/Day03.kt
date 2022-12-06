fun main() {

    fun mapPriorities(chars: List<Char>) = chars.map { if (it.isLowerCase()) it.code - 96 else it.code - 38 }

    fun part1(input: List<String>): Int {
        val pairs = input.map {
            val (a, b) = it.chunked(it.length / 2)
            Pair(a.toSet(), b.toSet())
        }
        val duplicates = pairs.map { (it.first intersect it.second).first() }
        return mapPriorities(duplicates).sum()
    }

    fun part2(input: List<String>): Int {
        val common = input.chunked(3)
            .map { it.map(CharSequence::toSet) }
            .map { (a, b, c) -> (a intersect b intersect c).first() }
        return mapPriorities(common).sum()
    }

    val testInput = readInputLines("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInputLines("Day03")
    println(part1(input))
    println(part2(input))
}
