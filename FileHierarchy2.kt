import java.io.File

// Finding all empty directories
fun main() {
    val file = File("C:\\Users\\Gulish\\Downloads\\basedir (1)\\basedir")
    val filesName = file.listFiles()

    file.walk(FileWalkDirection.TOP_DOWN).forEach {
        if (it.isDirectory && it.listFiles().isEmpty()) {
            println(it.toString().split("\\").last())

        }
    }

//    for (files in filesName) {
//        val f = files.listFiles()
//
//        for (i in f){
//            val a = File("$i")
//
//            if (a.isDirectory) {
//                if (a.listFiles().isEmpty())
//                {
//                    println(i.toString().split("\\").last())
//                }
//            }
//        }
//    }
}
