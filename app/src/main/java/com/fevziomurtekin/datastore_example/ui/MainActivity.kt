package com.fevziomurtekin.datastore_example.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fevziomurtekin.datastore_example.data.UserNameUiModel
import com.fevziomurtekin.datastore_example.ui.theme.DatastoretrainingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    private val userNameState: State<UserNameUiModel>
        @Composable get() = viewModel.userNameState.collectAsState()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DatastoretrainingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(
                        uiModel = userNameState.value,
                        onUpdateUserInfo = viewModel::updateUserName
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(
    modifier: Modifier = Modifier,
    uiModel: UserNameUiModel,
    onUpdateUserInfo: (name: String) -> Unit
) {
    var nameText by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!uiModel.isInitial) {
            Text(
                text = "Hello ${uiModel.userName}!",
                modifier = modifier
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        OutlinedTextField(
            value = nameText,
            onValueChange = { nameText = it },
            label = { Text("Name") }
        )
        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = { onUpdateUserInfo.invoke(nameText) }) {
            Text(text = "Update")
        }
    }

}
