package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "creator_projects")
data class ProjectEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val category: String,
    val targetAge: String,
    val prompt: String,
    val videoUrl: String?,
    val viralScore: Int,
    val titlesJson: String, // comma or json separated
    val description: String,
    val hashtags: String, // comma or space separated
    val createdAt: Long = System.currentTimeMillis(),
    val status: String = "COMPLETED" // DRAFT, GENERATING, COMPLETED
)
