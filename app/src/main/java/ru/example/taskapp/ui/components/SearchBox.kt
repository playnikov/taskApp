package ru.example.taskapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.example.taskapp.R
import ru.example.taskapp.ui.theme.colors.LocalExtendedColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBox(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Поиск"
) {
    val colors = LocalExtendedColors.current

    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_search),
                contentDescription = null
            )
        },
        shape = RoundedCornerShape(10.dp),
        singleLine = true,
        placeholder = {
            Text(text = placeholder)
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = colors.blue.copy(alpha = 0.4f),
            focusedBorderColor = colors.blue,
            errorBorderColor = Color.Red
        ),
        modifier = modifier
            .fillMaxWidth()
    )
}