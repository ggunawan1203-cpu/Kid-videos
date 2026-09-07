package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.ProjectEntity
import com.example.data.model.TrendReference
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.KidsCreatorViewModel
import com.example.ui.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: KidsCreatorViewModel,
    uiState: UiState,
    savedProjects: List<ProjectEntity>,
    modifier: Modifier = Modifier
) {
    val ageOptions = listOf("1-4 th (Balita)", "3-6 th (TK)", "5-9 th (Anak)")
    val styleOptions = listOf("3D Pixar", "Claymation", "2D Anime", "Storybook")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 80.dp, top = 8.dp)
    ) {
        // Hero Card (Vibrant Palette Theme)
        item {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEADDFF)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFD0BCFF)),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = Color(0xFFD0BCFF),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "🎨 STUDIO KONTEN ANAK 9:16",
                                color = Color(0xFF381E72),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }

                        // API Key status pill
                        Surface(
                            color = if (uiState.isKeyConfigured) Color(0xFF22C55E) else Color(0xFFF59E0B),
                            shape = CircleShape,
                            modifier = Modifier.clickable { viewModel.showApiKeyDialog(true) }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .background(Color.White, CircleShape)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (uiState.isKeyConfigured) "Gemini Pro Aktif" else "Atur API Key",
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Text(
                        text = "Otomatisasi Video Viral Anak dengan Veo 3",
                        color = Color(0xFF21005D),
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold)
                    )

                    Text(
                        text = "Analisis potensi penonton terbanyak dari tren viral, ciptakan video 9:16, judul memikat, dan hashtag SEO dalam hitungan detik.",
                        color = Color(0xFF49454F),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        // Pipeline Running Progress Card
        if (uiState.isRunningPipeline) {
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Sedang Memproses AI Otomatis...",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 3.dp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        LinearProgressIndicator(
                            progress = { uiState.currentStepNumber / 4f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = MaterialTheme.colorScheme.primary
                        )

                        Text(
                            text = uiState.currentStepMessage,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
        }

        // Main Creation Card
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFCAC4D0)),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            color = Color(0xFFF3EDF7),
                            shape = CircleShape,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = null,
                                    tint = Color(0xFF6750A4),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Buat Video Baru",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF1C1B1F)
                        )
                    }

                    // Topic Input
                    OutlinedTextField(
                        value = uiState.conceptInput,
                        onValueChange = { viewModel.setConceptInput(it) },
                        label = { Text("Topik / Ide Video Anak") },
                        placeholder = { Text("Contoh: Rexy Astronaut, Petualangan Balon Warna") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("concept_input_field"),
                        shape = RoundedCornerShape(14.dp),
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Lightbulb, contentDescription = null, tint = Color(0xFF6750A4))
                        }
                    )

                    // Target Age Chips
                    Text(
                        text = "Target Usia Anak:",
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                        color = Color(0xFF1C1B1F)
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        ageOptions.forEach { age ->
                            val isSelected = uiState.selectedAgeGroup.startsWith(age.take(5))
                            FilterChip(
                                selected = isSelected,
                                onClick = { viewModel.selectAgeGroup(age) },
                                label = { Text(age, fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = Color(0xFFEADDFF),
                                    selectedLabelColor = Color(0xFF21005D)
                                ),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    // Visual Style Chips
                    Text(
                        text = "Gaya Visual Animasi (Veo 3):",
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                        color = Color(0xFF1C1B1F)
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        styleOptions.forEach { style ->
                            val isSelected = uiState.selectedStyle.contains(style)
                            FilterChip(
                                selected = isSelected,
                                onClick = { viewModel.selectStyle(style) },
                                label = { Text(style, fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = Color(0xFFEADDFF),
                                    selectedLabelColor = Color(0xFF21005D)
                                ),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    // Aspect Ratio indicator
                    Surface(
                        color = Color(0xFFF3EDF7),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.PhoneAndroid,
                                    contentDescription = null,
                                    tint = Color(0xFF6750A4),
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Rasio Video: 9:16 Vertikal (Shorts / Reels / TikTok)",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF1C1B1F)
                                )
                            }
                            Text(
                                text = "Terkunci",
                                fontSize = 10.sp,
                                color = Color(0xFF6750A4),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // One-Tap Full Automated Pipeline Button
                    Button(
                        onClick = { viewModel.runFullPipeline() },
                        enabled = !uiState.isRunningPipeline,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .testTag("generate_pipeline_button"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6750A4),
                            contentColor = Color.White
                        )
                    ) {
                        Icon(imageVector = Icons.Default.PlayCircleFilled, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Jalankan AI Otomatis (Analisis + Veo 3 + SEO)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }

        // Trending References Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Referensi Video Sedang Tren 🔥",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Format terbukti memiliki jutaan penonton",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                TextButton(onClick = { viewModel.navigateTo(AppScreen.TREND_ANALYSIS) }) {
                    Text("Lihat Semua")
                }
            }
        }

        // Trending References Horizontal Cards
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 4.dp)
            ) {
                items(viewModel.trendingReferences) { trend ->
                    TrendCardItem(
                        trend = trend,
                        isSelected = uiState.selectedTrend?.id == trend.id,
                        onSelect = {
                            viewModel.selectTrend(trend)
                        }
                    )
                }
            }
        }

        // Recent Saved Projects Section
        if (savedProjects.isNotEmpty()) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Proyek Tersimpan (${savedProjects.size})",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    TextButton(onClick = { viewModel.navigateTo(AppScreen.PROJECT_LIBRARY) }) {
                        Text("Buka Koleksi")
                    }
                }
            }

            items(savedProjects.take(3)) { project ->
                SavedProjectMiniCard(
                    project = project,
                    onOpen = { viewModel.loadProjectIntoStudio(project) }
                )
            }
        }
    }
}

@Composable
fun TrendCardItem(
    trend: TrendReference,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFFEADDFF) else Color.White
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = if (isSelected) 1.5.dp else 1.dp,
            color = if (isSelected) Color(0xFF6750A4) else Color(0xFFCAC4D0)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 3.dp else 1.dp),
        modifier = Modifier
            .width(220.dp)
            .clickable { onSelect() }
            .testTag("trend_card_${trend.id}")
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Surface(
                color = if (isSelected) Color(0xFF6750A4) else Color(0xFFF3EDF7),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = trend.category,
                    color = if (isSelected) Color.White else Color(0xFF6750A4),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                )
            }

            Text(
                text = trend.title,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = if (isSelected) Color(0xFF21005D) else Color(0xFF1C1B1F),
                maxLines = 2
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Visibility,
                    contentDescription = null,
                    tint = Color(0xFF6750A4),
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = trend.estimatedViews,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF6750A4)
                )
            }

            Text(
                text = "Hook: \"${trend.viralHook}\"",
                fontSize = 11.sp,
                color = Color(0xFF49454F),
                maxLines = 2
            )
        }
    }
}

@Composable
fun SavedProjectMiniCard(
    project: ProjectEntity,
    onOpen: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFCAC4D0)),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onOpen() }
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = project.title,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${project.category} • Skor Viral: ${project.viralScore}%",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Buka",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}
