import androidx.compose.runtime.Composable
import java.io.BufferedReader
import java.io.File

fun parseFile(filePath: String): List<String> {
    val pcList = listOf("pc1831", "pc1829", "pc1832", "pc1833", "pc1834", "pc1835", "pc1836", "pc1837", "pc1838")
    val lines = mutableListOf<String>()
    val commands = mutableListOf<String>()

    // 파일 절대 경로에서 읽기
    val file = File(filePath)
    val reader = BufferedReader(file.reader())

    reader.use {
        var line = it.readLine()
        while (line != null) {
            lines.add(line)
            line = it.readLine()
        }
    }

    commands.add("${pcList[0]} ${lines[0].split(' ').joinToString(",")}")
    commands.add("${pcList[1]} ${lines[1].split(' ').joinToString(",")}")

    val indexline = lines[1].split(' ')

    var contentIndex = 2

    for (i in 0 until 7) {
        if (indexline[i] == "0") {
            commands.add(pcList[i + 2])
        } else {
            commands.add("${pcList[i + 2]} ${lines[contentIndex].split(' ').joinToString(",")}")
            contentIndex++
        }
    }

    // 리스트를 출력합니다.
    for (command in commands) {
        println(command)
    }

    return commands
}

fun get1829(filePath: String): List<Int> {
    val lines = File(filePath).readLines()
    val secondLine = lines[1]  // assuming that the file has at least 2 lines
    return secondLine.split(' ').map { it.toInt() }
}

fun main() {
    val commands = parseFile("/Users/minhyukkim/RobotApp/app/src/main/java/com/example/robotapp/Input.txt")
}
