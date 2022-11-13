package home.saied.beziercrves.one

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
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
import androidx.compose.ui.unit.toOffset
import kotlin.math.roundToInt

private val offsetRawList = listOf(
    20 to 110, 220 to 60, 70 to 250
)

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
//        Point({ dOffset }) {
//            offset = it
//        }
        repeat(3) { index ->
            Point(
                offset = { offsetList[index] },
                setOffset = { offsetList[index] = it }
            )
        }
        Line(vertice0 = { offsetList[0] }, vertice1 = { offsetList[1] })
        Line(vertice0 = { offsetList[0] }, vertice1 = { offsetList[2] })
    }
}

//@Composable
//private fun Point(
//    offset: () -> Offset, setOffset: (Offset) -> Unit
//) {
//    Box(
//        modifier = Modifier
//            .size(15.dp)
//            .offset {
//                val currentOffset = offset()
//                IntOffset(currentOffset.x.roundToInt(), currentOffset.y.roundToInt())
//            }
//            .background(Color.Red, shape = CircleShape)
//            .pointerInput(Unit) {
//                detectDragGestures { change, dragAmount ->
//                    val currentOffset = offset()
//                    val original = Offset(currentOffset.x, currentOffset.y)
//                    setOffset(original + dragAmount)
//                    Log.d("drag", offset.toString())
//                }
//            }
//            .wrapContentSize()
//    ) {
//        Text(text = offset().toString(), overflow = TextOverflow.Visible)
//    }
//}

@OptIn(ExperimentalTextApi::class)
@Composable
private fun Point(
    offset: () -> IntOffset,
    setOffset: (IntOffset) -> Unit,
    modifier: Modifier = Modifier
) {
    val textMeasure = rememberTextMeasurer()
    val radius = with(LocalDensity.current) { 15.dp.toPx() }
    Canvas(
        modifier = modifier
            .size(15.dp)
            .offset {
                offset() - IntOffset(radius.roundToInt() / 2, radius.roundToInt() / 2)
            }
            .background(Color.Red, shape = CircleShape)
            .pointerInput(Unit) {
                detectDragGestures { _, dragAmount ->
                    val delta = IntOffset(dragAmount.x.roundToInt(), dragAmount.y.roundToInt())
                    setOffset(offset() + delta)
                }
            }
            .wrapContentSize()
    ) {
        val currentOffset = offset()
        val textLayoutResult = textMeasure.measure(AnnotatedString(currentOffset.toString()))
        drawText(textLayoutResult, topLeft = Offset(this.size.width, this.size.height))
    }
}

@Composable
private fun Line(
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