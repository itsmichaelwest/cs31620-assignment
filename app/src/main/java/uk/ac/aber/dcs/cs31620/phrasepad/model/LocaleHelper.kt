package uk.ac.aber.dcs.cs31620.phrasepad.model

import uk.ac.aber.dcs.cs31620.phrasepad.R

/**
 * Helper class to match up locales with assigned flags. This is also where new languages should be
 * defined. It's possible to retrieve these directly, but since we don't want to hardcode any values
 * in the app, it's better to use [get] or [getAll] to interact with this class.
 *
 * @param iso3Language A three-letter abbreviation of the locale's language, in ISO 639-3 format.
 * @param flag Reference to the drawable resource for the locale's flag.
 * @see [Language]
 */
enum class LocaleHelper(val iso3Language: String, val flag: Int) {
    AFRIKAANS("afr", R.drawable.za),
    ALBANIAN("sqi", R.drawable.al),
    AMHARIC("amh", R.drawable.et),
    ARABIC("ara", R.drawable.sa),
    ARMENIAN("hye", R.drawable.am),
    AZERBAIJANI("aze", R.drawable.az),
    BELARUSIAN("bel", R.drawable.by),
    BENGALI("ben", R.drawable.bd),
    BOSNIAN("bos", R.drawable.ba),
    BULGARIAN("bul", R.drawable.bg),
    BURMESE("mya", R.drawable.mm),
    CATALAN("cat", R.drawable.ad),
    CHICHEWA("nya", R.drawable.mw),
    CHINESE("zho", R.drawable.cn),
    CORSICAN("cos", R.drawable.fr),
    CROATIAN("hrv", R.drawable.hr),
    CZECH("ces", R.drawable.cz),
    DANISH("dan", R.drawable.dk),
    DUTCH("nld", R.drawable.nl),
    ENGLISH("eng", R.drawable.en),
    ESPERANTO("epo", R.drawable.eo),
    FINNISH("fin", R.drawable.fi),
    FRENCH("fra", R.drawable.fr),
    GEROGIAN("kat", R.drawable.ge),
    GERMAN("deu", R.drawable.de),
    GREEK("ell", R.drawable.gr),
    GUJARATI("guj", R.drawable.ind),
    HEBREW("heb", R.drawable.il),
    HINDI("hin", R.drawable.ind),
    HUNGARIAN("hun", R.drawable.hu),
    ICELANDIC("isl", R.drawable.isc),
    IRISH("gle", R.drawable.ie),
    ITALIAN("ita", R.drawable.it),
    JAPANESE("jpn", R.drawable.jp),
    KANNADA("kan", R.drawable.ind),
    KAZAKH("kaz", R.drawable.kz),
    KHMER("khm", R.drawable.kh),
    KINYARWANDA("kin", R.drawable.rw),
    KOREAN("kor", R.drawable.kr),
    KURDISH("kur", R.drawable.iq),
    KYRGYZ("kir", R.drawable.kg),
    LAO("lao", R.drawable.la),
    LATVIAN("lav", R.drawable.lv),
    LITHUANIAN("lit", R.drawable.lt),
    MACEDONIAN("mkd", R.drawable.mk),
    MALAGASY("mlg", R.drawable.mg),
    MALAY("msa", R.drawable.my),
    MALAYALAM("mal", R.drawable.ind),
    MALTESE("mlt", R.drawable.mt),
    MAORI("mri", R.drawable.nz),
    MARATHI("mar", R.drawable.ind),
    MONGOLIAN("mon", R.drawable.mn),
    NEPALI("nep", R.drawable.np),
    NORWEGIAN("nor", R.drawable.no),
    PASHTO("pus", R.drawable.af),
    PERSIAN("fas", R.drawable.ir),
    POLISH("pol", R.drawable.pl),
    PORTUGUESE("por", R.drawable.pt),
    PUNJABI("pan", R.drawable.ind),
    ROMANIAN("ron", R.drawable.ro),
    RUSSIAN("rus", R.drawable.ru),
    SAMOAN("smo", R.drawable.ws),
    SCOTS_GAELIC("gla", R.drawable.scot),
    SERBIAN("srp", R.drawable.rs),
    SESOTHO("sot", R.drawable.ls),
    SHONA("sna", R.drawable.zw),
    SINDHI("snd", R.drawable.pk),
    SINHALA("sin", R.drawable.lk),
    SLOVAK("slk", R.drawable.sk),
    SLOVENIAN("slv", R.drawable.si),
    SOMALI("som", R.drawable.so),
    SPANISH("spa", R.drawable.es),
    SWAHILI("swa", R.drawable.ke),
    SWEDISH("swe", R.drawable.se),
    TAJIK("tgk", R.drawable.tj),
    TAMIL("tam", R.drawable.lk),
    TELUGU("tel", R.drawable.ind),
    THAI("tha", R.drawable.th),
    TURKISH("tur", R.drawable.tr),
    UKRANIAN("ukr", R.drawable.ua),
    URDU("urd", R.drawable.pk),
    UYGHUR("uig", R.drawable.cn),
    UZBEK("uzb", R.drawable.uz),
    VIETNAMESE("vie", R.drawable.vi),
    WELSH("cym", R.drawable.cy),
    XHOSA("xho", R.drawable.za),
    YIDDISH("yid", R.drawable.yid_prop),
    ZULU("zul", R.drawable.za);

    companion object {
        fun get(searchValue: String): LocaleHelper =
            values().first { it.iso3Language == searchValue }

        fun getAll(): Array<LocaleHelper> = values()
    }
}