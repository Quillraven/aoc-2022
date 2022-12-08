import java.io.File

fun main() {

    data class Tree(val height: Int, var visible: Boolean = false)

    // transforms input lines to columns
    fun colsToScan(trees: List<List<Tree>>): List<List<Tree>> {
        val width = trees.first().size
        val cols = List<MutableList<Tree>>(width) { mutableListOf() }

        for (i in 0 until width) {
            trees.forEach { cols[i] += it[i] }
        }

        return cols
    }

    fun scanVisibleTrees(trees: List<Tree>) {
        var maxHeight = trees.first().height

        // ignore edge trees
        for (i in 1 until trees.lastIndex) {
            if (trees[i].height > maxHeight) {
                trees[i].visible = true
                maxHeight = trees[i].height
            }
        }
    }

    fun part1() {
        val trees = File("resources/day8.txt").readLines()
            .map { it.map { c -> Tree(c.digitToInt()) } }
        val cols = colsToScan(trees)

        for (i in 1 until trees.lastIndex) {
            scanVisibleTrees(trees[i])
            scanVisibleTrees(trees[i].reversed())
        }
        for (i in 1 until trees.lastIndex) {
            scanVisibleTrees(cols[i])
            scanVisibleTrees(cols[i].reversed())
        }

        val edgeTrees = trees.lastIndex * 2 + cols.lastIndex * 2
        val visibleInteriorTrees = trees.flatten().count { it.visible }
        println(edgeTrees + visibleInteriorTrees)
    }

    fun Char.toScenicScore(x: Int, y: Int, w: Int, h: Int, lines: List<String>): Int {
        val height = this.digitToInt()
        var viewTop = 0
        var viewBottom = 0
        var viewLeft = 0
        var viewRight = 0

        for (i in x + 1 until w) {
            ++viewRight
            if (lines[y][i].digitToInt() >= height) {
                break
            }
        }

        for (i in x - 1 downTo 0) {
            ++viewLeft
            if (lines[y][i].digitToInt() >= height) {
                break
            }
        }

        for (i in y + 1 until h) {
            ++viewBottom
            if (lines[i][x].digitToInt() >= height) {
                break
            }
        }

        for (i in y - 1 downTo 0) {
            ++viewTop
            if (lines[i][x].digitToInt() >= height) {
                break
            }
        }

        return viewTop * viewBottom * viewLeft * viewRight
    }

    fun part2() {
        val lines = File("resources/day8.txt").readLines()
        val width = lines.first().length
        val height = lines.size
        val scores = lines.mapIndexed { y, line ->
            line.mapIndexed { x, c ->
                c.toScenicScore(x, y, width, height, lines)
            }
        }.flatten()

        println(scores.max())
    }

    part1()
    part2()
}
