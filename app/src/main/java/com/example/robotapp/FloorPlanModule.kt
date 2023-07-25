import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import com.example.robotapp.*
import kotlin.math.atan2
import kotlin.math.sqrt
import kotlin.math.pow
import kotlin.math.PI

@Composable
fun FloorPlan() {
    val lineList = CreateLineList()

    Box(modifier = Modifier
        .width(1300.dp)
        .fillMaxHeight()
    ) {
        Box {
            DrawLines(lineList)
            CreateInputBoxes()
        }
    }
}
