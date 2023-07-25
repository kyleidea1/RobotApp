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
    var correspondingLine: Line,
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
        modifier = Modifier
            .offset(x.dp, y.dp)
            .border(1.dp, Color.Black)
            .background(Color.White),
        contentAlignment = Alignment.TopStart
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .height(40.dp),
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
    correspondingLine: Line,
    x: Int,
    y: Int,
    gap: Int,
    legLength: Int,
    secondPass: Boolean,
    name: String
) : InputBox {
    return InputBox(correspondingLine, x, y, gap, legLength, secondPass, name)
}

@Composable
fun CreateInputBoxes() {
    val lineList = CreateLineList()

    val leftVerticalBox = createInputBox(lineList[1], 340, 100, 0, 7, false, "좌수직")
    RenderInputBox(
        box = leftVerticalBox,
        x = 340,
        y = 100,
        onGapChange = { gap -> leftVerticalBox.gap = gap },
        onLegLengthChange = { legLength -> leftVerticalBox.legLength = legLength }
    )

    val leftHorizontalBox = createInputBox(lineList[3], 40, 400, 0, 7, false, "좌수평")
    RenderInputBox(
        box = leftHorizontalBox,
        x = 40,
        y = 400,
        onGapChange = { gap ->
            leftHorizontalBox.gap = gap
            println("갭 값이 변경되었습니다. 변경된 값: $gap")
        },
        onLegLengthChange = { legLength ->
            leftHorizontalBox.legLength = legLength
            println("각장 값이 변경되었습니다. 변경된 값: $legLength")
        },
        onSwitchStateChange = {
            leftHorizontalBox.secondPass = !leftHorizontalBox.secondPass // 현재 상태를 반대로 바꿈
            println("2패스 스위치가 변경되었습니다. 변경된 값: ${leftHorizontalBox.secondPass}")
        }
    )

    val leftLongiBox = createInputBox(lineList[5], 340, 400, 0, 7, false, "좌론지")
    RenderInputBox(
        box = leftLongiBox,
        x = 340,
        y = 400,
        onGapChange = { gap -> leftLongiBox.gap = gap },
        onLegLengthChange = { legLength -> leftLongiBox.legLength = legLength }
    )

    val leftCollarBox = createInputBox(lineList[5], 340, 600, 0, 7, false, "좌칼라")
    RenderInputBox(
        box = leftCollarBox,
        x = 340,
        y = 600,
        onGapChange = { gap -> leftCollarBox.gap = gap },
        onLegLengthChange = { legLength -> leftCollarBox.legLength = legLength }
    )

    val horizontalBox = createInputBox(lineList[7], 530, 730, 0, 7, false, "수평")
    RenderInputBox(
        box = horizontalBox,
        x = 530,
        y = 730,
        onGapChange = { gap ->
            horizontalBox.gap = gap
            println("갭 값이 변경되었습니다. 변경된 값: $gap")
        },
        onLegLengthChange = { legLength ->
            horizontalBox.legLength = legLength
            println("각장 값이 변경되었습니다. 변경된 값: $legLength")
        },
        onSwitchStateChange = {
            horizontalBox.secondPass = !horizontalBox.secondPass // 현재 상태를 반대로 바꿈
            println("2패스 스위치가 변경되었습니다. 변경된 값: ${horizontalBox.secondPass}")
        }
    )

    val rightVerticalBox = createInputBox(lineList[9], 780, 100, 0, 7, false, "우수직")
    RenderInputBox(
        box = rightVerticalBox,
        x = 780,
        y = 100,
        onGapChange = { gap -> rightVerticalBox.gap = gap },
        onLegLengthChange = { legLength -> rightVerticalBox.legLength = legLength }
    )

    val rightHorizontalBox = createInputBox(lineList[11], 990, 400, 0, 7, false, "우수평")
    RenderInputBox(
        box = rightHorizontalBox,
        x = 990,
        y = 400,
        onGapChange = { gap ->
            rightHorizontalBox.gap = gap
            println("갭 값이 변경되었습니다. 변경된 값: $gap")
        },
        onLegLengthChange = { legLength ->
            rightHorizontalBox.legLength = legLength
            println("각장 값이 변경되었습니다. 변경된 값: $legLength")
        },
        onSwitchStateChange = {
            rightHorizontalBox.secondPass = !rightHorizontalBox.secondPass // 현재 상태를 반대로 바꿈
            println("2패스 스위치가 변경되었습니다. 변경된 값: ${rightHorizontalBox.secondPass}")
        }
    )

    val rightLongiBox = createInputBox(lineList[13], 780, 400, 0, 7, false, "우론지")
    RenderInputBox(
        box = rightLongiBox,
        x = 780,
        y = 400,
        onGapChange = { gap -> rightLongiBox.gap = gap },
        onLegLengthChange = { legLength -> rightLongiBox.legLength = legLength }
    )

    val rightCollarBox = createInputBox(lineList[13], 780, 600, 0, 7, false, "우칼라")
    RenderInputBox(
        box = rightCollarBox,
        x = 780,
        y = 600,
        onGapChange = { gap -> rightCollarBox.gap = gap },
        onLegLengthChange = { legLength -> rightCollarBox.legLength = legLength }
    )
}