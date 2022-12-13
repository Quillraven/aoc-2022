import java.io.File

fun main() {

    fun String.toPackage(): List<Any> {
        val result = mutableListOf<Any>()
        val lists = mutableListOf(result)
        var number = ""

        this.forEachIndexed { idx, c ->
            if (idx == 0) return@forEachIndexed

            when (c) {
                '[' -> {
                    lists += mutableListOf<Any>()
                    lists[lists.lastIndex - 1] += lists.last() as Any
                }

                ']' -> {
                    if (number.isNotBlank()) {
                        lists.last() += number.toInt()
                        number = ""
                    }
                    lists.removeLast()
                }

                ',' -> {
                    if (number.isNotBlank()) {
                        lists.last() += number.toInt()
                        number = ""
                    }
                }

                else -> number += c
            }
        }

        return result
    }

    @Suppress("UNCHECKED_CAST")
    fun comparePackages(left: List<Any>, right: List<Any>): Int {
        if (left.isEmpty() && right.isEmpty()) return 0
        if (left.isEmpty()) return 1
        if (right.isEmpty()) return -1

        val leftIter = left.iterator()
        val rightIter = right.iterator()
        while (leftIter.hasNext() && rightIter.hasNext()) {
            val leftVal = leftIter.next()
            val rightVal = rightIter.next()

            if (leftVal is Int && rightVal is Int) {
                if (leftVal < rightVal) return 1
                if (leftVal > rightVal) return -1
            } else if (leftVal is Int) {
                val innerOrderResult = comparePackages(listOf(leftVal), rightVal as List<Any>)
                if (innerOrderResult != 0) {
                    return innerOrderResult
                }
            } else if (rightVal is Int) {
                val innerOrderResult = comparePackages(leftVal as List<Any>, listOf(rightVal))
                if (innerOrderResult != 0) {
                    return innerOrderResult
                }
            } else {
                val innerOrderResult = comparePackages(leftVal as List<Any>, rightVal as List<Any>)
                if (innerOrderResult != 0) {
                    return innerOrderResult
                }
            }
        }

        return when {
            !rightIter.hasNext() && !leftIter.hasNext() -> 0
            !leftIter.hasNext() -> 1
            else -> -1
        }
    }

    fun part1() {
        val packages = File("resources/day13.txt").readLines()
            .windowed(size = 2, step = 3) {
                it[0].toPackage() to it[1].toPackage()
            }

        val ordered = mutableListOf<Int>()
        packages.forEachIndexed { index, (left, right) ->
            if (comparePackages(left, right) == 1) {
                ordered += (index + 1)
            }
        }

        println(ordered.sum())
    }

    fun part2() {
        val packages = File("resources/day13.txt").readLines()
            .filter { it.isNotBlank() }
            .map { it.toPackage() }
            .toMutableList()

        val divider1 = listOf(listOf(2))
        val divider2 = listOf(listOf(6))
        packages += divider1 as List<Any>
        packages += divider2 as List<Any>
        val sorted = packages.sortedWith { p1, p2 -> comparePackages(p2, p1) }

        println((sorted.indexOf(divider1) + 1) * (sorted.indexOf(divider2) + 1))
    }

    part1()
    part2()
}
