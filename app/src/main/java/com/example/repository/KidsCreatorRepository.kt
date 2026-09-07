package com.example.repository

import com.example.data.local.ApiKeyManager
import com.example.data.local.ProjectDao
import com.example.data.local.ProjectEntity
import com.example.data.model.*
import com.example.data.remote.GeminiClient
import kotlinx.coroutines.flow.Flow
import org.json.JSONArray

class KidsCreatorRepository(
    private val apiKeyManager: ApiKeyManager,
    private val geminiClient: GeminiClient,
    private val projectDao: ProjectDao
) {

    val savedProjects: Flow<List<ProjectEntity>> = projectDao.getAllProjects()

    fun getApiKey(): String = apiKeyManager.getApiKey()
    fun getCustomApiKey(): String = apiKeyManager.getCustomApiKey()
    fun saveCustomApiKey(key: String) = apiKeyManager.saveApiKey(key)
    fun isKeyConfigured(): Boolean = apiKeyManager.hasValidKey()
    fun isUsingCustomKey(): Boolean = apiKeyManager.isUsingCustomKey()
    fun clearCustomApiKey() = apiKeyManager.clearCustomKey()

    suspend fun testApiKeyConnection(key: String): Result<String> {
        return geminiClient.testConnection(key)
    }

    /**
     * Default trending references for kids content (viral benchmarks with metrics)
     */
    fun getTrendingReferences(): List<TrendReference> {
        return listOf(
            TrendReference(
                id = "trend_1",
                title = "Animasi Belajar Warna Mobil & Balon Ajaib",
                category = "Edukasi Balita (1-4 th)",
                targetAge = "1-4 Tahun",
                estimatedViews = "15M+ Views/Bulan",
                viralHook = "Mobil tabrak balon warna, muncul kejutan buah berbunyi 'Pop!'",
                visualStyle = "3D Pixar Bright Glossy",
                audioTrend = "Lagu riang xylophone + sound effect kartun 'Boing!'",
                viralFactors = listOf("Visual kontras tinggi", "Pola repetisi warna", "Sensory audio", "Looping 15 detik"),
                badgeColorHex = 0xFFEF4444
            ),
            TrendReference(
                id = "trend_2",
                title = "Petualangan Dino Penyelamat Hewan Lucu",
                category = "Cerita Hewan & Petualangan",
                targetAge = "3-6 Tahun",
                estimatedViews = "8.2M+ Views/Bulan",
                viralHook = "Anak kucing terjebak di pohon tinggi, T-Rex mini datang bantu",
                visualStyle = "Cute Claymation / Stop-Motion 3D",
                audioTrend = "Musik petualangan riang tempo cepat",
                viralFactors = listOf("Empati tolong menolong", "Karakter hewan bermata bulat", "Aksi penyelamatan dramatis santai"),
                badgeColorHex = 0xFF10B981
            ),
            TrendReference(
                id = "trend_3",
                title = "Tebak Bayangan Hewan Ajaib!",
                category = "Kuis Interaktif Anak",
                targetAge = "4-8 Tahun",
                estimatedViews = "12M+ Views/Bulan",
                viralHook = "Siluet misterius bergerak cepat: 'Siapa hewan ini? 3, 2, 1...'",
                visualStyle = "Vibrant 2D High Contrast Anime",
                audioTrend = "Detak jam tegang lucu 'Tik-Tok-Tik-Tok' lalu 'Tadaaa!'",
                viralFactors = listOf("Tingkat interaksi tinggi (anak ikut menjawab)", "Durasi retensi 100%", "FOMO rasa ingin tahu"),
                badgeColorHex = 0xFFF59E0B
            ),
            TrendReference(
                id = "trend_4",
                title = "Tari Buah & Sayur Ceria (Sensory Dance)",
                category = "Lagu & Gerak Ceria",
                targetAge = "1-5 Tahun",
                estimatedViews = "22M+ Views/Bulan",
                viralHook = "Pisang dan Semangka melompat goyang kaki lucu",
                visualStyle = "Soft Pastel 3D Cartoon",
                audioTrend = "Catchy Rhyme beat 130 BPM",
                viralFactors = listOf("Musik mudah dihafal", "Koreografi sederhana balita", "Warna buah menggugah selera"),
                badgeColorHex = 0xFF8B5CF6
            ),
            TrendReference(
                id = "trend_5",
                title = "Eksperimen Slime & Pelangi di Dapur Ajaib",
                category = "Fun Science & Craft Anak",
                targetAge = "5-9 Tahun",
                estimatedViews = "6.5M+ Views/Bulan",
                viralHook = "Campur 3 tetes ramuan warna, keluar busa pelangi raksasa",
                visualStyle = "Hyper-saturated 3D Miniature",
                audioTrend = "Sound ASMR lembut desis busa + tawa anak",
                viralFactors = listOf("Visual satisfaction ASMR", "Rasa takjub sains", "Cocok untuk ditonton bersama ibu"),
                badgeColorHex = 0xFFEC4899
            ),
            TrendReference(
                id = "trend_6",
                title = "Dongeng Bintang Tidur: Bulan Berselimut Awan",
                category = "Cerita Pengantar Tidur (Calm)",
                targetAge = "2-6 Tahun",
                estimatedViews = "9.8M+ Views/Bulan",
                viralHook = "Bintang kecil menguap lalu selimutan awan kapas putih",
                visualStyle = "Dreamy Glow Pastel & Stars",
                audioTrend = "Lullaby kotak musik lembut tempo pelan 70 BPM",
                viralFactors = listOf("Menenangkan anak rewel", "Banyak diputar orang tua malam hari", "Watch time panjang"),
                badgeColorHex = 0xFF3B82F6
            )
        )
    }

    /**
     * Step 1: Analyze kids video potential
     */
    suspend fun analyzeContent(
        concept: String,
        targetAge: String,
        referenceTitle: String
    ): Result<ContentAnalysis> {
        val apiKey = apiKeyManager.getApiKey()
        if (apiKey.isBlank()) {
            // Local fallback analysis if no key yet
            return Result.success(
                ContentAnalysis(
                    topic = concept,
                    targetAgeGroup = targetAge,
                    estimatedViralScore = 94,
                    retentionHook = "Hook 3 detik: Suara letupan balon ceria disertai karakter ekspresif menyapa anak.",
                    emotionalTrigger = "Kegembiraan instan dan visual warna-warni kontras tinggi yang menstimulasi visual anak.",
                    visualRecommendations = listOf(
                        "Gunakan pencahayaan cerah tanpa bayangan gelap",
                        "Ukuran mata karakter minimal 25% dari wajah untuk kontak mata ramah",
                        "Gerakan vertikal dinamis cocok untuk layar HP rasio 9:16"
                    ),
                    audioSoundtrackIdea = "Musik xylophone tempo 120 BPM dengan sound effect kartun 'Boing' dan 'Pop!'",
                    coppaSafetyCompliant = true,
                    potentialReasons = "Format edukasi repetitif sangat digemari balita dan sering ditonton berulang kali (high replay value)."
                )
            )
        }
        return geminiClient.analyzeKidsContent(apiKey, concept, targetAge, referenceTitle)
    }

    /**
     * Step 2: Generate Veo 3 Video Prompt
     */
    suspend fun generateVeoPrompt(
        concept: String,
        targetAge: String,
        visualStyle: String
    ): Result<VeoPromptSpec> {
        val apiKey = apiKeyManager.getApiKey()
        if (apiKey.isBlank()) {
            return Result.success(
                VeoPromptSpec(
                    prompt = "A high-retention 3D Pixar animated kids video in vertical 9:16 aspect ratio. Vivid primary colors, sunny bright illumination. A friendly cheerful cartoon character exploring an enchanted colorful playground with bouncing jelly fruits. Playful dynamic vertical camera push-in, expressive big eyes smiling, buttery smooth 60fps animation, kid-safe, wholesome.",
                    style = visualStyle,
                    characterDescription = "Karakter kartun ramah dengan mata bulat besar dan ekspresi gembira",
                    cameraMovement = "Smooth vertical push-in tracking shot, mobile vertical framing",
                    lightingMood = "Cahaya studio hangat ceria bertabur kilau bintang",
                    aspectRatio = "9:16",
                    resolution = "720p"
                )
            )
        }
        return geminiClient.generateVeoPrompt(apiKey, concept, targetAge, visualStyle)
    }

    /**
     * Step 3: Trigger Veo 3 Generation
     */
    suspend fun generateVeoVideo(
        prompt: String,
        aspectRatio: String = "9:16",
        resolution: String = "720p"
    ): Result<VeoGenerationResult> {
        val apiKey = apiKeyManager.getApiKey()
        if (apiKey.isBlank()) {
            return Result.success(
                VeoGenerationResult(
                    operationName = "demo_op_veo3",
                    videoUrl = null,
                    status = VeoStatus.COMPLETED,
                    message = "Simulasi Studio Veo 3: Prompt 9:16 telah dioptimalkan untuk render video!",
                    generatedPrompt = prompt
                )
            )
        }
        return geminiClient.triggerVeoGeneration(apiKey, prompt, aspectRatio, resolution)
    }

    /**
     * Step 4: Generate Titles, Description, and Hashtags
     */
    suspend fun generateMetadata(
        topic: String,
        targetAge: String,
        summary: String
    ): Result<MetadataPackage> {
        val apiKey = apiKeyManager.getApiKey()
        if (apiKey.isBlank()) {
            return Result.success(
                MetadataPackage(
                    titles = listOf(
                        "🎈 Wah Lucu Banget! Si Dinosaurus Belajar Warna Balon Ajaib 🦖✨ #Shorts",
                        "Tebak Warna Mobil Kartun Lucu! Bisa Bantu Jawab? 🚗🌈 #Shorts",
                        "Petualangan Hewan Imut: Si Kucing Mandi Busa Pelangi! 🐱🫧 #Shorts"
                    ),
                    description = "Halo adik-adik pintar dan ayah bunda tersayang! Yuk ikuti petualangan seru penuh warna hari ini. Video ini dirancang khusus untuk merangsang imajinasi dan pengenalan warna bagi si kecil dengan cara yang ceria dan aman! 🥰 Jangan lupa Like, Share, dan Subscribe ya!",
                    hashtags = listOf(
                        "#KidsShorts", "#AnimasiAnak", "#LaguAnak", "#BelajarWarna",
                        "#KartunLucu", "#Shorts", "#KidsLearning", "#ToddlerFun",
                        "#CeritaAnak", "#FYPKids", "#TrendingShorts"
                    ),
                    pinnedComment = "Warna apa yang paling disukai si kecil hari ini? Tulis di kolom komentar ya! 👇🎉",
                    bestPostingHours = "15:30 - 18:30 WIB (Waktu bersantai anak setelah mandi sore bersama orang tua)"
                )
            )
        }
        return geminiClient.generateMetadata(apiKey, topic, targetAge, summary)
    }

    /**
     * Save generated creation to Room database
     */
    suspend fun saveProject(
        title: String,
        category: String,
        targetAge: String,
        prompt: String,
        videoUrl: String?,
        viralScore: Int,
        titles: List<String>,
        description: String,
        hashtags: List<String>
    ): Long {
        val titlesArray = JSONArray()
        titles.forEach { titlesArray.put(it) }

        val entity = ProjectEntity(
            title = title,
            category = category,
            targetAge = targetAge,
            prompt = prompt,
            videoUrl = videoUrl,
            viralScore = viralScore,
            titlesJson = titlesArray.toString(),
            description = description,
            hashtags = hashtags.joinToString(" "),
            status = "COMPLETED"
        )
        return projectDao.insertProject(entity)
    }

    suspend fun deleteProject(id: Long) {
        projectDao.deleteById(id)
    }
}
