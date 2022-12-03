import java.io.File

fun main() {

    fun Char.toPriority(): Int {
        return when {
            isUpperCase() -> this - 'A' + 27
            else -> this - 'a' + 1
        }
    }

    fun part1() {

        operator fun String.component1(): Set<Char> = this.substring(0, (this.length * 0.5).toInt()).toSet()
        operator fun String.component2(): Set<Char> = this.substring((this.length * 0.5).toInt()).toSet()

        fun parseDuplicateItems(): List<Char> {
            val duplicates = mutableListOf<Char>()
            File("resources/day3.txt").forEachLine { line ->
                val (first, second) = line
                duplicates += first.filter { c -> c in second }
            }
            return duplicates
        }

        val duplicateItems = parseDuplicateItems()
        println(duplicateItems.sumOf { it.toPriority() })
    }

    fun part2() {

        fun parseBadges(): List<Char> {
            val badges = mutableListOf<Char>()
            File("resources/day3.txt").readLines().chunked(3).forEach { group ->
                val r1 = group[0].toSet()
                val r2 = group[1].toSet()
                val r3 = group[2].toSet()

                badges += r1.filter { c -> c in r2 && c in r3 }
            }
            return badges
        }

        val badges = parseBadges()
        println(badges.sumOf { it.toPriority() })
    }

    part1()
    part2()
}