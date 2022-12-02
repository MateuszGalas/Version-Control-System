package svcs

import java.io.File
import java.security.MessageDigest

var committed = File("vcs\\committed.txt")

fun config(name: String = ""): String {
    val file = File("vcs\\config.txt")

    val commitFile = File("vcs\\commits")
    val logFile = File("vcs\\log.txt")

    if (!commitFile.exists()) commitFile.mkdir()
    if (!logFile.exists()) logFile.createNewFile()
    if (!committed.exists()) {
        committed.createNewFile()
        committed.writeText("false")
    }

    if (!file.exists()) {
        file.createNewFile()
        file.writeText("Please, tell me who you are.")
    }

    return if (name == "") {
        file.readText()
    } else {
        committed.writeText("true")
        file.writeText("The username is $name.")
        "The username is $name."
    }
}

fun add(name: String = "") {
    val file = File("vcs\\index.txt")
    if (!committed.exists()) {
        committed.createNewFile()
        committed.writeText("false")
    }
    committed.writeText("true")

    if (!file.exists()) {
        file.createNewFile()
        println("Add a file to the index.")
        file.writeText(name + "\n")
        return
    }

    if (name == "") {
        println("Tracked files:${file.readText()}")
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
    val indexFile = File("vcs\\index.txt")

    if (!commitFile.exists()) commitFile.mkdir()
    if (!logFile.exists()) logFile.createNewFile()
    if (!indexFile.exists()) indexFile.createNewFile()
    if (!committed.exists()) {
        committed.createNewFile()
        committed.writeText("false")
    }

    if (message == "") {
        println("Message was not passed.")
        return
    }
    val author = config().split(" ").last().split(".").first()

    if (committed.readText() == "true") {
        val log = logFile.readText()
        logFile.writeText("commit ${message.toMD5()}\n")
        logFile.appendText("Author: $author\n")
        logFile.appendText("$message\n\n")
        logFile.appendText(log)
        println("Changes are committed.")
        committed.writeText("false")

        val file = File("vcs\\commits\\${message.toMD5()}")
        val mess = indexFile.readText().split("\n")

        if (!file.exists()) {
            file.mkdir()
        }


        for (i in 0 until mess.lastIndex) {
            val file2 = File("vcs\\commits\\${message.toMD5()}\\${mess[i].split(" ")[0]}")
            if (!file2.exists()) {
                file2.createNewFile()
                file2.writeText("commit ${message.toMD5()}\n")
                file2.appendText("Author: $author\n")
                file2.appendText(message)
            } else {
                file2.writeText("commit ${message.toMD5()}\n")
                file2.appendText("Author: $author\n")
                file2.appendText(message)
            }
        }

    } else {
        println("Nothing to commit.")
        committed.writeText("true")
    }
}

fun log(): String {
    val logFile = File("vcs\\log.txt")

    if (!logFile.exists())  return "No commits yet."
    if (logFile.isFile && logFile.readText().isEmpty()) return "No commits yet."

    return logFile.readText()
}

fun ByteArray.toHex(): String {
    return joinToString("") { "%02x".format(it) }
}

fun String.toMD5(): String {
    val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
    return bytes.toHex()
}

fun checkout(commitID: String = "") {
    val logFile = File("vcs\\log.txt")
    var exist = false

    if (!logFile.exists()) {
        println("Commit does not exist.")
        return
    }
    if (commitID == "") {
        println("Commit id was not passed.")
        return
    }


    val commitFile = File("vcs\\commits\\$commitID")
/*    val log = logFile.readLines()
    for (l in log) {
        if (l == "commit $commitID") {
            exist = true
        }
    }
    */
    if (commitFile.exists()) {
        print("Switched to commit $commitID.")

        for (f in commitFile.listFiles()) {
            File("${f.name}").writeText(f.readText())
        }
    } else {
        println("Commit does not exist.")
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

    //checkout("82dfa5549ebc9afc168eb7931ebece5f")
/*    config ("Christine2")
    add ("first_file.txt")
    add ("second_file.txt")
    commit ("First commit")
    commit ("Second commit")
    checkout()
    checkout ("wrongId")
    log()
    checkout ("32877ad7b71db068b968af65af2ebdc9")*/

    when (args.firstOrNull()) {
        "config" -> println(if (args.size > 1) config(args[1]) else config())
        "--help" -> println(help)
        "add" -> if (args.size > 1) add(args[1]) else add()
        "log" -> println(log())
        "commit" -> if (args.size > 1) commit(args[1]) else commit()
        "checkout" -> if (args.size > 1) checkout(args[1]) else checkout()
        null -> println(help)
        else -> println("'${args.first()}' is not a SVCS command.")
    }

}