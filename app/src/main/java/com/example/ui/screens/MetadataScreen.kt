package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.MetadataPackage
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.KidsCreatorViewModel
import com.example.ui.viewmodel.UiState

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MetadataScreen(
    viewModel: KidsCreatorViewModel,
    uiState: UiState,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val metadata = uiState.metadataPackage ?: MetadataPackage(
        titles = listOf(
            "🎈 Wah Lucu Banget! Si Dinosaurus Belajar Warna Balon Ajaib 🦖✨ #Shorts",
            "Tebak Warna Mobil Kartun Lucu! Bisa Bantu Jawab? 🚗🌈 #Shorts",
            "Petualangan Hewan Imut: Si Kucing Mandi Busa Pelangi! 🐱🫧 #Shorts"
        ),
        description = "Halo adik-adik pintar dan ayah bunda tersayang! Yuk ikuti petualangan seru penuh warna hari ini. Video ini dirancang khusus untuk merangsang imajinasi dan pengenalan warna bagi si kecil dengan cara yang ceria dan aman! 🥰\n\nJangan lupa Like, Share, dan Subscribe agar tidak ketinggalan video edukasi seru berikutnya! ✨",
        hashtags = listOf(
            "#KidsShorts", "#AnimasiAnak", "#LaguAnak", "#BelajarWarna",
            "#KartunLucu", "#Shorts", "#KidsLearning", "#ToddlerFun",
            "#CeritaAnak", "#FYPKids", "#TrendingShorts"
        ),
        pinnedComment = "Warna apa yang paling disukai si kecil hari ini? Tulis di kolom komentar ya! 👇🎉",
        bestPostingHours = "15:30 - 18:30 WIB (Waktu prime time anak bersantai bersama orang tua)"
    )

    fun copyToClipboard(label: String, text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "$label berhasil disalin!", Toast.LENGTH_SHORT).show()
    }

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
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .background(Color(0xFFD0BCFF), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Tag,
                                contentDescription = null,
                                tint = Color(0xFF381E72)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Paket SEO & Metadata",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = Color(0xFF21005D)
                            )
                            Text(
                                text = "Judul Click-Worthy • Deskripsi • Hashtag",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF49454F)
                            )
                        }
                    }

                    Button(
                        onClick = {
                            val fullPackage = buildString {
                                appendLine("=== JUDUL UTAMA ===")
                                appendLine(metadata.titles.firstOrNull() ?: "")
                                appendLine()
                                appendLine("=== DESKRIPSI ===")
                                appendLine(metadata.description)
                                appendLine()
                                appendLine("=== HASHTAG ===")
                                appendLine(metadata.hashtags.joinToString(" "))
                                appendLine()
                                appendLine("=== PINNED COMMENT ===")
                                appendLine(metadata.pinnedComment)
                            }
                            copyToClipboard("Semua Metadata", fullPackage)
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6750A4),
                            contentColor = Color.White
                        ),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        modifier = Modifier.testTag("copy_all_metadata_button")
                    ) {
                        Icon(imageVector = Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Salin Semua", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Click-Worthy Titles Card
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.FormatQuote,
                            contentDescription = null,
                            tint = Color(0xFF6750A4)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "3 Rekomendasi Judul Memikat (High CTR)",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF1C1B1F)
                        )
                    }

                    metadata.titles.forEachIndexed { index, title ->
                        Surface(
                            color = Color(0xFFF3EDF7),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    modifier = Modifier.weight(1f),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Surface(
                                        color = Color(0xFF6750A4),
                                        shape = CircleShape,
                                        modifier = Modifier.size(22.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Text(
                                                text = "${index + 1}",
                                                color = Color.White,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = title,
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                        color = Color(0xFF1C1B1F)
                                    )
                                }

                                IconButton(
                                    onClick = { copyToClipboard("Judul", title) },
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ContentCopy,
                                        contentDescription = "Salin Judul",
                                        tint = Color(0xFF6750A4),
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // SEO Description Card
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Description,
                                contentDescription = null,
                                tint = Color(0xFF6750A4)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Deskripsi Video Ramah Algoritma",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = Color(0xFF1C1B1F)
                            )
                        }

                        IconButton(
                            onClick = { copyToClipboard("Deskripsi", metadata.description) },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = "Salin Deskripsi",
                                tint = Color(0xFF6750A4),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Surface(
                        color = Color(0xFFF3EDF7),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = metadata.description,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(12.dp),
                            color = Color(0xFF49454F)
                        )
                    }
                }
            }
        }

        // Hashtags Card
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
                                imageVector = Icons.Default.Tag,
                                contentDescription = null,
                                tint = Color(0xFF6750A4)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Hashtags Viral Anak (${metadata.hashtags.size})",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = Color(0xFF1C1B1F)
                            )
                        }

                        Button(
                            onClick = {
                                copyToClipboard("Semua Hashtag", metadata.hashtags.joinToString(" "))
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF6750A4),
                                contentColor = Color.White
                            ),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 3.dp)
                        ) {
                            Text("Salin Tag", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        metadata.hashtags.forEach { tag ->
                            Surface(
                                color = Color(0xFFF3EDF7),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.clickable { copyToClipboard("Hashtag $tag", tag) }
                            ) {
                                Text(
                                    text = tag,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF6750A4),
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Pinned Comment & Best Hours Card
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.PushPin,
                            contentDescription = null,
                            tint = Color(0xFF6750A4)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Komentar Sematan (Pinned Comment):",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF1C1B1F)
                        )
                    }

                    Text(
                        text = metadata.pinnedComment,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF49454F)
                    )

                    HorizontalDivider(color = Color(0xFFF3EDF7))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = null,
                            tint = Color(0xFF6750A4)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Jadwal Upload Terbaik: ${metadata.bestPostingHours}",
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                            color = Color(0xFF1C1B1F)
                        )
                    }
                }
            }
        }

        // Bottom Navigation Actions
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = { viewModel.navigateTo(AppScreen.VEO_STUDIO) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Kembali ke Studio")
                }

                Button(
                    onClick = { viewModel.navigateTo(AppScreen.PROJECT_LIBRARY) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Buka Koleksi Proyek")
                }
            }
        }
    }
}
