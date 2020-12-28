package uk.ac.aber.dcs.cs31620.phrasepad.model

// Helper class to retrieve the name of a locale
enum class Locales(val localeCode: String, val localeName: String, val localeNameEnglish: String) {
    ZH("zh", "官话", "Mandarin Chinese"),
    ES("es", "Español", "Spanish"),
    EN("en", "English", "English"),
    CY("cy", "Cymraeg", "Welsh"),
    HI("hi", "हिन्दी", "Hindi"),
    BN("bn", "বাংলা", "Bengali"),
    PT("pt", "Português", "Portuguese"),
    RU("ru", "Pусский", "Russian"),
    JP("jp", "日本語", "Japanese"),
    PA("pa", "ਪੰਜਾਬੀ", "Punjabi"),
    MR("mr", "मराठी", "Marathi"),
    TE("te", "తెలుగు", "Telugu"),
    TR("tr", "Türkçe", "Turkish"),
    KO("ko", "한국어", "Korean"),
    FR("fr", "Français", "French"),
    DE("de", "Deutsch", "German"),
    VI("vi", "Tiếng Việt", "Vietnamese"),
    TA("ta", "தமிழ்", "Tamil"),
    UR("ur", "اُردُو", "Urdu"),
    IT("it", "Italiano", "Italian");

    companion object {
        fun get(searchValue: String): Locales = values().first { it.localeCode == searchValue }
    }
}