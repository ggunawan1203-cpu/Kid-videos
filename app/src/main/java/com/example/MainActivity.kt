package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.ApiKeyDialog
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.KidsCreatorViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: KidsCreatorViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val savedProjects by viewModel.savedProjects.collectAsStateWithLifecycle()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Surface(
                                        color = Color(0xFFD0BCFF),
                                        shape = CircleShape,
                                        modifier = Modifier.size(40.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(
                                                imageVector = Icons.Default.ChildCare,
                                                contentDescription = null,
                                                tint = Color(0xFF381E72),
                                                modifier = Modifier.size(24.dp)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Text(
                                            text = "KidCreator AI",
                                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                            color = MaterialTheme.colorScheme.onBackground
                                        )
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(6.dp)
                                                    .background(
                                                        if (uiState.isKeyConfigured) Color(0xFF22C55E) else Color(0xFFF59E0B),
                                                        CircleShape
                                                    )
                                            )
                                            Text(
                                                text = if (uiState.isKeyConfigured) "GEMINI PRO ACTIVE" else "SET GEMINI KEY",
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Bold,
                                                letterSpacing = 0.8.sp,
                                                color = Color(0xFF49454F)
                                            )
                                        }
                                    }
                                }
                            },
                            actions = {
                                // API Key Quick Action Button
                                Surface(
                                    shape = CircleShape,
                                    color = Color(0xFFF3EDF7),
                                    modifier = Modifier
                                        .size(38.dp)
                                        .clip(CircleShape)
                                ) {
                                    IconButton(
                                        onClick = { viewModel.showApiKeyDialog(true) },
                                        modifier = Modifier.testTag("top_bar_api_key_button")
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Key,
                                            contentDescription = "Kelola API Key",
                                            tint = if (uiState.isKeyConfigured) Color(0xFF6750A4) else Color(0xFFF59E0B),
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(6.dp))

                                // Settings button
                                Surface(
                                    shape = CircleShape,
                                    color = Color(0xFFF3EDF7),
                                    modifier = Modifier
                                        .size(38.dp)
                                        .clip(CircleShape)
                                ) {
                                    IconButton(
                                        onClick = { viewModel.navigateTo(AppScreen.SETTINGS) },
                                        modifier = Modifier.testTag("top_bar_settings_button")
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Settings,
                                            contentDescription = "Pengaturan",
                                            tint = Color(0xFF1C1B1F),
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color(0xFFFEF7FF)
                            )
                        )
                    },
                    bottomBar = {
                        NavigationBar(
                            containerColor = Color(0xFFF3EDF7),
                            tonalElevation = 0.dp
                        ) {
                            val navColors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFF21005D),
                                selectedTextColor = Color(0xFF21005D),
                                indicatorColor = Color(0xFFEADDFF),
                                unselectedIconColor = Color(0xFF49454F),
                                unselectedTextColor = Color(0xFF49454F)
                            )

                            NavigationBarItem(
                                selected = uiState.currentScreen == AppScreen.HOME,
                                onClick = { viewModel.navigateTo(AppScreen.HOME) },
                                icon = { Icon(Icons.Default.Home, contentDescription = "Beranda") },
                                label = { Text("Beranda", fontSize = 11.sp, fontWeight = FontWeight.Medium) },
                                colors = navColors,
                                modifier = Modifier.testTag("nav_home")
                            )

                            NavigationBarItem(
                                selected = uiState.currentScreen == AppScreen.TREND_ANALYSIS,
                                onClick = { viewModel.navigateTo(AppScreen.TREND_ANALYSIS) },
                                icon = { Icon(Icons.AutoMirrored.Filled.TrendingUp, contentDescription = "Tren") },
                                label = { Text("Tren", fontSize = 11.sp, fontWeight = FontWeight.Medium) },
                                colors = navColors,
                                modifier = Modifier.testTag("nav_trends")
                            )

                            NavigationBarItem(
                                selected = uiState.currentScreen == AppScreen.VEO_STUDIO,
                                onClick = { viewModel.navigateTo(AppScreen.VEO_STUDIO) },
                                icon = { Icon(Icons.Default.Videocam, contentDescription = "Veo 3") },
                                label = { Text("Studio 9:16", fontSize = 11.sp, fontWeight = FontWeight.Medium) },
                                colors = navColors,
                                modifier = Modifier.testTag("nav_veo_studio")
                            )

                            NavigationBarItem(
                                selected = uiState.currentScreen == AppScreen.METADATA_SEO,
                                onClick = { viewModel.navigateTo(AppScreen.METADATA_SEO) },
                                icon = { Icon(Icons.Default.Tag, contentDescription = "SEO") },
                                label = { Text("SEO Tag", fontSize = 11.sp, fontWeight = FontWeight.Medium) },
                                colors = navColors,
                                modifier = Modifier.testTag("nav_metadata")
                            )

                            NavigationBarItem(
                                selected = uiState.currentScreen == AppScreen.PROJECT_LIBRARY,
                                onClick = { viewModel.navigateTo(AppScreen.PROJECT_LIBRARY) },
                                icon = { Icon(Icons.Default.Folder, contentDescription = "Koleksi") },
                                label = { Text("Koleksi", fontSize = 11.sp, fontWeight = FontWeight.Medium) },
                                colors = navColors,
                                modifier = Modifier.testTag("nav_projects")
                            )
                        }
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        when (uiState.currentScreen) {
                            AppScreen.HOME -> HomeScreen(
                                viewModel = viewModel,
                                uiState = uiState,
                                savedProjects = savedProjects
                            )
                            AppScreen.TREND_ANALYSIS -> TrendAnalyzerScreen(
                                viewModel = viewModel,
                                uiState = uiState
                            )
                            AppScreen.VEO_STUDIO -> VeoStudioScreen(
                                viewModel = viewModel,
                                uiState = uiState
                            )
                            AppScreen.METADATA_SEO -> MetadataScreen(
                                viewModel = viewModel,
                                uiState = uiState
                            )
                            AppScreen.PROJECT_LIBRARY -> ProjectsScreen(
                                viewModel = viewModel,
                                projects = savedProjects
                            )
                            AppScreen.SETTINGS -> SettingsScreen(
                                viewModel = viewModel,
                                uiState = uiState
                            )
                        }
                    }

                    // API Key Dialog
                    if (uiState.showApiKeyDialog) {
                        ApiKeyDialog(
                            currentCustomKey = viewModel.uiState.value.conceptInput.let { "" },
                            isKeyConfigured = uiState.isKeyConfigured,
                            statusMessage = uiState.apiKeyStatusMessage,
                            onSave = { key -> viewModel.saveApiKey(key) },
                            onTestConnection = { key -> viewModel.testConnection(key) },
                            onClear = { viewModel.clearApiKey() },
                            onDismiss = { viewModel.showApiKeyDialog(false) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(text = "Hello $name!", modifier = modifier)
}
