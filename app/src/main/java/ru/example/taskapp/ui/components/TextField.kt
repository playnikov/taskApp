package ru.example.taskapp.ui.components

import android.graphics.drawable.Icon
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.example.taskapp.R
import ru.example.taskapp.ui.theme.TaskAppTheme
import ru.example.taskapp.ui.theme.colors.LocalExtendedColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isPassword: Boolean = false,
    isPhone: Boolean = false,
    leadingIcon: Painter?=null,
    contentDescription: String?=null
) {
    val colors = LocalExtendedColors.current
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = colors.ming.copy(alpha = 0.5f)) },
        leadingIcon = if (leadingIcon != null) { // Conditional leadingIcon
            {
                Icon(
                    painter = leadingIcon,
                    contentDescription = contentDescription
                )
            }
        } else {
            null
        },
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = colors.blue.copy(alpha = 0.4f),
            focusedBorderColor = colors.blue,
            errorBorderColor = Color.Red
        ),
        singleLine = true,
        visualTransformation =
            if (isPassword) PasswordVisualTransformation()
            else VisualTransformation.None,
        keyboardOptions =
            if (isPassword) KeyboardOptions(keyboardType = KeyboardType.Password)
            else if (isPhone) KeyboardOptions(keyboardType = KeyboardType.Phone)
            else KeyboardOptions(keyboardType = KeyboardType.Email),
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewTextField() {
    TaskAppTheme {
        TextField(
            value = "",
            onValueChange = {},
            placeholder = "1234",
            isPassword = false,
            isPhone = false
        )
    }
}