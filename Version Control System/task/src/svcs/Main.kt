package svcs

import java.io.File
import java.security.MessageDigest

fun config(name: String = ""): String {
    val file = File("vcs\\config.txt")

    val commitFile = File("vcs\\commits")
    val logFile = File("vcs\\log.txt")

    if (!commitFile.exists()) commitFile.mkdir()
    if (!logFile.exists()) logFile.createNewFile()

    if (!file.exists()) {
        file.createNewFile()
        file.writeText("Please, tell me who you are.")
    }

    if (name == "") {
        return file.readText()
    } else {
        file.writeText("The username is $name.")
        return "The username is $name."
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

fun commit(message: String = "") {
    val commitFile = File("vcs\\commits")
    val logFile = File("vcs\\log.txt")
    var changed = true

    if (!commitFile.exists()) commitFile.mkdir()
    if (!logFile.exists()) logFile.createNewFile()

    if (message == "") {
        println("Message was not passed.")
        return
    } else if (message == "\"Files were not changed\"") {
        println("Nothing to commit.")
        return
    }

    val changes = logFile.readLines()
    for (i in changes) {
        if (i == "commit ${message.toMD5()}") changed = false
    }

    if (changed) {
        val author = config().split(" ").last().split(".").first()
        logFile.writeText("commit ${message.toMD5()}\n")
        logFile.appendText("Author: $author\n")
        logFile.appendText("$message\n\n")
        println("Changes are committed.")
    } else {
        println("Nothing to commit.")
    }

//    val mess = message.split(" ")
//    val extention = mess.last().split(".")
//    val author = config().split(" ").last().split(".").first()
//
//    if (extention.last() == "txt")
//    {
//        val file = File("vcs\\commits\\${message.toMD5()}")
//
//        if (!file.exists()) {
//            file.mkdir()
//        }
//
//        val file2 = File("vcs\\commits\\${message.toMD5()}\\${mess.last()}")
//        if (!file2.exists()) {
//            file2.createNewFile()
//            file2.writeText("Author: $author\n")
//            file2.appendText(message)
//        } else {
//            file2.writeText("Author: $author\n")
//            file2.appendText(message)
//        }
//        logFile.appendText(file2.readText())
//    } else {
//        println("Changes are committed.")
//    //logFile.appendText()
//    }
}

fun log(): String {
    val logFile = File("vcs\\log.txt")
    var txt = ""

    if (!logFile.exists())  return "No commits yet."
    if (logFile.isFile && logFile.readText().isEmpty()) return "No commits yet."

    return logFile.readText()
    //logFile.walk(FileWalkDirection.TOP_DOWN).forEach { if (it.isFile) println(it.readText()) else println(it) }
//    for (i in logFile.listFiles()) {
//        if (i.isDirectory) {
//            txt += "commit ${i.name}\n"
//            for (j in i.listFiles()) {
//                txt += j.readText()
//                txt += "\n"
//            }
//            txt += "\n"
//        }
//    }

//    logFile.walkTopDown().forEach { if (it.isFile) println(it.readText()) else println("commit ${it.name}") }
//    println(logFile.listFiles().map { it.name })
//    return txt
}

fun ByteArray.toHex(): String {
    return joinToString("") { "%02x".format(it) }
}

fun String.toMD5(): String {
    val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
    return bytes.toHex()
}

fun main(args: Array<String>) {

    val file = File("vcs")

    if (!file.exists()) {
        file.mkdir()
    }

//    config("John")
//    commit("Changed several lines of code in the file2.txt")
//    println(log())

    val help = "These are SVCS commands:\n" +
            "config     Get and set a username.\n" +
            "add        Add a file to the index.\n" +
            "log        Show commit logs.\n" +
            "commit     Save changes.\n" +
            "checkout   Restore a file."



    when (args.firstOrNull()) {
        "config" -> println(if (args.size > 1) config(args[1]) else config())
        "--help" -> println(help)
        "add" -> if (args.size > 1) add(args[1]) else add()
        "log" -> println(log())
        "commit" -> if (args.size > 1) commit(args[1]) else commit()
        "checkout" -> println("Restore a file.")
        null -> println(help)
        else -> println("'${args.first()}' is not a SVCS command.")
    }

}