import java.io.File

fun main() {

    fun parseCalories(): List<Int> {
        val calories = mutableListOf(0)
        File("resources/day1.txt").forEachLine { line ->
            if (line.isBlank()) {
                calories.add(0)
                return@forEachLine
            }

            calories[calories.lastIndex] = calories[calories.lastIndex] + line.toInt()
        }
        return calories
    }

    fun part1() {
        val calories = parseCalories()
        println(calories.max())
    }

    fun part2() {
        val calories = parseCalories()
        println(calories.sortedDescending().take(3).sum())
    }

    part1()
    part2()
}