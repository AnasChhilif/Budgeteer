package com.budgeteer.budgeteer.android

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.budgeteer.budgeteer.Greeting
import com.budgeteer.budgeteer.android.services.BudgeteerApiService

class MainActivity : ComponentActivity() {

    private lateinit var resultReceiver: BroadcastReceiver
    var apiResult by mutableStateOf<String?>(null)

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resultReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                apiResult = intent?.getStringExtra("result")
                println(apiResult)
            }
        }
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()

                val tabBarItems = listOf(
                    TabBarItem(title = "Home", selectedIcon = Icons.Filled.Home, unselectedIcon = Icons.Outlined.Home),
                    TabBarItem(title = "Friends", selectedIcon = Icons.Filled.Person, unselectedIcon = Icons.Outlined.Person),
                    TabBarItem(title = "", selectedIcon = Icons.Filled.AddCircle, unselectedIcon = Icons.Outlined.AddCircle, size = 76.dp),
                    TabBarItem(title = "Alerts", selectedIcon = Icons.Filled.Notifications, unselectedIcon = Icons.Outlined.Notifications),
                    TabBarItem(title = "Settings", selectedIcon = Icons.Filled.Settings, unselectedIcon = Icons.Outlined.Settings),
                )

                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(navController, tabBarItems)
                    }
                ) {
                    MyScreen(apiResult = apiResult, onStartService = {
                        val intent = Intent(this@MainActivity, BudgeteerApiService::class.java)
                        startService(intent)
                    })
                }
            }
        }
    }
}

@Composable
fun GreetingView(text: String) {
    Text(text = text,
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier.padding(16.dp),
    )
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
        GreetingView("Budgeteer")
        BottomNavigationBar(navController = rememberNavController(), tabBarItems =tabBarItems )
    }
}

data class TabBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val size: Dp = 24.dp,

)

@Composable
fun BottomNavigationBar(navController: NavController, tabBarItems: List<TabBarItem>) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.Gray
    ) {
        NavigationBar {
            tabBarItems.forEachIndexed { index, tabBarItem ->
                val iconSize =
                    if (index == tabBarItems.indexOfFirst { it.title == "" }) 76.dp else 24.dp
                val marginBottom = when (tabBarItem.title) {
                    "" -> 32.dp // Adjust the space for the "Add" item
                    else -> 0.dp // Default space for other items
                }
                val verticalAlignment = when (tabBarItem.title) {
                    "" -> Alignment.Top // Align empty icon to the top
                    else -> Alignment.Bottom // Align other icons to the bottom
                }

                NavigationBarItem(
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                        //navController.navigate(tabBarItem.title)
                    },
                    icon = {
                        Icon(
                            imageVector = if (selectedTabIndex == index) tabBarItem.selectedIcon else tabBarItem.unselectedIcon,
                            contentDescription = tabBarItem.title,
                            modifier = Modifier
                                .size(iconSize)
                                .align(verticalAlignment)
                                .padding(bottom = marginBottom)
                                .align(Alignment.CenterVertically)


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



@Composable
fun CardMinimalExample(modifier: Modifier = Modifier) {
    val items = listOf(
        Pair("Brahim", "39"),
        Pair("Brahim", "39"),
        Pair("Brahim", "39")
    )

    Box(modifier = modifier.padding(6.dp)) {
        // Big card at the bottom
        BigCard(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .size(width = 300.dp, height = 750.dp)

        )

        // Column with small cards
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items.forEach { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    border = BorderStroke(1.5.dp, Color.Black),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.first,
                            style = MaterialTheme.typography.titleLarge,
                        )
                        Text(
                            text = item.second,
                            style = MaterialTheme.typography.titleLarge,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BigCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(6.dp),
        border = BorderStroke(1.dp, Color.Black),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

        }
    }
}

@Composable
fun MyScreen(apiResult: String?, onStartService: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        GreetingView("Budgeteer")
        Text(text = apiResult ?: "No data", modifier = Modifier.padding(16.dp))
        //CardMinimalExample(modifier = Modifier.fillMaxWidth())
        Button(onClick = onStartService) {
            Text("Start Service")
        }
        apiResult?.let {
            Text(text = it, modifier = Modifier.padding(16.dp))
        }
    }
}

