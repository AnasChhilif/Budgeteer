package com.budgeteer.budgeteer.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import  androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.budgeteer.budgeteer.Greeting

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()

                val tabBarItems = listOf(
                    TabBarItem(title = "Home", selectedIcon = Icons.Filled.Home, unselectedIcon = Icons.Outlined.Home),
                    TabBarItem(title = "Alerts", selectedIcon = Icons.Filled.Notifications, unselectedIcon = Icons.Outlined.Notifications),
                    TabBarItem(title = "Settings", selectedIcon = Icons.Filled.Settings, unselectedIcon = Icons.Outlined.Settings),
                )
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingView(Greeting().greet())

                }
                BottomNavigationBar(navController, tabBarItems)
            }
        }
    }
}

@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        val tabBarItems = listOf(
            TabBarItem(title = "Home", selectedIcon = Icons.Filled.Home, unselectedIcon = Icons.Outlined.Home),
            TabBarItem(title = "Alerts", selectedIcon = Icons.Filled.Notifications, unselectedIcon = Icons.Outlined.Notifications),
            TabBarItem(title = "Settings", selectedIcon = Icons.Filled.Settings, unselectedIcon = Icons.Outlined.Settings),
        )
        GreetingView("Hello, Android!")
        BottomNavigationBar(navController = rememberNavController(), tabBarItems =tabBarItems )
    }
}

data class TabBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@Composable
fun BottomNavigationBar(navController: NavController, tabBarItems: List<TabBarItem>) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    Surface(
        modifier = Modifier.fillMaxWidth(),
    ) {
        NavigationBar {
            tabBarItems.forEachIndexed { index, tabBarItem ->
                NavigationBarItem(
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                        //navController.navigate(tabBarItem.title)
                    },
                    icon = {
                        Icon(
                            imageVector = if (selectedTabIndex == index) tabBarItem.selectedIcon else tabBarItem.unselectedIcon,
                            contentDescription = tabBarItem.title
                        )
                    },
                    label = {
                        Text(text = tabBarItem.title)
                    }

                )
            }
        }
    }
}

