package home.saied.beziercrves

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun QuadraticBezier(
    point0: Offset,
    point1: Offset,
    point2: Offset,
    modifier: Modifier = Modifier,
    style: DrawStyle = Stroke(width = 1f)
) {
    Canvas(modifier = modifier) {
        val path = Path()
        path.moveTo(point0.x, point0.y)
        path.quadraticBezierTo(point1.x, point1.y, point2.x, point2.y)
        drawPath(path = path, color = Color.Black, style = style)
    }
}