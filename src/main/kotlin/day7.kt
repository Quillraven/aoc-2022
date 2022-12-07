fun main() {

    open class File(val name: String, var size: Long) {
        override fun toString(): String {
            return "$name ($size)"
        }
    }

    class Directory(name: String) : File(name, 0) {
        val files = mutableSetOf<File>()
        var parent: Directory? = null

        operator fun plusAssign(file: File) {
            files += file
            size += file.size
            if (file is Directory) {
                file.parent = this
            }

            var p = parent
            while (p != null) {
                p.size += file.size
                p = p.parent
            }
        }

        override fun toString(): String {
            return "dir $name ($size)"
        }
    }

    data class Terminal(private val input: List<String>) {
        private val root = Directory("/")
        private var currentDirectory = root
        // private val directories = mutableMapOf("/" to root)

        fun process() {
            var i = 0
            while (i < input.lastIndex) {
                val line = input[i]
                if (i == 125) {
                    println("")
                }
                when {
                    line.startsWith("$ cd") -> changeDirectory(line.substringAfter("$ cd "))
                    line.startsWith("$ ls") -> {
                        for (j in i + 1..input.lastIndex) {
                            if (input[j].startsWith("$")) {
                                break
                            }

                            ++i
                            listFile(input[j])
                        }
                    }

                    else -> error("Wrong input: $line")
                }

                ++i
            }
        }

        private fun listFile(inputLine: String) {
            when {
                inputLine.startsWith("dir") -> {
                    currentDirectory += Directory(inputLine.substringAfter("dir "))
                }

                else -> {
                    val size = inputLine.substringBefore(" ")
                    val name = inputLine.substringAfter(" ")
                    currentDirectory += File(name, size.toLong())
                }
            }
        }

        private fun changeDirectory(directoryName: String) {
            if (directoryName == "..") {
                currentDirectory = currentDirectory.parent!!
                return
            }

            val directory = Directory(directoryName)
            if (directory != currentDirectory) {
                currentDirectory += directory
            }
            currentDirectory = directory
        }

        private fun files(): List<File> {
            val toProcess = mutableListOf<File>().apply { addAll(root.files) }
            val allFiles = mutableListOf<File>()
            while (toProcess.isNotEmpty()) {
                val f = toProcess.removeFirst()
                allFiles += f
                if (f is Directory) {
                    toProcess += f.files
                }
            }
            return allFiles
        }

        fun filterDirectoriesBySize(maxSize: Long): List<Directory> {
            return files().filterIsInstance<Directory>()
                .filter { it.size <= maxSize }
        }

        fun findDirectoryToRemove(): Directory {
            val maxSize = 70_000_000
            val usedSize = maxSize - root.size
            val toFree = 30_000_000 - usedSize

            return files().filterIsInstance<Directory>()
                .filter { it.size >= toFree }
                .minBy { it.size }
        }
    }

    fun part1() {
        val terminal = Terminal(java.io.File("resources/day7.txt").readLines())
        terminal.process()

        println(terminal.filterDirectoriesBySize(100_000).sumOf { it.size })
    }

    fun part2() {
        val terminal = Terminal(java.io.File("resources/day7.txt").readLines())
        terminal.process()

        println(terminal.findDirectoryToRemove().size)
    }

    part1()
    part2()
}