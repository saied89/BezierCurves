package home.saied.beziercrves

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import home.saied.beziercrves.two.BezierInterpolation
import home.saied.beziercrves.ui.theme.BezierCrvesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BezierCrvesTheme {
                // A surface container using the 'background' color from the theme
                BezierInterpolation(
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }
    }
}