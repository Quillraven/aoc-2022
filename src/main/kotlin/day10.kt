import java.io.File

fun main() {

    fun part1() {
        var x = 1
        val signals = mutableListOf<Int>()

        File("resources/day10.txt").readLines()
            .map {
                when {
                    it == "noop" -> listOf(0)
                    it.startsWith("addx") -> {
                        listOf(0, it.substringAfter("addx ").toInt())
                    }

                    else -> error("unsupported input: $it")
                }
            }
            .flatten()
            .forEachIndexed { cycle, toAdd ->
                if (cycle in listOf(19, 59, 99, 139, 179, 219)) {
                    signals += ((cycle + 1) * x)
                }

                x += toAdd
            }

        println(signals.sum())
    }

    // outcome is ZKGRKGRK
    fun part2() {
        var x = 1

        File("resources/day10.txt").readLines()
            .map {
                when {
                    it == "noop" -> listOf(0)
                    it.startsWith("addx") -> {
                        listOf(0, it.substringAfter("addx ").toInt())
                    }

                    else -> error("unsupported input: $it")
                }
            }
            .flatten()
            .forEachIndexed { cycle, toAdd ->
                val hPos = cycle % 40
                when (x) {
                    in hPos - 1..hPos + 1 -> print("#")
                    else -> print(".")
                }

                if (hPos == 39) {
                    println()
                }

                x += toAdd
            }
    }

    part1()
    part2()
}
