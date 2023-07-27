package com.example.robotapp

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.robotapp.ui.theme.MyBlue
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
    var linkedInputBox: InputBox,
    var initialIsDotted: Boolean = false,
) : Line(startX, startY, stopX, stopY, isEnabled)

data class UnclickableLine(
    override val startX: Float,
    override val startY: Float,
    override val stopX: Float,
    override val stopY: Float,
    override var isEnabled: Boolean = true
) : Line(startX, startY, stopX, stopY, isEnabled)

data class Collar(
    val type: String,
    val image: Int,
    val dottedImage: Int,
    val linkedInputBox: InputBox,
    val position: Offset
)


@Composable
fun DrawPair(pair: Pair<List<Line>, List<Collar>>){
    val (lineList, collarList) = pair
    DrawLines(lineList)
    DrawCollars(collarList)
}


@Composable
fun DrawLines(lines: List<Line>) {
    val linesState = remember { lines }
    linesState.forEach { line ->
        when (line) {
            is ClickableLine -> DrawClickableLine(line)
            is UnclickableLine -> DrawUnclickableLine(line)
        }
    }
}

@Composable
fun DrawClickableLine(line: ClickableLine) {
    val density = LocalDensity.current
    val distance = sqrt((line.stopX.toDouble() - line.startX.toDouble()).pow(2) + (line.stopY.toDouble() - line.startY.toDouble()).pow(2)).toFloat()
    val angle = atan2(line.stopY.toDouble() - line.startY.toDouble(), line.stopX.toDouble() - line.startX.toDouble()) * (180 / PI).toFloat()
    val isDotted = remember { mutableStateOf(line.initialIsDotted) }

    Box(
        Modifier
            .fillMaxSize()
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            drawLine(
                color = if (isDotted.value) Color.Black else MyBlue,
                start = Offset(line.startX, line.startY),
                end = Offset(line.stopX, line.stopY),
                strokeWidth = 25f,
                pathEffect = if (isDotted.value) PathEffect.dashPathEffect(floatArrayOf(30f, 30f), 0f) else null
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
@Composable
fun DrawUnclickableLine(line: UnclickableLine) {
    if (line.isEnabled) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            drawLine(
                color = MyBlue,
                start = Offset(line.startX, line.startY),
                end = Offset(line.stopX, line.stopY),
                strokeWidth = 25f,
                cap = StrokeCap.Round
            )
        }
    }
}

@Composable
fun DrawCollars(collars: List<Collar>) {
    Box(Modifier.fillMaxSize()) { // Create a Box layout that fills the available space
        collars.forEach { collar ->
            DrawCollar(collar)
        }
    }
}

@Composable
fun DrawCollar(collar: Collar) {
    // Clickable state
    val isClicked = remember { mutableStateOf(false) }
    // Determine which image to show
    val image = if (isClicked.value) collar.dottedImage else collar.image
    val imageModifier = Modifier
        .offset(x = collar.position.x.dp, y = collar.position.y.dp)
        .clickable { isClicked.value = !isClicked.value }

    // Draw the image
    Image(
        painter = painterResource(id = image),
        contentDescription = collar.type,
        modifier = imageModifier
    )
}



fun parseUData(pathData: String): UnclickableLine {
    val points = pathData.substring(1).split('L').flatMap { it.split(',') }.map { it.toFloat() }

    return UnclickableLine(
        startX = points[0],
        startY = points[1],
        stopX = points[2],
        stopY = points[3],
        isEnabled = true // Change this based on your requirement
    )
}

fun parseCData(pathData: String, linkedInputBox: InputBox): ClickableLine {
    val points = pathData.substring(1).split('L', 'H').flatMap { it.split(',') }.map { it.toFloat() }

    if (pathData.contains('L')) {
        return ClickableLine(
            startX = points[0],
            startY = points[1],
            stopX = points[2],
            stopY = points[3],
            initialIsDotted = false,
            isEnabled = true,
            linkedInputBox = linkedInputBox
        )
    } else { // 'H' case
        return ClickableLine(
            startX = points[0],
            startY = points[1],
            stopX = points[2],
            stopY = points[1], // For 'H', y coordinate remains the same
            initialIsDotted = false,
            isEnabled = true,
            linkedInputBox = linkedInputBox
        )
    }
}


@Composable
fun CreatePair(LineToDraw: List<Int>): Pair<List<Line>, List<Collar>> {
    val inputBoxList = CreateInputBoxList()
    val lineList = mutableListOf<Line>()
    val collarList = mutableListOf<Collar>()

    val LBK0: ClickableLine = parseCData("C9.6,686.2L476.3,361.5", inputBoxList[0])
    lineList.add(LBK0)

    val leftBracketDiagonal: UnclickableLine = parseUData("U3.7,681.3L464.8,3.4")
    val LBK1: ClickableLine = parseCData("C467.3,0L467.3,366.1", inputBoxList[2])

    if (LineToDraw[2] == 1) {
        lineList.add(leftBracketDiagonal)
        lineList.add(LBK1)
    }

    val leftPanelBottom: UnclickableLine = parseUData("U16.2,1361.2L474.1,1041.7")
    lineList.add(leftPanelBottom)

    val LVL2: ClickableLine = parseCData("C468.2,366.1L468.2,1052.7", inputBoxList[1])
    lineList.add(LVL2)

    val leftPanelHorizontal: UnclickableLine = parseUData("U6.8,678.2L6.8,1364.7")
    lineList.add(leftPanelHorizontal)



    val upperHorizontal: UnclickableLine = parseUData("U463.2,361.1L1412.6,361.1")
    lineList.add(upperHorizontal)

    val CHF0: ClickableLine = parseCData("C461.4,1047.7L1410.8,1047.7", inputBoxList[4])
    lineList.add(CHF0)

    val RBK0: ClickableLine = parseCData("C1880.4,682.7L1413.7,357.9", inputBoxList[5])
    lineList.add(RBK0)

    val rightBracketDiagonal: UnclickableLine = parseUData("U1886.3,681.3L1425.2,3.4")
    val RBK1: ClickableLine = parseCData("C1419.2,0L1419.2,366.1", inputBoxList[7])

    if (LineToDraw[4] == 1) {
        lineList.add(rightBracketDiagonal)
        lineList.add(RBK1)
    }

    val rightPanelBottom: UnclickableLine = parseUData("U1871.6,1357L1413.7,1037.6")
    lineList.add(rightPanelBottom)

    val RVL2: ClickableLine = parseCData("C1418.3,366.1L1418.3,1052.7", inputBoxList[6])
    lineList.add(RVL2)

    val rightPanelHorizontal: UnclickableLine = parseUData("U1883.2,678.2L1883.2,1364.7")
    lineList.add(rightPanelHorizontal)

    val leftCollar = Collar(
        image = R.drawable.leftcollar,
        dottedImage = R.drawable.dottedleftcollar,
        linkedInputBox = inputBoxList[3],
        type = "leftCollar",
        position = Offset(x = 310f, y = 520f)
    )

    val rightCollar = Collar(
        image = R.drawable.rightcollar,
        dottedImage = R.drawable.dottedrightcollar,
        linkedInputBox = inputBoxList[3],
        type = "leftCollar",
        position = Offset(x = 750f, y = 520f)
    )

    if (LineToDraw[1] == 1) {
        collarList.add(leftCollar)
    }

    if (LineToDraw[5] == 1) {
        collarList.add(rightCollar)
    }

    return Pair(lineList, collarList)
}

