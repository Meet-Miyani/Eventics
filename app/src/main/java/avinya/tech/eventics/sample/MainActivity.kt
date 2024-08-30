package avinya.tech.eventics.sample

import android.content.Intent
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import avinya.tech.eventics.EventicsLogger
import avinya.tech.eventics.sample.compose.ComposeActivity
import avinya.tech.eventics.sample.compose.ui.theme.EventicsTheme
import avinya.tech.eventics.sample.views.ViewActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        EventicsLogger.enableDebugMode(true)
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
                            val intent = Intent(this@MainActivity, ComposeActivity::class.java)
                            startActivity(intent)
                        }) {
                            Text(text = "Compose Activity")
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = {
                            val intent = Intent(this@MainActivity, ViewActivity::class.java)
                            startActivity(intent)
                        }) {
                            Text(text = "Views Activity")
                        }
                    }

                }
            }
        }
    }
}