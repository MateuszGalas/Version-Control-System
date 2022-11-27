fun main() {
    val numbers = IntArray(100) { when(it) {
        0 -> 1
        9 -> 10
        99 -> 100
        else -> 0
    } }

    println(numbers.joinToString())
}