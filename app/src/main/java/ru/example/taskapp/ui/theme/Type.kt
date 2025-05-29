package ru.example.taskapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.example.taskapp.R

val notoSansDisplay = FontFamily(
    Font(R.font.noto_sans_display)
)

val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = notoSansDisplay,
        fontSize = 38.sp,
        fontWeight = FontWeight.Bold
    ),
    titleSmall = TextStyle(
        fontFamily = notoSansDisplay,
        fontSize = 23.sp,
        fontWeight = FontWeight.Medium
    ),
    displayMedium = TextStyle(
        fontFamily = notoSansDisplay,
        fontSize = 17.sp,
        fontWeight = FontWeight.SemiBold
    ),
    bodySmall = TextStyle(
        fontFamily = notoSansDisplay,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal
    ),
    bodyMedium = TextStyle(
        fontFamily = notoSansDisplay,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold
    ),

    labelSmall = TextStyle(
        fontFamily = notoSansDisplay,
        fontSize = 10.sp,
        fontWeight = FontWeight.Light
    )
)

