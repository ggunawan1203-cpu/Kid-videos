package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.ApiKeyManager
import com.example.data.local.KidsCreatorDatabase
import com.example.data.local.ProjectEntity
import com.example.data.model.*
import com.example.data.remote.GeminiClient
import com.example.repository.KidsCreatorRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

enum class AppScreen {
    HOME,
    TREND_ANALYSIS,
    VEO_STUDIO,
    METADATA_SEO,
    PROJECT_LIBRARY,
    SETTINGS
}

data class UiState(
    val currentScreen: AppScreen = AppScreen.HOME,
    val conceptInput: String = "Petualangan Mobil Balon Belajar Warna",
    val selectedAgeGroup: String = "1-4 Tahun (Balita)",
    val selectedStyle: String = "3D Pixar Cartoon",
    val selectedTrend: TrendReference? = null,
    val isRunningPipeline: Boolean = false,
    val currentStepNumber: Int = 0, // 0 to 4
    val currentStepMessage: String = "",
    val analysisResult: ContentAnalysis? = null,
    val veoPromptSpec: VeoPromptSpec? = null,
    val veoResult: VeoGenerationResult? = null,
    val metadataPackage: MetadataPackage? = null,
    val errorMessage: String? = null,
    val isKeyConfigured: Boolean = false,
    val isUsingCustomKey: Boolean = false,
    val apiKeyStatusMessage: String? = null,
    val showApiKeyDialog: Boolean = false,
    val isVideoPlaying: Boolean = true,
    val videoProgress: Float = 0.35f,
    val showSafeZoneOverlay: Boolean = true
)

class KidsCreatorViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: KidsCreatorRepository

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    val trendingReferences: List<TrendReference>

    val savedProjects: StateFlow<List<ProjectEntity>>

    init {
        val db = KidsCreatorDatabase.getDatabase(application)
        val apiKeyManager = ApiKeyManager(application)
        val geminiClient = GeminiClient()
        repository = KidsCreatorRepository(apiKeyManager, geminiClient, db.projectDao())

        trendingReferences = repository.getTrendingReferences()
        _uiState.update {
            it.copy(
                selectedTrend = trendingReferences.firstOrNull(),
                isKeyConfigured = repository.isKeyConfigured(),
                isUsingCustomKey = repository.isUsingCustomKey()
            )
        }

        savedProjects = repository.savedProjects
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    }

    fun navigateTo(screen: AppScreen) {
        _uiState.update { it.copy(currentScreen = screen, errorMessage = null) }
    }

    fun setConceptInput(concept: String) {
        _uiState.update { it.copy(conceptInput = concept) }
    }

    fun selectAgeGroup(ageGroup: String) {
        _uiState.update { it.copy(selectedAgeGroup = ageGroup) }
    }

    fun selectStyle(style: String) {
        _uiState.update { it.copy(selectedStyle = style) }
    }

    fun selectTrend(trend: TrendReference) {
        _uiState.update {
            it.copy(
                selectedTrend = trend,
                conceptInput = trend.title
            )
        }
    }

    fun toggleVideoPlay(play: Boolean? = null) {
        _uiState.update { it.copy(isVideoPlaying = play ?: !it.isVideoPlaying) }
    }

    fun setVideoProgress(progress: Float) {
        _uiState.update { it.copy(videoProgress = progress.coerceIn(0f, 1f)) }
    }

    fun toggleSafeZoneOverlay() {
        _uiState.update { it.copy(showSafeZoneOverlay = !it.showSafeZoneOverlay) }
    }

    fun showApiKeyDialog(show: Boolean) {
        _uiState.update { it.copy(showApiKeyDialog = show) }
    }

    fun saveApiKey(newKey: String) {
        repository.saveCustomApiKey(newKey)
        _uiState.update {
            it.copy(
                isKeyConfigured = repository.isKeyConfigured(),
                isUsingCustomKey = repository.isUsingCustomKey(),
                apiKeyStatusMessage = "API Key berhasil disimpan!",
                showApiKeyDialog = false
            )
        }
    }

    fun clearApiKey() {
        repository.clearCustomApiKey()
        _uiState.update {
            it.copy(
                isKeyConfigured = repository.isKeyConfigured(),
                isUsingCustomKey = repository.isUsingCustomKey(),
                apiKeyStatusMessage = "Kunci kustom dihapus."
            )
        }
    }

    fun testConnection(keyToTest: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(apiKeyStatusMessage = "Menguji koneksi ke Gemini API...") }
            val key = if (keyToTest.isBlank()) repository.getApiKey() else keyToTest
            val result = repository.testApiKeyConnection(key)
            result.onSuccess { msg ->
                _uiState.update {
                    it.copy(apiKeyStatusMessage = "Koneksi Berhasil: $msg")
                }
            }.onFailure { err ->
                _uiState.update {
                    it.copy(apiKeyStatusMessage = "Gagal terhubung: ${err.localizedMessage}")
                }
            }
        }
    }

    /**
     * Automated Full End-to-End Pipeline:
     * 1. Analisis Konten & Penonton Terbanyak
     * 2. Veo 3 Video Prompt Crafting (Rasio 9:16)
     * 3. Eksekusi Render Video Veo 3
     * 4. Pembuatan Judul, Deskripsi, dan Hashtag
     * 5. Simpan Proyek Otomatis ke Database
     */
    fun runFullPipeline() {
        val currentState = _uiState.value
        val concept = currentState.conceptInput.ifBlank { "Petualangan Kartun Warna Lucu" }
        val targetAge = currentState.selectedAgeGroup
        val trendRef = currentState.selectedTrend?.title ?: "Animasi Belajar Warna Ceria"
        val style = currentState.selectedStyle

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isRunningPipeline = true,
                    currentStepNumber = 1,
                    currentStepMessage = "Langkah 1/4: Menganalisis potensi viral & retensi audiens anak...",
                    errorMessage = null
                )
            }

            // Step 1: Content Analysis
            val analysisRes = repository.analyzeContent(concept, targetAge, trendRef)
            val analysis = analysisRes.getOrNull()
            if (analysis != null) {
                _uiState.update { it.copy(analysisResult = analysis) }
            }

            // Step 2: Veo 3 Prompt Generation
            _uiState.update {
                it.copy(
                    currentStepNumber = 2,
                    currentStepMessage = "Langkah 2/4: Merancang prompt video AI Veo 3 rasio vertikal 9:16..."
                )
            }
            val promptRes = repository.generateVeoPrompt(concept, targetAge, style)
            val promptSpec = promptRes.getOrNull()
            if (promptSpec != null) {
                _uiState.update { it.copy(veoPromptSpec = promptSpec) }
            }

            // Step 3: Trigger Veo 3 Video Generation
            _uiState.update {
                it.copy(
                    currentStepNumber = 3,
                    currentStepMessage = "Langkah 3/4: Memproses rendering video Veo 3 format vertikal 9:16..."
                )
            }
            val videoPrompt = promptSpec?.prompt ?: "A vibrant 3D animated kids video, 9:16 vertical ratio."
            val veoRes = repository.generateVeoVideo(videoPrompt, "9:16", "720p")
            val veoResult = veoRes.getOrNull()
            if (veoResult != null) {
                _uiState.update { it.copy(veoResult = veoResult) }
            }

            // Step 4: Generate Metadata (Titles, Description, Hashtags)
            _uiState.update {
                it.copy(
                    currentStepNumber = 4,
                    currentStepMessage = "Langkah 4/4: Membuat 3 pilihan judul memikat, deskripsi SEO, dan hashtag..."
                )
            }
            val metaRes = repository.generateMetadata(concept, targetAge, analysis?.potentialReasons ?: concept)
            val metadata = metaRes.getOrNull()
            if (metadata != null) {
                _uiState.update { it.copy(metadataPackage = metadata) }
            }

            // Auto-save project to local Room database
            try {
                repository.saveProject(
                    title = metadata?.titles?.firstOrNull() ?: concept,
                    category = currentState.selectedTrend?.category ?: "Edukasi & Hiburan Anak",
                    targetAge = targetAge,
                    prompt = videoPrompt,
                    videoUrl = veoResult?.videoUrl,
                    viralScore = analysis?.estimatedViralScore ?: 93,
                    titles = metadata?.titles ?: listOf(concept),
                    description = metadata?.description ?: "",
                    hashtags = metadata?.hashtags ?: listOf("#KidsShorts")
                )
            } catch (_: Exception) {}

            _uiState.update {
                it.copy(
                    isRunningPipeline = false,
                    currentStepNumber = 0,
                    currentStepMessage = "Selesai! Video 9:16, metadata, dan analisis siap digunakan.",
                    currentScreen = AppScreen.VEO_STUDIO
                )
            }
        }
    }

    fun deleteProject(id: Long) {
        viewModelScope.launch {
            repository.deleteProject(id)
        }
    }

    fun loadProjectIntoStudio(project: ProjectEntity) {
        _uiState.update {
            it.copy(
                conceptInput = project.title,
                selectedAgeGroup = project.targetAge,
                currentScreen = AppScreen.VEO_STUDIO
            )
        }
    }
}
