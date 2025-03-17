package com.example.interviewprep.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.interviewprep.presentation.viewmodel.UserViewModel
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.interviewprep.data.model.User

@Composable
fun UserScreen(viewModel: UserViewModel = hiltViewModel()) {
    val users by viewModel.users.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    Column {
        Text(text = "User List", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(16.dp))

        if (!errorMessage.isNullOrEmpty()) {
            Text(
                text = errorMessage!!,
                color = Color.Red,
                modifier = Modifier.padding(16.dp)
            )
        }

        Button(onClick = {
            val newUser = User(
                id = users.size + 1,
                name = "New User",
                email = "newuser@example.com",
                avatar = "https://robohash.org/${users.size + 1}.png"
            )
            viewModel.createUser(newUser)
        }, modifier = Modifier.padding(16.dp)) {
            Text("Add User")
        }

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }
        if (users.isEmpty()) {
            Text(text = "No users available. Check your internet connection.", modifier = Modifier.padding(16.dp))
        } else {
            LazyColumn {
                items(users, key = { it.id }) { user ->
                    UserCard(user, viewModel)
//                    Card(modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(8.dp)) {
//                        val avatarUrl = user.avatar.takeIf { !it.isNullOrEmpty() } ?: "https://robohash.org/${user.id}}.png"
//                        Row(modifier = Modifier.padding(16.dp)) {
//                            Image(
//                                painter = rememberAsyncImagePainter(avatarUrl),
//                                contentDescription = "User Avatar",
//                                modifier = Modifier
//                                    .size(48.dp)
//                                    .clip(CircleShape)
//                            )
//                            Spacer(modifier = Modifier.width(8.dp))
//                            Column {
//                                Text(text = user.name, style = MaterialTheme.typography.headlineMedium)
//                                Text(text = user.email, style = MaterialTheme.typography.bodyMedium )
//                            }
//                        }
//                        Row {
//                            Button(onClick = {
//                                viewModel.updateUser(user.id, user.copy(name = "Updated User"))
//                            }) {
//                                Text("Update")
//                            }
//                            Spacer(modifier = Modifier.width(8.dp))
//
//                            Button(onClick = {
//                                viewModel.deleteUser(user.id)
//                            }) {
//                                Text("Delete")
//                            }
//                        }
//
//                    }
                }
            }
        }
    }

}

@Composable
fun UserCard(user: User, viewModel: UserViewModel) {
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberAsyncImagePainter(user.avatar),
                contentDescription = "User Avatar",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = user.name, style = MaterialTheme.typography.headlineSmall)
                Text(text = user.email, style = MaterialTheme.typography.bodyMedium)
            }
        }
        Row {
            Button(onClick = {
                viewModel.updateUser(user.id, user.copy(name = "Updated User"))
            }) {
                Text("Update")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                viewModel.deleteUser(user.id)
            }) {
                Text("Delete")
            }
        }
    }
}
