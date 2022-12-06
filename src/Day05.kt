data class Instruction(val quantity: Int, val from: Int, val to: Int)

fun main() {

    fun parsInstructions(input: String): List<Instruction> = input.lines().filter(String::isNotBlank).map { line ->
        val (a, b, c) = Regex("move (\\d+) from (\\d+) to (\\d+)").find(line)!!.destructured
        Instruction(a.toInt(), b.toInt() - 1, c.toInt() - 1)
    }

    fun parsDrawing(input: String): List<String> =
        input.lines().reversed().drop(1).fold(mutableListOf()) { acc, line ->
            line.windowed(4, 4, true).foldIndexed(acc) { i, stacks, window ->
                Regex("[([A-Z]1)]").find(window)?.value?.let {
                    if (stacks.isEmpty() || stacks.size <= i) stacks.add(it) else stacks[i] = stacks[i] + it
                }
                stacks
            }
        }

    fun parsInput(input: String): Pair<List<Instruction>, List<String>> =
        input.split("\n\n").let { (drawing, moves) ->
            Pair(parsInstructions(moves), parsDrawing(drawing))
        }

    fun move(stacks: List<String>, instruction: Instruction, bulk: Boolean = false): List<String> {
        val newState = stacks.toMutableList()
        val popped = newState[instruction.from].takeLast(instruction.quantity)
        newState[instruction.from] = newState[instruction.from].dropLast(instruction.quantity)
        newState[instruction.to] = newState[instruction.to] + if (bulk) popped else popped.reversed()
        return newState
    }

    fun part1(input: String): String {
        val (instructions, stacks) = parsInput(input)
        return instructions.fold(stacks) { state, ins -> move(state, ins) }
            .reduce { acc, i -> acc + i.last() }
    }

    fun part2(input: String): String {
        val (instructions, stacks) = parsInput(input)
        return instructions.fold(stacks) { state, ins -> move(state, ins, true) }
            .reduce { acc, i -> acc + i.last() }
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
