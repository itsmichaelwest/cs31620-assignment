package uk.ac.aber.dcs.cs31620.phrasepad.ui;

import android.graphics.drawable.Drawable
import uk.ac.aber.dcs.cs31620.phrasepad.R;

enum class FlagHelper(val localeCode: String, val flag: Int) {
    ZH("zh", R.drawable.zh),
    ES("es", R.drawable.zh),
    EN("en", R.drawable.zh),
    CY("cy", R.drawable.cy),
    HI("hi", R.drawable.zh),
    BN("bn", R.drawable.zh),
    PT("pt", R.drawable.zh),
    RU("ru", R.drawable.zh),
    JP("jp", R.drawable.zh),
    PA("pa", R.drawable.zh),
    MR("mr", R.drawable.zh),
    TE("te", R.drawable.zh),
    TR("tr", R.drawable.zh),
    KO("ko", R.drawable.zh),
    FR("fr", R.drawable.zh),
    DE("de", R.drawable.zh),
    VI("vi", R.drawable.zh),
    TA("ta", R.drawable.zh),
    UR("ur", R.drawable.zh),
    IT("it", R.drawable.it);

    companion object {
        fun get(searchValue: String): FlagHelper = values().first { it.localeCode == searchValue }
    }
}