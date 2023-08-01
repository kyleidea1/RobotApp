package com.example.robotapp

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import get1829
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.pow
import kotlin.math.sqrt

open class Line(
    open val startX: Float,
    open val startY: Float,
    open val stopX: Float,
    open val stopY: Float,
    open var isEnabled: Boolean,
) {
    open fun invertLineVisibility() {
        isEnabled = !isEnabled
    }
}

data class ClickableLine(
    override val startX: Float,
    override val startY: Float,
    override val stopX: Float,
    override val stopY: Float,
    override var isEnabled: Boolean = true,
    val isDotted: MutableState<Boolean>,
    val pcIndex: Int,
    var linkedInputBox: InputBox? = null,  // 추가된 멤버 변수
) : Line(startX, startY, stopX, stopY, isEnabled)






data class UnclickableLine(
    override val startX: Float,
    override val startY: Float,
    override val stopX: Float,
    override val stopY: Float,
    override var isEnabled: Boolean,
) : Line(startX, startY, stopX, stopY, isEnabled)

@Composable
fun DrawLines(lines: List<Line>) {
    lines.forEach { line ->
        when (line) {
            is ClickableLine -> DrawClickableLine(line, line.isDotted)
            is UnclickableLine -> DrawUnclickableLine(line)
        }
    }
}

@Composable
fun DrawUnclickableLine(line: UnclickableLine) {
    if (line.isEnabled) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            drawLine(
                color = Color.Black,
                start = Offset(line.startX, line.startY),
                end = Offset(line.stopX, line.stopY),
                strokeWidth = 5f
            )
        }
    }
}

@Composable
fun DrawClickableLine(line: ClickableLine, isDotted: MutableState<Boolean>) {
    val density = LocalDensity.current
    val distance = sqrt((line.stopX.toDouble() - line.startX.toDouble()).pow(2) + (line.stopY.toDouble() - line.startY.toDouble()).pow(2)).toFloat()
    val angle = atan2(line.stopY.toDouble() - line.startY.toDouble(), line.stopX.toDouble() - line.startX.toDouble()) * (180 / PI).toFloat()

    Box(
        Modifier
            .fillMaxSize()
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            drawLine(
                color = Color.Black,
                start = Offset(line.startX, line.startY),
                end = Offset(line.stopX, line.stopY),
                strokeWidth = 5f,
                pathEffect = if (isDotted.value) PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f) else null
            )
        }

        // Invisible Button
        Box(
            modifier = Modifier
                .offset {
                    IntOffset(
                        (line.startX).toInt(),
                        (line.startY).toInt()
                    )
                }
                .size(
                    width = with(density) { distance.toDp() },
                    height = 1.dp
                )
                .graphicsLayer(
                    rotationZ = angle.toFloat(),
                    transformOrigin = TransformOrigin(0f, 0.5f)
                )
                .clickable {
                    isDotted.value = !isDotted.value
                }
                .background(Color.Transparent)
        )
    }
}

fun parseUData(pathData: String): UnclickableLine {
    val points = if (pathData.contains('H')) {
        val temp = pathData.substring(2).split('H').flatMap { it.split(',') }.map { it.toFloat() }
        listOf(temp[0], temp[1], temp[2], temp[1])
    } else {
        pathData.substring(2).split('L').flatMap { it.split(',') }.map { it.toFloat() }
    }

    return UnclickableLine(
        startX = points[0],
        startY = points[1],
        stopX = points[2],
        stopY = points[3],
        isEnabled = true // Change this based on your requirement
    )
}

fun parseCData(pathData: String, isDotted: MutableState<Boolean>, pcIndex: Int): ClickableLine {
    val points = if (pathData.contains('H')) {
        val temp = pathData.substring(2).split('H').flatMap { it.split(',') }.map { it.toFloat() }
        listOf(temp[0], temp[1], temp[2], temp[1])
    } else {
        pathData.substring(2).split('L').flatMap { it.split(',') }.map { it.toFloat() }
    }

    return ClickableLine(
        startX = points[0],
        startY = points[1],
        stopX = points[2],
        stopY = points[3],
        isEnabled = true,
        isDotted = isDotted,
        pcIndex = pcIndex
    )
}

@Composable
fun CreateLineList(): List<Line> {

    val leftBracketDiagonal: UnclickableLine = parseUData("UM3.7,681.3L464.8,3.4")
    val LBK0: ClickableLine = parseCData("CM9.6,686.2L476.3,361.5", remember { mutableStateOf(false) },2)
    val leftPanelBottom: UnclickableLine = parseUData("UM16.2,1361.2L474.1,1041.7")
    val LVL2: ClickableLine = parseCData("CM468.2,366.1L468.2,1052.7", remember { mutableStateOf(false) }, 0)
    val leftPanelHorizontal: UnclickableLine = parseUData("UM6.8,678.2L6.8,1364.7")
    val LBK1: ClickableLine = parseCData("CM467.3,0L467.3,366.1", remember { mutableStateOf(false) }, 7)
    val upperHorizontal: UnclickableLine = parseUData("UM463.2,361.1L1412.6,361.1")
    val CHF0: ClickableLine = parseCData("CM461.4,1047.7L1410.8,1047.7", remember { mutableStateOf(false) }, 3)
    val rightBracketDiagonal: UnclickableLine = parseUData("UM1886.3,681.3L1425.2,3.4")
    val RBK0: ClickableLine = parseCData("CM1880.4,682.7L1413.7,357.9", remember { mutableStateOf(false) }, 6)
    val rightPanelBottom: UnclickableLine = parseUData("UM1871.6,1357L1413.7,1037.6")
    val RVL2: ClickableLine = parseCData("CM1418.3,366.1L1418.3,1052.7", remember { mutableStateOf(false) }, 4)
    val rightPanelHorizontal: UnclickableLine = parseUData("UM1883.2,678.2L1883.2,1364.7")
    val RBK1: ClickableLine = parseCData("CM1419.2,0L1419.2,366.1", remember { mutableStateOf(false) }, 8)

    val isDottedLCP = remember { mutableStateOf(false) }
    val LCP1: ClickableLine = parseCData("CM1133,787H1411.4", isDottedLCP, 1)
    val LCP2: ClickableLine = parseCData("CM1133.4,787L1133,1042", isDottedLCP, 1)
    val isDottedRCP = remember { mutableStateOf(false) }
    val RCP1: ClickableLine = parseCData("CM475,787H753.4", isDottedRCP, 5)
    val RCP2: ClickableLine = parseCData("CM753.4,788L753,1043", isDottedRCP, 5)

    return listOf(
        leftBracketDiagonal, LBK0, leftPanelBottom, LVL2, leftPanelHorizontal, LBK1,
        upperHorizontal, CHF0, rightBracketDiagonal, RBK0, rightPanelBottom, RVL2,
        rightPanelHorizontal, RBK1, LCP1, LCP2, RCP1, RCP2
    )
}

class CommandManager {
    val initialCommand = get1829("/Users/minhyukkim/RobotApp/app/src/main/java/com/example/robotapp/Input.txt")
    val pc1829Command = MutableList(16) { 0 }

    init {
        updateCommand()
    }

    fun updateCommand() {
        for (i in 0 until 7) {
            pc1829Command[i] = initialCommand[i]
        }

        pc1829Command[7] = initialCommand[2]
        pc1829Command[8] = initialCommand[6]
        pc1829Command[9] = 1
        pc1829Command[10] = 1
        pc1829Command[11] = 0
        pc1829Command[12] = 0
        pc1829Command[13] = CheckBoxState.var1
        pc1829Command[14] = CheckBoxState.var2
        pc1829Command[15] = CheckBoxState.var3
    }

    fun checkBoxStateChanged() {
        updateCommand()
    }
}


fun main() {
    val commandManager = CommandManager()

    commandManager.checkBoxStateChanged()

    commandManager.pc1829Command.forEach { println(it) }
}
