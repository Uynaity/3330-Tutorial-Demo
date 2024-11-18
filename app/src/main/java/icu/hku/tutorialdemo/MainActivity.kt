package icu.hku.tutorialdemo

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import icu.hku.tutorialdemo.ui.theme.TutorialDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Get the data from the intent
        val data: Uri? = intent?.data

        setContent {
            TutorialDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        data = data ?: Uri.EMPTY,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun GetToken(key: String, secret: String, context: Context) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "STEP 1: Get Token",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(16.dp)
        )
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Key: $key", maxLines = 1, overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "Secret: $secret", maxLines = 1, overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Button(onClick = {
                // Open the browser to get the token
                val url = Uri.parse("https://<your_function_name>.azurewebsites.net/api/twitter/oauth")
                val intent = Intent(Intent.ACTION_VIEW, url)
                context.startActivity(intent)
            }) {
                Text("Get Token")
            }
        }
    }
}

@Composable
fun SendPost(key: String, secret: String, context: Context) {
    var content by remember { mutableStateOf("") }

    Card(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "STEP 2: Send Post",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(16.dp)
        )
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = content,
                onValueChange = { newValue -> content = newValue },
                label = { Text("Post Content") },
                maxLines = 5,
                enabled = key.isNotEmpty()
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Button(enabled = key.isNotEmpty(), onClick = {
                //TODO: Send post
            }) {
                Text("Send Post")
            }
        }
    }
}

@Composable
fun Greeting(data: Uri, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    // Set the key and secret from the query parameters
    var key by remember { mutableStateOf(data.getQueryParameter("access_token") ?: "") }
    var secret by remember { mutableStateOf(data.getQueryParameter("access_token_secret") ?: "") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp, 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        GetToken(key, secret, context)
        Spacer(modifier = Modifier.padding(16.dp))
        SendPost(key, secret, context)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TutorialDemoTheme {
        Greeting(Uri.EMPTY)
    }
}