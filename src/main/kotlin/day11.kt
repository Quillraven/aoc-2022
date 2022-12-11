import java.io.File

fun main() {

    data class Monkey(
        val items: MutableList<Long>,
        val operation: String,
        val divisor: Long,
        val trueMonkeyIdx: Int,
        val falseMonkeyIdx: Int,
        var inspected: Int = 0
    )

    fun parseInput(): List<Monkey> {
        val monkeys = mutableListOf<Monkey>()

        File("resources/day11.txt").readLines().windowed(size = 6, step = 7) { monkeyInput ->
            val items = monkeyInput[1].substringAfter(": ").split(", ").map { it.toLong() }.toMutableList()
            val op = monkeyInput[2].substringAfter("= ")
            val divisor = monkeyInput[3].substringAfter("by ").toLong()
            val trueMonkeyIdx = monkeyInput[4].substringAfter("monkey ").toInt()
            val falseMonkeyIdx = monkeyInput[5].substringAfter("monkey ").toInt()

            monkeys += Monkey(items, op, divisor, trueMonkeyIdx, falseMonkeyIdx)
        }

        return monkeys
    }

    fun round(monkeys: List<Monkey>, manageRelief: (worryLevel: Long) -> Long) {
        monkeys.forEach { monkey ->
            monkey.items.forEach { item ->
                monkey.inspected++

                val worryLevel: Long = if (monkey.operation.contains("+")) {
                    val (first, second) = monkey.operation.split(" + ")
                    val firstNr = if ("old" == first) item else first.toLong()
                    val secondNr = if ("old" == second) item else second.toLong()
                    manageRelief(firstNr + secondNr)

                } else {
                    val (first, second) = monkey.operation.split(" * ")
                    val firstNr = if ("old" == first) item else first.toLong()
                    val secondNr = if ("old" == second) item else second.toLong()
                    manageRelief(firstNr * secondNr)
                }

                if (worryLevel % monkey.divisor == 0L) {
                    monkeys[monkey.trueMonkeyIdx].items += worryLevel
                } else {
                    monkeys[monkey.falseMonkeyIdx].items += worryLevel
                }
            }
            monkey.items.clear()
        }
    }

    fun part1() {
        val monkeys = parseInput()
        repeat(20) { round(monkeys) { worryLevel -> worryLevel / 3 } }
        println(monkeys.sortedByDescending { it.inspected }.take(2).fold(1) { acc, monkey -> acc * monkey.inspected })
    }

    fun part2() {
        val monkeys = parseInput()
        // trick is that all divisors are prime numbers and therefore we can do modulo by their product
        // -> I am honest ... I couldn't figure that out myself *cough* reddit *cough cough*
        val reliefFactor = monkeys.map { it.divisor }.reduce(Long::times)
        repeat(10_000) { round(monkeys) { worryLevel -> worryLevel % reliefFactor } }
        println(monkeys.sortedByDescending { it.inspected }.take(2).fold(1L) { acc, monkey -> acc * monkey.inspected })
    }

    part1()
    part2()
}
