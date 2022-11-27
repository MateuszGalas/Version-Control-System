fun identity(identity: Int): Int {
    return identity
}

fun half(argument: Int): Int {
    return argument / 2
}

fun zero(zero: Int): Int {
    return 0
}

fun generate(functionName: String): (Int) -> Int {
    return when (functionName) {
        "identity" -> ::identity
        "half" -> ::half
        else -> ::zero
    }
}
