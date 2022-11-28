import java.io.File

// Searching for directory with the largest number of files
fun main() {
    val file = File("C:\\Users\\Gulish\\Downloads\\basedir")
    val filesNames = file.listFiles()
    val list = mutableListOf<MutableList<String>>()
    val list2 = mutableListOf<String>()

    for (files in filesNames) {
        list2.add(files.toString())
        list.add(files.listFiles().map { it.name }.toMutableList())
    }

    println(list2[list.map { it.size }.indexOf(list.maxOf { it.size })].filter { it.isDigit() })
    println(list.maxOf { it.size })
}