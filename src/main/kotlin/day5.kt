import java.io.File
import java.util.*

fun main() {

    /**
     * Doing this exercise with strings is most likely not ideal but for
     * whatever reason I did not want to use Stack<Int>. Don't ask why :)
     */

    data class Command(val move: Int, val from: Int, val to: Int)

    data class PuzzleInput(
        val stacks: MutableMap<Int, String> = TreeMap(),
        val commands: MutableList<Command> = mutableListOf()
    )

    fun parseInput(): PuzzleInput {
        val puzzleInput = PuzzleInput()
        var parseStacks = true
        File("resources/day5.txt").forEachLine { line ->
            if (line.isBlank()) {
                return@forEachLine
            }

            if (parseStacks && "[" !in line) {
                parseStacks = false
                return@forEachLine
            }

            if (parseStacks) {
                line.chunked(4).forEachIndexed { idx, chunk ->
                    if (chunk.isBlank()) {
                        return@forEachIndexed
                    }

                    puzzleInput.stacks.compute(idx + 1) { _, v ->
                        if (v == null) "" + chunk[1] else v + chunk[1]
                    }
                }
            } else {
                val moveFromTo = line.replace("move ", "")
                    .replace(" from", "")
                    .replace(" to", "")
                    .trim()
                    .split(" ")
                puzzleInput.commands += Command(
                    moveFromTo[0].toInt(),
                    moveFromTo[1].toInt(),
                    moveFromTo[2].toInt()
                )
            }
        }

        return puzzleInput
    }

    fun rearrange(input: PuzzleInput, keepOrder: Boolean) {
        input.commands.forEach { cmd ->
            val fromStack = input.stacks[cmd.from]!!
            val toStack = input.stacks[cmd.to]!!
            var crates = fromStack.substring(0, cmd.move)
            if (!keepOrder) {
                crates = crates.reversed()
            }

            input.stacks[cmd.from] = fromStack.removeRange(0, cmd.move)
            input.stacks[cmd.to] = crates + toStack
        }
    }

    fun part1() {
        val input = parseInput()
        rearrange(input, false)
        println(input.stacks.values.filter { it.isNotBlank() }.map { it[0] }.joinToString(""))
    }

    fun part2() {
        val input = parseInput()
        rearrange(input, true)
        println(input.stacks.values.filter { it.isNotBlank() }.map { it[0] }.joinToString(""))
    }

    part1()
    part2()
}