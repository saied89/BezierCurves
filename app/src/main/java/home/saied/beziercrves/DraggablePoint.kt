package home.saied.beziercrves

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

private val DraggablePointRadius = 15.dp

@OptIn(ExperimentalTextApi::class)
@Composable
fun DraggablePoint(
    offset: () -> IntOffset,
    setOffset: (IntOffset) -> Unit,
    color: Color,
    modifier: Modifier = Modifier
) {
    val textMeasure = rememberTextMeasurer()
    val radiusPx = with(LocalDensity.current) { DraggablePointRadius.toPx() }
    Canvas(
        modifier = modifier
            .size(DraggablePointRadius)
            .offset {
                offset() - IntOffset(radiusPx.roundToInt() / 2, radiusPx.roundToInt() / 2)
            }
            .background(color, shape = CircleShape)
            .pointerInput(Unit) {
                detectDragGestures { _, dragAmount ->
                    val delta = IntOffset(dragAmount.x.roundToInt(), dragAmount.y.roundToInt())
                    val offsetValue = offset() + delta
                    if (offsetValue.x >= 0 && offsetValue.y >= 0)
                        setOffset(offsetValue)
                }
            }
            .wrapContentSize()
    ) {
        val currentOffset = offset()
        val textLayoutResult = textMeasure.measure(AnnotatedString(currentOffset.toString()))
        drawText(textLayoutResult, topLeft = Offset(this.size.width, this.size.height))
    }
}