import java.io.File

//Longest word in file
fun main() {
    val file = File("C:\\Users\\Gulish\\Downloads\\words_sequence.txt")
    val words = file.readLines()
    var size = 0
    val s = { a: String, b: Int -> if (a.length > b) a.length else b }

    for (word in words) {
        size = s(word, size)
}
    println(size)
}