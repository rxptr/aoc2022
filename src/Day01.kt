fun main() {

    val emptyLine = "\n\n"

    fun sliceInputToListOfInts(input: String): List<List<Int>> =
        input.split(emptyLine)
            .map { lines -> lines.lines().filter { it.isNotBlank() }.map { it.toInt() } }

    fun sumOfCalories(input: String): List<Int> = sliceInputToListOfInts(input).map { it.sum() }.sortedDescending()

    fun part1(input: String): Int = sumOfCalories(input).max()

    fun part2(input: String): Int = sumOfCalories(input).take(3).sum()

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
