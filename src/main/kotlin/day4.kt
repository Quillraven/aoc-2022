import java.io.File

fun main() {

    operator fun String.component1(): IntRange {
        val fromTo = this.split(",")[0].split("-")
        return IntRange(fromTo[0].toInt(), fromTo[1].toInt())
    }

    operator fun String.component2(): IntRange {
        val fromTo = this.split(",")[1].split("-")
        return IntRange(fromTo[0].toInt(), fromTo[1].toInt())
    }

    fun parseInput(): List<Pair<IntRange, IntRange>> {
        val pairs = mutableListOf<Pair<IntRange, IntRange>>()
        File("resources/day4.txt").forEachLine { line ->
            val (firstRange, secondRange) = line
            pairs += Pair(firstRange, secondRange)
        }
        return pairs
    }

    fun part1() {
        val pairRanges = parseInput()
        println(pairRanges.count { (range1, range2) ->
            range1.all { it in range2 } || range2.all { it in range1 }
        })
    }

    fun part2() {
        val pairRanges = parseInput()
        println(pairRanges.count { (range1, range2) ->
            range1.any { it in range2 } || range2.any { it in range1 }
        })
    }

    part1()
    part2()
}