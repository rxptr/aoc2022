fun main() {

    fun CharSequence.allUnique(): Boolean = this.toSet().size == this.length

    fun findMarker(dataStream: String, uniqueLength: Int): Int = dataStream
        .windowedSequence(uniqueLength).indexOfFirst { it.allUnique() } + uniqueLength

    fun part1(input: String) = findMarker(input, 4)

    fun part2(input: String) = findMarker(input, 14)

    val testInput1 = listOf(
        Triple("mjqjpqmgbljsphdztnvjfqwrcgsmlb", 7, 19),
        Triple("bvwbjplbgvbhsrlpgdmjqwftvncz", 5, 23),
        Triple("nppdvjthqldpwncqszvftbrmjlhg", 6, 23),
        Triple("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 10, 29),
        Triple("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 11, 26)
    )

    testInput1.forEach {
        check(part1(it.first) == it.second)
        check(part2(it.first) == it.third)
    }

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
