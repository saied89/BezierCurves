package home.saied.beziercrves

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt

fun Density.toIntOffset(pair: Pair<Dp, Dp>): IntOffset =
    IntOffset(pair.first.toPx().roundToInt(), pair.second.toPx().roundToInt())