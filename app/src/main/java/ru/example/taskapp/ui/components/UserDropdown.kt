package ru.example.taskapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import ru.example.taskapp.data.model.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDropdown(
    users: List<User>,
    selectedUser: User?,
    onUserSelected: (User) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Select user",
    enabled: Boolean = true
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        OutlinedTextField(
            value = selectedUser?.fullName() ?: "",
            onValueChange = {},
            readOnly = true,
            enabled = enabled,
            label = { Text(label) },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.clickable { expanded = true }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = enabled) { expanded = true }
        )

        DropdownMenu(
            expanded = expanded && enabled,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            if (users.isEmpty()) {
                DropdownMenuItem(
                    text = { Text("No users available") },
                    onClick = { expanded = false }
                )
            } else {
                users.forEach { user ->
                    DropdownMenuItem(
                        text = { user.fullName() },
                        onClick = {
                            onUserSelected(user)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

private fun User.fullName(): String = listOfNotNull(
    lastName,
    firstName,
    middleName
).joinToString(" ").trim()