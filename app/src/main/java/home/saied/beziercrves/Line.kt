package home.saied.beziercrves

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.toOffset

@Composable
fun Line(
    modifier: Modifier = Modifier,
    vertice0: () -> IntOffset,
    vertice1: () -> IntOffset
) {
    Canvas(modifier = modifier) {
        val intOffset0 = vertice0()
        val intOffset1 = vertice1()
        drawLine(Color.Black, intOffset0.toOffset(), intOffset1.toOffset())
    }
}