package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.VeoPromptSpec
import com.example.ui.components.VerticalVideoPreview916
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.KidsCreatorViewModel
import com.example.ui.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VeoStudioScreen(
    viewModel: KidsCreatorViewModel,
    uiState: UiState,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val promptSpec = uiState.veoPromptSpec ?: VeoPromptSpec(
        prompt = "A high-retention 3D Pixar animated kids video in vertical 9:16 aspect ratio. Vivid saturated primary colors, sunny bright illumination. A friendly cheerful red cartoon car driving across a candy road, popping giant colorful balloons. Joyful smiling face with big expressive eyes, camera pushes smoothly forward in vertical framing, 60fps fluid motion, wholesome, child-safe.",
        style = uiState.selectedStyle,
        characterDescription = "Mobil kartun merah dengan mata besar bulat dan senyum lebar",
        cameraMovement = "Vertical mobile dynamic push-in dolly shot",
        lightingMood = "Studio animasi cerah bertabur kilau bintang",
        aspectRatio = "9:16",
        resolution = "720p"
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 80.dp, top = 8.dp)
    ) {
        // Studio Header
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
                                imageVector = Icons.Default.Videocam,
                                contentDescription = null,
                                tint = Color(0xFF381E72)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Veo 3 Video Studio",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = Color(0xFF21005D)
                            )
                            Text(
                                text = "Rasio 9:16 • Veo 3 Fast Preview",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF49454F)
                            )
                        }
                    }

                    Surface(
                        color = Color(0xFF22C55E),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "9:16 Ready",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }
            }
        }

        // 9:16 Vertical Video Preview Simulator
        item {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFCAC4D0)),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                VerticalVideoPreview916(
                    title = uiState.metadataPackage?.titles?.firstOrNull() ?: uiState.conceptInput,
                    promptSummary = promptSpec.characterDescription,
                    isPlaying = uiState.isVideoPlaying,
                    progress = uiState.videoProgress,
                    showSafeZone = uiState.showSafeZoneOverlay,
                    onTogglePlay = { viewModel.toggleVideoPlay() },
                    onProgressChange = { viewModel.setVideoProgress(it) },
                    onToggleSafeZone = { viewModel.toggleSafeZoneOverlay() }
                )
            }
        }

        // Veo 3 AI Prompt Detail Card
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
                                imageVector = Icons.Default.Code,
                                contentDescription = null,
                                tint = Color(0xFF6750A4)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Prompt AI Veo 3 (9:16)",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = Color(0xFF1C1B1F)
                            )
                        }

                        IconButton(
                            onClick = {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText("Veo 3 Prompt", promptSpec.prompt)
                                clipboard.setPrimaryClip(clip)
                                Toast.makeText(context, "Prompt Veo 3 berhasil disalin!", Toast.LENGTH_SHORT).show()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = "Salin Prompt",
                                tint = Color(0xFF6750A4)
                            )
                        }
                    }

                    // Prompt text container
                    Surface(
                        color = Color(0xFFF3EDF7),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = promptSpec.prompt,
                            style = MaterialTheme.typography.bodySmall,
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                            modifier = Modifier.padding(12.dp),
                            color = Color(0xFF49454F)
                        )
                    }

                    // Specs badges
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text("Aspek Rasio", fontSize = 10.sp, color = MaterialTheme.colorScheme.onPrimaryContainer)
                                Text("9:16 Vertikal", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                        }

                        Surface(
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text("Resolusi", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSecondaryContainer)
                                Text("720p HD", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                        }

                        Surface(
                            color = MaterialTheme.colorScheme.tertiaryContainer,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text("Gaya Visual", fontSize = 10.sp, color = MaterialTheme.colorScheme.onTertiaryContainer)
                                Text(promptSpec.style, fontWeight = FontWeight.Bold, fontSize = 12.sp, maxLines = 1)
                            }
                        }
                    }
                }
            }
        }

        // Action Buttons: Next to Metadata & SEO
        item {
            Button(
                onClick = { viewModel.navigateTo(AppScreen.METADATA_SEO) },
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("go_to_metadata_button")
            ) {
                Text(
                    text = "Lihat Judul, Deskripsi & Hashtag Viral",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
            }
        }
    }
}
