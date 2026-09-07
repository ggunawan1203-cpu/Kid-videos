package com.example.data.model

data class TrendReference(
    val id: String,
    val title: String,
    val category: String,
    val targetAge: String,
    val estimatedViews: String,
    val viralHook: String,
    val visualStyle: String,
    val audioTrend: String,
    val viralFactors: List<String>,
    val badgeColorHex: Long = 0xFF4F46E5
)

data class ContentAnalysis(
    val topic: String,
    val targetAgeGroup: String,
    val estimatedViralScore: Int, // 1 - 100
    val retentionHook: String,
    val emotionalTrigger: String,
    val visualRecommendations: List<String>,
    val audioSoundtrackIdea: String,
    val coppaSafetyCompliant: Boolean,
    val potentialReasons: String
)

data class VeoPromptSpec(
    val prompt: String,
    val style: String,
    val characterDescription: String,
    val cameraMovement: String,
    val lightingMood: String,
    val aspectRatio: String = "9:16",
    val resolution: String = "720p"
)

data class VeoGenerationResult(
    val operationName: String = "",
    val videoUrl: String? = null,
    val status: VeoStatus = VeoStatus.IDLE,
    val message: String = "",
    val generatedPrompt: String = "",
    val simulatedFrames: List<String> = emptyList()
)

enum class VeoStatus {
    IDLE,
    GENERATING_PROMPT,
    SUBMITTING_VEO,
    PROCESSING_VIDEO,
    COMPLETED,
    ERROR
}

data class MetadataPackage(
    val titles: List<String> = emptyList(),
    val description: String = "",
    val hashtags: List<String> = emptyList(),
    val pinnedComment: String = "",
    val bestPostingHours: String = ""
)

data class GenerationState(
    val isLoading: Boolean = false,
    val statusMessage: String = "",
    val currentStep: Int = 0, // 0: idle, 1: analyze, 2: prompt, 3: veo, 4: metadata
    val error: String? = null
)
