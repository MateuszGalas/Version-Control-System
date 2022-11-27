package svcs

fun main(args: Array<String>) {
    val help = "These are SVCS commands:\n" +
            "config     Get and set a username.\n" +
            "add        Add a file to the index.\n" +
            "log        Show commit logs.\n" +
            "commit     Save changes.\n" +
            "checkout   Restore a file."


    when (args.firstOrNull()) {
        "config" -> println("Get and set a username.")
        "--help" -> println(help)
        "add" -> println("Add a file to the index.")
        "log" -> println("Show commit logs.")
        "commit" -> println("Save changes.")
        "checkout" -> println("Restore a file.")
        null -> println(help)
        else -> println("'${args.first()}' is not a SVCS command.")
    }

}