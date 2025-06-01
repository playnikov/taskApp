package ru.example.taskapp.ui.components

import androidx.compose.foundation.border
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.example.taskapp.R
import ru.example.taskapp.ui.theme.colors.LocalExtendedColors

data class BottomNavItem(
    val route: String,
    val icon: Painter
)

@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    val items = listOf(
        BottomNavItem("projects", painterResource(R.drawable.ic_command)),
//        BottomNavItem("chats", painterResource(R.drawable.ic_chat)),
        BottomNavItem("calendar", painterResource(R.drawable.ic_calendar)),
        BottomNavItem("settings", painterResource(R.drawable.ic_settings))
    )

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val colors = LocalExtendedColors.current

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .border(
                width = 1.dp,
                color = colors.ming.copy(0.3f)
            )
    ) {
        NavigationBar {
            items.forEach { item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = item.icon,
                            contentDescription = null
                        )
                    },
                    label = null,
                    selected = currentRoute == item.route,
                    onClick = {
                        if (currentRoute != item.route) {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    colors = androidx.compose.material3.NavigationBarItemDefaults
                        .colors(
                            selectedIconColor = colors.azure,
                            unselectedIconColor = colors.ming,
                            indicatorColor = Color.Transparent
                        )
                )
            }
        }
    }

}