import java.io.File
import kotlin.math.max
import kotlin.math.min

fun main() {

    data class Position(var x: Int, var y: Int)

    class Cave(
        private val rocks: Set<Pair<IntRange, IntRange>>,
        private val floor: Int = -1,
    ) {
        private val boundaryX = rocks.minOf { it.first.min() }..rocks.maxOf { it.first.max() }
        private val boundaryY = 0..rocks.maxOf { it.second.max() }
        private val sandAreas = hashSetOf<Position>()
        private val startLocation = Position(500, 0)
        private var sand = Position(startLocation.x, startLocation.y)

        val numSand: Int
            get() = sandAreas.size

        private fun inAbyss(position: Position): Boolean {
            if (floor != -1) {
                return false
            }
            return position.x !in boundaryX || position.y !in boundaryY
        }

        private fun isAir(position: Position): Boolean {
            val (x, y) = position

            if (y == floor) {
                return false
            }

            rocks.forEach { rockLine ->
                if (x in rockLine.first && y in rockLine.second) {
                    return false
                }
            }

            if (position in sandAreas) {
                return false
            }

            return true
        }

        fun step(): Boolean {
            if (inAbyss(sand)) {
                return false
            }

            sand.y++
            if (isAir(sand)) {
                return true
            }

            sand.x--
            if (isAir(sand)) {
                return true
            }

            sand.x += 2
            if (isAir(sand)) {
                return true
            }

            sand.x--
            sand.y--
            sandAreas += sand
            if (sand == startLocation) {
                // sand comes to rest at origin
                return false
            }

            // drop new sand
            sand = Position(startLocation.x, startLocation.y)
            return true
        }
    }

    fun parseCave(withFloor: Boolean = false): Cave {
        val rocks = File("resources/day14.txt").readLines()
            .map { line ->
                buildSet {
                    line.split("->").windowed(2) { (from, to) ->
                        val (fromX, fromY) = from.split(",")
                        val (toX, toY) = to.split(",")
                        val rangeMinX = min(fromX.trim().toInt(), toX.trim().toInt())
                        val rangeMaxX = max(fromX.trim().toInt(), toX.trim().toInt())
                        val rangeMinY = min(fromY.trim().toInt(), toY.trim().toInt())
                        val rangeMaxY = max(fromY.trim().toInt(), toY.trim().toInt())
                        this += rangeMinX..rangeMaxX to rangeMinY..rangeMaxY
                    }
                }
            }
            .flatten()
            .toSet()

        if (withFloor) {
            return Cave(rocks, rocks.maxOf { it.second.max() } + 2)
        }

        return Cave(rocks)
    }

    fun part1() {
        val cave = parseCave(withFloor = false)
        while (cave.step()) {

        }
        println(cave.numSand)
    }

    fun part2() {
        val cave = parseCave(withFloor = true)
        while (cave.step()) {

        }
        println(cave.numSand)
    }

    part1()
    part2()
}
