package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.sin

@Composable
fun VerticalVideoPreview916(
    title: String,
    promptSummary: String,
    isPlaying: Boolean,
    progress: Float,
    showSafeZone: Boolean,
    onTogglePlay: () -> Unit,
    onProgressChange: (Float) -> Unit,
    onToggleSafeZone: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "video_canvas_anim")
    val bounceOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 20f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bounce"
    )

    val wavePhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 6.28f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "wave"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Badges & Controls
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.AspectRatio,
                        contentDescription = "Rasio 9:16",
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "9:16 Vertikal (Veo 3)",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Safe Zone Overlay Toggle
            FilterChip(
                selected = showSafeZone,
                onClick = onToggleSafeZone,
                label = {
                    Text(
                        text = if (showSafeZone) "Safe Zone: ON" else "Safe Zone: OFF",
                        fontSize = 11.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.GridOn,
                        contentDescription = "Safe Zone Overlay",
                        modifier = Modifier.size(14.dp)
                    )
                },
                modifier = Modifier.testTag("safe_zone_toggle")
            )
        }

        // 9:16 Video Screen Box
        Box(
            modifier = Modifier
                .width(230.dp)
                .aspectRatio(9f / 16f)
                .shadow(16.dp, RoundedCornerShape(24.dp))
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFF0F172A))
                .border(4.dp, Color(0xFFD0BCFF), RoundedCornerShape(24.dp))
                .clickable { onTogglePlay() },
            contentAlignment = Alignment.Center
        ) {
            // Animated Canvas rendering rich colorful 9:16 kids video elements
            Canvas(modifier = Modifier.fillMaxSize()) {
                val canvasWidth = size.width
                val canvasHeight = size.height

                // Vibrant background gradient
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF3B82F6),
                            Color(0xFF8B5CF6),
                            Color(0xFFEC4899),
                            Color(0xFFF97316)
                        )
                    )
                )

                // Animated rainbow ground hills
                val hillPath = Path().apply {
                    moveTo(0f, canvasHeight * 0.72f)
                    for (x in 0..canvasWidth.toInt() step 10) {
                        val y = canvasHeight * 0.72f + sin((x / 50f) + wavePhase) * 12f
                        lineTo(x.toFloat(), y)
                    }
                    lineTo(canvasWidth, canvasHeight)
                    lineTo(0f, canvasHeight)
                    close()
                }
                drawPath(
                    path = hillPath,
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xFF10B981), Color(0xFF34D399), Color(0xFF059669))
                    )
                )

                // Sun & Clouds
                drawCircle(
                    color = Color(0xFFFDE047),
                    radius = 32f,
                    center = Offset(canvasWidth * 0.8f, canvasHeight * 0.15f)
                )

                // Cute Animated 3D Character (Dino / Friendly Monster)
                val charCenterX = canvasWidth * 0.5f
                val charCenterY = canvasHeight * 0.55f - (if (isPlaying) bounceOffset else 0f)

                // Character Shadow
                drawOval(
                    color = Color(0x33000000),
                    topLeft = Offset(charCenterX - 38f, canvasHeight * 0.72f),
                    size = Size(76f, 18f)
                )

                // Character Body (Bright Cyan/Teal)
                drawCircle(
                    color = Color(0xFF06B6D4),
                    radius = 48f,
                    center = Offset(charCenterX, charCenterY)
                )

                // Cute big eyes
                val eyeY = charCenterY - 10f
                drawCircle(color = Color.White, radius = 13f, center = Offset(charCenterX - 18f, eyeY))
                drawCircle(color = Color.White, radius = 13f, center = Offset(charCenterX + 18f, eyeY))
                // Pupils
                drawCircle(color = Color(0xFF0F172A), radius = 6f, center = Offset(charCenterX - 16f, eyeY))
                drawCircle(color = Color(0xFF0F172A), radius = 6f, center = Offset(charCenterX + 16f, eyeY))
                // Eye shines
                drawCircle(color = Color.White, radius = 2.5f, center = Offset(charCenterX - 17f, eyeY - 3f))
                drawCircle(color = Color.White, radius = 2.5f, center = Offset(charCenterX + 15f, eyeY - 3f))

                // Cheerful Smile
                drawArc(
                    color = Color(0xFFE11D48),
                    startAngle = 0f,
                    sweepAngle = 180f,
                    useCenter = true,
                    topLeft = Offset(charCenterX - 14f, charCenterY + 8f),
                    size = Size(28f, 20f)
                )

                // Cheeks (Blushing Pink)
                drawCircle(color = Color(0x88FF6B8B), radius = 8f, center = Offset(charCenterX - 28f, charCenterY + 8f))
                drawCircle(color = Color(0x88FF6B8B), radius = 8f, center = Offset(charCenterX + 28f, charCenterY + 8f))

                // Floating balloons
                val balloonColors = listOf(Color(0xFFEF4444), Color(0xFFF59E0B), Color(0xFF8B5CF6))
                balloonColors.forEachIndexed { i, col ->
                    val bx = canvasWidth * 0.25f + (i * 45f)
                    val by = canvasHeight * 0.32f + sin(wavePhase + i) * 10f
                    drawOval(
                        color = col,
                        topLeft = Offset(bx - 14f, by - 20f),
                        size = Size(28f, 36f)
                    )
                }
            }

            // Safe Zone Overlay (TikTok/Shorts UI boundaries)
            if (showSafeZone) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .border(1.5.dp, Color(0x88FFFFFF))
                ) {
                    // Top safe margin text
                    Text(
                        text = "Header Safe Zone",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 9.sp,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 8.dp)
                    )

                    // Right column: TikTok/Shorts action buttons simulation
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(end = 8.dp, bottom = 48.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color.Black.copy(alpha = 0.45f),
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.padding(6.dp)
                            )
                        }
                        Surface(
                            shape = CircleShape,
                            color = Color.Black.copy(alpha = 0.45f),
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Comment,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.padding(6.dp)
                            )
                        }
                        Surface(
                            shape = CircleShape,
                            color = Color.Black.copy(alpha = 0.45f),
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.padding(6.dp)
                            )
                        }
                    }

                    // Bottom caption safe zone
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .fillMaxWidth(0.78f)
                            .padding(start = 8.dp, bottom = 12.dp)
                    ) {
                        Text(
                            text = title.ifBlank { "@KidsCreator • #Shorts" },
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 2
                        )
                    }
                }
            }

            // Central Play/Pause button indicator
            if (!isPlaying) {
                Surface(
                    shape = CircleShape,
                    color = Color.Black.copy(alpha = 0.6f),
                    modifier = Modifier.size(54.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Play Video",
                        tint = Color.White,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }

            // VEO 3 ENGINE badge (from Vibrant Palette design)
            Surface(
                color = Color.White.copy(alpha = 0.2f),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.35f)),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "VEO 3",
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = "ENGINE",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 0.5.sp
                    )
                }
            }
        }

        // Playback Scrubber & Time
        Row(
            modifier = Modifier
                .width(240.dp)
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onTogglePlay,
                modifier = Modifier.size(36.dp).testTag("play_pause_button")
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = if (isPlaying) "Pause" else "Play",
                    tint = Color(0xFF6750A4)
                )
            }

            Slider(
                value = progress,
                onValueChange = onProgressChange,
                modifier = Modifier.weight(1f).testTag("video_scrubber"),
                colors = SliderDefaults.colors(
                    thumbColor = Color(0xFF6750A4),
                    activeTrackColor = Color(0xFFD0BCFF),
                    inactiveTrackColor = Color(0xFFE8DEF8)
                )
            )

            Text(
                text = "0:${(progress * 15).toInt().toString().padStart(2, '0')}/0:15",
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF49454F)
            )
        }
    }
}
