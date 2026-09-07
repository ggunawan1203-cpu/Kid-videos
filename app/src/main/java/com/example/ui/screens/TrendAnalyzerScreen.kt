package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import com.example.data.model.ContentAnalysis
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.KidsCreatorViewModel
import com.example.ui.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrendAnalyzerScreen(
    viewModel: KidsCreatorViewModel,
    uiState: UiState,
    modifier: Modifier = Modifier
) {
    val analysis = uiState.analysisResult ?: ContentAnalysis(
        topic = uiState.conceptInput,
        targetAgeGroup = uiState.selectedAgeGroup,
        estimatedViralScore = 95,
        retentionHook = "3 Detik Pertama: Mobil menabrak balon raksasa dan meletus mengeluarkan bintang bercahaya warna-warni disertai suara 'Pop!' ceria.",
        emotionalTrigger = "Rasa takjub, penasaran visual, dan kegembiraan instan saat warna baru terungkap.",
        visualRecommendations = listOf(
            "Pencahayaan terang maksimal dengan palet warna primer kontras (Merah, Kuning, Biru)",
            "Karakter memiliki proporsi mata besar (kontak mata hangat dan bersahabat bagi balita)",
            "Gerakan dinamis vertikal setiap 2 detik agar anak tidak mudah bosan"
        ),
        audioSoundtrackIdea = "Irama melodi xylophone cepat 125 BPM dengan efek kartun 'Boing-Boing' dan 'Tadaaa!'",
        coppaSafetyCompliant = true,
        potentialReasons = "Format tebak warna memiliki tingkat replay (tonton ulang) hingga 3x lipat dibanding video naratif biasa karena anak suka menebak berulang kali."
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 80.dp, top = 8.dp)
    ) {
        // Header
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEADDFF)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFD0BCFF)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(Color(0xFFD0BCFF), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Analytics,
                            contentDescription = null,
                            tint = Color(0xFF381E72)
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text(
                            text = "Analisis Penonton Terbanyak",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF21005D)
                        )
                        Text(
                            text = "Dioptimalkan dari benchmarking video viral anak",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF49454F)
                        )
                    }
                }
            }
        }

        // Viral Score Card
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
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Skor Potensi Viral Anak",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = "Berdasarkan algoritma YouTube Shorts & Reels",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Surface(
                            color = Color(0xFF10B981).copy(alpha = 0.15f),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "${analysis.estimatedViralScore} / 100",
                                color = Color(0xFF10B981),
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }

                    LinearProgressIndicator(
                        progress = { analysis.estimatedViralScore / 100f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(RoundedCornerShape(5.dp)),
                        color = Color(0xFF10B981),
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )

                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = Color(0xFF10B981),
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Kategori Potensi Super Tinggi: Berpeluang masif ditonton berulang kali (High Looping Retention).",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }

        // Retention Hook (Golden 3 Seconds)
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Timer,
                            contentDescription = null,
                            tint = Color(0xFFEF4444)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Hook 3 Detik Pertama (Wajib Visual & Suara)",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }

                    Text(
                        text = analysis.retentionHook,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Mood,
                            contentDescription = null,
                            tint = Color(0xFFF59E0B)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Pemicu Emosi Anak:",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                        )
                    }

                    Text(
                        text = analysis.emotionalTrigger,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Visual & Audio Tips
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Palette,
                            contentDescription = null,
                            tint = Color(0xFF8B5CF6)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Panduan Visual & Audio Veo 3",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }

                    analysis.visualRecommendations.forEach { tip ->
                        Row(
                            verticalAlignment = Alignment.Top,
                            modifier = Modifier.padding(vertical = 2.dp)
                        ) {
                            Text(
                                text = "•",
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text(
                                text = tip,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Surface(
                        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.MusicNote,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Audio Rekomendasi: ${analysis.audioSoundtrackIdea}",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                }
            }
        }

        // Action Button: Move to Veo 3 Studio
        item {
            Button(
                onClick = { viewModel.navigateTo(AppScreen.VEO_STUDIO) },
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("go_to_veo_studio_button")
            ) {
                Icon(imageVector = Icons.Default.Videocam, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Lanjut ke Studio Video Veo 3 (9:16)",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
