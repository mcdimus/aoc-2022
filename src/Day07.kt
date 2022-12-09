fun main() {
    fun part1(input: List<String>): Long {
        val root = parse(input)
        return root.findRecursiveFoldersNotLargerThan(100000).sumOf(Node.Folder::getSize)
    }

    fun part2(input: List<String>): Long {
        val root = parse(input)
        val needToFree = root.getSize() - 40000000
        return root.getRecursiveFolders()
            .map(Node.Folder::getSize)
            .filter { it > needToFree }
            .min()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437L)
    check(part2(testInput) == 24933642L)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

private fun parse(input: List<String>): Node.Folder {
    val root = Node.Folder(name = "/", parent = null, children = mutableListOf())
    var currentDir: Node.Folder = root
    for (command in input.drop(1)) {
        when {
            command == "$ cd /" -> {
                currentDir = root
                continue
            }

            command == "$ cd .." -> {
                currentDir = currentDir.parent ?: throw IllegalStateException("no parent")
                continue
            }

            command.startsWith("$ cd ") -> {
                val folderName = command.replace("$ cd ", "")
                currentDir = currentDir.findDirectFolderByName(folderName)
                continue
            }

            command.startsWith("$ ls") -> continue

            command.startsWith("dir") -> {
                val folderName = command.replace("dir ", "")
                if (currentDir.children.none { it.name == folderName }) {
                    currentDir.children.add(
                        Node.Folder(name = folderName, parent = currentDir)
                    )
                }
                continue
            }

            !command.startsWith("$") && !command.startsWith("dir") -> {
                val split = command.split(' ')
                val fileSize = split[0]
                val fileName = split[1]
                if (currentDir.children.none { it.name == fileName }) {
                    currentDir.children.add(
                        Node.File(name = fileName, parent = currentDir, size = fileSize.toLong())
                    )
                }
            }
        }
    }
    return root
}

sealed class Node(
    open val name: String,
    open val parent: Folder?
) {
    data class File(
        override val name: String,
        override val parent: Folder?,
        val size: Long
    ) : Node(name, parent) {
        override fun toString() = "$size $name"
    }

    data class Folder(
        override val name: String,
        override val parent: Folder? = null,
        val children: MutableList<Node> = mutableListOf()
    ) : Node(name, parent) {

        private val _size: Long by lazy {
            children.filterIsInstance<File>().sumOf { it.size } +
                    children.filterIsInstance<Folder>().sumOf { it.getSize() }
        }

        fun getSize() = _size

        fun findRecursiveFoldersNotLargerThan(limit: Long): List<Folder> {
            val folders = children.filterIsInstance<Folder>()
            return folders.filter { it.getSize() < limit } +
                    folders.flatMap { it.findRecursiveFoldersNotLargerThan(limit) }
        }

        fun getRecursiveFolders(): List<Folder> {
            val folders = children.filterIsInstance<Folder>()
            return folders + folders.flatMap { it.getRecursiveFolders() }
        }

        fun findDirectFolderByName(name: String) =
            children.filterIsInstance<Folder>().singleOrNull { it.name == name }
                ?: throw IllegalArgumentException("folder '$name' not found")

        override fun toString() = "dir $name"

    }


}

