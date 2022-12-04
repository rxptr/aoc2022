fun main() {

    fun parsInput(input: List<String>) = input.map {
        it.split(',').map { id ->
            id.split('-')
                .let { (a, b) -> a.toInt() .. b.toInt() }
        }.let { (f, s) -> Pair(f, s) }
    }

    fun part1(input: List<String>) = parsInput(input).count {
        val common = it.first intersect it.second
        (common.isNotEmpty() && ((it.first - common).isEmpty() || (it.second - common).isEmpty()))
    }

    fun part2(input: List<String>) = parsInput(input).count {
        (it.first intersect it.second).isNotEmpty()
    }

    val testInput = readInputLines("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInputLines("Day04")
    println(part1(input))
    println(part2(input))

}