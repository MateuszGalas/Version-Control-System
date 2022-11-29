package svcs

import java.io.File

fun config(name: String = "") {
    val file = File("vcs\\config.txt")

    if (!file.exists()) {
        file.createNewFile()
        file.writeText("Please, tell me who you are.")
    }

    if (name == "") {
        println(file.readText())
    } else {
        file.writeText("The username is $name.")
        println("The username is $name.")
    }
}

fun add(name: String = "") {
    val file = File("vcs\\index.txt")

    if (!file.exists()) {
        file.createNewFile()
        println("Add a file to the index.")
        return
    }

    if (name == "") {
        println("Tracked files:\n${file.readText()}")
    } else {
        if (File("$name").exists()) {
            println("The file '$name' is tracked." )
            file.appendText(name + "\n")
        } else {
            println("Can't find '$name'.")
        }
    }
}

fun main(args: Array<String>) {

    val file = File("vcs")

    if (!file.exists()) {
        file.mkdir()
    }


    val help = "These are SVCS commands:\n" +
            "config     Get and set a username.\n" +
            "add        Add a file to the index.\n" +
            "log        Show commit logs.\n" +
            "commit     Save changes.\n" +
            "checkout   Restore a file."



    when (args.firstOrNull()) {
        "config" -> if (args.size > 1) config(args[1]) else config()
        "--help" -> println(help)
        "add" -> if (args.size > 1) add(args[1]) else add()
        "log" -> println("Show commit logs.")
        "commit" -> println("Save changes.")
        "checkout" -> println("Restore a file.")
        null -> println(help)
        else -> println("'${args.first()}' is not a SVCS command.")
    }

}