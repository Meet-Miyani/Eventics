package avinya.tech.eventics.sample.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import avinya.tech.eventics.put
import avinya.tech.eventics.sample.compose.ui.theme.EventicsTheme
import avinya.tech.eventics.sample.events.AppEvents
import avinya.tech.eventics.sample.events.EventProductClick
import avinya.tech.eventics.sample.events.core.MyEventManager
import avinya.tech.eventics.sample.model.Product

class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EventicsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Button(onClick = {
                            MyEventManager.get(this@ComposeActivity).log(AppEvents.SIMPLE_EVENT)
                        }) {
                            Text(text = "Simple event without Parameters")
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = {
                            MyEventManager.get(this@ComposeActivity)
                                .log(eventName = AppEvents.EVENT_ONE) {
                                    put("PropertyOne" to 1)
                                    put("PropertyTwo" to 2)
                                    // or
                                    put("PropertyThree", 3)
                                    put("PropertyFour", 4)
                                }
                        }) {
                            Text(text = "Dynamic Event with Lambda")
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = {
                            MyEventManager.get(this@ComposeActivity).log(
                                eventName = AppEvents.EVENT_ONE,
                                properties = mapOf(
                                    "PropertyOne" to 1,
                                    "PropertyTwo" to 2,
                                )
                            )
                        }) {
                            Text(text = "Dynamic Event with Parameters")
                        }
                        Button(onClick = {
                            val product = Product(
                                id = 123,
                                name = "Test Product",
                                price = 12.34,
                                quantity = 1
                            )
                            val eventProductClick = EventProductClick(product)
                            MyEventManager.get(this@ComposeActivity).log(eventProductClick)
                        }) {
                            Text(text = "Event with Data class")
                        }
                    }
                }
            }
            MyEventManager(this).log(eventName = "FIRE_APP_DYNAMIC") {
                put("This" to 12.3)
            }
            MyEventManager(this).log(
                eventName = "FIRE_APP",
                properties = mapOf("Meet" to true)
            )
        }

        // Logging Event
        MyEventManager.get(this).log(AppEvents.VIEW_COMPOSE_SCREEN)
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