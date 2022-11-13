package home.saied.beziercrves

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import home.saied.beziercrves.one.CubicBezierCurve
import home.saied.beziercrves.one.QuadraticBezierCurve
import home.saied.beziercrves.ui.theme.BezierCrvesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BezierCrvesTheme {
                // A surface container using the 'background' color from the theme
                CubicBezierCurve(
                    modifier = Modifier
                        .background(Color.LightGray)
                        .fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BezierCrvesTheme {
        Greeting("Android")
    }
}