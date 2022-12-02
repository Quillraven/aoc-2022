import java.io.File

fun main() {

    fun String.rpsToInt(): Int {
        return when {
            this == "A" || this == "X" -> 0
            this == "B" || this == "Y" -> 1
            else -> 2
        }
    }

    // returns RPS for right elf to get expected outcome
    fun String.toRps(otherElf: Int): Int {
        return when {
            this == "X" -> (otherElf + 2) % 3
            this == "Y" -> otherElf
            else -> (otherElf + 1) % 3
        }
    }

    // calculates score for right elf
    fun rpsResult(left: Int, right: Int): Int {
        return when {
            left == right -> 3
            (left + 1) % 3 == right -> 6
            else -> 0
        }
    }

    fun part1() {
        fun parseRounds(): List<Pair<Int, Int>> {
            val rounds = mutableListOf<Pair<Int, Int>>()
            File("resources/day2.txt").forEachLine { line ->
                val roundInput = line.split(" ")
                val leftElf = roundInput[0].rpsToInt()
                val rightElf = roundInput[1].rpsToInt()

                rounds += Pair(leftElf, rightElf)
            }
            return rounds
        }

        val rounds = parseRounds()
        println(rounds.sumOf { (left, right) -> rpsResult(left, right) + right + 1 })
    }

    fun part2() {
        fun parseRounds(): List<Pair<Int, Int>> {
            val rounds = mutableListOf<Pair<Int, Int>>()
            File("resources/day2.txt").forEachLine { line ->
                val roundInput = line.split(" ")
                val leftElf = roundInput[0].rpsToInt()
                val rightElf = roundInput[1].toRps(leftElf)

                rounds += Pair(leftElf, rightElf)
            }
            return rounds
        }

        val rounds = parseRounds()
        println(rounds.sumOf { (left, right) -> rpsResult(left, right) + right + 1 })
    }

    part1()
    part2()
}