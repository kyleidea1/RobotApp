import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.robotapp.*

@Composable
fun FloorPlan() {
    val context = LocalContext.current
    val fileName = "SN0000_S000P_001.txt"
    val drawData = SecondLineParser(context, fileName)
    val lineAndCollarPair = CreatePair(drawData)
    val inputBoxesToDraw = MakeInputBoxesToDrawList(drawData)

    Box(modifier = Modifier
        .width(1300.dp)
        .fillMaxHeight()
    ) {
        Box {
            DrawPair(lineAndCollarPair)
            DrawBoxes(inputBoxesToDraw)
        }
    }
}

