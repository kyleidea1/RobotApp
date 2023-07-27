package com.example.robotapp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

data class InputBox(
    var x: Int,
    var y: Int,
    var gap: Int,
    var legLength: Int,
    var secondPass: Boolean,
    var name: String
)

@Composable
fun RenderInputBox(
    box: InputBox,
    x: Int,
    y: Int,
    onGapChange: (Int) -> Unit,
    onLegLengthChange: (Int) -> Unit,
    onSwitchStateChange: ((Boolean) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val gapItems = listOf("0", "3", "6", "7")
    var gapExpanded by remember { mutableStateOf(false) }
    val legLengthItems = listOf("6", "7", "8", "9", "10", "11", "12", "13")
    var legLengthExpanded by remember { mutableStateOf(false) }
    var switchState by remember { mutableStateOf(box.secondPass) }

    Box(
        modifier = modifier
            .offset(x.dp, y.dp)
            .border(1.dp, Color.Black)
            .background(Color.LightGray)
            .padding(4.dp), // 바깥 박스의 여백 조정
        contentAlignment = Alignment.Center // 글씨를 텍스트 상자 중앙에 입력하기 위해 Alignment.Center로 설정
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .height(60.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "갭")
            Text(
                text = box.gap.toString(),
                modifier = Modifier
                    .width(40.dp)
                    .clickable { gapExpanded = true }
                    .background(Color.White)
                    .border(1.dp, Color.Black)
            )
            DropdownMenu(
                expanded = gapExpanded,
                onDismissRequest = { gapExpanded = false }
            ) {
                gapItems.forEach { label ->
                    DropdownMenuItem(onClick = {
                        onGapChange(label.toInt())
                        gapExpanded = false
                    }) {
                        Text(text = label)
                    }
                }
            }

            Spacer(modifier = Modifier.width(6.dp))

            Text(text = "각장")
            Text(
                text = box.legLength.toString(),
                modifier = Modifier
                    .width(40.dp)
                    .clickable { legLengthExpanded = true }
                    .background(Color.White)
                    .border(1.dp, Color.Black)
            )
            DropdownMenu(
                expanded = legLengthExpanded,
                onDismissRequest = { legLengthExpanded = false }
            ) {
                legLengthItems.forEach { label ->
                    DropdownMenuItem(onClick = {
                        onLegLengthChange(label.toInt())
                        legLengthExpanded = false
                    }) {
                        Text(text = label)
                    }
                }
            }

            Spacer(modifier = Modifier.width(6.dp))

            if (box.name.contains("수평")) {
                Text(text = "2패스")
                Switch(
                    checked = switchState,
                    onCheckedChange = { newSwitchState ->
                        switchState = newSwitchState
                        onSwitchStateChange?.invoke(newSwitchState)
                    }
                )
            } else {
                // Add Spacer in the place of the Switch if "수평" is not contained in the text
                Spacer(modifier = Modifier.size(6.dp))
            }
        }
    }
}
fun createInputBox(
    x: Int,
    y: Int,
    gap: Int,
    legLength: Int,
    secondPass: Boolean,
    name: String
) : InputBox {
    return InputBox(x, y, gap, legLength, secondPass, name)
}


@Composable
fun DrawBoxes(inputBoxList: List<InputBox>) {
    inputBoxList.forEach { box ->
        RenderInputBox(
            box = box,
            x = box.x,
            y = box.y,
            onGapChange = { gap ->
                box.gap = gap
                println("갭이 변경되었습니다. 변경된 값: ${box.gap}")
                          },
            onLegLengthChange = { legLength ->
                box.legLength = legLength
                println("각장이 변경되었습니다. 변경된 값: ${box.legLength}")},
            onSwitchStateChange = if (box.name.contains("수평")) {
                { switchState ->
                    box.secondPass = switchState
                    println("2패스 스위치가 변경되었습니다. 변경된 값: ${box.secondPass}")
                }
            } else null
        )
    }
}

fun CreateInputBoxList(): List<InputBox> {
    val inputBoxList = mutableListOf<InputBox>()
    inputBoxList.add(createInputBox(340, 100, 0, 7, false, "좌수직"))
    inputBoxList.add(createInputBox(40, 350, 0, 7, false, "좌수평"))
    inputBoxList.add(createInputBox(340, 400, 0, 7, false, "좌론지"))
    inputBoxList.add(createInputBox(780, 600, 0, 7, false, "좌칼라"))
    inputBoxList.add(createInputBox(530, 730, 0, 7, false, "수평"))
    inputBoxList.add(createInputBox(780, 100, 0, 7, false, "우수직"))
    inputBoxList.add(createInputBox(990, 340, 0, 7, false, "우수평"))
    inputBoxList.add(createInputBox(780, 400, 0, 7, false, "우론지"))
    inputBoxList.add(createInputBox(340, 600, 0, 7, false, "우칼라"))
    return inputBoxList
}

fun MakeInputBoxesToDrawList(conditionList: List<Int>): List<InputBox> {
    val inputBoxList = CreateInputBoxList()
    val inputBoxesToDraw = mutableListOf<InputBox>()

    if (conditionList[0] == 1) {
        inputBoxesToDraw.add(inputBoxList[2])
    }
    if (conditionList[1] == 1) {
        inputBoxesToDraw.add(inputBoxList[3])
    }
    if (conditionList[2] == 1) {
        inputBoxesToDraw.add(inputBoxList[0])
        inputBoxesToDraw.add(inputBoxList[1])
    }
    if (conditionList[3] == 1) {
        inputBoxesToDraw.add(inputBoxList[4])
    }
    if (conditionList[4] == 1) {
        inputBoxesToDraw.add(inputBoxList[5])
        inputBoxesToDraw.add(inputBoxList[6])
    }
    if (conditionList[5] == 1) {
        inputBoxesToDraw.add(inputBoxList[8])
    }
    if (conditionList[6] == 1) {
        inputBoxesToDraw.add(inputBoxList[7])
    }
    return inputBoxesToDraw
}
