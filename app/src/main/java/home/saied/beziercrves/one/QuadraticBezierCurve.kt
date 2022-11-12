package home.saied.beziercrves.one

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun QuadraticBezierCurve(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {

        var offset by remember { mutableStateOf(Offset(20f, 110f)) }
        val dOffset by remember {
            derivedStateOf {
                offset
            }
        }
//        Point({ dOffset }) {
//            offset = it
//        }
        Point(
            offset = { dOffset },
            setOffset = { offset = it }
        )
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
    offset: () -> Offset,
    setOffset: (Offset) -> Unit,
    modifier: Modifier = Modifier
) {
    val textMeasure = rememberTextMeasurer()
    Canvas(
        modifier = modifier
            .size(15.dp)
            .offset {
                val currentOffset = offset()
                IntOffset(currentOffset.x.roundToInt(), currentOffset.y.roundToInt())
            }
            .background(Color.Red, shape = CircleShape)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    val currentOffset = offset()
                    val original = Offset(currentOffset.x, currentOffset.y)
                    setOffset(original + dragAmount)
                    Log.d("drag", offset.toString())
                }
            }
            .wrapContentSize()
    ) {
        val currentOffset = offset()
        drawCircle(Color.Green)
        val textLayoutResult = textMeasure.measure(AnnotatedString(currentOffset.toString()))
        drawText(textLayoutResult, topLeft = Offset(this.size.width, this.size.height))
    }
}