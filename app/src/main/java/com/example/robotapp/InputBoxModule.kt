//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.material.DropdownMenu
//import androidx.compose.material.DropdownMenuItem
//import androidx.compose.material.Switch
//import androidx.compose.material.Text
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import com.example.robotapp.CreateLineList
//import com.example.robotapp.Line
//
//class InputBox
//@Composable
//fun CreateInputBox(
//    correspondingLine: Line,
//    gap: Int,
//    legLength: Int,
//    secondPass: Boolean,
//    text: String,
//    modifier: Modifier = Modifier
//) {
//    val gapItems = listOf("0", "3", "6", "7")
//    var gap by remember { mutableStateOf(gap) }
//    var gapExpanded by remember { mutableStateOf(false) }
//    val legLengthItems = listOf("6", "7", "8", "9", "10", "11", "12", "13")
//    var legLength by remember { mutableStateOf(legLength) }
//    var legLengthExpanded by remember { mutableStateOf(false) }
//    var secondPass by remember { mutableStateOf(secondPass) }
//
//    Box(
//        modifier = Modifier
//            .border(1.dp, Color.Black)
//            .background(Color.White),
//        contentAlignment = Alignment.TopStart
//    ) {
//        Row(
//            modifier = Modifier
//                .align(Alignment.BottomStart)
//                .height(40.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(text = "갭")
//            Text(
//                text = gap.toString(),
//                modifier = Modifier
//                    .width(40.dp)
//                    .clickable { gapExpanded = true }
//                    .background(Color.White)
//                    .border(1.dp, Color.Black)
//            )
//            DropdownMenu(
//                expanded = gapExpanded,
//                onDismissRequest = { gapExpanded = false }
//            ) {
//                gapItems.forEach { label ->
//                    DropdownMenuItem(onClick = {
//                        gap = label.toInt()
//                        gapExpanded = false
//                    }) {
//                        Text(text = label)
//                    }
//                }
//            }
//
//            Spacer(modifier = Modifier.width(6.dp))
//
//            Text(text = "각장")
//            Text(
//                text = legLength.toString(),
//                modifier = Modifier
//                    .width(40.dp)
//                    .clickable { legLengthExpanded = true }
//                    .background(Color.White)
//                    .border(1.dp, Color.Black)
//            )
//            DropdownMenu(
//                expanded = legLengthExpanded,
//                onDismissRequest = { legLengthExpanded = false }
//            ) {
//                legLengthItems.forEach { label ->
//                    DropdownMenuItem(onClick = {
//                        legLength = label.toInt()
//                        legLengthExpanded = false
//                    }) {
//                        Text(text = label)
//                    }
//                }
//            }
//
//            Spacer(modifier = Modifier.width(6.dp))
//
//            if (name.contains("수평")) {
//                Text(text = "2패스")
//                Switch(
//                    checked = secondPass,
//                    onCheckedChange = { secondPass = it }
//                )
//            } else {
//                Spacer(modifier = Modifier.size(6.dp))
//            }
//        }
//    }
//}