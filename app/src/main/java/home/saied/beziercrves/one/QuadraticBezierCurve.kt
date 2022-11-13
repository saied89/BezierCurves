package home.saied.beziercrves.one

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toOffset
import home.saied.beziercrves.Line
import home.saied.beziercrves.Point
import kotlin.math.roundToInt

private val offsetRawList = listOf(
    20 to 110, 220 to 60, 70 to 250
)
private val colorList = listOf(Color.Green, Color.Red, Color.Blue)

@Composable
private fun initOffsets(): List<IntOffset> =
    with(LocalDensity.current) {
        offsetRawList.map {
            IntOffset(it.first.dp.toPx().roundToInt(), it.second.dp.toPx().roundToInt())
        }
    }

@Composable
fun QuadraticBezierCurve(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        val initOffsetList = initOffsets()
        val offsetList = remember { initOffsetList.toMutableStateList() }
        Line(vertice0 = { offsetList[0] }, vertice1 = { offsetList[1] })
        Line(vertice0 = { offsetList[0] }, vertice1 = { offsetList[2] })
        QuadraticBezier(point0 = offsetList[1].toOffset(), point1 = offsetList[0].toOffset(), point2 = offsetList[2].toOffset())
        repeat(3) { index ->
            Point(
                offset = { offsetList[index] },
                setOffset = { offsetList[index] = it },
                color = colorList[index]
            )
        }
    }
}

@Composable
private fun QuadraticBezier(
    point0: Offset,
    point1: Offset,
    point2: Offset,
    modifier: Modifier = Modifier,) {
    Canvas(modifier = modifier) {
        val path = Path()
        path.moveTo(point0.x, point0.y)
        path.quadraticBezierTo(point1.x, point1.y,point2.x, point2.y)
        drawPath(path = path, color = Color.Black, style = Stroke(width = 1f))
    }
}