package uk.ac.aber.dcs.cs31620.phrasepad.model

import uk.ac.aber.dcs.cs31620.phrasepad.R

// Helper class to retrieve the name of a locale
enum class LocaleFlagHelper(val localeCode: String, val flag: Int) {
    ZH("zh", R.drawable.zh),
    ES("es",R.drawable.es),
    EN("en", R.drawable.en),
    CY("cy", R.drawable.cy),
    HI("hi", R.drawable.ind),
    BN("bn", R.drawable.bn),
    PT("pt", R.drawable.pt),
    RU("ru", R.drawable.ru),
    JA("ja", R.drawable.jp),
    PA("pa", R.drawable.ind),
    MR("mr", R.drawable.ind),
    TE("te", R.drawable.ind),
    TR("tr", R.drawable.tr),
    KO("ko", R.drawable.sko),
    FR("fr", R.drawable.fr),
    DE("de", R.drawable.de),
    VI("vi", R.drawable.vi),
    TA("ta", R.drawable.ta),
    UR("ur", R.drawable.ur),
    IT("it", R.drawable.it);

    companion object {
        fun get(searchValue: String): LocaleFlagHelper = values().first { it.localeCode == searchValue }
    }
}