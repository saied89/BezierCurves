package home.saied.beziercrves.two

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.toOffset
import home.saied.beziercrves.Line
import home.saied.beziercrves.DraggablePoint
import home.saied.beziercrves.QuadraticBezier
import home.saied.beziercrves.intPairToIntOffset
import kotlin.math.roundToInt

private val offsetList = listOf(
    70.dp to 160.dp, 20.dp to 60.dp, 280.dp to 10.dp
)
private val colorList = listOf(Color.Red, Color.Green, Color.Blue)

private val PointRadius = 5.dp


@Composable
fun BezierInterpolation(modifier: Modifier = Modifier) {
    val initOffsetList = offsetList.map { LocalDensity.current.intPairToIntOffset(it) }
    val offsetList = remember { initOffsetList.toMutableStateList() }
    var step by remember { mutableStateOf(25) }
    val ratioList = (0 until 100 step step).drop(1)
    val intepolation01 =
        ratioList.map {
            lerp(offsetList[0], offsetList[1], it.toFloat() / 100)
        }
    val intepolation12 =
        ratioList.map {
            lerp(offsetList[1], offsetList[2], it.toFloat() / 100)
        }
    Column(modifier = modifier) {
        QuadraticBezierCurveInterpolated0(
            offsetList = offsetList,
            ratioList = ratioList,
            setOffset = offsetList::set,
            interpolation01 = intepolation01,
            interpolation12 = intepolation12,
            modifier = Modifier.weight(1f)
        )
        Divider(thickness = 3.dp)
        QuadraticBezierCurveInterpolated1(
            offsetList = offsetList,
            setOffset = offsetList::set,
            interpolation01 = intepolation01,
            interpolation12 = intepolation12,
            modifier = Modifier.weight(1f)
        )
        Divider(thickness = 3.dp)
        QuadraticBezierCurveInterpolated2(
            offsetList = offsetList,
            setOffset = offsetList::set,
            modifier = Modifier.weight(1f)
        )
        Divider(thickness = 3.dp)
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Slider(
                value = step.toFloat(),
                onValueChange = { step = it.toInt() },
                modifier = Modifier.weight(1f),
                steps = 91,
                valueRange = 10f.rangeTo(90f)
            )
            Text(text = step.toString())
        }
    }
}

@Composable
private fun QuadraticBezierCurveInterpolated0(
    offsetList: List<IntOffset>,
    ratioList: List<Int>,
    setOffset: (index: Int, offset: IntOffset) -> Unit,
    interpolation01: List<IntOffset>,
    interpolation12: List<IntOffset>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(RectangleShape)
    ) {
        Line(vertice0 = { offsetList[0] }, vertice1 = { offsetList[1] })
        Line(vertice0 = { offsetList[1] }, vertice1 = { offsetList[2] })
        repeat(3) { index ->
            DraggablePoint(
                offset = { offsetList[index] },
                setOffset = { setOffset(index, it) },
                color = colorList[index]
            )
        }
        interpolation01.forEachIndexed { index, intOffset ->
            Point(
                offset = intOffset,
                labelText = "${ratioList[index]}%",
                labelOffset = IntOffset(-60, 0)
            )
        }
        interpolation12.forEachIndexed { index, intOffset ->
            Point(
                offset = intOffset,
                labelText = "${ratioList[index]}%",
                labelOffset = IntOffset(0, -40)
            )
        }
        interpolation01.forEachIndexed { index, intOffset ->
            Line(
                vertice0 = { intOffset },
                vertice1 = { interpolation12[index] })
        }
    }
}

@Composable
private fun QuadraticBezierCurveInterpolated1(
    offsetList: List<IntOffset>,
    setOffset: (index: Int, offset: IntOffset) -> Unit,
    interpolation01: List<IntOffset>,
    interpolation12: List<IntOffset>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(RectangleShape)
    ) {
        Line(vertice0 = { offsetList[0] }, vertice1 = { offsetList[1] })
        Line(vertice0 = { offsetList[1] }, vertice1 = { offsetList[2] })
        repeat(3) { index ->
            DraggablePoint(
                offset = { offsetList[index] },
                setOffset = { setOffset(index, it) },
                color = colorList[index]
            )
        }
        interpolation01.forEachIndexed { _, intOffset ->
            Point(offset = intOffset, labelOffset = IntOffset(-60, 0))
        }
        interpolation12.forEachIndexed { _, intOffset ->
            Point(offset = intOffset, labelOffset = IntOffset(0, -40))
        }
        interpolation01.forEachIndexed { index, intOffset ->
            Line(
                vertice0 = { intOffset },
                vertice1 = { interpolation12[index] })
        }
    }
}

@Composable
private fun QuadraticBezierCurveInterpolated2(
    offsetList: List<IntOffset>,
    setOffset: (index: Int, offset: IntOffset) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(RectangleShape)
    ) {
        Line(vertice0 = { offsetList[0] }, vertice1 = { offsetList[1] })
        Line(vertice0 = { offsetList[1] }, vertice1 = { offsetList[2] })
        QuadraticBezier(
            point0 = offsetList[0].toOffset(),
            point1 = offsetList[1].toOffset(),
            point2 = offsetList[2].toOffset(),
            style = Stroke(
                width = 3f,
                pathEffect = PathEffect.dashPathEffect(listOf(3f, 5f).toFloatArray())
            )
        )
        repeat(3) { index ->
            DraggablePoint(
                offset = { offsetList[index] },
                setOffset = { setOffset(index, it) },
                color = colorList[index]
            )
        }
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
private fun Point(
    offset: IntOffset,
    labelOffset: IntOffset = IntOffset.Zero,
    labelText: String? = null
) {
    val radiusPx = with(LocalDensity.current) { PointRadius.toPx() }
    val textMeasure = rememberTextMeasurer()
    val displaySmall = MaterialTheme.typography.labelSmall
    Canvas(modifier = Modifier
        .size(PointRadius)
        .offset { offset - IntOffset(radiusPx.roundToInt() / 2, radiusPx.roundToInt() / 2) }
        .background(Color.Black, shape = CircleShape)
    ) {
        if (labelText != null) {
            val text = buildAnnotatedString {
                withStyle(displaySmall.toSpanStyle()) {
                    append(labelText)
                }
            }
            val textLayoutResult = textMeasure.measure(text)
            drawText(textLayoutResult, topLeft = labelOffset.toOffset())
        }
    }
}
