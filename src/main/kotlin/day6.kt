import java.io.File

fun main() {

    fun String.isMarker(): Boolean {
        for (i in indices) {
            for (j in i + 1 until length) {
                if (this[i] == this[j]) {
                    return false
                }
            }
        }

        return true
    }

    fun part1() {
        val input = File("resources/day6.txt").readLines().first()
        val markerSize = 4
        val markerIdx = input.windowed(markerSize).indexOfFirst { it.isMarker() }

        println(markerIdx + markerSize)
    }

    fun part2() {
        val input = File("resources/day6.txt").readLines().first()
        val markerSize = 14
        val markerIdx = input.windowed(markerSize).indexOfFirst { it.isMarker() }

        println(markerIdx + markerSize)
    }

    part1()
    part2()
}