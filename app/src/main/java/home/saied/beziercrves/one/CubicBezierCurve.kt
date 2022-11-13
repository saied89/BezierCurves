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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toOffset
import home.saied.beziercrves.Line
import home.saied.beziercrves.DraggablePoint
import home.saied.beziercrves.intPairToIntOffset

private val offsetList = listOf(
    110.dp to 150.dp, 25.dp to 190.dp, 210.dp to 250.dp, 210.dp to 30.dp
)
private val colorList = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow)

@Composable
fun CubicBezierCurve(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        val initOffsetList = offsetList.map { LocalDensity.current.intPairToIntOffset(it) }
        val offsetList = remember { initOffsetList.toMutableStateList() }
        Line(vertice0 = { offsetList[0] }, vertice1 = { offsetList[1] })
        Line(vertice0 = { offsetList[1] }, vertice1 = { offsetList[2] })
        Line(vertice0 = { offsetList[2] }, vertice1 = { offsetList[3] })
        QubicBezier(
            point0 = offsetList[0].toOffset(),
            point1 = offsetList[1].toOffset(),
            point2 = offsetList[2].toOffset(),
            point3 = offsetList[3].toOffset()
        )
        repeat(4) { index ->
            DraggablePoint(
                offset = { offsetList[index] },
                setOffset = { offsetList[index] = it },
                color = colorList[index]
            )
        }
    }
}

@Composable
private fun QubicBezier(
    point0: Offset,
    point1: Offset,
    point2: Offset,
    point3: Offset,
    modifier: Modifier = Modifier,
) {
    Canvas(modifier = modifier) {
        val path = Path()
        path.moveTo(point0.x, point0.y)
        path.cubicTo(point1.x, point1.y, point2.x, point2.y, point3.x, point3.y)
        drawPath(path = path, color = Color.Black, style = Stroke(width = 1f))
    }
}