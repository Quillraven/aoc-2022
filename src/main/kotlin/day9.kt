import java.io.File
import kotlin.math.abs
import kotlin.math.sign

fun main() {

    data class Vector2(var x: Int, var y: Int) {

        operator fun plusAssign(other: Vector2) {
            x += other.x
            y += other.y
        }

        fun moveTo(target: Vector2) {
            x += (target.x - x).sign
            y += (target.y - y).sign
        }

        infix fun notAdjacent(other: Vector2): Boolean {
            return abs(other.x - x) > 1 || abs(other.y - y) > 1
        }
    }


    fun vec2(x: Int = 0, y: Int = 0) = Vector2(x, y)
    fun vec2(cpy: Vector2) = Vector2(cpy.x, cpy.y)

    fun moveKnots(numKnots: Int, movement: List<String>): Set<Vector2> {
        val knots = List(numKnots) { vec2() }
        val head = knots.first()
        val tail = knots.last()
        val positions = mutableSetOf(vec2(tail))

        movement.forEach { line ->
            val dirAmount = line.split(" ")
            val direction = dirAmount[0].first()
            val numSteps = dirAmount[1].toInt()

            for (i in 0 until numSteps) {
                head += when (direction) {
                    'R' -> vec2(1, 0)
                    'L' -> vec2(-1, 0)
                    'U' -> vec2(0, 1)
                    else -> vec2(0, -1)
                }

                for (j in 1..knots.lastIndex) {
                    if (knots[j] notAdjacent knots[j - 1]) {
                        knots[j].moveTo(knots[j - 1])
                        if (knots[j] === tail) {
                            positions += vec2(tail)
                        }
                    } else {
                        // a tail part didn't move -> remaining tail parts will also not move
                        break
                    }
                }
            }
        }

        return positions
    }

    fun part1() {
        println(moveKnots(numKnots = 2, movement = File("resources/day9.txt").readLines()).size)
    }

    fun part2() {
        println(moveKnots(numKnots = 10, movement = File("resources/day9.txt").readLines()).size)
    }

    part1()
    part2()
}
