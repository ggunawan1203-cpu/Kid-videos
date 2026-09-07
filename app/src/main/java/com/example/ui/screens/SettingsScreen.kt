package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.viewmodel.KidsCreatorViewModel
import com.example.ui.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: KidsCreatorViewModel,
    uiState: UiState,
    modifier: Modifier = Modifier
) {
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
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .background(Color(0xFFD0BCFF), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = null,
                            tint = Color(0xFF381E72)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Pengaturan & Integrasi API",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF21005D)
                        )
                        Text(
                            text = "Konfigurasi Gemini API pribadi & Veo 3",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF49454F)
                        )
                    }
                }
            }
        }

        // Gemini API Key Management
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Key,
                                contentDescription = null,
                                tint = Color(0xFF6750A4)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Kunci API Gemini Pribadi",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = Color(0xFF1C1B1F)
                            )
                        }

                        Surface(
                            color = if (uiState.isKeyConfigured) Color(0xFF22C55E).copy(alpha = 0.15f) else Color(0xFFB3261E).copy(alpha = 0.15f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = if (uiState.isKeyConfigured) "Terhubung" else "Belum Diatur",
                                color = if (uiState.isKeyConfigured) Color(0xFF22C55E) else Color(0xFFB3261E),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Text(
                        text = "Aplikasi ini menggunakan Gemini API pribadi Anda untuk menganalisis tren video anak, merancang prompt Veo 3, dan membuat paket metadata (judul, deskripsi, hashtag).",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF49454F)
                    )

                    Button(
                        onClick = { viewModel.showApiKeyDialog(true) },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6750A4),
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("manage_api_key_button")
                    ) {
                        Icon(imageVector = Icons.Default.VpnKey, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(if (uiState.isKeyConfigured) "Ubah / Uji API Key" else "Masukkan API Key Pribadi", fontWeight = FontWeight.Bold)
                    }

                    if (!uiState.apiKeyStatusMessage.isNullOrBlank()) {
                        Text(
                            text = uiState.apiKeyStatusMessage,
                            fontSize = 11.sp,
                            color = Color(0xFF6750A4)
                        )
                    }
                }
            }
        }

        // Active AI Models Configuration
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
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Konfigurasi Model AI",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFF1C1B1F)
                    )

                    ListItem(
                        headlineContent = { Text("Model Analisis & Metadata", fontWeight = FontWeight.SemiBold) },
                        supportingContent = { Text("Google Gemini 2.5 Flash (Super cepat & kreatif)") },
                        leadingContent = {
                            Icon(Icons.Default.Bolt, contentDescription = null, tint = Color(0xFF6750A4))
                        }
                    )

                    HorizontalDivider(color = Color(0xFFF3EDF7))

                    ListItem(
                        headlineContent = { Text("Model Generator Video AI", fontWeight = FontWeight.SemiBold) },
                        supportingContent = { Text("Google Veo 3 (veo-3.1-fast-generate-preview)") },
                        leadingContent = {
                            Icon(Icons.Default.VideoCameraBack, contentDescription = null, tint = Color(0xFF6750A4))
                        }
                    )

                    HorizontalDivider(color = Color(0xFFF3EDF7))

                    ListItem(
                        headlineContent = { Text("Format & Rasio Video", fontWeight = FontWeight.SemiBold) },
                        supportingContent = { Text("9:16 Vertikal (YouTube Shorts, Reels, TikTok)") },
                        leadingContent = {
                            Icon(Icons.Default.AspectRatio, contentDescription = null, tint = Color(0xFF6750A4))
                        }
                    )
                }
            }
        }

        // COPPA & Kids Safety Guidelines
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
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = Color(0xFF22C55E)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Standar Keamanan Konten Anak (COPPA)",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF1C1B1F)
                        )
                    }

                    Text(
                        text = "Semua prompt dan skenario yang dihasilkan oleh aplikasi ini menerapkan filter ramah anak:",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF49454F)
                    )

                    Text("✓ Bebas adegan menakutkan, kekerasan, atau jumpscare", fontSize = 12.sp, color = Color(0xFF1C1B1F))
                    Text("✓ Visual warna kontras ceria tanpa efek flashing berbahaya (anti-epilepsi)", fontSize = 12.sp, color = Color(0xFF1C1B1F))
                    Text("✓ Nilai edukasi positif: pengenalan warna, angka, tolong-menolong & empati", fontSize = 12.sp, color = Color(0xFF1C1B1F))
                }
            }
        }
    }
}
