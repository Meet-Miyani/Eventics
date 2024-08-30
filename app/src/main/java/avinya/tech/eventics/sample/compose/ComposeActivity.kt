package avinya.tech.eventics.sample.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import avinya.tech.eventics.put
import avinya.tech.eventics.sample.events.core.MyEventManager
import avinya.tech.eventics.sample.events.EventHome
import avinya.tech.eventics.sample.compose.ui.theme.EventicsTheme

class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EventicsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }

            val eventHome = EventHome(
                message = "Home screen is now opened"
            )
            MyEventManager.get(this).log(eventHome)

            MyEventManager(this).log(eventName = "FIRE_APP_DYNAMIC") {
                put("This" to 12.3)
            }
            MyEventManager(this).log(
                eventName = "FIRE_APP",
                properties = mapOf("Meet" to true)
            )
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
    EventicsTheme {
        Greeting("Android")
    }
}